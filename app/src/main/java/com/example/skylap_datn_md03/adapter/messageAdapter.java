package com.example.skylap_datn_md03.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.Message;
import com.example.skylap_datn_md03.retrofitController.MessageRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;

import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.messageAdapterViewHolder>{
MessageRetrofit messageRetrofit;

    private RetrofitService retrofitService;
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
        retrofitService = new RetrofitService();
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
                     bottom_sheet_message(mess , v.getContext());
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
              holder.text_message.setText("Bạn đã thu hồi một tin nhắn");
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

    private void bottom_sheet_message(Message message  , Context context){
       View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_message , null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        LinearLayout view_remove_message = view.findViewById(R.id.view_remove_message);
       view_remove_message.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               View view = LayoutInflater.from(context).inflate(R.layout.dialog_err_remove_message, null);
               builder.setView(view);


               TextView txt_title_delete = view.findViewById(R.id.txt_title_delete);
               Button btn_no_dialog = view.findViewById(R.id.btn_no_dialog);
               Button btn_yes_dialog = view.findViewById(R.id.btn_yes_dialog);

               txt_title_delete.setText("REMOVE MESSAGE");
               AlertDialog alertDialog = builder.create();
               btn_no_dialog.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       bottomSheetDialog.dismiss();
                        alertDialog.dismiss();

                   }
               });

               btn_yes_dialog.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                      messageRetrofit = retrofitService.retrofit.create(MessageRetrofit.class);
                       Call<Void> revokeMess  = messageRetrofit.revokeMess(message.getId());
                       revokeMess.enqueue(new Callback<Void>() {
                           @Override
                           public void onResponse(Call<Void> call, Response<Void> response) {
                               if(response.code() == 200){
                                   bottomSheetDialog.dismiss();
                                   alertDialog.dismiss();
                               }
                           }

                           @Override
                           public void onFailure(Call<Void> call, Throwable t) {
                               Log.d("err", "onFailure: " +  t.getMessage());
                           }
                       });
                   }
               });

              alertDialog.show();
           }
       });
        bottomSheetDialog.show();
    }
}
