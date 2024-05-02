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
import com.example.skylap_datn_md03.data.models.BaoHanh;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.ChatRetrofit;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.activities.ChiTietDonHangActivity;
import com.example.skylap_datn_md03.ui.activities.DanhGiaActivity;
import com.example.skylap_datn_md03.ui.activities.MessageActivity;
import com.example.skylap_datn_md03.ui.activities.SanPhamActivity;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.DateUtils;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.example.skylap_datn_md03.utils.WarrantyCalculator;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Collections;
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

    private View view;

    public QuanLyDonHangAdapter(List<DonHang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qldh, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        DonHang donHang = list.get(position);
        int index = position;
        sharedPreferencesManager = new SharedPreferencesManager(context);
        retrofitService = new RetrofitService();
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(donHang.getIdSanPham());
        getSanPham.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                sanPham = response.body();
                for (int i = 0; i < sanPham.getBienThe().size(); i++) {
                    if (sanPham.getBienThe().get(i).get_id().equals(donHang.getIdBienThe())) {
                        holder.ramRom.setText(sanPham.getBienThe().get(i).getRam() + " + " + sanPham.getBienThe().get(i).getRom());
                        holder.gia.setText(String.format("%,.0f", sanPham.getBienThe().get(i).getGiaTien()) + "₫");
                    }
                }
                holder.tenSanPham.setText(sanPham.getTenSanPham());
                Picasso.get().load(sanPham.getAnhSanPham()).into(holder.anhSanPham);

                for (int i = 0; i < donHang.getTrangThai().size(); i++) {
                    if (donHang.getTrangThai().get(i).getIsNow() == true) {
                        switch (donHang.getTrangThai().get(i).getTrangThai()) {
                            case "Chờ xác nhận":
                                holder.button.setVisibility(View.VISIBLE);
                                holder.button.setText("Liên hệ Shop");
                                holder.moTa.setText("Đơn hàng của bạn đang chờ xác nhận và chuẩn bị hàng!");
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
                            case "Chờ giao hàng":
                                holder.button.setVisibility(View.VISIBLE);
                                holder.button.setText("Liên hệ Shop");
                                holder.moTa.setText("Đơn hàng của bạn đang chờ giao hàng!");
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
                            case "Đang giao hàng":
                                holder.button.setVisibility(View.VISIBLE);
                                holder.button.setText("Đã nhận hàng");
                                holder.moTa.setText("Đơn hàng của bạn đang chờ giao hàng!");
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
                                                CustomToast.showToast(context, "Đã nhận hàng thành công!");
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                CustomToast.showToast(context, "Đã nhận hàng thất bại");
                                            }
                                        });
                                    }
                                });
                                break;
                            case "Đã giao hàng":

                                if (donHang.getDanhGia() != null && WarrantyCalculator.remainingWarrantyTime(donHang.getTrangThai().get(i).getThoiGian(), sanPham.getBaohanh()) > 0) {
                                    List<BaoHanh> baoHanhList = donHang.getBaoHanh();
                                    ArrayList<String> bhList = new ArrayList<>();
                                    for (BaoHanh bh : baoHanhList) {
                                        if (bh.getTinhTrang() == 0 && WarrantyCalculator.remainingWarrantyTime(bh.getThoiGian(), sanPham.getBaohanh()) > 0) {
                                            bhList.add(bh.getImei());
                                        }
                                    }
                                    if (bhList.size() == 0) {
                                        holder.button.setVisibility(View.GONE);
                                        holder.moTa.setText("Đơn hàng đã được giao thành công!");
                                    } else {
                                        holder.button.setVisibility(View.GONE);
                                        holder.moTa.setText("Bạn có thể yêu cầu bảo hành nếu có lỗi trong vòng " + WarrantyCalculator.remainingWarrantyTime(donHang.getTrangThai().get(i).getThoiGian(), sanPham.getBaohanh()) + " ngày nữa");
                                    }

                                } else if (donHang.getDanhGia() == null && DateUtils.getDaysDifference(donHang.getTrangThai().get(i).getThoiGian()) >= -15 && DateUtils.getDaysDifference(donHang.getTrangThai().get(i).getThoiGian()) <= 0 ) {
                                    holder.button.setVisibility(View.VISIBLE);
                                    holder.button.setText("Đánh giá");
                                    holder.moTa.setText("Bạn còn có " + (DateUtils.getDaysDifference(donHang.getTrangThai().get(i).getThoiGian())+15) + " ngày để đánh giá cảm nhận về sản phẩm nhé!");
                                    holder.button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(context, DanhGiaActivity.class);
                                            intent.putExtra("idDonHang", donHang.get_id());
                                            intent.putExtra("idSanPham", donHang.getIdSanPham());
                                            context.startActivity(intent);
                                        }
                                    });
                                } else if (donHang.getDanhGia() == null && WarrantyCalculator.remainingWarrantyTime(donHang.getTrangThai().get(i).getThoiGian(), sanPham.getBaohanh()) > 0) {
                                    List<BaoHanh> baoHanhList = donHang.getBaoHanh();
                                    ArrayList<String> bhList = new ArrayList<>();
                                    for (BaoHanh bh : baoHanhList) {
                                        if (bh.getTinhTrang() == 0 && WarrantyCalculator.remainingWarrantyTime(bh.getThoiGian(), sanPham.getBaohanh()) > 0) {
                                            bhList.add(bh.getImei());
                                        }
                                    }
                                    if (bhList.size() == 0) {
                                        holder.button.setVisibility(View.GONE);
                                        holder.moTa.setText("Đơn hàng đã được giao thành công!");
                                    } else {
                                        holder.button.setVisibility(View.GONE);
                                        holder.moTa.setText("Bạn có thể yêu cầu bảo hành nếu có lỗi trong vòng " + WarrantyCalculator.remainingWarrantyTime(donHang.getTrangThai().get(i).getThoiGian(), sanPham.getBaohanh()) + " ngày nữa");
                                    }
                                } else {
                                    holder.button.setVisibility(View.GONE);
                                    holder.moTa.setText("Đơn hàng đã được giao thành công!");
                                }

                                break;
                            case "Đã hủy":
                                holder.button.setVisibility(View.VISIBLE);
                                holder.button.setText("Mua lại");
                                holder.moTa.setText("Đơn hàng đã bị hủy!");
                                break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
            }
        });

        holder.soLuong.setText("x" + donHang.getSoLuong());
        holder.tongTien.setText(String.format("%,.0f", donHang.getTongTien()) + "₫");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietDonHangActivity.class);
                intent.putExtra("DonHang", list.get(index).get_id());
                context.startActivity(intent);
            }
        });
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
        private TextView tenSanPham, soLuong, gia, tongTien, moTa, ramRom;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            anhSanPham = itemView.findViewById(R.id.itdh_img);
            tenSanPham = itemView.findViewById(R.id.itdh_ten);
            soLuong = itemView.findViewById(R.id.itdh_soLuong);
            gia = itemView.findViewById(R.id.itdh_gia);
            tongTien = itemView.findViewById(R.id.itdh_tv_tongTien);
            button = itemView.findViewById(R.id.itdh_button);
            moTa = itemView.findViewById(R.id.itdh_mota);
            ramRom = itemView.findViewById(R.id.itdh_ram_rom);
        }
    }
    // Phương thức để đảo ngược danh sách
    public void reverseList() {
        Collections.reverse(list);
        notifyDataSetChanged();
    }
}
