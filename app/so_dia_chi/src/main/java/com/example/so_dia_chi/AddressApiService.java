package com.example.so_dia_chi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AddressApiService {
    @GET("api/so-dia-chi")
    Call<List<Address>> getAddresses();

    @POST("api/so-dia-chi")
    Call<Address> addAddress(@Body Address address);

    @PUT("api/so-dia-chi/{id}")
    Call<Address> updateAddress(@Path("id") String id, @Body Address address);

    @DELETE("api/so-dia-chi/{id}")
    Call<Void> deleteAddress(@Path("id") String id);
}
