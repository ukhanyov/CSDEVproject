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
import com.example.admin_linux.csdevproject.data.models.ConversationDetailsMessage;
import com.example.admin_linux.csdevproject.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class CDMessageAdapter extends RecyclerView.Adapter<CDMessageAdapter.CDMessageViewHolder> {

    private final LayoutInflater mInflater;
    private List<ConversationDetailsMessage> mList;
    private Context mContext;

    public CDMessageAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public CDMessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.list_item_conversation_details_messages, viewGroup, false);
        return new CDMessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CDMessageViewHolder holder, int i) {
        if (mList != null) {
            // Bind views
            ConversationDetailsMessage current = mList.get(i);

            // Bind profile picture
            Picasso.with(mContext).load(current.getProfilePictureUrl()).fit().centerInside()
                    .placeholder(mContext.getDrawable(R.drawable.ic_profile_default))
                    .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                    .transform(new CircleTransform())
                    .into(holder.ivProfilePicture);

            // Bind profile name
            holder.tvProfileName.setText(current.getProfileName());

            // Bind message
            holder.tvMessage.setText(current.getMessage());

        } else {
            throw new IllegalArgumentException("Some error with binding data for CDMessage recycler view");
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    public void setConversationDetailsMessages(List<ConversationDetailsMessage> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    class CDMessageViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfilePicture;
        TextView tvProfileName;
        TextView tvMessage;

        CDMessageViewHolder(View itemView){
            super(itemView);

            ivProfilePicture = itemView.findViewById(R.id.list_item_iv_c_d_message_profile_picture);
            tvProfileName = itemView.findViewById(R.id.list_item_tv_c_d_message_profile_name);
            tvMessage = itemView.findViewById(R.id.list_item_tv_c_d_message_text);
        }
    }

}
