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
import com.ead.train_management.models.ReservationHandlerModel;
import com.ead.train_management.models.ViewBookingModel;
import com.ead.train_management.service.ReservationService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.ViewHolder> {

    private List<ViewBookingModel> dataList;
    private ReservationService bgService;
    public BookingListAdapter(List<ViewBookingModel> dataList) {
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bgService =  RetrofitManager.getClient().create(ReservationService.class);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewBookingModel item = dataList.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewName2.setText(item.getDate());
        holder.textViewName3.setText(String.valueOf(item.getNum()));
        holder.buttonDelete.setOnClickListener(v -> {
            String itemIdToDelete = item.getId();
            ReservationHandlerModel d = new ReservationHandlerModel();
            d.updateStatus(true);
            Log.d("abcd", itemIdToDelete);
            Call<String> data = bgService.deleteReservation( item.getId(),d);

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