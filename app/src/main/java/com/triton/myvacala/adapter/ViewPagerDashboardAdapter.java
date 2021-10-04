package com.triton.myvacala.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import com.triton.myvacala.R;
import com.triton.myvacala.responsepojo.MasterServiceGetlistResponse;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerDashboardAdapter extends PagerAdapter {
    private final List<MasterServiceGetlistResponse.HomeBannerListBean> homeBannerResponseArrayList;
    private Context context;
    private LayoutInflater inflater;
    private View itemView;
    MasterServiceGetlistResponse.HomeBannerListBean currentItem;
    private String TAG = "ViewPagerDashboardAdapter";

    public ViewPagerDashboardAdapter(Context context, List<MasterServiceGetlistResponse.HomeBannerListBean> homeBannerResponseArrayList){

        this.context = context;
        this.homeBannerResponseArrayList = homeBannerResponseArrayList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return homeBannerResponseArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }


    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View itemView = inflater.inflate(R.layout.sliding_image, view, false);
        ImageView imageView = itemView.findViewById(R.id.itemImage);





        try {
            String imageURL = homeBannerResponseArrayList.get(position).getHomebanner_Image();
           // Log.w(TAG,"imageURL-->"+imageURL);

            Glide.with(context)
                    .load(imageURL)
                    .into(imageView);

        } catch (NumberFormatException nfe) {
            // Handle the condition when str is not a number.
           // Log.i("nummmberfromae", "" + nfe);
        }


        view.addView(itemView);

        return itemView;

    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
