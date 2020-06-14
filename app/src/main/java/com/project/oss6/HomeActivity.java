package com.project.oss6;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Random;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;




public class HomeActivity extends YouTubeBaseActivity {
    private Button btn_Weather;
    Random random;
    String gps_GetProvider;
    double gps_GetLatitude;
    double gps_GetLongitude;
    double gps_GetAltitude;

    {
        random = new Random();
    }
    YouTubePlayerView youtubeView;
    Button playbutton,nextbutton,pausebutton;
    YouTubePlayer.OnInitializedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        playbutton = findViewById(R.id.youtubebutton);
        youtubeView = findViewById(R.id.youtubeView);
        nextbutton = findViewById(R.id.nextbutton);
        pausebutton = findViewById(R.id.pausebutton);


        listener = new YouTubePlayer.OnInitializedListener(){

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS); //화면 못건드리게
                NewRunnable nr = new NewRunnable();
                final Thread w = new Thread(nr);
                if(true){
                    w.start();
                }
                //처음 시작시 보이지 않는다 play를 눌러야 재생되면서 보임
                youTubePlayer.loadPlaylist("PL4fGSI1pDJn6jXS_Tv_N9B8Z0HTRVJE0m",random.nextInt(100),0);//db에 재생목록 아이디와 최대 곡수를 저장시키고 랜덤으로 플레이
                nextbutton.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        youTubePlayer.next();//다음곡 버튼


                        /*if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                            ActivityCompat.requestPermissions( HomeActivity.this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION }, 0 );
                        }
                        else{
                            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            String gps_Provider = location.getProvider();
                            double gps_Latitude = location.getLatitude();
                            double gps_Longitude = location.getLongitude();
                            double gps_Altitude = location.getAltitude();
                            gps_GetProvider = gps_Provider;
                            gps_GetLatitude = gps_Latitude;
                            gps_GetLongitude = gps_Longitude;
                            gps_GetAltitude = gps_Altitude;
                            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                    5000,
                                    1,
                                    gps_LocationListener);
                            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                    5000,
                                    1,
                                    gps_LocationListener);
                        }*/
                    }

                });
                playbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        youTubePlayer.play();//재생버튼

                    }
                });
                pausebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        youTubePlayer.pause();//일시정지 버튼
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youtubeView.initialize("AIzaSyDKl_NSpIyEdS2WQYp0CDaDCxHCkh_eztM",listener);


        btn_Weather = findViewById(R.id.btn_Weather);
        btn_Weather.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });
    }
    final LocationListener gps_LocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            String gps_Provider = location.getProvider();
            double gps_Latitude = location.getLatitude();
            double gps_Longitude = location.getLongitude();
            double gps_Altitude = location.getAltitude();
            gps_GetProvider = gps_Provider;
            gps_GetLatitude = gps_Latitude;
            gps_GetLongitude = gps_Longitude;
            gps_GetAltitude = gps_Altitude;
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };
    class NewRunnable implements Runnable {
        @Override
        public void run() {

            while(true){
            try {
                weather();
                Thread.sleep(5000); //5초마다 재실행
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            }

        }
    }

        public void weather() throws IOException {
            StringBuilder urlBuilder = new StringBuilder("http://api.openweathermap.org/data/2.5/weather"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("lat","UTF-8") + "=37"); /*위도*/
            urlBuilder.append("&" + URLEncoder.encode("lon","UTF-8") + "=126" ); /*공공데이터포털에서 받은 인증키*/
            urlBuilder.append("&" + URLEncoder.encode("appid","UTF-8") + "=94c29906846b82e6e359edce29c1a4c8"); /*키번호*/
            urlBuilder.append("&units=metric"); /*섭씨*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            System.out.println(sb.toString());
        }

}
