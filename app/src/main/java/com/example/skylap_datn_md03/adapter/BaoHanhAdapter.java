package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.skylap_datn_md03.ui.activities.ChiTietBaoHanhActivity;
import com.example.skylap_datn_md03.ui.activities.ChiTietDonHangActivity;
import com.example.skylap_datn_md03.ui.activities.DanhGiaActivity;
import com.example.skylap_datn_md03.ui.activities.MessageActivity;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaoHanhAdapter extends RecyclerView.Adapter<BaoHanhAdapter.BaoHanhViewHolder> {

    private List<BaoHanh> list;
    private Context context;
    private SanPhamRetrofit sanPhamRetrofit;
    private RetrofitService retrofitService;
    private SanPham sanPham;
    private SharedPreferencesManager sharedPreferencesManager;
    private ChatRetrofit chatRetrofit;

    private View view;

    public BaoHanhAdapter(List<BaoHanh> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BaoHanhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baohanh, parent, false);
        return new BaoHanhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaoHanhViewHolder holder, int position) {
        BaoHanh baoHanh = list.get(position);
        int index = position;
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
        holder.thoiGian.setText(dateFormat.format(baoHanh.getThoiGian()));
        switch (baoHanh.getTinhTrang()) {
            case 1:
                holder.button.setText("Liên hệ Shop");
                holder.moTa.setText("Vui lòng chờ cửa hàng phản hồi!");
                break;
            case 2:
                holder.button.setText("Liên hệ Shop");
                holder.moTa.setText("Cửa hàng đã chấp nhận bảo hành!");
                break;
            case 3:
                holder.button.setText("Liên hệ Shop");
                holder.moTa.setText("Cửa hành đã từ chối bảo hành!");
                break;
        }

        sharedPreferencesManager = new SharedPreferencesManager(context);
        retrofitService = new RetrofitService();
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(baoHanh.getIdSanPham());
        getSanPham.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                sanPham = response.body();
                holder.tenSanPham.setText(sanPham.getTenSanPham());
                Picasso.get().load(sanPham.getAnhSanPham()).into(holder.anhSanPham);
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietBaoHanhActivity.class);
                intent.putExtra("idbh", list.get(index).get_id());
                context.startActivity(intent);
            }
        });
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
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public static class BaoHanhViewHolder extends RecyclerView.ViewHolder {
        private ImageView anhSanPham;
        private Button button;
        private TextView tenSanPham,moTa,thoiGian;

        public BaoHanhViewHolder(View itemView) {
            super(itemView);
            anhSanPham = itemView.findViewById(R.id.itbh_img);
            tenSanPham = itemView.findViewById(R.id.itbh_ten);
            button = itemView.findViewById(R.id.itbh_button);
            moTa = itemView.findViewById(R.id.itbh_mota);
            thoiGian = itemView.findViewById(R.id.itbh_thoiGian);
        }
    }
}
