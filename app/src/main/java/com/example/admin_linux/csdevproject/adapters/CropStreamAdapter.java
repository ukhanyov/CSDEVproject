package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.data.models.CropStreamMessage;
import com.example.admin_linux.csdevproject.data.models.TemplateItemModelBase;
import com.example.admin_linux.csdevproject.utils.CircleTransform;
import com.example.admin_linux.csdevproject.utils.ColorPicker;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.example.admin_linux.csdevproject.utils.DateHelper;
import com.example.admin_linux.csdevproject.utils.RoundCorners;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CropStreamAdapter extends RecyclerView.Adapter<CropStreamAdapter.CorpStreamViewHolder> {

    private List<CropStreamMessage> mList;
    private Context mContext;
    private CropStreamClickListener mListener;

    public CropStreamAdapter(List<CropStreamMessage> list, CropStreamClickListener listener, Context context) {
        this.mList = new ArrayList<>();
        this.mList = list;
        mListener = listener;
        mContext = context;
    }

    public void addItem(CropStreamMessage message) {
        if (this.mList == null) {
            this.mList = new ArrayList<>();
        }
        this.mList.add(message);
    }

    public void removeAllItems() {
        this.mList = null;
    }

    @NonNull
    @Override
    public CorpStreamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_crop_stream, viewGroup, false);

        return new CorpStreamViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CorpStreamViewHolder holder, int i) {
        if (mList != null) {
            CropStreamMessage current = mList.get(i);

            // Bind views
            // Possible roots |1|: organization -> "ConversationId"(null) -> "InvolvedPersons"(null) -> you
            // Possible roots |2|: organization -> "ConversationId" -> "InvolvedPersons" -> list everyone but you
            // Possible roots |3|: organization(null) -> "Person" -> "ConversationId"(null) -> "InvolvedPersons"(null) -> you
            // Possible roots |4|: organization(null) -> "Person" -> "ConversationId" -> "InvolvedPersons" -> list everyone but you

            // Sub root |5| universal for all roots

            // Sub root |6|: FeedEvents -> CatalogEntryId(not null) -> CatalogEntries -> CatalogEntryInDetailsModel ->
            // FormTemplateModel -> FormTemplateItemModelBase(all items) -> ItemType(add views according to this type)
            // universal for all roots

            // Sub root |7|: FeedEvents -> CatalogEntryId(null) -> CardRenderDataModel -> CatalogEntries -> CatalogEntryInDetailsModel ->
            // FormTemplateModel -> FormTemplateItemModelBase(all items) -> ItemType(add views according to this type)
            // universal for all roots

            if (current.isFromOrganization()) {
                if (current.getInvolvedPersonsNames().equals("you")) {
                    bindViewsRootOne(current, holder); // root |1|
                } else {
                    bindViewsRootTwo(current, holder); // root |2|
                }
            } else {
                if (current.getInvolvedPersonsNames().equals("you")) {
                    bindViewsRootThree(current, holder); // root |3|
                } else {
                    bindViewsRootFour(current, holder);  // root |4|
                }
            }

            // Sub root |5|
            bindWebView(current, holder);

            // Sub root |6| & |7|
            // TODO: make max height of card 1.3 of width
            // TODO: figure out what to do with radar
            bindCatalogEntry(holder, current);

        } else {
            throw new IllegalArgumentException("Some error with binding data for CorpStream recycler view");
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // root |1|
    private void bindViewsRootOne(CropStreamMessage current, CorpStreamViewHolder holder) {

        // Bind profile picture
        bindImage(holder, current.getProfileName(), current.getProfilePicture());

        // Bind name
        bindName(holder, current.getProfileName(), null);

        // Bind involved persons
        holder.tvInvolvedPersons.setText(current.getInvolvedPersonsNames());

        // Bind message text
        bindMessageText(holder, current.getMessageText());

        // Bind time
        holder.tvMessageTime.setText(DateHelper.normalizeDate(current.getMessageTime()));

        // Bind message picture
        //bindMessagePicture(holder, current.getMessagePicture());

        // Bind message order (is it first)
        bindMessageOrder(holder, current.getConversationFirstMessage(), current.getConversationChat());

        // Bind bottom views (Reply/Start/View Message)
        //bindStartReplyViewMessageViews(holder, current.getFeedType());
        bindStartReplyViewMessageViews(holder, current.getConversationChat());

    }

    // root |2|
    private void bindViewsRootTwo(CropStreamMessage current, CorpStreamViewHolder holder) {

        bindImageMultiple(holder,
                current.getCombineImageNameFirst(),
                current.getCombineImageNameSecond(),
                current.getCombineImageUrlFirst(),
                current.getCombineImageUrlSecond());

        // Bind name
        bindName(holder, current.getProfileName(), null);

        // Bind involved persons
        holder.tvInvolvedPersons.setText(current.getInvolvedPersonsNames());

        // Bind message text
        bindMessageText(holder, current.getMessageText());

        // Bind time
        holder.tvMessageTime.setText(DateHelper.normalizeDate(current.getMessageTime()));

        // Bind message picture
        //bindMessagePicture(holder, current.getMessagePicture());

        // Bind message order (is it first)
        bindMessageOrder(holder, current.getConversationFirstMessage(), current.getConversationChat());

        // Bind bottom views (Reply/Start/View Message)
        //bindStartReplyViewMessageViews(holder, current.getFeedType());
        bindStartReplyViewMessageViews(holder, current.getConversationChat());

    }

    // root |3|
    private void bindViewsRootThree(CropStreamMessage current, CorpStreamViewHolder holder) {

        // Bind profile picture
        bindImage(holder, current.getProfileName(), current.getProfilePicture());

        // Bind name
        bindName(holder, current.getProfileName(), current.getProfileCorpName());

        // Bind involved persons
        holder.tvInvolvedPersons.setText(current.getInvolvedPersonsNames());

        // Bind message text
        bindMessageText(holder, current.getMessageText());

        // Bind time
        holder.tvMessageTime.setText(DateHelper.normalizeDate(current.getMessageTime()));

        // Bind message picture
        //bindMessagePicture(holder, current.getMessagePicture());

        // Bind message order (is it first)
        bindMessageOrder(holder, current.getConversationFirstMessage(), current.getConversationChat());

        // Bind bottom views (Reply/Start/View Message)
        //bindStartReplyViewMessageViews(holder, current.getFeedType());
        bindStartReplyViewMessageViews(holder, current.getConversationChat());
    }

    // root |4|
    private void bindViewsRootFour(CropStreamMessage current, CorpStreamViewHolder holder) {

        bindImageMultiple(holder,
                current.getCombineImageNameFirst(),
                current.getCombineImageNameSecond(),
                current.getCombineImageUrlFirst(),
                current.getCombineImageUrlSecond());

        // Bind name
        bindName(holder, current.getProfileName(), current.getProfileCorpName());

        // Bind involved persons
        holder.tvInvolvedPersons.setText(current.getInvolvedPersonsNames());

        // Bind message text
        bindMessageText(holder, current.getMessageText());

        // Bind time
        holder.tvMessageTime.setText(DateHelper.normalizeDate(current.getMessageTime()));

        // Bind message picture
        //bindMessagePicture(holder, current.getMessagePicture());

        // Bind message order (is it first)
        bindMessageOrder(holder, current.getConversationFirstMessage(), current.getConversationChat());

        // Bind bottom views (Reply/Start/View Message)
        //bindStartReplyViewMessageViews(holder, current.getFeedType());
        bindStartReplyViewMessageViews(holder, current.getConversationChat());

    }

    private void bindImage(CorpStreamViewHolder holder, String name, String urlOne) {

        if (urlOne != null && !urlOne.equals("")) {
            Picasso.get().load(urlOne).fit().centerInside()
                    .placeholder(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_profile_default)))
                    .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                    .transform(new RoundCorners(dpToPx(8), dpToPx(0)))
                    .into(holder.ivProfilePicture);
        } else {
            holder.ivProfilePicture.setImageDrawable(makeCircleWithALatter(name));
        }
        holder.ivProfilePictureMashTop.setVisibility(View.GONE);
        holder.ivProfilePictureMashBottom.setVisibility(View.GONE);
        holder.ivProfilePicture.setVisibility(View.VISIBLE);
    }

    private void bindImageMultiple(CorpStreamViewHolder holder, String nameOne, String nameTwo, String urlOne, String urlTwo) {

        // TODO : some bug with missing name
        // TODO : some weird bug with colors

        if (urlOne != null) {
            Picasso.get().load(urlOne).fit().centerInside()
                    .placeholder(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_profile_default)))
                    .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                    .transform(new CircleTransform())
                    .into(holder.ivProfilePictureMashBottom);
        } else {
            holder.ivProfilePictureMashBottom.setImageDrawable(makeCircleWithALatter(nameOne));
            holder.ivProfilePictureMashTop.setAdjustViewBounds(true);
        }

        if (urlTwo != null) {
            Picasso.get().load(urlTwo).fit().centerInside()
                    .placeholder(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_profile_default)))
                    .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                    .transform(new CircleTransform())
                    .into(holder.ivProfilePictureMashTop);
        } else {
            holder.ivProfilePictureMashTop.setImageDrawable(makeCircleWithALatter(nameTwo));
            holder.ivProfilePictureMashTop.setAdjustViewBounds(true);
        }

        holder.ivProfilePictureMashTop.setVisibility(View.VISIBLE);
        holder.ivProfilePictureMashBottom.setVisibility(View.VISIBLE);
        holder.ivProfilePicture.setVisibility(View.GONE);
    }

    private void bindName(CorpStreamViewHolder holder, String nameOne, String nameTwo) {

        if (nameTwo == null) {
            // Bind only organization name
            holder.tvProfileFirst.setText(nameOne);
            holder.tvProfileSecond.setVisibility(View.GONE);
        } else {
            holder.tvProfileFirst.setText(nameOne);
            if (!nameTwo.equals("")) {
                holder.tvProfileSecond.setVisibility(View.VISIBLE);
                holder.tvProfileSecond.setText(nameTwo);
            } else {
                holder.tvProfileSecond.setVisibility(View.GONE);
            }
        }
    }

    private void bindMessageText(CorpStreamViewHolder holder, String text) {
        if (text != null) {
            holder.tvMessageText.setVisibility(View.VISIBLE);
            holder.tvMessageText.setText(text);
        } else {
            holder.tvMessageText.setVisibility(View.GONE);
        }
    }

