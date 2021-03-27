package com.example.musiccloud.Service;

import com.example.musiccloud.Model.Playlist;
import com.example.musiccloud.Model.Quangcao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {
    @GET("ads.php")
    Call<List<Quangcao>> GetDataBanner();

    @GET("playlistEveryday.php")
    Call<List<Playlist>> GetPlayListCurrentDay();
}
