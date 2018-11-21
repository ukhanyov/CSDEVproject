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
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoritePossibleItem;

import java.util.ArrayList;
import java.util.List;

public class FavoritesTabPossibleItemAdapter extends RecyclerView.Adapter<FavoritesTabPossibleItemAdapter.ViewHolderPossibleItem> {

    private List<FavoritePossibleItem> mList;
    private Context mContext;

    public FavoritesTabPossibleItemAdapter(List<FavoritePossibleItem> mList, Context mContext) {
        this.mList = new ArrayList<>();
        this.mList = mList;

        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderPossibleItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_fragment_favorites_page_possible_item, viewGroup, false);

        return new ViewHolderPossibleItem(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPossibleItem holder, int position) {
        if (mList != null) {
            FavoritePossibleItem item = mList.get(position);
            /*
            "ItemType" -> "color"

            "CP" -> "fpi_CP" -> (orange)
            "Seed" -> "fpi_Seed" -> (green)
            "Fert" -> "fpi_Fert" -> (blue)
             */

            holder.tvName.setText(item.getFavoriteFormTemplateName());
            holder.tvManufacturer.setText(item.getFavoriteFormTemplateManufacturer());

            if(item.getFavoriteFormTemplateItemType() != null && item.getFavoriteFormTemplateItemType().equals("CP")){
                holder.ivLeftLine.setBackgroundColor(mContext.getColor(R.color.fpi_CP));
                holder.ivStar.setBackground(mContext.getDrawable(R.drawable.ic_favorites_cp));
                holder.tvName.setTextColor(mContext.getColor(R.color.fpi_CP));
            }

            if(item.getFavoriteFormTemplateItemType() != null && item.getFavoriteFormTemplateItemType().equals("Seed")){
                holder.ivLeftLine.setBackgroundColor(mContext.getColor(R.color.fpi_Seed));
                holder.ivStar.setBackground(mContext.getDrawable(R.drawable.ic_favorites_seed));
                holder.tvName.setTextColor(mContext.getColor(R.color.fpi_Seed));
            }

            if(item.getFavoriteFormTemplateItemType() != null && item.getFavoriteFormTemplateItemType().equals("Fert")){
                holder.ivLeftLine.setBackgroundColor(mContext.getColor(R.color.fpi_Fert));
                holder.ivStar.setBackground(mContext.getDrawable(R.drawable.ic_favorites_fert));
                holder.tvName.setTextColor(mContext.getColor(R.color.fpi_Fert));
            }

        } else {
            throw new IllegalArgumentException("Some error with binding data for FavoritesTabPossibleItemAdapter recycler view");
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

    class ViewHolderPossibleItem extends RecyclerView.ViewHolder {

        ImageView ivLeftLine;
        ImageView ivStar;
        TextView tvName;
        TextView tvManufacturer;

        ViewHolderPossibleItem(View itemView){
            super(itemView);

            tvName = itemView.findViewById(R.id.list_item_possible_tv_name);
            tvManufacturer = itemView.findViewById(R.id.list_item_possible_tv_manufacturer);
            ivLeftLine = itemView.findViewById(R.id.list_item_possible_iv_line_left);
            ivStar = itemView.findViewById(R.id.list_item_possible_iv_star_right);
        }
    }
}
