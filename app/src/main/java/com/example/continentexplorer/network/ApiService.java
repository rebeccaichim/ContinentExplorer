package com.example.continentexplorer.network;

import com.example.continentexplorer.dto.CombinedScoresResponse;
import com.example.continentexplorer.dto.GuessRequest;

import com.example.continentexplorer.dto.PasswordResetRequest;
import com.example.continentexplorer.dto.ResetPasswordRequest;
import com.example.continentexplorer.dto.Score;
import com.example.continentexplorer.dto.ScoreRequestEuropa;
import com.example.continentexplorer.dto.ScoreRequestRomania;
import com.example.continentexplorer.dto.ScoreResponse;
import com.example.continentexplorer.dto.VisitedCountryRequest;
import com.example.continentexplorer.dto.VisitedCountyRequest;
import com.example.continentexplorer.model.Country;
import com.example.continentexplorer.model.County;
import com.example.continentexplorer.model.User;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    @POST("/api/users/login")
    Call<User> login(@Body User user);

    @POST("/api/users/register")
    Call<User> register(@Body User user);

    @GET("/api/counties/all")
    Call<List<County>> getAllCounties();

    @GET("/api/countries/all")
    Call<List<Country>> getAllCountries();

    @POST("/api/scores/saveEuropa")
    Call<Void> saveEuropaScore(@Body ScoreRequestEuropa scoreRequest);

    @POST("/api/scores/saveRomania")
    Call<Void> saveRomaniaScore(@Body ScoreRequestRomania scoreRequest);

    @POST("/api/visited-counties")
    Call<Void> addVisitedCounty(@Body VisitedCountyRequest visitedCountyRequest);

    @POST("/api/visited-countries")
    Call<Void> addVisitedCountry(@Body VisitedCountryRequest visitedCountryRequest);

    @GET("visited-counties/{userId}")
    Call<List<String>> getVisitedCounties(@Path("userId") long userId);

    @GET("visited-countries/{userId}")
    Call<List<String>> getVisitedCountries(@Path("userId") long userId);

    @GET("users/users/{userId}")
    Call<User> getUserProfile(@Path("userId") Long userId);

    @GET("/api/scores/{userId}")
    Call<CombinedScoresResponse> getAllScores(@Path("userId") Long userId);

    @PUT("users/users/{userId}")
    Call<Void> updateUserProfile(@Path("userId") Long userId, @Body User user);

    @POST("users/check-email")
    Call<Map<String, String>> checkEmail(@Body PasswordResetRequest request);

    @PUT("users/reset-password")
    Call<Void> resetPassword(@Body ResetPasswordRequest request);

}