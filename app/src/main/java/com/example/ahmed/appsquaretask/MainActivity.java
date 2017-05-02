package com.example.ahmed.appsquaretask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
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


public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener{

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    private RequestQueue queue;
    private String paginationURL,completeURL;
    private int pageNumber;
    private int currentVisibleItemCount,currentScrollState;
    DBAdapter db;
    RelativeLayout MainActivity_relative;
    Button btnRepo,btnOwner;
    DataModel data_moModel;
    private SwipeRefreshLayout swipeContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity_relative = (RelativeLayout) findViewById(R.id.activity_main);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);


        db = new DBAdapter(this);
        pageNumber = 1;
        //  paginationURL="?page="+pageNumber+"&per_page=10";


        //completeURL=EndPoints.URL_REPO+paginationURL;

        queue = Volley.newRequestQueue(this);
        JsonArrayRequest strReq = new JsonArrayRequest(EndPoints.URL_REPO+"?page="+pageNumber+"&per_page=10", new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //Toast.makeText(MainActivity.this, jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
                        String repoName = jsonObject.getString("name");
                        String description = jsonObject.getString("description");
                        String fork = jsonObject.getString("fork");
                        String repoHTML=jsonObject.getString("html_url");


                        JSONObject sub_jsonobObject = jsonObject.getJSONObject("owner");
                        String ownerUserName = sub_jsonobObject.getString("login");
                        String ownerHTML=sub_jsonobObject.getString("html_url");


                        data_moModel= new DataModel(repoName, description, ownerUserName, fork,repoHTML,ownerHTML);
                        dataModels.add(data_moModel);
                        adapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.getMessage();
                    }
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Adding String request to request queue
        queue.add(strReq);

        listView = (ListView) findViewById(R.id.activity_main_listView);
        dataModels = new ArrayList<>();
        adapter = new CustomAdapter(dataModels, this);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                Dialog d=new Dialog(MainActivity.this);
                d.setContentView(R.layout.custom_dialog);
                d.setTitle("choose one ");
                btnRepo=(Button)d.findViewById(R.id.custom_dialog_btnRepo);
                btnOwner=(Button)d.findViewById(R.id.custom_dialog_btnOwner);
                d.show();
                btnRepo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(MainActivity.this, data_moModel.getRepoHTMLURL(), Toast.LENGTH_SHORT).show();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data_moModel.getRepoHTMLURL()));
                        startActivity(browserIntent);
                    }
                });

                btnOwner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data_moModel.getOwnerHTMLURL()));
                        startActivity(browserIntent);
                    }
                });

                return true;
            }
        });


        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                queue = Volley.newRequestQueue(MainActivity.this);
                JsonArrayRequest strReq = new JsonArrayRequest(EndPoints.URL_REPO+"?page="+pageNumber+"&per_page=10", new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //Toast.makeText(MainActivity.this, jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
                                String repoName = jsonObject.getString("name");
                                String description = jsonObject.getString("description");
                                String fork = jsonObject.getString("fork");
                                String repoHTML=jsonObject.getString("html_url");


                                JSONObject sub_jsonobObject = jsonObject.getJSONObject("owner");
                                String ownerUserName = sub_jsonobObject.getString("login");
                                String ownerHTML=sub_jsonobObject.getString("html_url");


                                data_moModel= new DataModel(repoName, description, ownerUserName, fork,repoHTML,ownerHTML);
                                dataModels.add(data_moModel);
                                adapter.notifyDataSetChanged();


                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                // Adding String request to request queue
                queue.add(strReq);

                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
                            String repoName=jsonObject.getString("name");
                            String description=jsonObject.getString("description");
                            String fork=jsonObject.getString("fork");
                            String repoHTML=jsonObject.getString("html_url");
                            JSONObject sub_jsonobObject=jsonObject.getJSONObject("owner");
                            String ownerUserName=sub_jsonobObject.getString("login");

                            String ownerHTML=sub_jsonobObject.getString("html_url");


                           data_moModel = new DataModel(repoName, description, ownerUserName, fork,repoHTML,ownerHTML);
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








