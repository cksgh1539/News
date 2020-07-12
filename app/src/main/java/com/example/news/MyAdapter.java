package com.example.news;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> mDataset;
    private static View.OnClickListener onClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitle;
        public TextView mContent;
        public SimpleDraweeView mImage;
        public View rootView;

        public MyViewHolder(View v) {
            super(v);
            mTitle = v.findViewById(R.id.view_title);
            mContent = v.findViewById(R.id.view_content);
            mImage =  v.findViewById(R.id.view_image);
            rootView = v;
            v.setClickable(true);
            v.setEnabled(true);
            v.setOnClickListener(onClickListener);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<NewsData> myDataset, Context context, View.OnClickListener onClick) {
        mDataset = myDataset;
        onClickListener = onClick;
        Fresco.initialize(context);
        //context는 Activity에서만 쓰인다. this 사용 불가
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        NewsData news = mDataset.get(position);

        if(news.getContent() == "null"){
            holder.mContent.setText("-");
        }else{
            holder.mContent.setText(news.getContent());
        }
        holder.mTitle.setText(news.getTitle());

        Uri uri = Uri.parse(news.getUrlToImage());
        holder.mImage.setImageURI(uri);

        holder.rootView.setTag(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public NewsData getNews(int position){
        return mDataset != null ? mDataset.get(position) : null;
    }
}

