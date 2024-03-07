package com.example.skylap_datn_md03.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.messageAdapter;
import com.example.skylap_datn_md03.data.models.Message;

import com.example.skylap_datn_md03.retrofitController.GioHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.MessageRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {
    Toolbar message_toolBar;
    ProgressBar message_ativity_loading;
    RecyclerView message_ativity_rcv_listmest;

    LinearLayout message_ativity_sentMess;

    private MessageRetrofit messageRetrofit;

    private RetrofitService retrofitService;
    private SharedPreferencesManager sharedPreferencesManager;
    private DatabaseReference mDatabase;
    private String conversationKey;

    private messageAdapter adapter;
    private List<Message> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        intitUi();
        conversationKey = getIntent().getStringExtra("conversation_key");
        sharedPreferencesManager = new SharedPreferencesManager(this);
        retrofitService = new RetrofitService();
        Getdata();
        message_toolBar.findViewById(R.id.message_img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        message_ativity_sentMess.findViewById(R.id.btn_sent_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mess = message_ativity_sentMess.findViewById(R.id.edt_sent_message);

                if (mess.length() > 0){
                    messageRetrofit = retrofitService.retrofit.create(MessageRetrofit.class);
                     String userId = sharedPreferencesManager.getUserId();
                        Message message = new Message();
                        message.setIdAccount(userId);
                        message.setContent(mess.getText().toString().trim());
                        message.setIdChat(conversationKey);

                        Call<Void> themTinNhan = messageRetrofit.CreateMess(message);
                        themTinNhan.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.code() == 200){
                                    Log.d("err_message_activity", "ok : " + response.body());
                                    mess.setText("");
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(mess.getWindowToken(), 0);
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("err_message_activity", "onFailure: " + t.getMessage());
                            }
                        });
                }

            }
        });

    }
        public void intitUi () {
            message_ativity_sentMess = findViewById(R.id.message_ativity_sentMess);
            message_toolBar = findViewById(R.id.message_toolBar);
            message_ativity_loading = findViewById(R.id.message_ativity_loading);
            message_ativity_rcv_listmest = findViewById(R.id.message_ativity_rcv_listmest);
        }


        private void Getdata () {
            mDatabase = FirebaseDatabase.getInstance().getReference("messages");
            mDatabase.orderByChild("idChat").equalTo(conversationKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list = new ArrayList<>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Message mess = data.getValue(Message.class);
                        list.add( mess);
                    }
                    adapter = new messageAdapter(list, MessageActivity.this);
                    message_ativity_rcv_listmest.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                    message_ativity_rcv_listmest.setAdapter(adapter);
                    message_ativity_rcv_listmest.scrollToPosition(list.size()-1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("err", "onCancelled: " + error);
                }
            });
        }

    }