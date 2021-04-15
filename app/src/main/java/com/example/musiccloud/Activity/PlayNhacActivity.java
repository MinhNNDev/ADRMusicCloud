package com.example.musiccloud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.musiccloud.Adapter.ViewPagerPlaylistNhac;
import com.example.musiccloud.Fragment.Fragment_Dia_Nhac;
import com.example.musiccloud.Fragment.Fragment_Playlist;
import com.example.musiccloud.Model.BaiHat;
import com.example.musiccloud.R;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity {

    Toolbar toolbarplaynhac;
    TextView txtTimesong, txtTotaltimesong;

    SeekBar sktime;
    ImageButton imgPlay, imgRepeat, imgNext, imgPre, imgRandom;
    ViewPager viewPagerplaynhac;
    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();
    public static ViewPagerPlaylistNhac adapternhac;
    Fragment_Dia_Nhac fragment_dia_nhac = new Fragment_Dia_Nhac();
    Fragment_Playlist fragment_playlist;
    MediaPlayer mediaPlayer;

    private Handler mHandler = new Handler();

    int position = 0;
    boolean repeat = false;
    boolean checkrandom = false;
    boolean next = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        Intent intent = getIntent();
        if (intent.hasExtra("cakhuc")) {
            BaiHat baihat = intent.getParcelableExtra("cakhuc");
            mangbaihat.add(baihat);
        }
        init();
        eventClick();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (intent.hasExtra("cacbaihat")) {
            ArrayList<BaiHat> mangbaihat = intent.getParcelableArrayListExtra("cacbaihat");
            for (int i = 0; i < mangbaihat.size(); i++) {
                Log.d("BBB", mangbaihat.get(i).getTenBaiHat());
            }
        }


        PlayNhacActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    sktime.setProgress(mCurrentPosition);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                }
                mHandler.postDelayed(this, 1000);
            }
        });

        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }
        });
    }

    private void eventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapternhac.getCount() > 0 && adapternhac.getItem(0) != null) {
                    if (mangbaihat.size() > 0) {
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(0).getHinhBaiHat());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 3000);
                    }
                }
            }
        }, 500);


        // ---Bắt sự kiện mấy cái nút nhạc

        //-- Nuts Play
        imgPlay.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                imgPlay.setImageResource(R.drawable.iconplay);
                if (fragment_dia_nhac.objectAnimator != null) {
                    fragment_dia_nhac.objectAnimator.pause();
                }
            } else {
                mediaPlayer.start();
                imgPlay.setImageResource(R.drawable.iconpause);
                if (fragment_dia_nhac.objectAnimator != null) {
                    fragment_dia_nhac.objectAnimator.resume();
                }
            }
        });
        // -- Nút lặp
        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    if (repeat == false) {
                        if (checkrandom == true) {
                            checkrandom = false;
                            imgRepeat.setImageResource(R.drawable.iconsyned);
                            imgRandom.setImageResource(R.drawable.iconsuffle);
                        }

                        imgRepeat.setImageResource(R.drawable.iconsyned);
                        repeat = true;
                    } else {
                        imgRepeat.setImageResource(R.drawable.iconrepeat);
                        repeat = false;
                    }
                }
            }
        });

        // -- Nút chạy ngẫu nhiên
        imgRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkrandom == false) {
                    if (repeat == true) {
                        repeat = false;
                        imgRandom.setImageResource(R.drawable.iconshuffled);
                        imgRepeat.setImageResource(R.drawable.iconrepeat);
                    }

                    imgRandom.setImageResource(R.drawable.iconshuffled);
                    checkrandom = true;
                } else {
                    imgRandom.setImageResource(R.drawable.iconsuffle);
                    checkrandom = false;
                }
            }
        });

        // -- cập nhật thanh seekbar
        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        // Nút next
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (mangbaihat.size())) {
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position) {
                                position = index - 1; // để index đừng vượt qua giá trị của mảng
                            }
                            position = index;
                        }
                        if (position > (mangbaihat.size() - 1)) {
                            position = 0;
                        }
                        new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                        Update();
                    }
                }

                // tránh việc click quá nhiều gây crack app
                imgPre.setClickable(false);
                imgNext.setClickable(false);

                Handler handler1 = new Handler(); // dùng để thực thi
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 2000); // thực thi sau 2s
            }
        });

        // -- Nút Previous bài hát

        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if (position < (mangbaihat.size())) {
                    imgPlay.setImageResource(R.drawable.iconpause);
                    position--;

                    // trường hợp position đang ở bài đầu tiên
                    if (position < 0) {
                        position = mangbaihat.size() - 1;
                    }

                    if (repeat == true) {
                        position += 1;
                    }
                    if (checkrandom == true) {
                        Random random = new Random();
                        int index = random.nextInt(mangbaihat.size());
                        if (index == position) {
                            position = index - 1;
                        }
                        position = index;
                    }

                    new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
                    fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                    getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                    Update();
                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 2000);
            }
        });
    }

    private void init() {
        toolbarplaynhac = findViewById(R.id.toolbarplaynhac);
        txtTimesong = findViewById(R.id.textviewtimesong);
        txtTotaltimesong = findViewById(R.id.textviewtotaltimesong);
        sktime = findViewById(R.id.seekbarsong);
        imgPlay = findViewById(R.id.imagebuttonplay);
        imgNext = findViewById(R.id.imagebuttonnext);
        imgPre = findViewById(R.id.imagebuttonpreview);
        imgRandom = findViewById(R.id.imagebuttonsuffle);
        imgRepeat = findViewById(R.id.imagebuttonrepeat);
        viewPagerplaynhac = findViewById(R.id.viewpagerplaynhac);
        setSupportActionBar(toolbarplaynhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                mangbaihat.clear();
            }
        });
        toolbarplaynhac.setTitleTextColor(Color.WHITE);
        fragment_playlist = new Fragment_Playlist();
        adapternhac = new ViewPagerPlaylistNhac(getSupportFragmentManager());
        adapternhac.AddFragment(fragment_dia_nhac);
        viewPagerplaynhac.setAdapter(adapternhac);
        if (mangbaihat.size() > 0) {
            getSupportActionBar().setTitle(mangbaihat.get(0).getTenBaiHat());
            new PlayMp3().execute(mangbaihat.get(0).getLinkBaiHat());
            imgPlay.setImageResource(R.drawable.iconpause);
        }
    }

    class PlayMp3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            Update();
        }
    }

    // cập nhật thời gian cho bài hát
    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotaltimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        sktime.setMax(mediaPlayer.getDuration() / 1000);
    }

    private void Update() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        next = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, 300);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true) {
                    if (position < (mangbaihat.size())) {
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position) {
                                position = index - 1; // để index đừng vượt qua giá trị của mảng
                            }
                            position = index;
                        }
                        if (position > (mangbaihat.size() - 1)) {
                            position = 0;
                        }
                        new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                    }


                    // tránh việc click quá nhiều gây crack app
                    imgPre.setClickable(false);
                    imgNext.setClickable(false);

                    Handler handler1 = new Handler(); // dùng để thực thi
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgPre.setClickable(true);
                            imgNext.setClickable(true);
                        }
                    }, 2000); // thực thi sau 2s
                    next = false;
                    handler1.removeCallbacks(this);
                } else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }
}