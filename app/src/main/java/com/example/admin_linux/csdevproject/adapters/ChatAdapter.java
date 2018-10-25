package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.data.ChatItem;
import com.example.admin_linux.csdevproject.utils.ImageHelper;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<ChatItem> mList;

    public ChatAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ChatItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.list_item_chat, viewGroup, false);
        return new ChatItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatItemViewHolder holder, int i) {
        if(mList != null){
            ChatItem current = mList.get(i);

            holder.ivProfilePicture.setImageBitmap(ImageHelper.decodeFromByteArray(current.getProfilePicture()));
            holder.tvProfileName.setText(current.getProfileName());
            holder.tvMessageDate.setText(current.getMessageTime());
            holder.tvMessageText.setText(current.getMessageText());

        }else {
            throw new IllegalArgumentException("Some error with binding data for ChatItem recycler view");
        }
    }

    @Override
    public int getItemCount() {
        if(mList != null) return mList.size();
        else return 0;
    }

    public void setChatItemList(List<ChatItem> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    class ChatItemViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfilePicture;
        TextView tvProfileName;
        TextView tvMessageText;
        TextView tvMessageDate;

        ChatItemViewHolder(@NonNull View itemView){
            super(itemView);

            ivProfilePicture = itemView.findViewById(R.id.list_item_iv_chat_profile_picture);
            tvProfileName = itemView.findViewById(R.id.list_item_tv_chat_name);
            tvMessageText = itemView.findViewById(R.id.list_item_tv_chat_message_sneak_pick);
            tvMessageDate = itemView.findViewById(R.id.list_item_tv_chat_date);
        }
    }
}
