package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.data.CropStreamMessage;
import com.example.admin_linux.csdevproject.utils.DateHelper;
import com.example.admin_linux.csdevproject.utils.RoundCorners;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class CropStreamAdapter extends RecyclerView.Adapter<CropStreamAdapter.CorpStreamViewHolder> {

    private final LayoutInflater mInflater;
    private List<CropStreamMessage> mList;
    private Context mContext;

    public CropStreamAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public CorpStreamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.list_item_crop_stream, viewGroup, false);
        return new CorpStreamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CorpStreamViewHolder holder, int i) {
        if (mList != null) {
            CropStreamMessage current = mList.get(i);

            // Bind views
            if (current.getCombineImageUrlFirst() != null && current.getCombineImageUrlFirst() != null) {
                Picasso.with(mContext).load(current.getCombineImageUrlFirst()).fit().centerInside()
                        .placeholder(mContext.getDrawable(R.drawable.ic_profile_default))
                        .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                        .into(holder.ivProfilePictureMashTop);

                Picasso.with(mContext).load(current.getCombineImageUrlSecond()).fit().centerInside()
                        .placeholder(mContext.getDrawable(R.drawable.ic_profile_default))
                        .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                        .into(holder.ivProfilePictureMashBottom);

                holder.ivProfilePictureMashTop.setVisibility(View.VISIBLE);
                holder.ivProfilePictureMashBottom.setVisibility(View.VISIBLE);
                holder.ivProfilePicture.setVisibility(View.GONE);

            } else {
                Picasso.with(mContext).load(current.getProfilePicture()).fit().centerInside()
                        .placeholder(mContext.getDrawable(R.drawable.ic_profile_default))
                        .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                        .transform(new RoundCorners(dpToPx(8), dpToPx(0)))
                        .into(holder.ivProfilePicture);

                holder.ivProfilePictureMashTop.setVisibility(View.GONE);
                holder.ivProfilePictureMashBottom.setVisibility(View.GONE);
                holder.ivProfilePicture.setVisibility(View.VISIBLE);
            }

            if (current.getProfileCorpName() != null) {
                holder.tvProfileFirst.setText(current.getProfileCorpName());
                holder.tvProfileSecond.setVisibility(View.GONE);
            } else {
                holder.tvProfileFirst.setText(current.getProfileName());
                if(current.getPersonsCorp() != null && !current.getPersonsCorp().equals("")){
                    holder.tvProfileSecond.setVisibility(View.VISIBLE);
                    holder.tvProfileSecond.setText(current.getPersonsCorp());
                }else {
                    holder.tvProfileSecond.setVisibility(View.GONE);
                }

            }

            holder.tvInvolvedPersons.setText(current.getInvolvedPersonsNames());

            if(current.getMessageText() != null){
                holder.tvMessageText.setVisibility(View.VISIBLE);
                holder.tvMessageText.setText(current.getMessageText());
            }else {
                holder.tvMessageText.setVisibility(View.GONE);
            }

            holder.tvMessageTime.setText(DateHelper.normalizeDate(current.getMessageTime()));

            if (current.getMessagePicture() != null) {
                holder.ivMessagePicture.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(current.getMessagePicture()).fit().centerInside()
                        .placeholder(mContext.getDrawable(R.drawable.ic_profile_default))
                        .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                        .into(holder.ivMessagePicture);
            }else {
                holder.ivMessagePicture.setVisibility(View.GONE);
            }

            if (current.getConversationFirstMessage())
                holder.tvTypeOfConversation.setText(mContext.getString(R.string.started_chat_with));
            else
                holder.tvTypeOfConversation.setText(mContext.getString(R.string.replied_to_chat_with));

            if(current.getFeedType().equals("ConversationMessage")){
                holder.ibUnderProfile.setVisibility(View.INVISIBLE);
                holder.tvUnderProfile.setVisibility(View.INVISIBLE);
                holder.tvViewMessage.setVisibility(View.VISIBLE);
            }else {
                holder.ibUnderProfile.setVisibility(View.VISIBLE);
                holder.tvUnderProfile.setVisibility(View.VISIBLE);
                holder.tvViewMessage.setVisibility(View.INVISIBLE);
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

    public void setCorpStreamMessages(List<CropStreamMessage> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources()
                .getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    class CorpStreamViewHolder extends RecyclerView.ViewHolder {

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


        CorpStreamViewHolder(@NonNull View itemView) {
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
        }
    }
}
