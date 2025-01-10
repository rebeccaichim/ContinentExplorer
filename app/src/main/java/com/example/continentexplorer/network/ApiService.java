package com.example.continentexplorer.network;

import com.example.continentexplorer.dto.GuessRequest;

import com.example.continentexplorer.dto.ScoreRequestEuropa;
import com.example.continentexplorer.dto.ScoreRequestRomania;
import com.example.continentexplorer.dto.ScoreResponse;
import com.example.continentexplorer.model.Country;
import com.example.continentexplorer.model.County;
import com.example.continentexplorer.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;


public interface ApiService {

    @GET("users")
    Call<List<User>> getAllUsers();

    @POST("users")
    Call<User> createUser(@Body User user);

    @POST("/api/users/login")
    Call<User> login(@Body User user);

    @POST("/api/users/register")
    Call<User> register(@Body User user);



    @GET("/api/counties/random")
    Call<County> getRandomCounty();

    @GET("/api/counties/all")
    Call<List<County>> getAllCounties();


    @POST("/api/game/submit-guess")
    Call<ScoreResponse> submitGuess(@Body GuessRequest guessRequest);

//    @POST("/api/scores/save")
//    Call<Void> saveScore(@Body ScoreRequest scoreRequest);

    @GET("/api/countries/random")
    Call<Country> getRandomCountry();

    @GET("/api/countries/all")
    Call<List<Country>> getAllCountries();

    @POST("/api/scores/saveEuropa")
    Call<Void> saveEuropaScore(@Body ScoreRequestEuropa scoreRequest);


    @POST("/api/scores/saveRomania")
    Call<Void> saveRomaniaScore(@Body ScoreRequestRomania scoreRequest);




//    @POST("/api/users/profile")
//    Call<User> getProfile(@Header("Authorization") String token);
}