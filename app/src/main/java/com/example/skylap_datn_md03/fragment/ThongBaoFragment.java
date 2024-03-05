package com.example.skylap_datn_md03.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.ui.activities.auth.LoginActivity;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

public class ThongBaoFragment extends Fragment {
    private Button btn_dangXuat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // You can initialize the btn_dangXuat here if needed
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_bao, container, false);

        btn_dangXuat = view.findViewById(R.id.btn_dangXuat);

        btn_dangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
                sharedPreferencesManager.clearUserId();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return view;
    }
}
