package com.example.skylap_datn_md03.utils;

import static android.util.Log.d;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.skylap_datn_md03.data.models.Message;
import com.example.skylap_datn_md03.retrofitController.ChatRetrofit;
import com.example.skylap_datn_md03.retrofitController.MessageRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagePreferences {
    private int numberChat_notSeen;
    private DatabaseReference mDatabase;
    private MessageRetrofit messageRetrofit;
    private RetrofitService retrofitService;


    public  void checkChat(String idChat , TextView txtNumberUnSeenMessage , Context  context){
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        d("checkChat", "checkChat: " +  sharedPreferencesManager.getUserId());

        if (idChat.length() >  0){
            mDatabase = FirebaseDatabase.getInstance().getReference("messages");
            mDatabase.orderByChild("idChat").equalTo(idChat).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    numberChat_notSeen = 0;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Message mess = data.getValue(Message.class);
                        if (mess.isDaxem() == false && !mess.getIdAccount().equals(sharedPreferencesManager.getUserId())) {
                                numberChat_notSeen += 1;
                                d("check", "onDataChange: " +  numberChat_notSeen);


                        }else {
                            numberChat_notSeen = 0  ;
                        }


                        txtNumberUnSeenMessage.setText(""+numberChat_notSeen);

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("err", "onCancelled: " + error);
                }
            });
        }


    }

    public void putSeeNAllwhenOnclick (String idChat) {
        retrofitService = new RetrofitService();
        messageRetrofit = retrofitService.retrofit.create(MessageRetrofit.class);

        Call<Void> putSeenAll = messageRetrofit.putSeen(idChat);
        putSeenAll.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200){
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                d("MessagePre", "onFailure: " +  t.getMessage());
            }
        });
    }


}
