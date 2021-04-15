package com.example.musiccloud.Service;

import com.example.musiccloud.Model.Album;
import com.example.musiccloud.Model.BaiHat;
import com.example.musiccloud.Model.Playlist;
import com.example.musiccloud.Model.QuangCao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("ads.php")
    Call<List<QuangCao>> GetDataBanner();

    @GET("playlistEveryday.php")
    Call<List<Playlist>> GetPlayListCurrentDay();


    @GET("album.php")
    Call<List<Album>> GetAlbumHot();

    @GET("topSong.php")
    Call<List<BaiHat>> GetBaiHatHot();

    @FormUrlEncoded
    @POST("listSong.php")
    Call<List<BaiHat>> GetDanhsachbaihattheoquangcao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("playlistEveryday.php")
    Call<List<BaiHat>> GetDanhsachbaihattheoplaylist(@Field("idPlaylist") String idplaylist);

}
