package com.example.skylap_datn_md03.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.SanPhamAdapter;
import com.example.skylap_datn_md03.data.models.DVHCVN.Tinh;
import com.example.skylap_datn_md03.data.models.HangSX;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.HangSxInterface;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.activities.ThemDiaChiActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterFragment extends Fragment {
    private Spinner spnHang, spnBaoHanh, spnGia, spnManHinh, spnCPU, spnGPU, spnRAM, spnROM;
    private RecyclerView recyclerView;
    private RelativeLayout viewNull;
    private HangSxInterface hangSxInterface;
    private RetrofitService retrofitService;
    private SanPhamRetrofit sanPhamRetrofit;
    private List<HangSX> hangSXList;
    private SanPhamAdapter sanPhamAdapter;
    private List<SanPham> listSanPham;
    String idHangSx = null;
    String giaMin = null;
    String giaMax = null;
    String cpu = null;
    String gpu = null;
    String ram = null;
    String display = null;
    String baohanh = null;
    String rom = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        retrofitService = new RetrofitService();
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        hangSxInterface = retrofitService.retrofit.create(HangSxInterface.class);
        sanPhamAdapter = new SanPhamAdapter(getContext());
        spnCPU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = spnCPU.getSelectedItem().toString();

                switch (value) {
                    case "Tất cả":
                        cpu = null;
                        break;
                    case "Intel i3":
                        cpu = "i3";
                        break;
                    case "Intel i5":
                        cpu = "i5";
                        break;
                    case "Intel i7":
                        cpu = "i7";
                        break;
                    case "Intel i9":
                        cpu = "i9";
                        break;
                    case "AMD Ryzen 3":
                        cpu = "Ryzen 3";
                        break;
                    case "AMD Ryzen 5":
                        cpu = "Ryzen 5";
                        break;
                    case "AMD Ryzen 7":
                        cpu = "Ryzen 7";
                        break;
                    case "AMD Ryzen 9":
                        cpu = "Ryzen 9";
                        break;
                    case "Apple M1":
                        cpu = "Apple M1";
                        break;
                    case "Apple M2":
                        cpu = "Apple M2";
                        break;
                    case "Apple M3":
                        cpu = "Apple M3";
                        break;
                    case "Apple M3 Pro":
                        cpu = "Apple M3 Pro";
                        break;
                    case "Apple M3 Max":
                        cpu = "Apple M3 Max";
                        break;
                    default:
                        break;
                }
                getListSanPham();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cpu = null;
            }
        });

        spnGPU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = spnGPU.getSelectedItem().toString();
                switch (value) {
                    case "Tất cả":
                        gpu = null;
                        break;
                    case "Card rời":
                        gpu = "Có";
                        break;
                    case "Tích hợp":
                        gpu = "Không";
                        break;
                    default:
                        break;
                }
                getListSanPham();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gpu = null;
            }
        });
        spnGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = spnGia.getSelectedItem().toString();
                switch (value) {
                    case "Tất cả":
                        giaMin = null;
                        giaMax = null;
                        break;
                    case "Dưới 10 triệu":
                        giaMin = "0";
                        giaMax = "10000000";
                        break;
                    case "10 - 15 triệu":
                        giaMin = "10000000";
                        giaMax = "15000000";
                        break;
                    case "15 - 20 triệu":
                        giaMin = "15000000";
                        giaMax = "20000000";
                        break;
                    case "20 - 25 triệu":
                        giaMin = "20000000";
                        giaMax = "25000000";
                        break;
                    case "25 - 30 triệu":
                        giaMin = "25000000";
                        giaMax = "30000000";
                        break;
                    case "Trên 30 triệu":
                        giaMin = "30000000";
                        giaMax = null;
                        break;
                    default:
                        break;
                }
                getListSanPham();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                giaMin = null;
                giaMax = null;
            }
        });
        spnManHinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = spnManHinh.getSelectedItem().toString();
                if (value.equals("Tất cả")) {
                    display = null;
                } else {
                    display = value;
                }
                getListSanPham();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                display = null;
            }
        });
        spnBaoHanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = spnBaoHanh.getSelectedItem().toString();
                if (value.equals("Tất cả")) {
                    baohanh = null;
                } else {
                    baohanh = value;
                }
                getListSanPham();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                baohanh = null;
            }
        });
        spnROM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = spnROM.getSelectedItem().toString();
                if (value.equals("Tất cả")) {
                    rom = null;
                } else {
                    rom = value;
                }
                getListSanPham();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                rom = null;
            }
        });
        spnRAM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = spnRAM.getSelectedItem().toString();
                if (value.equals("Tất cả")) {
                    ram = null;
                } else {
                    ram = value;
                }
                getListSanPham();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ram = null;
            }
        });
        spnHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = spnHang.getSelectedItem().toString();
                if (value.equals("Tất cả")) {
                    idHangSx = null;
                } else {
                    for (HangSX hangSX : hangSXList) {
                        if (hangSX.getTenHangSx().equals(value)) {
                            idHangSx = hangSX.get_id();
                            break;
                        }
                    }
                }
                getListSanPham();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idHangSx = null;
            }
        });
        getHang();
        getListSanPham();
    }

    private void getListSanPham() {
        sanPhamRetrofit.filterSanPham(idHangSx, giaMin, giaMax, cpu, ram, display, baohanh, gpu, rom)
                .enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                        listSanPham = response.body();
                        if(listSanPham.isEmpty()){
                            recyclerView.setVisibility(View.GONE);
                            viewNull.setVisibility(View.VISIBLE);
                        }else{
                            sanPhamAdapter.setList(listSanPham);
                            recyclerView.setAdapter(sanPhamAdapter);
                            recyclerView.setVisibility(View.VISIBLE);
                            viewNull.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {

                    }
                });
    }

    private void getHang() {
        hangSxInterface.getListHangSx().enqueue(new Callback<List<HangSX>>() {
            @Override
            public void onResponse(Call<List<HangSX>> call, Response<List<HangSX>> response) {
                hangSXList = response.body();
                ArrayList<String> listTenHang = new ArrayList<>();
                listTenHang.add("Tất cả");
                for (HangSX hangSx : hangSXList) {
                    listTenHang.add(hangSx.getTenHangSx());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listTenHang);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnHang.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<HangSX>> call, Throwable t) {

            }
        });
    }

    private void initView(View view) {
        spnHang = view.findViewById(R.id.spinner_idHangSx);
        spnBaoHanh = view.findViewById(R.id.spinner_baohanh);
        spnGia = view.findViewById(R.id.spinner_gia);
        spnManHinh = view.findViewById(R.id.spinner_display);
        spnCPU = view.findViewById(R.id.spinner_cpu);
        spnGPU = view.findViewById(R.id.spinner_gpu);
        spnRAM = view.findViewById(R.id.spinner_ram);
        spnROM = view.findViewById(R.id.spinner_rom);
        spnROM = view.findViewById(R.id.spinner_rom);
        recyclerView = view.findViewById(R.id.rcv_filter);
        viewNull = view.findViewById(R.id.view_null_ft);
    }
}
