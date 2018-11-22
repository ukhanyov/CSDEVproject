package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import static android.content.Context.MODE_PRIVATE;

public class CropStreamAdapter extends RecyclerView.Adapter<CropStreamAdapter.CropStreamViewHolder> {

    // TODO : change CropStreamMessage in the way so that you can have a list of involved people, and only then, judging by it's size, bind one or two icons in the header

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
    public CropStreamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_crop_stream, viewGroup, false);

        return new CropStreamViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CropStreamViewHolder holder, int i) {
        if (mList != null) {
            CropStreamMessage current = mList.get(i);

            // Bind views
            // Possible roots |1|: organization -> "ConversationId"(null) -> "InvolvedPersons"(null) -> you
            // Possible roots |2|: organization -> "ConversationId" -> "InvolvedPersons" -> list everyone but you
            // Possible roots |3|: organization(null) -> "Person" -> "ConversationId"(null) -> "InvolvedPersons"(null) -> you
            // Possible roots |4|: organization(null) -> "Person" -> "ConversationId" -> "InvolvedPersons" -> list everyone but you

            // Sub root |5| universal for all roots

            // Sub root |6|: FeedEvents -> CatalogEntryId(not null) -> CatalogEntries -> CatalogEntryInDetailsModel ->
            // FFTFormTemplateModel -> FormTemplateItemModelBase(all items) -> ItemType(add views according to this type)
            // universal for all roots

            // Sub root |7|: FeedEvents -> CatalogEntryId(null) -> CardRenderDataModel -> CatalogEntries -> CatalogEntryInDetailsModel ->
            // FFTFormTemplateModel -> FormTemplateItemModelBase(all items) -> ItemType(add views according to this type)
            // universal for all roots

            // Some pre setup
            holder.btnConnect.setVisibility(View.GONE);
            holder.wvCardRenderData.setVisibility(View.GONE);
            holder.tvWebPlainText.setVisibility(View.GONE);

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

            // TODO : also, need to check if template belongs to the cardRender object? if so - radar will have a header with date
            // Sub root |5|
            bindWebView(current, holder);

            // Sub root |6| & |7|
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
    private void bindViewsRootOne(CropStreamMessage current, CropStreamViewHolder holder) {

        // Bind profile picture
        bindImage(holder, current.getProfileName(), current.getProfilePicture());

        // Bind name
        bindName(holder, current.getProfileCorpName(), null);

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
        bindStartReplyViewMessageViews(holder, current.getConversationChat(), current.isFeedSourceinFavorites(), current.getCardRenderDataId());

    }

    // root |2|
    private void bindViewsRootTwo(CropStreamMessage current, CropStreamViewHolder holder) {

        bindImageMultiple(holder,
                current.getCombineImageNameFirst(),
                current.getCombineImageNameSecond(),
                current.getCombineImageUrlFirst(),
                current.getCombineImageUrlSecond());

        // Bind name
        bindName(holder, current.getProfileCorpName(), null);

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
        bindStartReplyViewMessageViews(holder, current.getConversationChat(), current.isFeedSourceinFavorites(), current.getCardRenderDataId());

    }

    // root |3|
    private void bindViewsRootThree(CropStreamMessage current, CropStreamViewHolder holder) {

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
        bindStartReplyViewMessageViews(holder, current.getConversationChat(), current.isFeedSourceinFavorites(), current.getCardRenderDataId());
    }

    // root |4|
    private void bindViewsRootFour(CropStreamMessage current, CropStreamViewHolder holder) {

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

        // Bind message order (is it first)
        bindMessageOrder(holder, current.getConversationFirstMessage(), current.getConversationChat());

        // Bind bottom views (Reply/Start/View Message)
        //bindStartReplyViewMessageViews(holder, current.getFeedType());
        bindStartReplyViewMessageViews(holder, current.getConversationChat(), current.isFeedSourceinFavorites(), current.getCardRenderDataId());

    }

    private void bindImage(CropStreamViewHolder holder, String name, String urlOne) {

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

    private void bindImageMultiple(CropStreamViewHolder holder, String nameOne, String nameTwo, String urlOne, String urlTwo) {

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
            if (nameTwo != null) {
                holder.ivProfilePictureMashTop.setImageDrawable(makeCircleWithALatter(nameTwo));
                holder.ivProfilePictureMashTop.setAdjustViewBounds(true);
            } else {
                SharedPreferences preferences = mContext.getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
                Picasso.get().load(preferences.getString(Constants.PREF_PROFILE_IMAGE_URL, null)).fit().centerInside()
                        .placeholder(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_profile_default)))
                        .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                        .transform(new CircleTransform())
                        .into(holder.ivProfilePictureMashTop);
            }
        }

        holder.ivProfilePictureMashTop.setVisibility(View.VISIBLE);
        holder.ivProfilePictureMashBottom.setVisibility(View.VISIBLE);
        holder.ivProfilePicture.setVisibility(View.GONE);
    }

    private void bindName(CropStreamViewHolder holder, String nameOne, String nameTwo) {

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

    private void bindMessageText(CropStreamViewHolder holder, String text) {
        if (text != null && !text.equals("")) {
            holder.tvMessageText.setVisibility(View.VISIBLE);
            holder.tvMessageText.setText(text);
        } else {
            holder.tvMessageText.setVisibility(View.GONE);
        }
    }

    private void bindMessageOrder(CropStreamViewHolder holder, boolean isFirstMessage, boolean isAChat) {
        if (isFirstMessage) {
            holder.tvTypeOfConversation.setText(mContext.getString(R.string.started_chat_with));
        } else if (isAChat) {
            holder.tvTypeOfConversation.setText(mContext.getString(R.string.replied_to_chat_with));
        } else {
            holder.tvTypeOfConversation.setText(mContext.getString(R.string.send_a_message_to));
        }
    }

    private void bindStartReplyViewMessageViews(CropStreamViewHolder holder, boolean isChatConversation, boolean feedSourceinFavorites, int cardRenderDataId) {
        if (isChatConversation) {
            holder.ibUnderProfile.setVisibility(View.VISIBLE);
            holder.tvUnderProfile.setVisibility(View.VISIBLE);
            holder.tvUnderProfile.setText(mContext.getString(R.string.view_private_chat));
            holder.tvViewMessage.setVisibility(View.GONE);
        } else {
            holder.ibUnderProfile.setVisibility(View.VISIBLE);
            holder.tvUnderProfile.setVisibility(View.VISIBLE);
            holder.tvUnderProfile.setText(mContext.getString(R.string.start_private_chat));
            holder.tvViewMessage.setVisibility(View.GONE);
        }
        if (!feedSourceinFavorites && cardRenderDataId != 0) {
            holder.ibUnderProfile.setVisibility(View.GONE);
            holder.tvUnderProfile.setVisibility(View.GONE);
            holder.tvUnderProfile.setText(mContext.getString(R.string.view_private_chat));
            holder.tvViewMessage.setVisibility(View.VISIBLE);
        }
    }

    // Sub root |5|
    private void bindWebView(CropStreamMessage current, CropStreamViewHolder holder) {
        if (current.getMessageHttp() != null) {

            if (!current.isFeedSourceinFavorites() && current.getCardRenderDataId()!= 0){
                if (current.getMessageType().equals("PlainText")) {
                    holder.tvWebPlainText.setText(current.getMessageHttp()
                            .replaceAll("<div>", "")
                            .replaceAll("</div>", "")
                            .replaceAll("<br/>", "\n")
                            .replaceAll("&#39;", "\u2019")
                            .trim());

                } else {
                    holder.wvCardRenderData.loadDataWithBaseURL(null, current.getMessageHttp(), "text/html; charset=utf-8", "utf-8", null);
                    holder.wvCardRenderData.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            holder.wvCardRenderData.getLayoutParams().height = (int) (holder.vWidth.getWidth() / current.getAspectRatio());
                            holder.wvCardRenderData.setBackgroundColor(Color.TRANSPARENT);
                            holder.wvCardRenderData.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
                        }
                    });
                }
            } else {
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
                    holder.tvWebPlainText.setVisibility(View.GONE);
                    holder.wvCardRenderData.setVisibility(View.VISIBLE);
                }
            }

        } else {
            holder.wvCardRenderData.setVisibility(View.GONE);
            holder.tvWebPlainText.setVisibility(View.GONE);
        }
    }

    // Sub root |6| & |7|
    private void bindCatalogEntry(@NonNull CropStreamViewHolder holder, CropStreamMessage current) {
        if (current.getTemplateItemModelBaseList() != null) {
            holder.llCatalogEntry.setVisibility(View.VISIBLE);
            holder.llCatalogEntry.requestDisallowInterceptTouchEvent(true);
            holder.llCatalogEntry.setVerticalScrollBarEnabled(false);
            holder.llCatalogEntry.setHorizontalScrollBarEnabled(false);
            holder.llCatalogEntrySVWrapper.setVerticalScrollBarEnabled(false);
            holder.llCatalogEntrySVWrapper.setHorizontalScrollBarEnabled(false);
            if (holder.svCatalogEntry.getChildCount() > 0) holder.svCatalogEntry.removeAllViews();

            LinearLayout linearLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams paramsLL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(paramsLL);
            linearLayout.setVerticalScrollBarEnabled(false);
            linearLayout.setHorizontalScrollBarEnabled(false);

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
                    //checkerOfImage = true;
                }

                if (item.getType().equals("Message")) {
                    addWebViewMessageToLinearLayout(linearLayout, list, item);
                    //Log.d("catalog entry", "Message: " + item.getLabel());
                }

                if (item.getType().equals("WeatherDaysOutlook")) {
                    addWebViewWeatherDaysOutlookToLinearLayout(linearLayout, list, item);
                    //Log.d("catalog entry", "WeatherDaysOutlook: " + item.getInnerHtml());
                }

                if (item.getType().equals("WeatherRegionalRadar")) {
                    View view = new View(mContext);
                    view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20));
                    view.setBackgroundColor(mContext.getColor(R.color.radar_header_color));
                    linearLayout.addView(view);
                    addWebViewWeatherRegionalRadarToLinearLayout(linearLayout, list, item);
                    //Log.d("catalog entry", "WeatherRegionalRadar: " + item.getInnerHtml());
                }
            }

            if (list.size() == 1) {
                resizeSWCatalogEntry(holder);
            } else {
                resizeLLWCatalogEntry(holder);
            }


            holder.svCatalogEntry.addView(linearLayout);

            holder.tvFooterTop.setText(current.getTemplateModelName());
            if (current.getTemplateModelDescription().contains("*"))
                holder.tvFooterBot.setText(current.getTemplateModelDescription());
            else
                holder.tvFooterBot.setText(mContext.getString(R.string.footer_description, current.getTemplateModelDescription()));

        } else {
            holder.llCatalogEntry.setVisibility(View.GONE);
        }
    }

    private void resizeLLWCatalogEntry(@NonNull CropStreamViewHolder holder) {
        ViewTreeObserver viewTreeObserver = holder.llCatalogEntrySVWrapper.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    holder.llCatalogEntrySVWrapper.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    holder.llCatalogEntrySVWrapper.getLayoutParams().height = holder.llCatalogEntrySVWrapper.getWidth();

                }
            });
        }
    }

    private void resizeSWCatalogEntry(@NonNull CropStreamViewHolder holder) {
        ViewTreeObserver viewTreeObserver = holder.llCatalogEntrySVWrapper.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    holder.llCatalogEntrySVWrapper.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    int swW = holder.svCatalogEntry.getWidth();
                    int swH = holder.svCatalogEntry.getHeight();

                    if (swH < swW) {
                        holder.llCatalogEntrySVWrapper.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        holder.svCatalogEntry.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    } else {
                        holder.llCatalogEntrySVWrapper.getLayoutParams().height = swW;
                        holder.svCatalogEntry.getLayoutParams().height = swW;
                    }

                }
            });
        }
    }

    private static String changedHeaderHtml(String htmlText) {
        String head = "<head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head>";
        String closedTag = "</body></html>";
        return head + htmlText + closedTag;
    }

    private void addWebViewWeatherRegionalRadarToLinearLayout(LinearLayout linearLayout, List<TemplateItemModelBase> list, TemplateItemModelBase item) {
        WebView webView = new WebView(mContext);
        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setWebChromeClient(new WebChromeClient());
        String changeFontHtml = changedHeaderHtml(item.getInnerHtml());
        webView.loadDataWithBaseURL(null, changeFontHtml, "text/html", "UTF-8", null);

        webView.setId(list.indexOf(item) + 300);
        linearLayout.addView(webView);
    }

    private void addWebViewWeatherDaysOutlookToLinearLayout(LinearLayout linearLayout, List<TemplateItemModelBase> list, TemplateItemModelBase item) {
        WebView webView = new WebView(mContext);
        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setWebChromeClient(new WebChromeClient());
        String changeFontHtml = changedHeaderHtml(item.getInnerHtml());
        webView.loadDataWithBaseURL(null, changeFontHtml, "text/html", "UTF-8", null);

        webView.setId(list.indexOf(item) + 200);
        linearLayout.addView(webView);
    }

    private void addWebViewMessageToLinearLayout(LinearLayout linearLayout, List<TemplateItemModelBase> list, TemplateItemModelBase item) {
        if (item.getLabel() != null && !item.getLabel().equals("")) {
            WebView webView = new WebView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            webView.setLayoutParams(params);

            webView.loadDataWithBaseURL(null, item.getLabel(), "text/html; charset=utf-8", "utf-8", null);
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

            webView.setId(list.indexOf(item) + 100);
            linearLayout.addView(webView);
        }

    }

    private void addPictureToLinearLayout(LinearLayout linearLayout, List<TemplateItemModelBase> list, TemplateItemModelBase item) {
        if (item.getResourceUrl() != null && !item.getResourceUrl().equals("")) {
            ImageView imageView = new ImageView(mContext);

            Picasso.get().load((item.getResourceUrl())).fit().centerInside()
                    .placeholder(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_profile_default)))
                    .into(imageView);

            imageView.setId(list.indexOf(item));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
        if (list.indexOf(item) == 0) params.setMargins(50, 50, 0, 50);
        else params.setMargins(50, 0, 0, 0);
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

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources()
                .getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public class CropStreamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivProfilePicture;
        TextView tvProfileFirst;
        TextView tvProfileSecond;
        TextView tvInvolvedPersons;
        TextView tvMessageText;
        TextView tvMessageTime;
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
        ScrollView svCatalogEntry;
        TextView tvFooterTop;
        TextView tvFooterBot;
        LinearLayout llCatalogEntrySVWrapper;

        Button btnConnect;

        private CropStreamClickListener mListener;


        CropStreamViewHolder(@NonNull View itemView, CropStreamClickListener listener) {
            super(itemView);

            ivProfilePicture = itemView.findViewById(R.id.list_item_iv_profile_picture);
            tvProfileFirst = itemView.findViewById(R.id.list_item_tv_profile_first);
            tvProfileSecond = itemView.findViewById(R.id.list_item_tv_profile_second);
            tvInvolvedPersons = itemView.findViewById(R.id.list_item_tv_profile_replied_to_target_of_reply);
            tvMessageText = itemView.findViewById(R.id.list_item_tv_text_message);
            tvMessageTime = itemView.findViewById(R.id.list_item_tv_profile_time);
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
            svCatalogEntry = itemView.findViewById(R.id.list_item_sw_catalog_entry);
            tvFooterTop = itemView.findViewById(R.id.list_item_tv_catalog_entry_footer_top);
            tvFooterBot = itemView.findViewById(R.id.list_item_tv_catalog_entry_footer_bot);
            llCatalogEntrySVWrapper = itemView.findViewById(R.id.list_item_ll_catalog_entry_scroll_view_wrapper);

            btnConnect = itemView.findViewById(R.id.list_item_button_connect);

            mListener = listener;
            tvTypeOfConversation.setOnClickListener(this);
            ibUnderProfile.setOnClickListener(this);
            tvUnderProfile.setOnClickListener(this);
            tvViewMessage.setOnClickListener(this);
            btnConnect.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.list_item_tv_profile_replied_to_label) {
                if (tvUnderProfile.getText().equals(mContext.getString(R.string.start_private_chat)))
                    mListener.onClick(view,
                            Integer.valueOf(mList.get(getAdapterPosition()).getConversationId()),
                            Integer.valueOf(mList.get(getAdapterPosition()).getPersonId()),
                            mList.get(getAdapterPosition()).getProfileName(),
                            mList.get(getAdapterPosition()).getPersonsCorp(),
                            mList.get(getAdapterPosition()).getPersonPictureUrl(),
                            mList.get(getAdapterPosition()).getMessageText(),
                            Constants.CLICK_KEY_START_CHAT);

                if (tvUnderProfile.getText().equals(mContext.getString(R.string.view_private_chat)))
                    mListener.onClick(view,
                            Integer.valueOf(mList.get(getAdapterPosition()).getConversationId()),
                            Integer.valueOf(mList.get(getAdapterPosition()).getPersonId()),
                            mList.get(getAdapterPosition()).getProfileName(),
                            mList.get(getAdapterPosition()).getPersonsCorp(),
                            mList.get(getAdapterPosition()).getPersonPictureUrl(),
                            mList.get(getAdapterPosition()).getMessageText(),
                            Constants.CLICK_KEY_CONVERSATION_DETAILS);

            } else if (view.getId() == R.id.list_item_ib_start_chat || view.getId() == R.id.list_item_tv_start_shat) {
                if (tvUnderProfile.getText().equals(mContext.getString(R.string.start_private_chat)))
                    mListener.onClick(view,
                            Integer.valueOf(mList.get(getAdapterPosition()).getConversationId()),
                            Integer.valueOf(mList.get(getAdapterPosition()).getPersonId()),
                            mList.get(getAdapterPosition()).getProfileName(),
                            mList.get(getAdapterPosition()).getPersonsCorp(),
                            mList.get(getAdapterPosition()).getPersonPictureUrl(),
                            mList.get(getAdapterPosition()).getMessageText(),
                            Constants.CLICK_KEY_START_CHAT);

                if (tvUnderProfile.getText().equals(mContext.getString(R.string.view_private_chat)))
                    mListener.onClick(view,
                            Integer.valueOf(mList.get(getAdapterPosition()).getConversationId()),
                            Integer.valueOf(mList.get(getAdapterPosition()).getPersonId()),
                            mList.get(getAdapterPosition()).getProfileName(),
                            mList.get(getAdapterPosition()).getPersonsCorp(),
                            mList.get(getAdapterPosition()).getPersonPictureUrl(),
                            mList.get(getAdapterPosition()).getMessageText(),
                            Constants.CLICK_KEY_CONVERSATION_DETAILS);
            }
            if (view.getId() == R.id.list_item_tv_view_message) {
                mListener.onClick(view,
                        0,
                        0,
                        null,
                        null,
                        null,
                        null,
                        Constants.CLICK_KEY_VIEW_MESSAGE);

                ibUnderProfile.setVisibility(View.VISIBLE);
                tvUnderProfile.setVisibility(View.VISIBLE);
                btnConnect.setVisibility(View.VISIBLE);
                tvViewMessage.setVisibility(View.GONE);
                if (tvWebPlainText.getText() != null) tvWebPlainText.setVisibility(View.VISIBLE);
                wvCardRenderData.setVisibility(View.VISIBLE);

            }
            if (view.getId() == R.id.list_item_button_connect) {
                mListener.onClick(view,
                        mList.get(getAdapterPosition()).getOrganizationId(), // pass organizationId
                        mList.get(getAdapterPosition()).getFeedEventId(), // pass feedEventId
                        null,
                        null,
                        null,
                        null,
                        Constants.CLICK_KEY_BUTTON_CONNECT);

                btnConnect.setVisibility(View.GONE);
            }
        }
    }
}
