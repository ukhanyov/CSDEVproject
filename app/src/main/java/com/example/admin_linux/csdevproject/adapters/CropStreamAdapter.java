package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.data.CropStreamMessage;
import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.utils.ImageHelper;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class CropStreamAdapter extends RecyclerView.Adapter<CropStreamAdapter.CorpStreamViewHolder> {

    private final LayoutInflater mInflater;
    private List<CropStreamMessage> mList;

    public CropStreamAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
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
            holder.ivProfilePicture.setBackground(loadImageFromWebOperations(current.getProfilePicture()));

            if(current.getProfileCorpName() != null) {
                holder.tvProfileCorp.setText(current.getProfileCorpName());
                holder.tvProfileName.setVisibility(View.GONE);
            }
            else {
                holder.tvProfileName.setText(current.getProfileName());
                holder.tvProfileName.setVisibility(View.VISIBLE);
            }

            holder.tvMessageDestination.setText(current.getMessageDestination());

            holder.tvMessageText.setText(current.getMessageText());

            holder.tvMessageTime.setText(current.getMessageTime());

            if(current.getMessagePicture() != null) holder.ivMessagePicture.setImageBitmap(ImageHelper.decodeFromByteArray(current.getMessagePicture()));

        } else {
            throw new IllegalArgumentException("Some error with binding data for CorpStream recycler view");
        }
    }

    public static Drawable loadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, null);
        } catch (Exception e) {
            return null;
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


    class CorpStreamViewHolder extends RecyclerView.ViewHolder {

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
