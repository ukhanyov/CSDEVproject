package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.data.models.TemplateItemModelBase;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoriteFormTemplate;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoriteFormTemplateItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            FavoriteFormTemplate formTemplate = mList.get(position);

            if (formTemplate.getFavoriteFormTemplateItemList() != null) {

                LinearLayout linearLayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams paramsLL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(paramsLL);


                List<FavoriteFormTemplateItem> list = formTemplate.getFavoriteFormTemplateItemList();
                for (FavoriteFormTemplateItem item : list) {
                    if (item.getFavoriteFormTemplateType().equals("Label")) {
                        addLabelToLinearLayout(linearLayout, list, item);
                    }

                    if (item.getFavoriteFormTemplateType().equals("HyperLink")) {
                        addLabelToLinearLayout(linearLayout, list, item);
                    }

                    if (item.getFavoriteFormTemplateType().equals("Image")) {
                        addPictureToLinearLayout(linearLayout, list, item);
                    }

                    if (item.getFavoriteFormTemplateType().equals("Message")) {
                        addWebViewMessageToLinearLayout(linearLayout, list, item);
                    }

                    if (item.getFavoriteFormTemplateType().equals("WeatherDaysOutlook")) {
                        addWebViewWeatherDaysOutlookToLinearLayout(linearLayout, list, item);
                    }

                    if (item.getFavoriteFormTemplateType().equals("WeatherRegionalRadar")) {
                        addWebViewWeatherRegionalRadarToLinearLayout(linearLayout, list, item);
                    }
                }

                resizeLinearLayout(holder, linearLayout);

                holder.cvTempleteItem.addView(linearLayout);
            }
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

    private void resizeLinearLayout(ViewHolderTemplateItem holder, LinearLayout linearLayout) {
        ViewTreeObserver viewTreeObserver = holder.cvTempleteItem.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    holder.cvTempleteItem.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    holder.cvTempleteItem.getLayoutParams().height = (int) (1.3 * linearLayout.getWidth());
                    //linearLayout.getLayoutParams().height = (int)(linearLayout.getWidth() * 1.3);

                }
            });
        }
    }

    private static String changedHeaderHtml(String htmlText) {
        String head = "<head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head>";
        String closedTag = "</body></html>";
        return head + htmlText + closedTag;
    }

    private void addWebViewWeatherRegionalRadarToLinearLayout(LinearLayout linearLayout, List<FavoriteFormTemplateItem> list, FavoriteFormTemplateItem item) {
        WebView webView = new WebView(mContext);
        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setWebChromeClient(new WebChromeClient());
        String changeFontHtml = changedHeaderHtml(item.getFavoriteFormTemplateInnerHtml());
        webView.loadDataWithBaseURL(null, changeFontHtml, "text/html", "UTF-8", null);

        webView.setId(list.indexOf(item) + 300);
        linearLayout.addView(webView);
    }

    private void addWebViewWeatherDaysOutlookToLinearLayout(LinearLayout linearLayout, List<FavoriteFormTemplateItem> list, FavoriteFormTemplateItem item) {
        WebView webView = new WebView(mContext);
        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setWebChromeClient(new WebChromeClient());
        String changeFontHtml = changedHeaderHtml(item.getFavoriteFormTemplateInnerHtml());
        webView.loadDataWithBaseURL(null, changeFontHtml, "text/html", "UTF-8", null);

        webView.setId(list.indexOf(item) + 200);
        linearLayout.addView(webView);
    }

    private void addWebViewMessageToLinearLayout(LinearLayout linearLayout, List<FavoriteFormTemplateItem> list, FavoriteFormTemplateItem item) {
        if (item.getFavoriteFormTemplateLabel() != null && !item.getFavoriteFormTemplateLabel().equals("")) {
            WebView webView = new WebView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            webView.setLayoutParams(params);

            webView.loadDataWithBaseURL(null, item.getFavoriteFormTemplateLabel(), "text/html; charset=utf-8", "utf-8", null);
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

            webView.setId(list.indexOf(item) + 100);
            linearLayout.addView(webView);
        }

    }

    private void addPictureToLinearLayout(LinearLayout linearLayout, List<FavoriteFormTemplateItem> list, FavoriteFormTemplateItem item) {
        if (item.getFavoriteFormTemplateResourceUrl() != null && !item.getFavoriteFormTemplateResourceUrl().equals("")) {
            ImageView imageView = new ImageView(mContext);

            Picasso.get().load((item.getFavoriteFormTemplateResourceUrl())).fit().centerInside()
                    .placeholder(Objects.requireNonNull(mContext.getDrawable(R.drawable.ic_profile_default)))
                    .into(imageView);

            imageView.setId(list.indexOf(item));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            linearLayout.addView(imageView);
        }
    }

    private void addLabelToLinearLayout(LinearLayout linearLayout, List<FavoriteFormTemplateItem> list, FavoriteFormTemplateItem item) {
        TextView textView = new TextView(mContext);
        textView.setTextColor(mContext.getColor(R.color.black));
        textView.setText(item.getFavoriteFormTemplateLabel());
        textView.setId(list.indexOf(item));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (list.indexOf(item) == 0) params.setMargins(50, 50, 0, 50);
        else params.setMargins(50, 0, 0, 0);
        textView.setLayoutParams(params);
        linearLayout.addView(textView);
    }


    // TODO : read about "smart" viewHolders that bind data by themselves
    class ViewHolderTemplateItem extends RecyclerView.ViewHolder {

        CardView cvTempleteItem;

        ViewHolderTemplateItem(View itemView) {
            super(itemView);

            cvTempleteItem = itemView.findViewById(R.id.list_item_template_cv_catalog_entry);
        }
    }

}
