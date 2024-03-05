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
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.activities.SanPhamActivity;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
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
    private boolean ischecked = false;
    private int lastCheckedPosition = -1;


    // Interface để thông báo sự kiện khi checkbox thay đổi
    public interface OnTotalPriceChangedListener {
        void onTotalPriceChanged(double totalPrice);
    }


    private OnTotalPriceChangedListener onTotalPriceChangedListener;

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
                maxSL = sanPham.getSoLuong();
                holder.tvGiaSP.setText(String.format("%,.0f", sanPham.getGiaTien()) + "₫");
                holder.tvTenSP.setText(sanPham.getTenSanPham());
                Picasso.get().load(sanPham.getAnh().get(0)).into(holder.imgAnhSP);
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
                    if (onTotalPriceChangedListener != null && ischecked) {
                        ischecked = true;
                        onTotalPriceChangedListener.onTotalPriceChanged(sanPham.getGiaTien() * Integer.parseInt(holder.ipSoLuong.getText().toString().trim()));
                    }
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
                    if (onTotalPriceChangedListener != null && ischecked) {
                        ischecked = true;
                        onTotalPriceChangedListener.onTotalPriceChanged(sanPham.getGiaTien() * Integer.parseInt(holder.ipSoLuong.getText().toString().trim()));
                    }
                } else {
                    CustomToast.showToast(context, "Số lượng tối thiểu là 1");
                }
            }
        });
        InputFilter filter = new InputFilter() {
            int min = 1;

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                try {
                    // Nếu người dùng nhập giá trị mới
                    int input = Integer.parseInt(dest.toString() + source.toString());

                    // Kiểm tra xem giá trị mới có nằm trong khoảng từ 1 đến max không
                    if (input >= min && input <= maxSL) {
                        return null; // Giá trị hợp lệ, không có thay đổi
                    } else {
                        CustomToast.showToast(context, "Chỉ nhập số lượng trong khoảng từ 1 đến " + maxSL);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                // Giá trị không hợp lệ, không cho nhập
                return "";
            }
        };
// Thiết lập InputFilter cho EditText
        holder.ipSoLuong.setFilters(new InputFilter[]{filter});

//        Theo dõi trạng thái checkbox
        holder.checkBox.setOnCheckedChangeListener(null); // Remove previous listener to prevent recursive calls

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
                        ischecked = true;
                        onTotalPriceChangedListener.onTotalPriceChanged(sanPham.getGiaTien() * Integer.parseInt(holder.ipSoLuong.getText().toString().trim()));
                    }
                } else {
                    if (onTotalPriceChangedListener != null) {
                        ischecked = false;
                        onTotalPriceChangedListener.onTotalPriceChanged(0);
                    }
                }
                notifyDataSetChanged();
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
        private TextView tvTenSP, tvGiaSP;
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
        }
    }
}
