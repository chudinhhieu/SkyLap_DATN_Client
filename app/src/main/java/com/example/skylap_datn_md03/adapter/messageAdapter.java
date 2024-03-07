package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.Message;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import java.util.List;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.messageAdapterViewHolder>{

  private   List<Message> list ;
Context context;
    SharedPreferencesManager  sharedPreferencesManager;


    public messageAdapter(List<Message> list, Context context) {
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public messageAdapterViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new messageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder( messageAdapterViewHolder holder, int position) {
        sharedPreferencesManager = new SharedPreferencesManager(context);
        String userId = sharedPreferencesManager.getUserId();
        Drawable backgroundSender = ContextCompat.getDrawable(context, R.drawable.custom_message_sender);
        Drawable backgroundPa = ContextCompat.getDrawable(context, R.drawable.custom_message_recipients);
        Drawable backgroundUnsent = ContextCompat.getDrawable(context, R.drawable.custom_message_unsent);
        RelativeLayout.LayoutParams layoutParamsStart = (RelativeLayout.LayoutParams) holder.message_ativity_item_mess.getLayoutParams();
        RelativeLayout.LayoutParams layoutParamsEnd = (RelativeLayout.LayoutParams) holder.message_ativity_item_mess.getLayoutParams();




        Message mess = list.get(position);

          if (mess.getIdAccount().equals(userId)){
              layoutParamsEnd.addRule(RelativeLayout.ALIGN_PARENT_END);
              holder.message_ativity_item_mess.setBackground(backgroundSender);
              holder.message_ativity_item_mess.setLayoutParams(layoutParamsEnd);

             holder.text_message.setTextColor(Color.WHITE);
             holder.message_ativity_item_mess.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {
                     Log.d("check", "onLongClick: " +  mess.getId());
                     return true;
                 }
             });
          }else {
              layoutParamsStart.addRule(RelativeLayout.ALIGN_PARENT_START);
              holder.message_ativity_item_mess.setBackground(backgroundPa);
              holder.message_ativity_item_mess.setLayoutParams(layoutParamsStart);
         holder.text_message.setTextColor(Color.BLACK);
          }

          if (mess.isThuHoi()){
              holder.message_ativity_item_mess.setBackground(backgroundUnsent);
              holder.text_message.setText("unsent message");
              holder.text_message.setTextColor(Color.GRAY);
          }else {
              holder.text_message.setText(mess.getContent());
          }




    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public class messageAdapterViewHolder extends RecyclerView.ViewHolder{
        LinearLayout message_ativity_item_mess;
        TextView text_message;
        public messageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            message_ativity_item_mess = itemView.findViewById(R.id.message_ativity_item_mess);
            text_message = itemView.findViewById(R.id.text_message);
        }
    }
}
