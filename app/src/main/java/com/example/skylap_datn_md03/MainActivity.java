package com.example.skylap_datn_md03;

import static android.util.Log.d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.skylap_datn_md03.fragment.HomeFragment;
import com.example.skylap_datn_md03.fragment.ThongBaoFragment;
import com.example.skylap_datn_md03.ui.activities.DatHangActivity;
import com.example.skylap_datn_md03.fragment.UserFragment;
import com.example.skylap_datn_md03.ui.activities.auth.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btnLogout;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private BottomNavigationView bottomNavigationView;
    private int id_itemSelected = R.id.menu_nav_home;
    private SharedPreferences sharedPreferences;
    private boolean canCommitFragmentTransaction = true;
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
        sharedPreferences = getSharedPreferences("id_user_auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("uid");
        editor.apply();
    }
    private void unitUI () {
        sharedPreferences = getSharedPreferences("id_user_auth", Context.MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.main_bottomnavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_nav_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();
                } else if (item.getItemId() == R.id.menu_nav_history) {
                    // Add your logic for history here
                } else if (item.getItemId() == R.id.menu_nav_notification) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ThongBaoFragment()).commit();
                } else if (item.getItemId() == R.id.menu_nav_account) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new UserFragment()).commit();
                }
                return true;
            }
        });

    }
}