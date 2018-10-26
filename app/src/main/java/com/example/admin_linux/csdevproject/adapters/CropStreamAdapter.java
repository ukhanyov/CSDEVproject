package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.data.CropStreamMessage;
import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.utils.DateHelper;
import com.example.admin_linux.csdevproject.utils.ImageHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
            if (current.getCombineImage()) {
                try {
                    // TODO: put into async task
                    URL url1;
                    URL url2;
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    url1 = new URL(current.getCombineImageUrlFirst());
                    HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
                    connection1.setDoInput(true);
                    connection1.connect();
                    InputStream input1 = connection1.getInputStream();
                    Bitmap myBitmap1 = BitmapFactory.decodeStream(input1);

                    url2 = new URL(current.getCombineImageUrlSecond());
                    HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
                    connection2.setDoInput(true);
                    connection2.connect();
                    InputStream input2 = connection2.getInputStream();
                    Bitmap myBitmap2 = BitmapFactory.decodeStream(input2);

                    Bitmap mergedBitmap;
                    int w, h;
                    h = myBitmap1.getHeight() + myBitmap2.getHeight();

                    if (myBitmap1.getWidth() > myBitmap2.getWidth()) w = myBitmap1.getWidth();
                    else w = myBitmap2.getWidth();

                    mergedBitmap = Bitmap.createBitmap(2*w, h, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(mergedBitmap);
                    canvas.drawBitmap(myBitmap1, 0f, 0f, null);
                    canvas.drawBitmap(myBitmap2, myBitmap1.getWidth()/2, myBitmap1.getHeight()/2, null);

                    holder.ivProfilePicture.setImageDrawable(new BitmapDrawable(mContext.getResources(), mergedBitmap));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Picasso.with(mContext).load(current.getProfilePicture()).fit().centerCrop()
                        .placeholder(mContext.getDrawable(R.drawable.ic_dummy_default))
                        .error(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_error_red)))
                        .into(holder.ivProfilePicture);
            }

            if (current.getProfileCorpName() != null) {
                holder.tvProfileCorp.setText(current.getProfileCorpName());
                holder.tvProfileCorp.setTypeface(Typeface.DEFAULT_BOLD);
                holder.tvProfileCorp.setTextColor(mContext.getColor(R.color.black));
                holder.tvProfileName.setVisibility(View.GONE);
            } else {
                holder.tvProfileCorp.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.tvProfileCorp.setTextColor(mContext.getColor(R.color.grey));
                holder.tvProfileCorp.setText(current.getPersonsCorp());
                holder.tvProfileName.setText(current.getProfileName());
                holder.tvProfileName.setTypeface(Typeface.DEFAULT_BOLD);
                holder.tvProfileName.setTextColor(mContext.getColor(R.color.black));
                holder.tvProfileName.setVisibility(View.VISIBLE);
            }

            holder.tvInvolvedPersons.setText(current.getInvolvedPersonsNames());

            holder.tvMessageText.setText(current.getMessageText());

            holder.tvMessageTime.setText(DateHelper.normalizeDate(current.getMessageTime()));

            if (current.getMessagePicture() != null)
                holder.ivMessagePicture.setImageBitmap(ImageHelper.decodeFromByteArray(current.getMessagePicture()));

            if (current.getConversationFirstMessage())
                holder.tvTypeOfConversation.setText(mContext.getString(R.string.started_chat_with));
            else
                holder.tvTypeOfConversation.setText(mContext.getString(R.string.replied_to_chat_with));


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


    class CorpStreamViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePicture;
        TextView tvProfileName;
        TextView tvProfileCorp;
        TextView tvInvolvedPersons;
        TextView tvMessageText;
        TextView tvMessageTime;
        ImageView ivMessagePicture;
        TextView tvTypeOfConversation;


        CorpStreamViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfilePicture = itemView.findViewById(R.id.list_item_iv_profile_picture);
            tvProfileName = itemView.findViewById(R.id.list_item_tv_profile_name);
            tvProfileCorp = itemView.findViewById(R.id.list_item_tv_profile_company);
            tvInvolvedPersons = itemView.findViewById(R.id.list_item_tv_profile_replied_to_target_of_reply);
            tvMessageText = itemView.findViewById(R.id.list_item_tv_text_message);
            tvMessageTime = itemView.findViewById(R.id.list_item_tv_profile_time);
            ivMessagePicture = itemView.findViewById(R.id.list_item_iv_message_image);
            tvTypeOfConversation = itemView.findViewById(R.id.list_item_tv_profile_replied_to_label);
        }
    }
}
