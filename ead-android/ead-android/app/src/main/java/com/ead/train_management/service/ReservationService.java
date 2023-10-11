package com.ead.train_management.service;

import com.ead.train_management.models.ReservationModel;
import com.ead.train_management.models.ReservationHandlerModel;
import com.ead.train_management.models.TravelModel;
import com.ead.train_management.models.ViewBookingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * This interface defines services related to booking and reservations
 */
public interface ReservationService {

    // Get list of available trains
    @GET("api/Train")
    Call<List<TravelModel>> fetchTrainData();

    // Get booking details for a user
    @GET("api/Reservation/{id}")
    Call<List<ViewBookingModel>> reservationFetcher(@Path("id") String nic);

    // Remove a booking
    @PUT("api/Reservation/{id}")
    Call<String> deleteReservation(@Path("id") String id ,@Body ReservationHandlerModel db);

    // Create a new booking
    @POST("api/Reservation")
    Call<String> makeReservation(@Body ReservationModel u);

}
