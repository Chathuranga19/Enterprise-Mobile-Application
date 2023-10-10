package com.ead.train_management.util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ead.train_management.R;
import com.ead.train_management.models.reservationHandlerModel;
import com.ead.train_management.models.viewBookingModel;
import com.ead.train_management.service.ReservationService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<viewBookingModel> dataList;
    private ReservationService bgService;
    public MyAdapter(List<viewBookingModel> dataList) {
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bgService =  RetrofitClient.getClient().create(ReservationService.class);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        viewBookingModel item = dataList.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewName2.setText(item.getDate());
        holder.textViewName3.setText(String.valueOf(item.getNum()));
        holder.buttonDelete.setOnClickListener(v -> {
            String itemIdToDelete = item.getId();
            reservationHandlerModel d = new reservationHandlerModel();
            d.setAcc(true);
            Log.d("abcd", itemIdToDelete);
            Call<String> data = bgService.removeBooking( item.getId(),d);

            data.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call1, Response<String> response1) {

                    if (response1.isSuccessful() && response1.body() != null) {

                        Log.d("abcd", itemIdToDelete);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Log.d("abcd", itemIdToDelete);
                }
            });

            dataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataList.size());
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;

        TextView textViewName2;
        TextView textViewName3;
        Button buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.vname);
            textViewName2 = itemView.findViewById(R.id.vdate);
            textViewName3 = itemView.findViewById(R.id.vnum);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}