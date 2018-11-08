package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.data.models.CropStreamMessage;
import com.example.admin_linux.csdevproject.utils.CircleTransform;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.example.admin_linux.csdevproject.utils.DateHelper;
import com.example.admin_linux.csdevproject.utils.RoundCorners;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class CropStreamAdapter extends RecyclerView.Adapter<CropStreamAdapter.CorpStreamViewHolder> {

    private final LayoutInflater mInflater;
    private List<CropStreamMessage> mList;
    private Context mContext;
    private CropStreamClickListener mListener;
    private int position = 0;

    public CropStreamAdapter(Context context, CropStreamClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mListener = listener;
    }


    @NonNull
    @Override
    public CorpStreamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.list_item_crop_stream, viewGroup, false);
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

            // Sub root |5| universal for all roots
            // TODO : look into bug when webview is not displaying image
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
//                if (current.getMessageType().equals("Image") ||
//                        current.getMessageType().equals("Message") ||
//                        current.getMessageType().equals("MessageWithImage")) {
                    holder.tvWebPlainText.setVisibility(View.GONE);
                    holder.wvCardRenderData.setVisibility(View.VISIBLE);
                    holder.wvCardRenderData.loadDataWithBaseURL(null, current.getMessageHttp(), "text/html; charset=utf-8", "utf-8", null);

                    holder.wvCardRenderData.getLayoutParams().height = (int) (holder.vWidth.getWidth() / current.getAspectRatio());
                    holder.wvCardRenderData.setBackgroundColor(Color.TRANSPARENT);
                    holder.wvCardRenderData.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
                }
            } else {
                holder.wvCardRenderData.setVisibility(View.GONE);
                holder.tvWebPlainText.setVisibility(View.GONE);
            }

        } else {
            throw new IllegalArgumentException("Some error with binding data for CorpStream recycler view");
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    // root |1|
    private void bindViewsRootOne(CropStreamMessage current, CorpStreamViewHolder holder) {

        // Bind profile picture
        bindImage(holder, current.getProfilePicture(), null);

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

        // Bind profile picture
        if (current.getCombineImageUrlFirst() != null && current.getCombineImageUrlSecond() != null) {
            bindImage(holder, current.getCombineImageUrlFirst(), current.getCombineImageUrlSecond());
        } else if (current.getCombineImageUrlFirst() != null) {
            bindImage(holder, current.getCombineImageUrlFirst(), null);
        }

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
        bindImage(holder, current.getProfilePicture(), null);

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

        // Bind profile picture
        if (current.getCombineImageUrlFirst() != null && current.getCombineImageUrlSecond() != null) {
            bindImage(holder, current.getCombineImageUrlFirst(), current.getCombineImageUrlSecond());
        } else if (current.getCombineImageUrlFirst() != null) {
            bindImage(holder, current.getCombineImageUrlFirst(), null);
        }

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

    private void bindImage(CorpStreamViewHolder holder, String urlOne, String urlTwo) {

        if (urlTwo == null) {
            Picasso.with(mContext).load(urlOne).fit().centerInside()
                    .placeholder(mContext.getDrawable(R.drawable.ic_profile_default))
                    .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                    .transform(new RoundCorners(dpToPx(8), dpToPx(0)))
                    .into(holder.ivProfilePicture);

            holder.ivProfilePictureMashTop.setVisibility(View.GONE);
            holder.ivProfilePictureMashBottom.setVisibility(View.GONE);
            holder.ivProfilePicture.setVisibility(View.VISIBLE);
        } else {
            Picasso.with(mContext).load(urlOne).fit().centerInside()
                    .placeholder(mContext.getDrawable(R.drawable.ic_profile_default))
                    .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                    .transform(new CircleTransform())
                    .into(holder.ivProfilePictureMashTop);

            Picasso.with(mContext).load(urlTwo).fit().centerInside()
                    .placeholder(mContext.getDrawable(R.drawable.ic_profile_default))
                    .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                    .transform(new CircleTransform())
                    .into(holder.ivProfilePictureMashBottom);

            holder.ivProfilePictureMashTop.setVisibility(View.VISIBLE);
            holder.ivProfilePictureMashBottom.setVisibility(View.VISIBLE);
            holder.ivProfilePicture.setVisibility(View.GONE);
        }
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

    private void bindMessagePicture(CorpStreamViewHolder holder, String pictureUrl) {
        if (pictureUrl != null) {
            holder.ivMessagePicture.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(pictureUrl).fit().centerInside()
                    .placeholder(mContext.getDrawable(R.drawable.ic_profile_default))
                    .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                    .into(holder.ivMessagePicture);
        } else {
            holder.ivMessagePicture.setVisibility(View.GONE);
        }
    }

    private void bindMessageOrder(CorpStreamViewHolder holder, boolean isFirstMessage, boolean isAChat) {
        if (isFirstMessage) {
            holder.tvTypeOfConversation.setText(mContext.getString(R.string.started_chat_with));
        } else if (isAChat) {
            holder.tvTypeOfConversation.setText(mContext.getString(R.string.replied_to_chat_with));
        } else {
            holder.tvTypeOfConversation.setText(mContext.getString(R.string.send_a_message_to));
        }
    }

    // Old one was with feed type
    private void bindStartReplyViewMessageViews(CorpStreamViewHolder holder, boolean isChat) {
        //if (feedType.equals("ConversationMessage")) {
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

    public void setCorpStreamMessages(List<CropStreamMessage> list) {
        this.mList = list;
        position = mList.size();
    }

    public List<CropStreamMessage> getCorpStreamMessages() {
        return this.mList;
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
