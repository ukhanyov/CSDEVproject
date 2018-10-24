package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.data.CorpStreamMessage;
import com.example.admin_linux.csdevproject.R;

import java.util.List;

public class CorpStreamAdapter extends RecyclerView.Adapter<CorpStreamAdapter.CorpStreamViewHolder> {

    private final LayoutInflater mInflater;
    private List<CorpStreamMessage> mList;

    public CorpStreamAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CorpStreamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.list_item_corp_stream, viewGroup, false);
        return new CorpStreamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CorpStreamViewHolder holder, int i) {
        if (mList != null) {
            // Bind views
            holder.ivProfilePicture = null;
            holder.tvProfileName = null;
            holder.tvProfileCorp = null;
            holder.tvMessageDestination = null;
            holder.tvMessageText = null;
            holder.tvMessageTime = null;
            holder.ivMessagePicture = null;

        } else {
            throw new IllegalArgumentException("Some error with binding data for CorpStream recycler view");
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    public void setCorpStreamMessages(List<CorpStreamMessage> list) {
        this.mList = list;
        notifyDataSetChanged();
    }


    public class CorpStreamViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePicture;
        TextView tvProfileName;
        TextView tvProfileCorp;
        TextView tvMessageDestination;
        TextView tvMessageText;
        TextView tvMessageTime;
        ImageView ivMessagePicture;


        CorpStreamViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfilePicture = itemView.findViewById(R.id.list_item_iv_profile_picture);
            tvProfileName = itemView.findViewById(R.id.list_item_tv_profile_name);
            tvProfileCorp = itemView.findViewById(R.id.list_item_tv_profile_company);
            tvMessageDestination = itemView.findViewById(R.id.list_item_tv_profile_replied_to_target_of_reply);
            tvMessageText = itemView.findViewById(R.id.list_item_tv_text_message);
            tvMessageTime = itemView.findViewById(R.id.list_item_tv_profile_time);
            ivMessagePicture = itemView.findViewById(R.id.list_item_iv_message_image);
        }
    }
}
