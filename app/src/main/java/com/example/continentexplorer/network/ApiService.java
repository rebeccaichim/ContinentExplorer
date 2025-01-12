package com.example.continentexplorer.network;

import com.example.continentexplorer.dto.CombinedScoresResponse;
import com.example.continentexplorer.dto.GuessRequest;

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
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;


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

    @POST("/api/visited-counties")
    Call<Void> addVisitedCounty(@Body VisitedCountyRequest visitedCountyRequest);

    @POST("/api/visited-countries")
    Call<Void> addVisitedCountry(@Body VisitedCountryRequest visitedCountryRequest);


    @GET("visited-counties/{userId}")
    Call<List<String>> getVisitedCounties(@Path("userId") long userId);

    @GET("visited-countries/{userId}")
    Call<List<String>> getVisitedCountries(@Path("userId") long userId);

    @GET("scores/{userId}")
    Call<List<Score>> getUserScores(@Path("userId") Long userId);

    @GET("scores/romania/{userId}")
    Call<List<Score>> getScoresRomania(@Path("userId") Long userId);

    @GET("scores/europa/{userId}")
    Call<List<Score>> getScoresEuropa(@Path("userId") Long userId);

    @GET("users/users/{userId}")
    Call<User> getUserProfile(@Path("userId") Long userId);

    @GET("/api/scores/{userId}")
    Call<CombinedScoresResponse> getAllScores(@Path("userId") Long userId);

    @PUT("users/users/{userId}")
    Call<Void> updateUserProfile(@Path("userId") Long userId, @Body User user);

//    @PUT("/api/users/{userId}")
//    Call<Void> updateUserProfile(@Path("userId") Long userId, @Body User user);

}