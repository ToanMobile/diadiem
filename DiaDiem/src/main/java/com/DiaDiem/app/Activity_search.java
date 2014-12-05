package com.DiaDiem.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.DiaDiem.app.InsertDuLieu.InsertMonAu;
import com.DiaDiem.app.InsertDuLieu.InsertMonNhat;
import com.DiaDiem.app.InsertDuLieu.InsertMonPhap;
import com.DiaDiem.app.InsertDuLieu.InsertNhaHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by toan on 3/2/14.
 */
public class Activity_search extends ListActivity {
    private ProgressDialog pDialog;
    ListViewSearchAdapter adapter_search;
    ListView mlistview;
    EditText editText;
    TextView txt1;
    TextView txt2;
    TextView Name,DiaDiem,thongtin;
    Double ToaDox,ToaDoy;
    String TenDiaDiem,DiaChi,ThongTin;
    // URL to get contacts JSON
    private static String url = "http://toankul.url.ph/TimKiem.php";

    // JSON Node names
    private static final String TAG_CONTACTS = "diadiem";
    private static final String TAG_ID = "MaDiaDiem";
    public static final String TAG_TEN = "TenDiaDiem";
    public static final String TAG_THONGTIN = "ThongTin";
    public static final String TAG_DIACHI = "DiaChi";
    public static final String TAG_TOADOX = "ToaDox";
    public static final String TAG_TOADOY = "ToaDoy";
    // contacts JSONArray
    JSONArray contacts = null;
    ArrayList<HashMap<String, String>> searchResults;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    // nav drawer title
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        mTitle = mDrawerTitle = getTitle();
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items2);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icon);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slider_menu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, 1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.icon_menu, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
        //Tạo thông báo Toast cho Màn hình chính
        LinearLayout layout=new LinearLayout(this);
        TextView tex_toast=new TextView(this);
        // Tạo màu cho Toast(Thông báo)
        tex_toast.setTextColor(Color.RED);
        tex_toast.setTextSize(15);
        tex_toast.setGravity(Gravity.CENTER_VERTICAL);
        // Text cho Toast
        tex_toast.setText("Tìm Món Ăn ^_^");
        ImageView img=new ImageView(this);
        // Tạo ảnh cho Toast
        img.setImageResource(R.drawable.icon_smile);
        // Thêm ảnh và text vào layout Main
        layout.addView(img);
        layout.addView(tex_toast);
        Toast toast1=new Toast(this);
        toast1.setDuration(toast1.LENGTH_LONG);
        toast1.setView(layout);
        toast1.setGravity(Gravity.BOTTOM, 0, 20);
        toast1.show();
        //////////////////Tạo search
        editText = (EditText) findViewById(R.id.edit_search);
        mlistview=(ListView) findViewById(R.layout.listview_item_search);
        contactList = new ArrayList<HashMap<String, String>>();
        searchResults=new ArrayList<HashMap<String, String>>(contactList);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchString=editText.getText().toString();
                int textLength=searchString.length();
                searchResults.clear();

                for(int i=0;i<contactList.size();i++) {
                    String playerName = contactList.get(i).get("TenDiaDiem").toString();
                    System.out.println("player name " + playerName);
                    if (textLength <= playerName.length()) {
                        //compare the String in EditText with Names in the ArrayList
                        if (searchString.equalsIgnoreCase(playerName.substring(0, textLength))) {
                            searchResults.add(contactList.get(i));
                            System.out.println("the array list is " + contactList.get(i));
                            adapter_search = new ListViewSearchAdapter(Activity_search.this, searchResults);
                            //mlistview.setAdapter(adapter);
                            setListAdapter(adapter_search);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ListView lv = getListView();

        // Listview on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Khai báo các id
                Name=(TextView) view.findViewById(R.id.text_ten);
                thongtin=(TextView) view.findViewById(R.id.text_thongtin);
                DiaDiem=(TextView) view.findViewById(R.id.text_diachi);
                txt1=(TextView) view.findViewById(R.id.ToaDox);
                txt2=(TextView) view.findViewById(R.id.ToaDoy);
                Intent intent = new Intent(Activity_search.this,MapsActivity.class);
                Bundle bundle=new Bundle();
                //Gửi dữ liệu đi
                TenDiaDiem= new String(Name.getText().toString());
                ThongTin=new String(thongtin.getText().toString());
                DiaChi= new String(DiaDiem.getText().toString());
                ToaDox = new Double(txt1.getText().toString());
                ToaDoy = new Double(txt2.getText().toString());
                bundle.putString("TenDiaDiem",TenDiaDiem);
                bundle.putString("ThongTin",ThongTin);
                bundle.putString("DiaChi",DiaChi);
                bundle.putDouble("ToaDox",ToaDox);
                bundle.putDouble("ToaDoy",ToaDoy);
                intent.putExtra("SendToaDo", bundle);
                //Bắt đầu Intent mới
                startActivity(intent);
            }
        });

        // Calling async task to get json
        new GetContacts().execute();

    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_home:
                openMain();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void openMain(){
        Intent Main=new Intent(Activity_search.this,MainActivity.class);
        startActivity(Main);
        finish();
    }
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_home).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Fragment();
                break;
            case 1:
                Intent i=new Intent(Activity_search.this,InsertNhaHang.class);
                startActivity(i);
                break;
            case 2:
                Intent i1=new Intent(Activity_search.this,InsertMonNhat.class);
                startActivity(i1);
                break;
            case 3:
                Intent i2=new Intent(Activity_search.this,InsertMonAu.class);
                startActivity(i2);
                break;
            case 4:
                Intent i3=new Intent(Activity_search.this,InsertMonPhap.class);
                startActivity(i3);
                break;
            case 5:
                fragment = new About();
                break;
            case 6:
                fragment = new Error();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_slide_out_bottom);
    }
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Activity_search.this);
            pDialog.setMessage("Load dữ liệu.Xin chờ...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_CONTACTS);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_TEN);
                        String tt = c.getString(TAG_THONGTIN);
                        String email = c.getString(TAG_DIACHI);
                        String address = c.getString(TAG_TOADOX);
                        String gender = c.getString(TAG_TOADOY);

                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        contact.put(TAG_ID, id);
                        contact.put(TAG_TEN, name);
                        contact.put(TAG_THONGTIN, tt);
                        contact.put(TAG_DIACHI, email);
                        contact.put(TAG_TOADOX, address);
                        contact.put(TAG_TOADOY, gender);
                        //adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(
                    Activity_search.this, contactList,
                    R.layout.listview_item_search, new String[] { TAG_TEN,TAG_THONGTIN, TAG_DIACHI,
                    TAG_TOADOX,TAG_TOADOY }, new int[] { R.id.text_ten,R.id.text_thongtin,
                    R.id.text_diachi, R.id.ToaDox,R.id.ToaDoy });
            //listview.setAdapter(adapter);*/
            setListAdapter(adapter);
        }

    }
}