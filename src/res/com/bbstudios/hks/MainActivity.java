package com.bbstudios.hks;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
final String my_api="PjbmJnlTsz86XX1x6otzTNpUgP59msB85tfPhRmc";
    String dat1="",dat2="",isspoisk="36086";
    String[] camlist = {"FHAZ", "RHAZ", "MAST", "CHEMCAM","MAHLI","MARDI","NAVCAM","PANCAM","MINITES","Ählisi"};
    BottomSheetDialog bottomSheetDialog2,bottomSheetDialog1,marsdialog;
    Spinner cams;
    List<String> surlar;
    String  html_start,html_fin;
    ImageView nasalogo;
    Button marsbutton;
    String aurl="",alem_url="https://api.n2yo.com/rest/v1/satellite/positions/40617/42.32773/59.15442/0/2/&apiKey=";
    String selected="FHAZ";
    View mksview,browview,marsview;
    Dialog dialog,source;
    String TAG="options";
    private static GoogleMap mMap;
    String json7="http://api.open-notify.org/iss-now.json";
    String json1="https://api.nasa.gov/planetary/apod?api_key="+my_api+"&thumbs=true&date="+dat1;
    String json2="https://api.nasa.gov/neo/rest/v1/feed?start_date="+dat1+"&end_date="+dat2+"&api_key="+my_api;
    RecyclerView recyclerView;
    List<sanawucin> amallar;
    WebView webweb;
    TextView title,addinfo;
    String n2yoapi="NVBPH8-TY37AF-QNKABL-5EX9";
    EditText sany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        recyclerView=findViewById(R.id.sanaw2);
        amallar=new ArrayList<>();
        html_start="<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Mars Rover Photos</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            background-color: black;\n" +
                "            color: #00FF00; /* Неоновый зеленый */\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .photo-container {\n" +
                "            margin-top: 50px;\n" +
                "            padding: 20px;\n" +
                "            border: 3px solid #00FF00;\n" +
                "            display: inline-block;\n" +
                "            margin-bottom: 50px;\n" +
                "        }\n" +
                "\n" +
                "        .photo-container h2 {\n" +
                "            font-size: 24px;\n" +
                "        }\n" +
                "\n" +
                "        .photo-container p {\n" +
                "            font-size: 18px;\n" +
                "        }\n" +
                "\n" +
                "        .photo-container img {\n" +
                "            width: 80%;\n" +
                "            max-width: 600px;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            color: #FF00FF;\n" +
                "            text-shadow: 0 0 10px #FF00FF;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Marsahodyň  suratlary</h1>\n";
        html_fin="</body>\n" +
                "</html>";
        nasalogo=findViewById(R.id.nasalogo);
        nasalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog1=new Dialog(MainActivity.this);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.setContentView(R.layout.alemapi);
                dialog1.setCancelable(false);
                dialog1.show();
                Button button=dialog1.findViewById(R.id.butapi);
                EditText editText=dialog1.findViewById(R.id.myneoid);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editText.getText().length()>0){
                            n2yoapi=editText.getText().toString();
                            aurl=alem_url+n2yoapi;
                            dialog1.dismiss();
                        }
                    }
                });

            }
        });
        amallar.add(new sanawucin("1","Günüň astronomiki suraty","NASA-nyň iň meşhur ýerlerinden biri, günüň astronomiýa suraty. Aslynda bu sahypa ähli federal agentlikleriň arasynda iň meşhur saýtlardan biridir."));
        amallar.add(new sanawucin("4","Marsahodyň suratlary","Her bir mars boýunça syýahat edýän transportda maglumatlar bazasynda saklanýan öz suratlar toplumy bar"));
        amallar.add(new sanawucin("6","Türkmen älem ","Ilkinji aragatnaşyk hemrasy ”TürkmenÄlem 52.0E” Kanaweral (Florida, ABŞ) burundan 2015-nji ýylyň aprel aýynyň 27-ne 23 sagat 03 minutda älem giňişligine uçuryldy. Türkmenistanyň buýurmasy boýunça fransuz kompaniýasy “Thales Alenia Space” tarapyndan gurlan Älem gämisi, Falcon 9 raketa göteriji arkaly äleme goýberildi. Emeli hemranyň ýerleşýän ýerini görmek"));
        amallar.add(new sanawucin("7","HKS ýerleşişi","Halkara kosmiki stansiýasynyň häzirki wagtda haýsy çäkden uçup barýandygyny görkezýär"));
        amallar.add(new sanawucin("","",""));
        amallar.add(new sanawucin("8","Peýdalanylan çeşmeler","Amerikanyň Birleşen Ştatlarynyň Milli awiasiýa we kosmos dolandyryşy NASA guramasynyň berýän maglumatlary hem-de Halkara kosmiki stansiýany dolandyrýan guramanyň berýän maglumatlary"));
        amallar.add(new sanawucin("9","Programmany döredijiler ","\nAwtory : Balakaýewa Ýazgül\n\nÝolbaşçysy : Myratberdiýew Rejepmyrat\n"));
        mksview= LayoutInflater.from(MainActivity.this).inflate(R.layout.kartadialog,findViewById(R.id.kartad));
        browview=LayoutInflater.from(MainActivity.this).inflate(R.layout.browserdialog,findViewById(R.id.bd2));
        marsview=LayoutInflater.from(MainActivity.this).inflate(R.layout.marsahod,findViewById(R.id.marsid));
        bottomSheetDialog1=new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog1.setContentView(browview);
        marsdialog=new BottomSheetDialog(MainActivity.this);
        marsdialog.setContentView(marsview);
        cams=marsview.findViewById(R.id.camlist2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                R.layout.custom_spinner_item, camlist);
        adapter.setDropDownViewResource(R.layout.custom_spinner_item); // Optional: same style for dropdown
        cams.setAdapter(adapter);
        sany=marsview.findViewById(R.id.limphoto);
        marsbutton=marsdialog.findViewById(R.id.showp);
        addinfo=mksview.findViewById(R.id.addinfo);
        cams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        title=browview.findViewById(R.id.changetitle);
        webweb=browview.findViewById(R.id.webweb);
        marsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(sany.getText().toString())>0){
                    String url_json="";
                    if (selected.equals("Ählisi")){
                    url_json="https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key="+my_api;
                    } else {
                    url_json="https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=150&camera="+selected.toLowerCase(Locale.ROOT)+"&api_key="+my_api;
                    }
                    //Log.e(TAG, "MarsData: "+url_json );
                    new FetchMarsDataTask().execute(url_json);

                } else {
                    Toast.makeText(getApplicationContext(),"Limidy görkeziň",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Calendar calendar=Calendar.getInstance();
        String t1,t2;
        if ((calendar.get(Calendar.MONTH)+1)<10){
            t1="0"+(calendar.get(Calendar.MONTH)+1);
        }else {
            t1=(calendar.get(Calendar.MONTH)+1)+"";
        }
        if (calendar.get(Calendar.DAY_OF_MONTH)<10){
            t2="0"+calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            t2=calendar.get(Calendar.DAY_OF_MONTH)+"";
        }
        dat1=calendar.get(Calendar.YEAR)+"-"+t1+"-"+t2;
        dialog=new Dialog(MainActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        },6500);

        bottomSheetDialog2=new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog2.setContentView(mksview);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new sAdapter(amallar,MainActivity.this));
    }

    public BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String id=intent.getStringExtra("id");
            Toast.makeText(getApplicationContext(),"Amal ýerine ýetirilýär garaşyň . . .",Toast.LENGTH_SHORT).show();
            //Log.e(TAG, "onReceive: "+id );
            if (id.equals("4")){
                sany.setText("500");
                if (!marsdialog.isShowing()){
                    marsdialog.show();
                }
            }

            if (id.equals("6")){
//                bottomSheetDialog1.show();
                //Log.e(TAG, alem_url+n2yoapi);
                new FetchSatelliteDataTask().execute();
            }
            if (id.equals("8")){
                source=new Dialog(MainActivity.this);
                source.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                source.setContentView(R.layout.edebi);
                source.setCancelable(false);
                source.show();
                Button button=source.findViewById(R.id.closebutton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (source.isShowing()){
                            source.dismiss();
                        }
                    }
                });
            }
            if (id.equals("9")){
                Toast.makeText(getApplicationContext(),"Programma öz maglumatlaryny NASA serwerinden alýar",Toast.LENGTH_LONG).show();
            }
            if (id.equals("7")){

                        new FetchISSDataTask().execute(json7);
            }
            if (id.equals("1")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        json1="https://api.nasa.gov/planetary/apod?api_key="+my_api+"&thumbs=true&date="+dat1;
                        //Log.e(TAG, "run: "+json1 );
                        new momnetofday().execute(json1);
                    }
                }).start();
            }


