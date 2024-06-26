package com.example.skylap_datn_md03.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.skylap_datn_md03.data.models.BienThe;
import com.example.skylap_datn_md03.data.models.GioHang;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.GioHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.MessageRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.activities.SanPhamActivity;
import com.example.skylap_datn_md03.ui.activities.auth.LoginActivity;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Collections;
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
    private BienThe bienThe;
    private OnTotalPriceChangedListener onTotalPriceChangedListener;

    // Interface để thông báo sự kiện khi checkbox thay đổi
    public interface OnTotalPriceChangedListener {
        void onTotalPriceChanged(double totalPrice, GioHang gioHang);
    }

    public void setOnTotalPriceChangedListener(OnTotalPriceChangedListener listener) {
        this.onTotalPriceChangedListener = listener;
    }

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
                for (int i = 0; i < sanPham.getBienThe().size(); i++) {
                    if (sanPham.getBienThe().get(i).get_id().equals(gioHang.getIdBienThe())) {
                        bienThe = sanPham.getBienThe().get(i);
                        maxSL = sanPham.getBienThe().get(i).getSoLuong();
                        holder.tvRamRom.setText(sanPham.getBienThe().get(i).getRam()+ " + "+sanPham.getBienThe().get(i).getRom());
                        holder.tvGiaSP.setText(String.format("%,.0f", sanPham.getBienThe().get(i).getGiaTien()) + "₫");
                    }
                }
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
                    holder.suaSoLuong(gioHang.get_id(), currentSL, holder.ipSoLuong);
                    if (list.get(index).isChecked()) {
                        if (onTotalPriceChangedListener != null) {
                            GioHang gh = list.get(index);
                            Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(gh.getIdSanPham());
                            getSanPham.enqueue(new Callback<SanPham>() {
                                @Override
                                public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                                    for (int i = 0; i < response.body().getBienThe().size(); i++) {
                                        if (response.body().getBienThe().get(i).get_id().equals(gioHang.getIdBienThe())) {
                                            onTotalPriceChangedListener.onTotalPriceChanged(response.body().getBienThe().get(i).getGiaTien() * gh.getSoLuong(), gh);
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<SanPham> call, Throwable t) {
                                }
                            });
                        }
                    }
                    list.get(index).setSoLuong(currentSL);
                    notifyDataSetChanged();
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
                    holder.suaSoLuong(gioHang.get_id(), currentSL, holder.ipSoLuong);
                    if (list.get(index).isChecked()) {
                        if (onTotalPriceChangedListener != null) {
                            GioHang gh = list.get(index);
                            Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(gh.getIdSanPham());
                            getSanPham.enqueue(new Callback<SanPham>() {
                                @Override
                                public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                                    for (int i = 0; i < response.body().getBienThe().size(); i++) {
                                        if (response.body().getBienThe().get(i).get_id().equals(gioHang.getIdBienThe())) {
                                            onTotalPriceChangedListener.onTotalPriceChanged(response.body().getBienThe().get(i).getGiaTien() * gh.getSoLuong(), gh);
                                        }
                                    }                                }

                                @Override
                                public void onFailure(Call<SanPham> call, Throwable t) {
                                }
                            });
                        }
                    }
                    list.get(index).setSoLuong(currentSL);
                    notifyDataSetChanged();
                } else {
                    CustomToast.showToast(context, "Số lượng tối thiểu là 1");
                }
            }
        });

//        Theo dõi trạng thái checkbox
        holder.checkBox.setOnCheckedChangeListener(null);

        holder.checkBox.setChecked(list.get(index).isChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                for (int i = 0; i < list.size(); i++) {
                    if (i != index) {
                        list.get(i).setChecked(false);
                    }
                }
                list.get(index).setChecked(isChecked);
                if (isChecked) {
                    if (onTotalPriceChangedListener != null) {
                        GioHang gh = list.get(index);
                        Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(gh.getIdSanPham());
                        getSanPham.enqueue(new Callback<SanPham>() {
                            @Override
                            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                                for (int i = 0; i < response.body().getBienThe().size(); i++) {
                                    if (response.body().getBienThe().get(i).get_id().equals(gioHang.getIdBienThe())) {
                                        onTotalPriceChangedListener.onTotalPriceChanged(response.body().getBienThe().get(i).getGiaTien() * gh.getSoLuong(), gh);
                                    }
                                }                            }

                            @Override
                            public void onFailure(Call<SanPham> call, Throwable t) {
                            }
                        });
                    }
                } else {
                    if (onTotalPriceChangedListener != null) {
                        onTotalPriceChangedListener.onTotalPriceChanged(0, null);
                    }
                }
                notifyDataSetChanged();
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_check);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tvNoiDung = dialog.findViewById(R.id.dal_noiDung);
                TextView tvTieuDe = dialog.findViewById(R.id.dal_tieuDe);
                Button btnHuy = dialog.findViewById(R.id.dal_btnHuy);
                Button btnOk = dialog.findViewById(R.id.dal_btnOk);
                tvTieuDe.setText("Thông báo");
                tvNoiDung.setText("Bạn chắc chắn muốn xóa giỏ hàng?");
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GioHangRetrofit gioHangRetrofit = retrofitService.retrofit.create(GioHangRetrofit.class);
                        gioHangRetrofit.xoaGioHang(list.get(index).get_id()).enqueue(new Callback<MyAuth>() {
                            @Override
                            public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                                CustomToast.showToast(context, "Xóa giỏ hàng thành công!");
                                list.remove(index);
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<MyAuth> call, Throwable t) {

                            }
                        });
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private ImageView imgAnhSP, btnDelete, btnCongSL, btnTruSl;
        private EditText ipSoLuong;
        private TextView tvTenSP, tvGiaSP, tvRamRom;
        private ProgressBar progressBar;
        private RelativeLayout views;

        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.item_gh_chk);
            imgAnhSP = itemView.findViewById(R.id.item_gh_img);
            btnDelete = itemView.findViewById(R.id.item_gh_exit);
            btnCongSL = itemView.findViewById(R.id.item_gh_btn_congSL);
            btnTruSl = itemView.findViewById(R.id.item_gh_btn_truSL);
            ipSoLuong = itemView.findViewById(R.id.item_gh_ip_soLuong);
            tvTenSP = itemView.findViewById(R.id.item_gh_ten);
            tvGiaSP = itemView.findViewById(R.id.item_gh_gia);
            tvRamRom = itemView.findViewById(R.id.item_gh_ram_rom);

        }

        private void suaSoLuong(String id, int soLuong, TextView textView) {
            GioHangRetrofit gioHangRetrofit = retrofitService.retrofit.create(GioHangRetrofit.class);
            Call<GioHang> suaSoLuong = gioHangRetrofit.suaSoLuong(id, new GioHang(soLuong));
            suaSoLuong.enqueue(new Callback<GioHang>() {
                @Override
                public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                    textView.setText(soLuong + "");
                }

                @Override
                public void onFailure(Call<GioHang> call, Throwable t) {
                }
            });
        }
    }
    public void reverseList() {
        Collections.reverse(list);
        notifyDataSetChanged();
    }
}