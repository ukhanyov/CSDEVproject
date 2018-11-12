package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.network.pojo.conversation_details.model.participants.CDParticipants;
import com.example.admin_linux.csdevproject.utils.CircleTransform;
import com.example.admin_linux.csdevproject.utils.ColorPicker;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class CDPeopleAdapter extends RecyclerView.Adapter<CDPeopleAdapter.CDPeopleViewHolder> {

    private final LayoutInflater mInflater;
    private List<CDParticipants> mList;
    private Context mContext;

    public CDPeopleAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public CDPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.list_item_conversation_details_toolbar, viewGroup, false);
        return new CDPeopleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CDPeopleViewHolder holder, int i) {
        if (mList != null) {
            // Bind views
            CDParticipants current = mList.get(i);

            // Bind persons picture
            if(current.getPersonImageUrl() != null && !current.getPersonImageUrl().equals("")){
                Picasso.get().load(current.getPersonImageUrl()).fit().centerInside()
                        .placeholder(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_profile_default)))
                        .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                        .transform(new CircleTransform())
                        .into(holder.ivPersonPicture);
            }else {
                holder.ivPersonPicture.setImageDrawable(makeCircleWithALatter(current.getPersonFullName()));
            }

            // Bind persons name
            holder.tvPersonName.setText(current.getPersonFirstName());

        } else {
            throw new IllegalArgumentException("Some error with binding data for CDPeople recycler view");
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    private Drawable makeCircleWithALatter(String name) {
        if(name.contains(" ")){
            String[] list = name.split(" ");
            char charName = list[0].charAt(0);
            char charSecondName = list[1].charAt(0);
            return TextDrawable.builder().buildRound(String.valueOf(charName) + String.valueOf(charSecondName), ColorPicker.pickRandomColor());
        }else {
            char charName = name.charAt(0);
            return TextDrawable.builder().buildRound(String.valueOf(charName), ColorPicker.pickRandomColor());
        }
    }

    public void setConversationDetailsParticipants(List<CDParticipants> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    class CDPeopleViewHolder extends RecyclerView.ViewHolder{

        ImageView ivPersonPicture;
        TextView tvPersonName;

        CDPeopleViewHolder(View itemView){
            super(itemView);

            ivPersonPicture = itemView.findViewById(R.id.list_item_iv_conversation_details_profile_people);
            tvPersonName = itemView.findViewById(R.id.list_item_tv_conversation_details_profile_name);
        }

    }

}
