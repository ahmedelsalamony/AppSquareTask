package com.example.ahmed.appsquaretask;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.id.list;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    private RequestQueue queue;
    private String paginationURL,completeURL;
    private int pageNumber;
    private int currentVisibleItemCount,currentScrollState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pageNumber=1;
      //  paginationURL="?page="+pageNumber+"&per_page=10";


        //completeURL=EndPoints.URL_REPO+paginationURL;

        queue = Volley.newRequestQueue(this);
        JsonArrayRequest strReq = new JsonArrayRequest(EndPoints.URL_REPO+"?page="+pageNumber+"&per_page=10", new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray)
            {
                for(int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //Toast.makeText(MainActivity.this, jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
                        String repoName=jsonObject.getString("name");
                        String description=jsonObject.getString("description");

                        JSONObject sub_jsonobObject=jsonObject.getJSONObject("owner");
                        String ownerUserName=sub_jsonobObject.getString("login");

                        DataModel data_moModel=new DataModel(repoName,description,ownerUserName);
                        dataModels.add(data_moModel);
                        adapter.notifyDataSetChanged();



                    }
                    catch(JSONException e) {
                      e.getMessage();
                    }
                }

            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Adding String request to request queue
        queue.add(strReq);

        listView = (ListView) findViewById(R.id.activity_main_listView);
        dataModels = new ArrayList<>();
        adapter = new CustomAdapter(dataModels,this);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);

    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        this.currentVisibleItemCount = visibleItemCount;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.currentScrollState = scrollState;
        this.isScrollCompleted();
    }

    private void isScrollCompleted() {
        if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
            /*** In this way I detect if there's been a scroll which has completed ***/
            /*** do the work! ***/

            pageNumber=pageNumber+1;
            queue = Volley.newRequestQueue(this);
            JsonArrayRequest strReq = new JsonArrayRequest(EndPoints.URL_REPO+"?page="+pageNumber+"&per_page=10", new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArray)
                {
                    for(int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //Toast.makeText(MainActivity.this, jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
                            String repoName=jsonObject.getString("name");
                            String description=jsonObject.getString("description");

                            JSONObject sub_jsonobObject=jsonObject.getJSONObject("owner");
                            String ownerUserName=sub_jsonobObject.getString("login");

                            DataModel data_moModel=new DataModel(repoName,description,ownerUserName);
                            dataModels.add(data_moModel);
                            adapter.notifyDataSetChanged();



                        }
                        catch(JSONException e) {
                            e.getMessage();
                        }
                    }

                }
            }, new Response.ErrorListener()
            {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            // Adding String request to request queue
            queue.add(strReq);


        }
    }
    }





