package com.example.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;

public class NewsDetail extends AppCompatActivity {
    private NewsData news;
    private TextView Title, Content;
    private SimpleDraweeView Image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        Intent intent = getIntent();
        Bundle bundle= intent.getExtras();

        setComp();
        getNewsDetail();
        setNews();

    }

    public void setComp(){
        Title = findViewById(R.id.detail_title);
        Image = findViewById(R.id.detail_image);
        Content = findViewById(R.id.detail_content);
    }

    public void getNewsDetail(){
        Intent intent = getIntent();

        if(intent != null){
            Bundle bundle= intent.getExtras();
            Object obj = bundle.get("news");
            if(bundle != null && obj instanceof NewsData){
                this.news = (NewsData) obj;
            }
        }
    }

    public void setNews(){
        String title = this.news.getTitle();
        String content = this.news.getContent();
        Uri uri = Uri.parse(news.getUrlToImage());
        if(title != null){
            Title.setText(title);
        }
        if(content != null){
            Content.setText(content);
        }
        if(uri != null){
            Image.setImageURI(uri);
        }
    }

}
