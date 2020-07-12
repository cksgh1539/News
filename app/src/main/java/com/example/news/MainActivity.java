package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)


        queue = Volley.newRequestQueue(this);
        getNews();
        // 1. 화면 로딩 -> 뉴스 정보를 받아온다.
        // 2. 정보 -> 어댑터 넘겨준다.
        // 3.  어댑터 -> 셋팅


    }

    public void getNews(){

        // Instantiate the RequestQueue.

        String url ="http://newsapi.org/v2/top-headlines?country=kr&apiKey=eb8372ad6ec74e81b0ef06c013bca4a9";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("chanho", response);

                        try {
                            JSONObject jsonobj = new JSONObject(response);
                            JSONArray jsonArray = jsonobj.getJSONArray("articles");

                            //response ->> NewsData Class 분류
                            List<NewsData> news = new ArrayList<>();

                            for(int i = 0, j = jsonArray.length(); i < j; i++){
                                JSONObject obj = jsonArray.getJSONObject(i);

                                Log.d("chanho", obj.toString());
                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setContent(obj.getString("content"));
                                news.add(newsData);

                            }

                            mAdapter = new MyAdapter(news, MainActivity.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Object obj = view.getTag();
                                    if(obj != null){
                                        int position = (int) obj;
                                      //  ((MyAdapter)mAdapter).getNews(position);
                                        Intent intent = new Intent(MainActivity.this,NewsDetail.class);
                                        intent.putExtra("news",((MyAdapter)mAdapter).getNews(position));
                                        startActivity(intent);

                                    }
                                }
                            });
                            recyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}