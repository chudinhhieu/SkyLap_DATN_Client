package com.example.skylap_datn_md03.fragment;

import static android.util.Log.d;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.HangSanPhamAdapter;
import com.example.skylap_datn_md03.adapter.SanPhamAdapter;
import com.example.skylap_datn_md03.adapter.SlideAdapter;
import com.example.skylap_datn_md03.data.models.HangSX;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.HangSxInterface;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment  {
    private ViewPager slidePager;
    private RecyclerView rcvHangSx, rcvSanPham;
    private CircleIndicator indicator;
    private HangSanPhamAdapter hangSxAdapter;
    private SanPhamAdapter sanPhamAdapter;
    private SlideAdapter slideAdapter;
    private View context;
    private Timer mTimer;
    private List<HangSX> list;
    private SanPhamRetrofit sanPhamRetrofit;
    private RetrofitService retrofitService;
    private List<SanPham> dataList = new ArrayList<>();
    private List<HangSX> dataHangSx = new ArrayList<>();
    private int limit = 10; // Số lượng dữ liệu muốn tải trong mỗi lần
    private boolean isLoadingSanPham = false; // Biến để kiểm tra xem đang tải dữ liệu hay không
    private boolean isLoadingHangSx = false; // Biến để kiểm tra xem đang tải dữ liệu hay không

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = inflater.inflate(R.layout.fragment_home, container, false);
        retrofitService = new RetrofitService();
        unitUI();
        list = getListH();
        getListSanPham();
        getListHangSx();
        configAdapter();
        autoSlideImage();
        return context;
    }
    private void getListSanPham() {
            sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
            Call<List<SanPham>> getSp = sanPhamRetrofit.getListSanPham();
            getSp.enqueue(new Callback<List<SanPham>>() {
                @Override
                public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                    isLoadingSanPham = false; // Đánh dấu đã tải xong dữ liệu
                    if (response.isSuccessful()) {
                        List<SanPham> newData = response.body();
                        // Cập nhật dữ liệu lên RecyclerView
                        sanPhamAdapter.setList(newData);
                        rcvSanPham.setAdapter(sanPhamAdapter);

                    } else {
                        Log.d("ca" + "chung", "onResponse: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<SanPham>> call, Throwable t) {
                }
            });
    }
    private void getListHangSx() {
            HangSxInterface hangSxInterface =  retrofitService.retrofit.create(HangSxInterface.class);
            Call<List<HangSX>> getList = hangSxInterface.getListHangSx();
            getList.enqueue(new Callback<List<HangSX>>() {
                @Override
                public void onResponse(Call<List<HangSX>> call, Response<List<HangSX>> response) {
                    isLoadingHangSx = false; // Đánh dấu đã tải xong dữ liệu
                    if (response.isSuccessful()) {
                        List<HangSX> newData = response.body();
                        hangSxAdapter.setList(newData);
                        rcvHangSx.setAdapter(hangSxAdapter);
                    } else {
                        // Xử lý khi có lỗi từ server
                        Log.d("ca" + "chung", "onResponse: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<HangSX>> call, Throwable t) {
                }
            });
    }
    private List<HangSX> getListH(){
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

        slideAdapter.setList(list);
        //
        slidePager.setAdapter(slideAdapter);
        indicator.setViewPager(slidePager);
        slideAdapter.registerDataSetObserver(indicator.getDataSetObserver());
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