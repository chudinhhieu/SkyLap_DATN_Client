package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.GioHang;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangAdapter2 extends RecyclerView.Adapter<GioHangAdapter2.GioHangViewHolder>{
    private List<GioHang> list;
    private Context context;
    private RetrofitService retrofitService;
    private SanPhamRetrofit sanPhamRetrofit;

    public GioHangAdapter2(Context context) {
        this.context = context;
        retrofitService = new RetrofitService();
    }
    public void setList (List<GioHang> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dathang,parent,false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
        holder.showLoading();
        GioHang gioHang = list.get(position);
        if (gioHang == null) return;
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(gioHang.getIdSanPham());
        getSanPham.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                SanPham sanPham = response.body();
                holder.txt_gia.setText(String.format("%,.0f", sanPham.getGiaTien()) + "₫");
                holder.txt_name.setText(sanPham.getTenSanPham());
                Picasso.get().load(sanPham.getAnh().get(0)).into(holder.img);
                holder.txt_soLuong.setText("x "+gioHang.getSoLuong());
                holder.hiddenLoading();
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                CustomToast.showToast(context, t.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public final class GioHangViewHolder extends RecyclerView.ViewHolder{
        ProgressBar loading;
        ImageView img;
        LinearLayout view;
        TextView txt_name, txt_soLuong, txt_gia;
        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            loading = itemView.findViewById(R.id.item_datHang_loadding);
            img = itemView.findViewById(R.id.item_datHang_img);
            txt_name = itemView.findViewById(R.id.item_datHang_txtName);
            txt_soLuong = itemView.findViewById(R.id.item_datHang_txt_sl);
            txt_gia = itemView.findViewById(R.id.item_datHang_txtGia);
            view = itemView.findViewById(R.id.item_datHang_view);
        }
        public void showLoading (){
            loading.setVisibility(View.VISIBLE);
            view.setVisibility(View.GONE);
        }
        public void hiddenLoading (){
            loading.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }
    }
}