//    private void bindMessagePicture(CorpStreamViewHolder holder, String pictureUrl) {
//        if (pictureUrl != null) {
//            holder.ivMessagePicture.setVisibility(View.VISIBLE);
//            Picasso.get().load(pictureUrl).fit().centerInside()
//                    .placeholder(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_profile_default)))
//                    .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
//                    .into(holder.ivMessagePicture);
//        } else {
//            holder.ivMessagePicture.setVisibility(View.GONE);
//        }
//    }

    private void bindMessageOrder(CorpStreamViewHolder holder, boolean isFirstMessage, boolean isAChat) {
        if (isFirstMessage) {
            holder.tvTypeOfConversation.setText(mContext.getString(R.string.started_chat_with));
        } else if (isAChat) {
            holder.tvTypeOfConversation.setText(mContext.getString(R.string.replied_to_chat_with));
        } else {
            holder.tvTypeOfConversation.setText(mContext.getString(R.string.send_a_message_to));
        }
    }

    // Sub root |5|
    private void bindWebView(CropStreamMessage current, CorpStreamViewHolder holder) {
        if (current.getMessageHttp() != null) {
            if (current.getMessageType().equals("PlainText")) {
                holder.tvWebPlainText.setText(current.getMessageHttp()
                        .replaceAll("<div>", "")
                        .replaceAll("</div>", "")
                        .replaceAll("<br/>", "\n")
                        .replaceAll("&#39;", "\u2019")
                        .trim());
                holder.tvWebPlainText.setVisibility(View.VISIBLE);
                holder.wvCardRenderData.setVisibility(View.GONE);
            } else {
                holder.tvWebPlainText.setVisibility(View.GONE);
                holder.wvCardRenderData.loadDataWithBaseURL(null, current.getMessageHttp(), "text/html; charset=utf-8", "utf-8", null);
                holder.wvCardRenderData.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        holder.wvCardRenderData.getLayoutParams().height = (int) (holder.vWidth.getWidth() / current.getAspectRatio());
                        holder.wvCardRenderData.setVisibility(View.GONE);
                        holder.wvCardRenderData.setVisibility(View.VISIBLE);
                        holder.wvCardRenderData.setBackgroundColor(Color.TRANSPARENT);
                        holder.wvCardRenderData.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
                    }
                });
            }
        } else {
            holder.wvCardRenderData.setVisibility(View.GONE);
            holder.tvWebPlainText.setVisibility(View.GONE);
        }
    }

    // Sub root |6| & |7|
    private void bindCatalogEntry(@NonNull CorpStreamViewHolder holder, CropStreamMessage current) {
        if (current.getTemplateItemModelBaseList() != null) {
            holder.llCatalogEntry.setVisibility(View.VISIBLE);

            LinearLayout linearLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams paramsLL = new LinearLayout.LayoutParams(holder.vWidth.getMeasuredWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(paramsLL);
            linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                Log.d("CatalogEntry", "Height: " + linearLayout.getMeasuredHeight());
                Log.d("CatalogEntry", "Width: " + linearLayout.getMeasuredWidth());
                if (linearLayout.getMeasuredHeight() > linearLayout.getMeasuredWidth()){
                    linearLayout.getLayoutParams().height = holder.vWidth.getMeasuredWidth();
                    Log.d("CatalogEntry", "New Height: " + linearLayout.getMeasuredHeight());
                }
            });


            if (holder.llCatalogEntry.getChildCount() > 0)
                holder.llCatalogEntry.removeAllViews();

            List<TemplateItemModelBase> list = current.getTemplateItemModelBaseList();
            for (TemplateItemModelBase item : list) {
                if (item.getType().equals("Label")) {
                    addLabelToLinearLayout(linearLayout, list, item);
                }

                if (item.getType().equals("HyperLink")) {
                    addLabelToLinearLayout(linearLayout, list, item);
                }

                if (item.getType().equals("Image")) {
                    addPictureToLinearLayout(linearLayout, list, item);
                }

                if (item.getType().equals("Message")) {
                    addWebViewMessageToLinearLayout(linearLayout, list, item);
                }

                if (item.getType().equals("WeatherDaysOutlook")) {
                    addWebViewWeatherDaysOutlookToLinearLayout(linearLayout, list, item);
                }

                if (item.getType().equals("WeatherRegionalRadar")) {
                    addWebViewWeatherRegionalRadarToLinearLayout(linearLayout, list, item);
                }
            }

            holder.llCatalogEntry.addView(linearLayout);

            if (current.getTemplateModelName() != null) {
                TextView textView = new TextView(mContext);
                textView.setTextSize(dpToPx(4));
                textView.setTextColor(mContext.getColor(R.color.grey));
                textView.setText(current.getTemplateModelName());
                textView.setId(list.size() + 10);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(25, 50, 0, 0);
                textView.setLayoutParams(params);
                holder.llCatalogEntry.addView(textView);
            }

            if (current.getTemplateModelDescription() != null) {
                TextView textView = new TextView(mContext);
                textView.setTextSize(dpToPx(3));
                textView.setTextColor(mContext.getColor(R.color.grey_500));
                if (current.getTemplateModelDescription().contains("*"))
                    textView.setText(current.getTemplateModelDescription());
                else
                    textView.setText(mContext.getString(R.string.footer_description, current.getTemplateModelDescription()));
                textView.setId(list.size() + 20);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(25, 10, 0, 50);
                textView.setLayoutParams(params);
                holder.llCatalogEntry.addView(textView);
            }

        } else {
            holder.llCatalogEntry.setVisibility(View.GONE);
        }
    }

    private void addWebViewWeatherRegionalRadarToLinearLayout(LinearLayout linearLayout, List<TemplateItemModelBase> list, TemplateItemModelBase item) {
        WebView webView = new WebView(mContext);
        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        webView.loadDataWithBaseURL(null, item.getInnerHtml(), "text/html; charset=utf-8", "utf-8", null);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        //webView.getLayoutParams().height = linearLayout.getWidth();
        webView.setId(list.indexOf(item) + 300);
        linearLayout.addView(webView);
    }

    private void addWebViewWeatherDaysOutlookToLinearLayout(LinearLayout linearLayout, List<TemplateItemModelBase> list, TemplateItemModelBase item) {
        WebView webView = new WebView(mContext);
        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        webView.loadDataWithBaseURL(null, item.getInnerHtml(), "text/html; charset=utf-8", "utf-8", null);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webView.setId(list.indexOf(item) + 200);
        linearLayout.addView(webView);
    }

    private void addWebViewMessageToLinearLayout(LinearLayout linearLayout, List<TemplateItemModelBase> list, TemplateItemModelBase item) {
        WebView webView = new WebView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        webView.setLayoutParams(params);
        webView.loadDataWithBaseURL(null, item.getLabel(), "text/html; charset=utf-8", "utf-8", null);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webView.setId(list.indexOf(item) + 1000);
        linearLayout.addView(webView);
    }

    private void addPictureToLinearLayout(LinearLayout linearLayout, List<TemplateItemModelBase> list, TemplateItemModelBase item) {
        if (item.getResourceUrl() != null && !item.getResourceUrl().equals("")) {
            ImageView imageView = new ImageView(mContext);

            Picasso.get().load((item.getResourceUrl())).fit().centerInside()
                    .placeholder(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_profile_default)))
                    .into(imageView);

            imageView.setId(list.indexOf(item));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 80, 0);
            imageView.setLayoutParams(params);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            linearLayout.addView(imageView);
        }
    }

    private void addLabelToLinearLayout(LinearLayout linearLayout, List<TemplateItemModelBase> list, TemplateItemModelBase item) {
        TextView textView = new TextView(mContext);
        textView.setTextColor(mContext.getColor(R.color.black));
        textView.setText(item.getLabel());
        textView.setId(list.indexOf(item));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        linearLayout.addView(textView);
    }

    private Drawable makeCircleWithALatter(String name) {
        if (name != null) {
            if (name.contains(" ")) {
                String[] list = name.split(" ");
                char charName = list[0].charAt(0);
                char charSecondName = list[1].charAt(0);
                return TextDrawable.builder().buildRound(String.valueOf(charName) + String.valueOf(charSecondName), ColorPicker.pickRandomColor());
            } else {
                char charName = name.charAt(0);
                return TextDrawable.builder().buildRound(String.valueOf(charName), ColorPicker.pickRandomColor());
            }
        } else return null;
    }

    private void bindStartReplyViewMessageViews(CorpStreamViewHolder holder, boolean isChat) {
        if (isChat) {
            holder.ibUnderProfile.setVisibility(View.INVISIBLE);
            holder.tvUnderProfile.setVisibility(View.INVISIBLE);
            holder.tvViewMessage.setVisibility(View.INVISIBLE);
        } else {
            holder.ibUnderProfile.setVisibility(View.VISIBLE);
            holder.tvUnderProfile.setVisibility(View.VISIBLE);
            holder.tvViewMessage.setVisibility(View.INVISIBLE);
        }
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources()
                .getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    class CorpStreamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivProfilePicture;
        TextView tvProfileFirst;
        TextView tvProfileSecond;
        TextView tvInvolvedPersons;
        TextView tvMessageText;
        TextView tvMessageTime;
        ImageView ivMessagePicture;
        TextView tvTypeOfConversation;
        ImageView ivProfilePictureMashTop;
        ImageView ivProfilePictureMashBottom;
        ImageButton ibUnderProfile;
        TextView tvUnderProfile;
        TextView tvViewMessage;
        WebView wvCardRenderData;
        View vWidth;
        TextView tvWebPlainText;
        LinearLayout llCatalogEntry;

        private CropStreamClickListener mListener;


        CorpStreamViewHolder(@NonNull View itemView, CropStreamClickListener listener) {
            super(itemView);

            ivProfilePicture = itemView.findViewById(R.id.list_item_iv_profile_picture);
            tvProfileFirst = itemView.findViewById(R.id.list_item_tv_profile_first);
            tvProfileSecond = itemView.findViewById(R.id.list_item_tv_profile_second);
            tvInvolvedPersons = itemView.findViewById(R.id.list_item_tv_profile_replied_to_target_of_reply);
            tvMessageText = itemView.findViewById(R.id.list_item_tv_text_message);
            tvMessageTime = itemView.findViewById(R.id.list_item_tv_profile_time);
            ivMessagePicture = itemView.findViewById(R.id.list_item_iv_message_image);
            tvTypeOfConversation = itemView.findViewById(R.id.list_item_tv_profile_replied_to_label);
            ivProfilePictureMashTop = itemView.findViewById(R.id.list_item_iv_profile_picture_mash_top);
            ivProfilePictureMashBottom = itemView.findViewById(R.id.list_item_iv_profile_picture_mash_bottom);
            ibUnderProfile = itemView.findViewById(R.id.list_item_ib_start_chat);
            tvUnderProfile = itemView.findViewById(R.id.list_item_tv_start_shat);
            tvViewMessage = itemView.findViewById(R.id.list_item_tv_view_message);
            wvCardRenderData = itemView.findViewById(R.id.list_item_wv_card_render);
            vWidth = itemView.findViewById(R.id.list_item_v_width);
            tvWebPlainText = itemView.findViewById(R.id.list_item_tv_web_plain_text);
            llCatalogEntry = itemView.findViewById(R.id.list_item_ll_catalog_entry);

            mListener = listener;
            tvTypeOfConversation.setOnClickListener(this);
            ibUnderProfile.setOnClickListener(this);
            tvUnderProfile.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mList.get(getAdapterPosition()).getConversationChat()) {
                mListener.onClick(view,
                        Integer.valueOf(mList.get(getAdapterPosition()).getConversationId()),
                        Integer.valueOf(mList.get(getAdapterPosition()).getPersonId()),
                        mList.get(getAdapterPosition()).getProfileName(),
                        mList.get(getAdapterPosition()).getPersonsCorp(),
                        mList.get(getAdapterPosition()).getPersonPictureUrl(),
                        mList.get(getAdapterPosition()).getMessageText(),
                        Constants.CLICK_KEY_CONVERSATION_DETAILS);
            }
            if (view.getId() == R.id.list_item_ib_start_chat || view.getId() == R.id.list_item_tv_start_shat) {
                mListener.onClick(view,
                        Integer.valueOf(mList.get(getAdapterPosition()).getConversationId()),
                        Integer.valueOf(mList.get(getAdapterPosition()).getPersonId()),
                        mList.get(getAdapterPosition()).getProfileName(),
                        mList.get(getAdapterPosition()).getPersonsCorp(),
                        mList.get(getAdapterPosition()).getPersonPictureUrl(),
                        mList.get(getAdapterPosition()).getMessageText(),
                        Constants.CLICK_KEY_START_CHAT);
            }
        }
    }
}
