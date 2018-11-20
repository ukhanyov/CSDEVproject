package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoriteFormTemplate;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoritePossibleItem;

import java.util.ArrayList;
import java.util.List;

public class FavoritesTabTemplateItemAdapter extends RecyclerView.Adapter<FavoritesTabTemplateItemAdapter.ViewHolderTemplateItem> {

    private List<FavoriteFormTemplate> mList;
    private Context mContext;

    public FavoritesTabTemplateItemAdapter(List<FavoriteFormTemplate> list, Context context) {
        mList = new ArrayList<>();
        this.mList = list;

        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolderTemplateItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_fragment_favorites_page_template, viewGroup, false);

        return new ViewHolderTemplateItem(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTemplateItem holder, int position) {
        if (mList != null) {


        } else {
            throw new IllegalArgumentException("Some error with binding data for FavoritesTabTemplateItemAdapter recycler view");
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

    // TODO : read about "smart" viewHolders that bind data by themselves
    class ViewHolderTemplateItem extends RecyclerView.ViewHolder {

        //...

        ViewHolderTemplateItem(View itemView){
            super(itemView);

            //...

        }

        void showFavoriteFormTemplateDetails(FavoriteFormTemplate formTemplate) {
        }
    }

}
