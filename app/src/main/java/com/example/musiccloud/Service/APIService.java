package com.example.musiccloud.Service;

import retrofit2.Retrofit;

public class APIService {
    private static String base_url = "https://music.minhn.dev/";

    private static DataService instance = ApiRetrofitClient.getClient(base_url).create(DataService.class);

    public static DataService getService() {
        return instance;
    }
}
