package com.example.skylap_datn_md03;

import static android.util.Log.d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.skylap_datn_md03.adapter.HangSanPhamAdapter;
import com.example.skylap_datn_md03.adapter.SanPhamAdapter;
import com.example.skylap_datn_md03.adapter.SlideAdapter;
import com.example.skylap_datn_md03.data.models.HangSX;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.fragment.HomeFragment;
import com.example.skylap_datn_md03.ui.activities.auth.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    Button btnLogout;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        unitUI();
    }

    private void logoutUser() {
        firebaseAuth.signOut();
        googleSignInClient.signOut();
        clearSharedPreferences();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("id_user_auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("uid");
        editor.apply();
    }
    private void unitUI (){
        bottomNavigationView = findViewById(R.id.main_bottomnavigation);
       // getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_nav_home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment()).commit();
                }
                if (item.getItemId() == R.id.menu_nav_history){
                    //lich su giao dich
                }
                if (item.getItemId() == R.id.menu_nav_notification){
                    //thong bao
                }
                if (item.getItemId() == R.id.menu_nav_account){
                    // account
                }

                return true;
            }
        });
    }
}