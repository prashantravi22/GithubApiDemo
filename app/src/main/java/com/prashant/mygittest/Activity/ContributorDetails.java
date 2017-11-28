package com.prashant.mygittest.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.prashant.mygittest.Adapter.ContriRecAdapter;
import com.prashant.mygittest.HttpHandler;
import com.prashant.mygittest.Model.RepoDetailsModel;
import com.prashant.mygittest.R;
import com.prashant.mygittest.RecytemClick;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by ravi on 25-11-2017.
 */

public class ContributorDetails extends AppCompatActivity {
    ArrayList<RepoDetailsModel> repoDetailsModels;
    ImageView imageView;
    String reponame, repolink;
    RecyclerView recyclerView;
    public ContriRecAdapter contriRecAdapter;
    String intentdata,imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contri_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        intentdata = getIntent().getStringExtra("count_det");

        imageView = (ImageView) findViewById(R.id.countri_image);
        recyclerView = (RecyclerView) findViewById(R.id.contri_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContributorDetails.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new RecytemClick(this, new RecytemClick.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String repo = repoDetailsModels.get(position).getProjectlink();

                Intent intent = new Intent(ContributorDetails.this, RepoDetailsActivity.class);
                intent.putExtra("Repo", repo);
                startActivity(intent);
            }
        }));
        new GetGITData().execute();
    }


    private class GetGITData extends AsyncTask<Void, Void, Void> {
        private String TAG = MainActivity.class.getSimpleName();
        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ContributorDetails.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(intentdata);

            if (jsonStr != null) {
                try {
                    repoDetailsModels = new ArrayList<RepoDetailsModel>();
                    JSONArray firstArray = new JSONArray(jsonStr);

                    for (int i = 0; i < firstArray.length(); i++) {

                        JSONObject c = firstArray.getJSONObject(i);
                        reponame = c.getString("name");
                        repolink = c.getString("url");
                        if(i==0) {
                            JSONObject jsonObject = c.getJSONObject("owner");
                            imageurl = jsonObject.getString("avatar_url");
                        }


                        RepoDetailsModel activityModel = new RepoDetailsModel();
                        activityModel.setName(reponame);
                        activityModel.setProjectlink(repolink);
                        repoDetailsModels.add(activityModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

            Picasso.with(ContributorDetails.this).load(imageurl).into(imageView);

            contriRecAdapter = new ContriRecAdapter(ContributorDetails.this, repoDetailsModels);
            recyclerView.setAdapter(contriRecAdapter);
            contriRecAdapter.notifyDataSetChanged();
        }
    }
}
