package com.prashant.mygittest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prashant.mygittest.Model.ActivityModel;
import com.prashant.mygittest.R;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 25-11-17.
 */

public class MainRecyAdap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ActivityModel> data;

    public MainRecyAdap(Context context, List<ActivityModel> data)
    {
        this.context=context;
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        ViewGroup mainGroup=(ViewGroup)layoutInflater.inflate(R.layout.item_main_row,parent,false);
        MainRecyAdap.RecyclerViewHolder recyclerViewHolder=new MainRecyAdap.RecyclerViewHolder(mainGroup);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ActivityModel activityModel = data.get(position);
        MainRecyAdap.RecyclerViewHolder recyclerViewHolder= (MainRecyAdap.RecyclerViewHolder)holder;

      try {

      recyclerViewHolder.user.setText(activityModel.getUser());
      recyclerViewHolder.fullname.setText(" "+activityModel.getFullname());
      recyclerViewHolder.watcher.setText(" "+activityModel.getWatcher());
      recyclerViewHolder.stargazers_count.setText( " "+activityModel.getStargazers_count());
      recyclerViewHolder.fork.setText(" "+activityModel.getFork_count());
      recyclerViewHolder.score.setText(" "+activityModel.getScore());

      String imageURL=activityModel.getImage();

    try {
          URI uri = new URI(imageURL.replace(" ", "%20"));
          Picasso.with(context).load(String.valueOf(uri)).placeholder(R.drawable.ic_launcher).into(recyclerViewHolder.imageView);
      } catch (URISyntaxException e) {
          e.printStackTrace();
      }
       }
     catch (Exception e)
   {
      e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setItems(List<ActivityModel> datas){
        data = new ArrayList<>(datas);
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView fullname;
        public ImageView imageView;
        public TextView user;
        public TextView watcher,stargazers_count,score,fork;
        public TextView comit;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            this.fullname=(TextView)itemView.findViewById(R.id.full_name);
            this.user=(TextView)itemView.findViewById(R.id.username);
            this.imageView=(ImageView) itemView.findViewById(R.id.user_image);
            this.watcher=(TextView)itemView.findViewById(R.id.watcher_count);
            this.comit=(TextView)itemView.findViewById(R.id.commit_count);
            this.stargazers_count=(TextView)itemView.findViewById(R.id.stargazers_count);
            this.score=(TextView)itemView.findViewById(R.id.score);
            this.fork=(TextView)itemView.findViewById(R.id.fork1);
        }
    }
}
