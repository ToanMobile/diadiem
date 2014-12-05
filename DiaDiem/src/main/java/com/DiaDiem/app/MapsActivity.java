package com.DiaDiem.app;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMyLocationChangeListener{
    GoogleMap mGoogleMap;
    ArrayList<LatLng> mMarkerPoints;
    double mLatitude;
    double mLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Tạo Bundle nhận dữ liệu
       /* Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("SendToaDo");
        String TenDiaDiem = bundle.getString("TenDiaDiem");
        String DiaChi = bundle.getString("DiaChi");
        final Double ToaDox = bundle.getDouble("ToaDox");
        final Double ToaDoy = bundle.getDouble("ToaDoy");
        // LatLng ToaDo = new LatLng(ToaDox, ToaDoy);
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(ToaDox, ToaDoy));
        // Thiết lập tên khi click vào lá cờ địa điểm
        options.title("Tên Quán:" + TenDiaDiem);
        // Tạo thông báo phía dưới Title(Tên)
        options.snippet("    Địa Chỉ:" + DiaChi);*/
        // options.snippet("Latitude:"+ToaDox+",Longitude:"+ToaDoy);
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Initializing
            mMarkerPoints = new ArrayList<LatLng>();
            SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            // Bắt đầu tạo Google Map
            mGoogleMap = fragment.getMap();
           // mGoogleMap.addMarker(options);
            mGoogleMap.getUiSettings().setCompassEnabled(true);
            mGoogleMap.setMyLocationEnabled(true);
           // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ToaDox, ToaDoy), 15));

            //Tạo định vị GPS chỗ ta đang đứng
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(provider);
            mGoogleMap.setOnMyLocationChangeListener(this);

           /*// Ham ve duong di tu dia diem den cho ta chon tren ban do
           mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    mMarkerPoints.add(new LatLng(ToaDox,ToaDoy));
                    mMarkerPoints.add(point);

                    LatLng origin = mMarkerPoints.get(0);
                    LatLng dest = mMarkerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }
            });*/
        }
    }
    @Override
    public void onMyLocationChange(Location location) {
        // Lay toa do x cho ta dang dung
        mLatitude = location.getLatitude();
        // Lay toa do y cho ta dang dung
        mLongitude = location.getLongitude();
        // Tao toa do moi tai vi tri hien tai
        LatLng latLng = new LatLng(mLatitude, mLongitude);
        // Di chuyen den toa do dinh san
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        // Phong to Map
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("SendToaDo");
        String TenDiaDiem = bundle.getString("TenDiaDiem");
        String DiaChi = bundle.getString("DiaChi");
        final Double ToaDox = bundle.getDouble("ToaDox");
        final Double ToaDoy = bundle.getDouble("ToaDoy");
        // LatLng ToaDo = new LatLng(ToaDox, ToaDoy);
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(ToaDox, ToaDoy));
        // Thiết lập tên khi click vào lá cờ địa điểm
        options.title("Tên Quán:" + TenDiaDiem);
        // Tạo thông báo phía dưới Title(Tên)
        options.snippet("    Địa Chỉ:" + DiaChi);
        mGoogleMap.addMarker(options);
            mMarkerPoints.add(latLng);
            mMarkerPoints.add(new LatLng(ToaDox,ToaDoy));
           // mMarkerPoints.add(lat)
            LatLng origin = mMarkerPoints.get(0);
            LatLng dest = mMarkerPoints.get(1);

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(origin, dest);

            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);

    }
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /** A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";
            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Directions in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(3);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            mGoogleMap.addPolyline(lineOptions);
        }
    }
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_search, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                openMain();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void openMain(){
        Intent i=new Intent(MapsActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

}