//        dialog.dismiss();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(broadcastReceiver,
                new IntentFilter("custom-id"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }


    private class FetchISSDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();
                reader.close();
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                // Разбираем JSON
                //Log.e(TAG, "Разбираем" );
                JSONObject jsonObject = new JSONObject(result);
                long timestamp = jsonObject.getLong("timestamp");
                String message = jsonObject.getString("message");

                JSONObject issPosition = jsonObject.getJSONObject("iss_position");
                String longitude = issPosition.getString("longitude");
                String latitude = issPosition.getString("latitude");
                addinfo.setText("Orbital belentligi 418.2km\nOrbital tizligi : 7.7km/sekunt");
                if (message.equals("success")){
                        mMap.clear();
                    BitmapDescriptor bitmapDescriptor;
                    Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.satellite_35px);
                    bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(img);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latitude),
                            Double.parseDouble(longitude))).title("Halkara kosmos stansiýasy").icon(bitmapDescriptor));


                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latitude),
                            Double.parseDouble(longitude)),
                            1.0f));


                    if (!bottomSheetDialog2.isShowing()){
                        bottomSheetDialog2.show();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (bottomSheetDialog2.isShowing()) {
                                new FetchISSDataTask().execute(json7);
                            }
                        }
                    },5500);
                } else {
                    Toast.makeText(getApplicationContext(),"Maglumady anyklap bolmady",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                //Log.e("TAG", "onPostExecute: Ошибка при обработке JSON");
            }
        }
    }

    public class momnetofday extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();
                reader.close();
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                // Разбираем JSON
                JSONObject jsonObject = new JSONObject(result);
                String thumbnail_url ="";
                        String explanation = jsonObject.getString("explanation");
                String media_type = jsonObject.getString("media_type");
                if (media_type.equals("video")){
                 thumbnail_url = jsonObject.getString("thumbnail_url");} else {

                 thumbnail_url = jsonObject.getString("url");
                }
                String titles = jsonObject.getString("title");

                title.setText(titles);
                webweb.loadUrl(thumbnail_url);

                if (!bottomSheetDialog1.isShowing()){
                    bottomSheetDialog1.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                //Log.e("TAG", "onPostExecute: Ошибка при обработке");
            }
        }


    }


    public class FetchSatelliteDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(alem_url+n2yoapi);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray positions = jsonObject.getJSONArray("positions");
                    StringBuilder output = new StringBuilder();
                    double lat=0.0,lon=0.0;
                    for (int i = 0; i < positions.length(); i++) {
                        JSONObject position = positions.getJSONObject(i);
                        lat = position.getDouble("satlatitude");
                         lon = position.getDouble("satlongitude");
//                        output.append("Latitude: ").append(lat).append(", Longitude: ").append(lon).append("\n");
                        //Log.e(TAG, ": "+lat+"\n"+lon );
                    }
                    if (lat!=0.0){
                    mMap.clear();
                    BitmapDescriptor bitmapDescriptor;
                    Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.satellite_signal_35px);
                    bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(img);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lat,
                            lon)).title("Türkmen älem 52E").icon(bitmapDescriptor));


                    addinfo.setText("Orbital belentligi 35 786 km\nOrbital tizligi : 3.07km/sekunt");
                    if (!bottomSheetDialog2.isShowing()){
                        View bottomSheet = bottomSheetDialog2.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        behavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                        bottomSheetDialog2.show();
                    }
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon),
                                1.0f));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    //Log.e(TAG, e.getMessage() );
                }
            } else {
                //Log.e(TAG, "Failed to fetch data");
            }
        }
    }


    public void who(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822"); // Email MIME type
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"b4bstudios@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Can u question?");
        intent.putExtra(Intent.EXTRA_TEXT, "Send to me mail b4bstudios@gmail.com");

        try {
            startActivity(Intent.createChooser(intent, "Send Email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email client installed.", Toast.LENGTH_SHORT).show();
        }

    }


    public class FetchMarsDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String jsonUrl = params[0]; // URL для получения данных
            StringBuilder result = new StringBuilder();

            try {
                // Создаем URL объект
                URL url = new URL(jsonUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);

                // Получаем данные
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }

                return result.toString();
            } catch (Exception e) {
                //Log.e("FetchMarsDataTask", "Error", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    // Парсим полученный JSON
                    JSONObject jsonResponse = new JSONObject(result);
                    JSONArray photos = jsonResponse.getJSONArray("photos");
String net="",last1="",last2="";
surlar=new ArrayList<>();surlar.clear();
                    for (int i = 0; i < photos.length(); i++) {
                        JSONObject photo = photos.getJSONObject(i);
                        String imgSrc = photo.getString("img_src");
                        JSONObject rover = photo.getJSONObject("rover");
                        String roverName = rover.getString("name");
                        String landingDate = rover.getString("landing_date");
                        if (surlar.size()>0){
                            last2=imgSrc.substring(imgSrc.length()-15);
                            if (!last1.equals(last2)){
                            if (!surlar.contains(imgSrc)){

                                surlar.add(new String(imgSrc));
                                net=net+"\n"+"<div class=\"photo-container\">\n" +
                                        "    <h2>Ady: "+roverName+"</h2>\n" +
                                        "    <p>Kabul edilen wagty: "+landingDate+"</p>\n" +
                                        "    <img src=\""+imgSrc+"\" alt=\"Curiosity Rover Photos "+i+"\">\n" +
                                        "</div>";
                                last1=last2;

                            }
                            }

                        } else {
                                    last1=imgSrc.substring(imgSrc.length()-15);
                            surlar.add(new String(imgSrc));
                            net=net+"\n"+"<div class=\"photo-container\">\n" +
                                    "    <h2>Ady: "+roverName+"</h2>\n" +
                                    "    <p>Kabul edilen wagty: "+landingDate+"</p>\n" +
                                    "    <img src=\""+imgSrc+"\" alt=\"Curiosity Rover Photos "+i+"\">\n" +
                                    "</div>";

                        }
                         // Логируем полученные данные
                        //Log.e("MarsData", "Image URL: " + imgSrc);
                        //Log.e("MarsData", "Rover Name: " + roverName);
                        //Log.e("MarsData", "Landing Date: " + landingDate);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        webweb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                    }
                    WebSettings webSettings = webweb.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webweb.setWebViewClient(new WebViewClient());
                    String mycode=html_start+"\n"+net+"\n"+html_fin;
//                    //Log.e("WebView", mycode);
                    webweb.loadDataWithBaseURL(null,mycode, "text/html", "UTF-8",null);
                    if (!bottomSheetDialog1.isShowing()){
                        marsdialog.dismiss();
                        title.setText("Mars Rover");
                        bottomSheetDialog1.show();
                    }


                } catch (JSONException e) {
                    //Log.e("FetchMarsDataTask", "JSON Parsing Error", e);
                }
            }
        }

}
}