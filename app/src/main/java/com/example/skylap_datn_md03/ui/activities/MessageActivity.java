package com.example.skylap_datn_md03.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.messageAdapter;
import com.example.skylap_datn_md03.data.models.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    Toolbar message_toolBar;
    ProgressBar message_ativity_loading;
    RecyclerView message_ativity_rcv_listmest;

    private DatabaseReference mDatabase;

    private messageAdapter adapter;
    private  List<Message> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        intitUi();
        String conversationKey = getIntent().getStringExtra("conversation_key");
        message_toolBar.findViewById(R.id.message_img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("messages");
        mDatabase.orderByChild("idChat").equalTo(conversationKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for ( DataSnapshot data : snapshot.getChildren()) {
                    Message mess = data.getValue(Message.class);
                    list.add(mess);
                }
                adapter = new messageAdapter(list , MessageActivity.this);
                message_ativity_rcv_listmest.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                message_ativity_rcv_listmest.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("err", "onCancelled: " + error);
                }
        });




    }
    public void intitUi() {
        message_toolBar = findViewById(R.id.message_toolBar);
        message_ativity_loading = findViewById(R.id.message_ativity_loading);
        message_ativity_rcv_listmest = findViewById(R.id.message_ativity_rcv_listmest);
    }
   
}