package com.prashant.mygittest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.prashant.mygittest.Model.ActivityModel;
import com.prashant.mygittest.Model.RepoDetailsModel;
import com.prashant.mygittest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ravi on 25-11-2017.
 */

public class CustomGridAdapter extends BaseAdapter {

    private Context mContext;

    ArrayList<ActivityModel> repoDetailsModels;

    public CustomGridAdapter(Context c, ArrayList<ActivityModel> repoDetailsModels ) {
        mContext = c;
    this.repoDetailsModels=repoDetailsModels;
    }

    @Override
    public int getCount() {
        if(repoDetailsModels != null)
            return repoDetailsModels.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.item_grid_row, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(repoDetailsModels.get(position).getUser());
           // imageView.setImageResource();
            Picasso.with(mContext).load(repoDetailsModels.get(position).getImage()).into(imageView);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }




}
