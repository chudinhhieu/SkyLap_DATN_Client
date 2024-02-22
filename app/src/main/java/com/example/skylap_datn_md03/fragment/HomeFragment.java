package com.example.skylap_datn_md03.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.HangSanPhamAdapter;
import com.example.skylap_datn_md03.adapter.SanPhamAdapter;
import com.example.skylap_datn_md03.adapter.SlideAdapter;
import com.example.skylap_datn_md03.data.models.HangSX;
import com.example.skylap_datn_md03.data.models.SanPham;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private ViewPager slidePager;
    private RecyclerView rcvHangSx, rcvSanPham;
    private CircleIndicator indicator;
    private HangSanPhamAdapter hangSxAdapter;
    private SanPhamAdapter sanPhamAdapter;
    private SlideAdapter slideAdapter;
    private View context;
    private Timer mTimer;
    private List<HangSX> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = inflater.inflate(R.layout.fragment_home, container, false);
        unitUI();
        list = getListH();
        configAdapter();
        autoSlideImage();
        return context;
    }
    private List<SanPham> getListP(){
        List<SanPham> list =  new ArrayList<>();
        for (int i = 0; i< 10; i++){
            SanPham sanPham = new SanPham();
            sanPham.setTenSanPham("macbook pro 20 , 1T");
            sanPham.setCpu("9nhan 20 luong");
            sanPham.setGpu("7d92jddd");
            sanPham.setGiaTien(1231232323);
            sanPham.setDisplay("am dep khong che");
            List<String> listA = new ArrayList<>();
            listA.add(new String("https://img.thuthuat123.com/uploads/2019/07/12/hinh-anh-thien-nhien-cho-man-hinh-window_085325853.jpg"));
            sanPham.setAnh(listA);
            list.add(sanPham);
            //
            SanPham sanPham2 = new SanPham();
            sanPham2.setTenSanPham("macbook pro 20 , 1T");
            sanPham2.setCpu("9nhan 20 luong");
            sanPham2.setGpu("7d92jddd");
            sanPham2.setGiaTien(2000000);
            sanPham2.setDisplay("am dep Với cách tạo dáng này, bạn có thể áp dụng ở hầu hết các không gian chụp ảnh ngoại cảnh. Bạn chỉ cần đặt một tay lên mặt để chặn ánh nắng trực tiếp,");
            listA.add(new String("https://img.thuthuat123.com/uploads/2019/07/12/hinh-anh-thien-nhien-cho-man-hinh-window_085325853.jpg"));
            sanPham2.setAnh(listA);
            list.add(sanPham2);

        }
        return list;
    }
    private List<HangSX> getListH(){
        List<HangSX> list =  new ArrayList<>();
        list.add(new HangSX("1","dell","https://upload.wikimedia.org/wikipedia/commons/0/03/Lenovo_Global_Corporate_Logo.png", true));
        list.add(new HangSX("1","dell","https://img.thuthuat123.com/uploads/2019/07/12/hinh-anh-thien-nhien-cho-man-hinh-window_085325853.jpg", true));
        list.add(new HangSX("1","dell","https://www.shutterstock.com/image-vector/chattogram-bangladesh-may-9-2023-600nw-2300365877.jpg", true));
        list.add(new HangSX("1","dell","https://inkythuatso.com/uploads/thumbnails/800/2021/11/logo-asus-inkythuatso-2-01-26-09-21-11.jpg", true));
        list.add(new HangSX("1","dell","https://inkythuatso.com/uploads/thumbnails/800/2021/11/logo-asus-inkythuatso-2-01-26-09-21-11.jpg", true));
        return list;
    }
    private void unitUI (){
        slidePager = context.findViewById(R.id.fragment_home_viewpager_slide);
        rcvHangSx = context.findViewById(R.id.fragment_home_rcv_listHang);
        rcvSanPham = context.findViewById(R.id.fragment_home_rcv_listProduct);
        indicator = context.findViewById(R.id.fragment_home_indicator);

    }
    private void configAdapter (){
        hangSxAdapter = new HangSanPhamAdapter(getContext());
        sanPhamAdapter = new SanPhamAdapter(getContext());
        slideAdapter = new SlideAdapter(getContext());
        //
        hangSxAdapter.setList(getListH());
        sanPhamAdapter.setList(getListP());
        slideAdapter.setList(list);
        //
        slidePager.setAdapter(slideAdapter);
        indicator.setViewPager(slidePager);
        slideAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        rcvSanPham.setAdapter(sanPhamAdapter);
        rcvHangSx.setAdapter(hangSxAdapter);
    }
    private void autoSlideImage (){
        if (list == null || list.isEmpty() || slidePager == null){
            return;
        }
        if (mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new TimerTask() {
                    @Override
                    public void run() {
                        int currentItem = slidePager.getCurrentItem();
                        int totalItem = list.size() -1;
                        if (currentItem < totalItem){
                            currentItem++;
                            slidePager.setCurrentItem(currentItem);
                        }
                        else{
                            slidePager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }
}