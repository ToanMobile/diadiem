package com.DiaDiem.app.InsertDuLieu;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.DiaDiem.app.JSONParser;
import com.DiaDiem.app.MapsActivity;
import com.DiaDiem.app.R;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InsertQuan5 extends ListActivity {
    private static String strUrl = "http://toankul.url.ph/Quan5.php";
    ListView mListView;
    TextView txt1;
    TextView txt2;
    TextView Name,DiaDiem;
    Double ToaDox,ToaDoy;
    String TenDiaDiem,DiaChi;
    ArrayList<HashMap<String, String>> contactList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listview);
        // Tạo biến Download Json
        DownloadTask downloadTask = new DownloadTask();
        // Bắt đầu lấy dữ liệu từ Json qua URL
        downloadTask.execute(strUrl);
        mListView= getListView();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Khai báo các id
                Name=(TextView) view.findViewById(R.id.text_ten);
                DiaDiem=(TextView) view.findViewById(R.id.text_diachi);
                txt1=(TextView) view.findViewById(R.id.ToaDox);
                txt2=(TextView) view.findViewById(R.id.ToaDoy);
                Intent intent = new Intent(InsertQuan5.this,MapsActivity.class);
                Bundle bundle=new Bundle();
                //Gửi dữ liệu đi
                TenDiaDiem= new String(Name.getText().toString());
                DiaChi= new String(DiaDiem.getText().toString());
                ToaDox = new Double(txt1.getText().toString());
                ToaDoy = new Double(txt2.getText().toString());
                bundle.putString("TenDiaDiem",TenDiaDiem);
                bundle.putString("DiaChi",DiaChi);
                bundle.putDouble("ToaDox",ToaDox);
                bundle.putDouble("ToaDoy",ToaDoy);
                intent.putExtra("SendToaDo", bundle);
                //Bắt đầu Intent mới
                startActivity(intent);
                finish();
            }
        });
    }
    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
        }

        return data;
    }


    /**
     * AsyncTask to download json data
     */
    private class DownloadTask extends AsyncTask<String, Integer, String> {
        String data = null;

        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);

            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {

            // The parsing of the xml data is done in a non-ui thread
            ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();

            // Start parsing xml data
            listViewLoaderTask.execute(result);

        }
    }

    /**
     * AsyncTask to parse json data and load ListView
     */
    private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter> {

        JSONObject jObject;

        // Doing the parsing of xml data in a non-ui thread
        @Override
        protected SimpleAdapter doInBackground(String... strJson) {
            try {
                jObject = new JSONObject(strJson[0]);
                JSONParser jsonParser = new JSONParser();
                jsonParser.parse(jObject);
            } catch (Exception e) {
                Log.d("JSON Exception1", e.toString());
            }

            // Instantiating json parser class
            JSONParser jsonParser = new JSONParser();

            // A list object to store the parsed countries list
            List<HashMap<String, Object>> countries = null;

            try {
                // Getting the parsed data as a List construct
                countries = jsonParser.parse(jObject);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }

            //Lấy Name đúng như trong file PHP
            String[] from = { "image","TenDiaDiem", "ThongTin","DiaChi","ToaDox","ToaDoy"};

            // Khai báo id trong layout List_Item
            int[] to = { R.id.img_hienthi,R.id.text_ten,R.id.text_thongtin,R.id.text_diachi,R.id.ToaDox,R.id.ToaDoy};

            //Gán các giá trị vào listview
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), countries, R.layout.listview_item, from, to);

            return adapter;
        }

        /**
         * Invoked by the Android on "doInBackground" is executed
         */
        @Override
        protected void onPostExecute(SimpleAdapter adapter) {

            // Setting adapter for the listview
            mListView.setAdapter(adapter);

            for (int i = 0; i < adapter.getCount(); i++) {
                HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(i);
                String imgUrl = (String) hm.get("flag_path");
                ImageLoaderTask imageLoaderTask = new ImageLoaderTask();
                HashMap<String, Object> hmDownload = new HashMap<String, Object>();
                hm.put("flag_path", imgUrl);
                hm.put("position", i);
                // Starting ImageLoaderTask to download and populate image in the listview
                imageLoaderTask.execute(hm);
            }
        }
    }

    /**
     * AsyncTask to download and load an image in ListView
     */
    private class ImageLoaderTask extends AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>> {

        @Override
        protected HashMap<String, Object> doInBackground(HashMap<String, Object>... hm) {

            InputStream iStream = null;
            String imgUrl = (String) hm[0].get("flag_path");
            int position = (Integer) hm[0].get("position");

            URL url;
            try {
                url = new URL(imgUrl);

                // Creating an http connection to communicate with url
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                // Getting Caching directory
                File cacheDirectory = getBaseContext().getCacheDir();

                // Temporary file to store the downloaded image
                File tmpFile = new File(cacheDirectory.getPath() + "/wpta_" + position + ".png");

                // The FileOutputStream to the temporary file
                FileOutputStream fOutStream = new FileOutputStream(tmpFile);

                // Creating a bitmap from the downloaded inputstream
                Bitmap b = BitmapFactory.decodeStream(iStream);

                // Writing the bitmap to the temporary file as png file
                b.compress(Bitmap.CompressFormat.PNG, 100, fOutStream);

                // Flush the FileOutputStream
                fOutStream.flush();

                //Close the FileOutputStream
                fOutStream.close();

                // Create a hashmap object to store image path and its position in the listview
                HashMap<String, Object> hmBitmap = new HashMap<String, Object>();

                // Storing the path to the temporary image file
                hmBitmap.put("image", tmpFile.getPath());

                // Storing the position of the image in the listview
                hmBitmap.put("position", position);

                // Returning the HashMap object containing the image path and position
                return hmBitmap;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            // Getting the path to the downloaded image
            String path = (String) result.get("image");

            // Getting the position of the downloaded image
            int position = (Integer) result.get("position");

            // Getting adapter of the listview
            SimpleAdapter adapter = (SimpleAdapter) mListView.getAdapter();

            // Getting the hashmap object at the specified position of the listview
            HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(position);

            // Overwriting the existing path in the adapter
            hm.put("image", path);

            // Noticing listview about the dataset changes
            adapter.notifyDataSetChanged();
        }
    }

}
