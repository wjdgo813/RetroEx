package com.example.lg.retroex;

import java.io.IOException;
import java.util.List;

import retrofit.*;

/**
 * Created by LG on 2016-01-28.
 */
public class NetworkManager {

    private static final NetworkManager networkManager = new NetworkManager();

    private NetworkManager(){

    }
    public static NetworkManager getInstance(){
        return networkManager;
    }

    public <T> T getRetrofit(Class<T> aa){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.API_URL).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(aa);

    }

}
