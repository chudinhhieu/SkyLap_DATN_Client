package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.GioHang;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.GioHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.activities.SanPhamActivity;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {
    private List<GioHang> list;
    private Context context;
    private SanPhamRetrofit sanPhamRetrofit;
    private RetrofitService retrofitService;
    private SanPham sanPham;
    private int maxSL;

    public GioHangAdapter(List<GioHang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public GioHangAdapter.GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham_gio_hang, parent, false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.GioHangViewHolder holder, int position) {
        GioHang gioHang = list.get(position);
        int index = position;
        holder.ipSoLuong.setText(gioHang.getSoLuong() + "");
        retrofitService = new RetrofitService();
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(gioHang.getIdSanPham());
        getSanPham.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                sanPham = response.body();
                maxSL = sanPham.getSoLuong();
                holder.tvGiaSP.setText(String.format("%,.0f", sanPham.getGiaTien()) + "₫");
                holder.tvTenSP.setText(sanPham.getTenSanPham());
                Picasso.get().load(sanPham.getAnhSanPham()).into(holder.imgAnhSP);
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
            }
        });
        holder.btnCongSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ipSoLuong.getText().toString().isEmpty()) {
                    holder.ipSoLuong.setText("1");
                }
                // Lấy số lượng hiện tại từ EditText
                int currentSL = Integer.parseInt(holder.ipSoLuong.getText().toString());

                // Kiểm tra xem số lượng hiện tại có bé hơn maxSL không
                if (currentSL < maxSL) {
                    // Tăng số lượng lên 1
                    currentSL++;

                    // Cập nhật số lượng mới vào EditText
                    holder.ipSoLuong.setText(String.valueOf(currentSL));

                    holder.suaSoLuong(gioHang.get_id(),new GioHang(Integer.parseInt(holder.ipSoLuong.getText().toString().trim())));

                } else {
                    CustomToast.showToast(context, "Số lượng tối đa là " + maxSL);
                }
            }
        });

        holder.btnTruSl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.ipSoLuong.getText().toString().isEmpty()) {
                    holder.ipSoLuong.setText("1");
                }
                // Lấy số lượng hiện tại từ EditText
                int currentSL = Integer.parseInt(holder.ipSoLuong.getText().toString());
                // Kiểm tra xem số lượng hiện tại có lớn hơn 1 không
                if (currentSL > 1) {
                    // Giảm số lượng đi 1
                    currentSL--;

                    // Cập nhật số lượng mới vào EditText
                    holder.ipSoLuong.setText(String.valueOf(currentSL));
                    holder.suaSoLuong(gioHang.get_id(),new GioHang(Integer.parseInt(holder.ipSoLuong.getText().toString().trim())));
                } else {
                    CustomToast.showToast(context, "Số lượng tối thiểu là 1");
                }
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHangRetrofit gioHangRetrofit = retrofitService.retrofit.create(GioHangRetrofit.class);
                gioHangRetrofit.xoaGioHang(gioHang.get_id()).enqueue(new Callback<MyAuth>() {
                    @Override
                    public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                        MyAuth myAuth = response.body();
                        CustomToast.showToast(context,myAuth.getMessage().toString());
                        list.remove(index);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<MyAuth> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAnhSP, btnDelete, btnCongSL, btnTruSl;
        private EditText ipSoLuong;
        private TextView tvTenSP, tvGiaSP;
        private ProgressBar progressBar;
        private RelativeLayout views;

        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhSP = itemView.findViewById(R.id.item_gh_img);
            btnDelete = itemView.findViewById(R.id.item_gh_exit);
            btnCongSL = itemView.findViewById(R.id.item_gh_btn_congSL);
            btnTruSl = itemView.findViewById(R.id.item_gh_btn_truSL);
            ipSoLuong = itemView.findViewById(R.id.item_gh_ip_soLuong);
            tvTenSP = itemView.findViewById(R.id.item_gh_ten);
            tvGiaSP = itemView.findViewById(R.id.item_gh_gia);
        }
        private void suaSoLuong(String id,GioHang gioHang) {
            GioHangRetrofit gioHangRetrofit = retrofitService.retrofit.create(GioHangRetrofit.class);
            Call<GioHang> suaSoLuong = gioHangRetrofit.suaSoLuong(id, gioHang);
            suaSoLuong.enqueue(new Callback<GioHang>() {
                @Override
                public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                }

                @Override
                public void onFailure(Call<GioHang> call, Throwable t) {

                }
            });
        }
    }
}
