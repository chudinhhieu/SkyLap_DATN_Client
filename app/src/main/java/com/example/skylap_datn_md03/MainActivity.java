package com.example.skylap_datn_md03;

import static android.util.Log.d;
import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.Toast;


import com.example.skylap_datn_md03.fragment.home.DanhSachFragment;
import com.example.skylap_datn_md03.fragment.home.HomeFragment;
import com.example.skylap_datn_md03.fragment.home.ThongBaoFragment;
import com.example.skylap_datn_md03.fragment.home.UserFragment;
import com.example.skylap_datn_md03.ui.activities.auth.LoginActivity;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {
    Button btnLogout;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private BottomNavigationView bottomNavigationView;
    private int id_itemSelected = R.id.menu_nav_home;
    private SharedPreferences sharedPreferences;
    private boolean canCommitFragmentTransaction = true;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int ANDROID_VERSION_TIRAMISU = 33;

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
        dangKyTopicFirebase();
        if (Build.VERSION.SDK_INT >= ANDROID_VERSION_TIRAMISU) {
            askNotificationPermission();
        } else {
            // For lower Android versions, you may have fallback behavior
        }
    }
    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(POST_NOTIFICATIONS);
            }
        }
    }
    private void dangKyTopicFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("skylap")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                    }
                });
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
        FirebaseMessaging.getInstance().subscribeToTopic(sharedPreferencesManager.getUserId())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                    }
                });
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

    private void unitUI() {
        sharedPreferences = getSharedPreferences("id_user_auth", Context.MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.main_bottomnavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_nav_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();
                } else if (item.getItemId() == R.id.menu_nav_history) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new DanhSachFragment()).commit();
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