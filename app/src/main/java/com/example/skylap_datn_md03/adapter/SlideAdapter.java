package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.HangSX;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SlideAdapter extends PagerAdapter {
    private Context mContext;
    private List<HangSX> list;

    public SlideAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setList(List<HangSX> list){
        this.list = list;
        notifyDataSetChanged();

    }
    @Override
    public int getCount() {
        if (list != null) return  list.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_slide,container,false);
        ImageView img_logo = view.findViewById(R.id.item_slide_img);

        HangSX hangSX = list.get(position);
        if (hangSX != null){
            Picasso.get().load(hangSX.getLogo()).into(img_logo);
        }
        // add to view group
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // remove view
        container.removeView((View) object);
    }
}
