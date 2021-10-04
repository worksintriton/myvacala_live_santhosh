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
import com.triton.myvacala.responsepojo.SplashScreenListResponse;

import java.util.List;


public class ViewPagerSplashAdapter extends PagerAdapter {
    private  List<SplashScreenListResponse.DataBean> homeBannerResponseArrayList;
    private Context context;
    private LayoutInflater inflater;
    private View itemView;
    SplashScreenListResponse.DataBean currentItem;
    private String TAG = "ViewPagerSplashAdapter";

    public ViewPagerSplashAdapter(Context context, List<SplashScreenListResponse.DataBean> homeBannerResponseArrayList){

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
            String imageURL = homeBannerResponseArrayList.get(position).getGif_path();
            Log.w(TAG,"imageURL-->"+imageURL);

            Glide.with(context)
                    .load(imageURL)
                    .into(imageView);

        } catch (NumberFormatException nfe) {
            // Handle the condition when str is not a number.
            Log.i("nummmberfromae", "" + nfe);
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
