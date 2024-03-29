package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.ChatRetrofit;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.activities.DanhGiaActivity;
import com.example.skylap_datn_md03.ui.activities.MessageActivity;
import com.example.skylap_datn_md03.ui.activities.SanPhamActivity;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.squareup.picasso.Picasso;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLyDonHangAdapter extends RecyclerView.Adapter<QuanLyDonHangAdapter.ReviewViewHolder> {

    private List<DonHang> list;
    private Context context;
    private SanPhamRetrofit sanPhamRetrofit;
    private RetrofitService retrofitService;
    private SanPham sanPham;
    private SharedPreferencesManager sharedPreferencesManager;
    private ChatRetrofit chatRetrofit;

    public QuanLyDonHangAdapter(List<DonHang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qldh, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        DonHang donHang = list.get(position);
        int index = position;
        for (int i = 0; i < donHang.getTrangThai().size(); i++) {
            if (donHang.getTrangThai().get(i).getIsNow() == true) {
                switch (donHang.getTrangThai().get(i).getTrangThai()) {
                    case "Chờ xác nhận":
                        holder.button.setText("Liên hệ Shop");
                        holder.moTa.setText("Đơn hàng của bạn đang chờ xác nhận và chuẩn bị hàng!");
                        break;
                    case "Chờ giao hàng":
                        holder.button.setText("Liên hệ Shop");
                        holder.moTa.setText("Đơn hàng của bạn đang chờ giao hàng!");
                        break;
                    case "Đang giao hàng":
                        holder.button.setText("Đã nhận hàng");
                        holder.moTa.setText("Đơn hàng của bạn đang chờ giao hàng!");
                        break;
                    case "Đã giao hàng":
                        if (donHang.getDanhGia() != null){
                            holder.button.setVisibility(View.GONE);
                            holder.moTa.setText("Đơn hàng của bạn đã giao hàng thành công!");
                        }else {
                            holder.button.setText("Đánh giá");
                            holder.moTa.setText("Đơn hàng của bạn đã giao hàng thành công!\nHãy đánh giá cảm nhận về sản phẩm nhé!");
                        }
                        break;
                    case "Đã hủy":
                        holder.button.setText("Mua lại");
                        holder.moTa.setText("Đơn hàng đã bị hủy!");
                        break;
                }
            }
        }

        holder.soLuong.setText("x" + donHang.getSoLuong());
        holder.tongTien.setText(String.format("%,.0f", donHang.getTongTien()) + "₫");
        sharedPreferencesManager = new SharedPreferencesManager(context);
        retrofitService = new RetrofitService();
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(donHang.getIdSanPham());
        getSanPham.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                sanPham = response.body();
                holder.gia.setText(String.format("%,.0f", sanPham.getGiaTien()) + "₫");
                holder.tenSanPham.setText(sanPham.getTenSanPham());
                Picasso.get().load(sanPham.getAnhSanPham()).into(holder.anhSanPham);
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
            }
        });

        switch (holder.button.getText().toString()) {
            case "Liên hệ Shop":
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chatRetrofit = retrofitService.retrofit.create(ChatRetrofit.class);
                        String userId = sharedPreferencesManager.getUserId();
                        Call<String> addChat = chatRetrofit.CreateConverSation(userId);
                        addChat.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.code() == 206) {
                                    Intent intent = new Intent(context, MessageActivity.class);
                                    intent.putExtra("conversation_key", response.body());
                                    context.startActivity(intent);
                                }

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                });
                break;
            case "Đã nhận hàng":
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DonHangRetrofit donHangRetrofit = retrofitService.retrofit.create(DonHangRetrofit.class);
                        Call<Void> donHangCall = donHangRetrofit.themTrangThai(donHang.get_id(), "Đã giao hàng");
                        donHangCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                list.remove(position);
                                notifyDataSetChanged();
                                CustomToast.showToast(context,"Đã nhận hàng thành công!");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                CustomToast.showToast(context,"Đã nhận hàng thất bại");
                            }
                        });
                    }
                });
                break;
            case "Đánh giá":
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DanhGiaActivity.class);
                        intent.putExtra("idDonHang", donHang.get_id());
                        intent.putExtra("idSanPham", donHang.getIdSanPham());
                        context.startActivity(intent);
                    }
                });
                break;
            case "Mua lại":
                break;
        }
    }


    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private ImageView anhSanPham;
        private Button button;
        private TextView tenSanPham, soLuong, gia, tongTien, moTa;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            anhSanPham = itemView.findViewById(R.id.itdh_img);
            tenSanPham = itemView.findViewById(R.id.itdh_ten);
            soLuong = itemView.findViewById(R.id.itdh_soLuong);
            gia = itemView.findViewById(R.id.itdh_gia);
            tongTien = itemView.findViewById(R.id.itdh_tv_tongTien);
            button = itemView.findViewById(R.id.itdh_button);
            moTa = itemView.findViewById(R.id.itdh_mota);
        }
    }
}
