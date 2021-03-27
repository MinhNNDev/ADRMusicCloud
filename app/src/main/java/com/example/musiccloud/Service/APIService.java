package com.example.musiccloud.Service;

public class APIService {
    private static String base_url = "https://music.minhn.dev/";

    public static DataService getService() {
        return ApiRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
