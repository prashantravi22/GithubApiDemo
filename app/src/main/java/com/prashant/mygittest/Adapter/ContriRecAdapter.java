package com.prashant.mygittest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.prashant.mygittest.Model.RepoDetailsModel;
import com.prashant.mygittest.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 25-11-2017.
 */

public class ContriRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<RepoDetailsModel> data;

    public ContriRecAdapter(Context context, List<RepoDetailsModel> data)
    {
        this.context=context;
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        ViewGroup mainGroup=(ViewGroup)layoutInflater.inflate(R.layout.item_countri_row,parent,false);
         RecyclerViewHolder recyclerViewHolder=new  RecyclerViewHolder(mainGroup);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RepoDetailsModel activityModel = data.get(position);
         RecyclerViewHolder recyclerViewHolder= ( RecyclerViewHolder)holder;

        try {

            recyclerViewHolder.count_repo.setText((position+1) +". " +activityModel.getName());
            recyclerViewHolder.my_repo.setText(activityModel.getDescription());
            recyclerViewHolder.repourl.setText(activityModel.getProjectlink());


        }
        catch (Exception e)
        {
            e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setItems(List<RepoDetailsModel> datas){
        data = new ArrayList<>(datas);
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        public TextView count_repo , my_repo,repourl;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            this.count_repo=(TextView)itemView.findViewById(R.id.count_repo);
            this.my_repo=(TextView)itemView.findViewById(R.id.my_repo);
            this.repourl=(TextView)itemView.findViewById(R.id.repo_url);

        }
    }
}
