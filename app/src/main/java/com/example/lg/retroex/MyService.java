package com.example.lg.retroex;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by LG on 2016-01-28.
 */
public interface MyService {
    // "https://apis.daum.net/search/image?apikey=b62b20a07b737c1cca5b88737980adb11809762a";

    @GET("/search/image")
    Call<SearchResult> contributors(
            @Query("apikey") String apikey,
            @Query("q") String query,
            @Query("result") int result,
            @Query("pageno") int pageno,
            @Query("output") String output
    );
}
