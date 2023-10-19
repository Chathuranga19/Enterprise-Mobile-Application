package com.ead.train_management.util;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ead.train_management.BookingListActivity;
import com.ead.train_management.R;
import com.ead.train_management.models.ReservationHandlerModel;
import com.ead.train_management.models.ViewBookingModel;
import com.ead.train_management.service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.ViewHolder> {

    private List<ViewBookingModel> dataList;
    private ReservationService bgService;
    private BookingListActivity bookingListActivity;

    public BookingListAdapter(List<ViewBookingModel> dataList, BookingListActivity bookingListActivity) {
        this.dataList = dataList;
        this.bookingListActivity = bookingListActivity;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ViewBookingModel item = dataList.get(position);
        String formattedDate = convertMongoDBDateToReadableFormat(item.getDate());

        Log.d("itemDate", item.getDate());
        holder.textViewName.setText("Name: " + item.getName());
        holder.textViewName2.setText("Date: " + formattedDate);
        holder.textViewName3.setText("Tickets: " + String.valueOf(item.getNum()));
        holder.buttonDelete.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(bookingListActivity);
            builder.setTitle("Confirm Cancellation");
            builder.setMessage("Are you sure you want to cancel this booking?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                boolean violateFlag = true;
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ReservationHandlerModel d = new ReservationHandlerModel();
                    d.updateStatus(true);
                    Log.d("itemIdToDelete", item.getId());
                    Call<String> data = bgService.deleteReservation( item.getId());

                    data.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call1, Response<String> response1) {
                            Log.d("Response", "Response from server: " + response1.body());
                                Toast.makeText(holder.itemView.getContext(), "Booking Cancellation failed", Toast.LENGTH_SHORT).show();
                                // dataList.remove(position);
                                // notifyItemRemoved(position);
                                // notifyItemRangeChanged(position, dataList.size());
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                                violateFlag = false;
                                Toast.makeText(holder.itemView.getContext(), "Booking Cancelled", Toast.LENGTH_SHORT).show();
                                dataList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, dataList.size());
                        
                        }

                    });
                }
            });

            builder.setNegativeButton("No", (dialog, which) -> {
                // User clicked No, do nothing
            });

            builder.show();
        });
    }


        private String convertMongoDBDateToReadableFormat(String inputDate) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            try {
                Date date = inputFormat.parse(inputDate);
                if (date != null) {
                    return outputFormat.format(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return inputDate;
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