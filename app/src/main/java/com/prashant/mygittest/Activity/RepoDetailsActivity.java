package com.prashant.mygittest.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.prashant.mygittest.Adapter.CustomGridAdapter;
import com.prashant.mygittest.HttpHandler;
import com.prashant.mygittest.Model.ActivityModel;
import com.prashant.mygittest.R;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by ravi on 25-11-2017.
 */

public class RepoDetailsActivity extends AppCompatActivity {

    String repos_url;
    String user_name, description, html_url, contributors_url, img_url;
    TextView repo_name;
    TextView repo_project;
    TextView repo_desc;
    ImageView image;
    GridView gridView;
    ArrayList<ActivityModel> activityModels=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        repos_url = getIntent().getStringExtra("Repo");
        repo_name = (TextView) findViewById(R.id.repo_name);
        repo_project = (TextView) findViewById(R.id.repo_project_name);
        repo_project.setPaintFlags(repo_project.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        repo_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RepoDetailsActivity.this, WebViewActivity.class);
                intent.putExtra("url",html_url);
                startActivity(intent);
            }
        });
        repo_desc = (TextView) findViewById(R.id.repo_description);
        image = (ImageView) findViewById(R.id.repo_image);
        gridView=(GridView)findViewById(R.id.repo_grid) ;

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s=activityModels.get(position).getRepo();
                Intent intent=new Intent(RepoDetailsActivity.this,ContributorDetails.class);
                intent.putExtra("count_det",s);
                startActivity(intent);
            }
        });
        new GetRepoData().execute();

    }


    private class GetRepoData extends AsyncTask<Void, Void, Void> {
        private String TAG = RepoDetailsActivity.class.getSimpleName();
        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RepoDetailsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(repos_url);

            if (jsonStr != null) {
                try {
                    JSONObject c = new JSONObject(jsonStr);
                    user_name = c.getString("name");
                    description = c.getString("description");
                    html_url = c.getString("html_url");
                    contributors_url = c.getString("contributors_url");

                    String s = sh.makeServiceCall(contributors_url);
                    JSONArray jsonArray=new JSONArray(s);
                    activityModels=new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String name=jsonObject.getString("login");
                        String image=jsonObject.getString("avatar_url");
                        String url=jsonObject.getString("repos_url");

                        ActivityModel activityModel=new ActivityModel();
                        activityModel.setUser(name);
                        activityModel.setImage(image);
                        activityModel.setRepo(url);

                         activityModels.add(activityModel);
                    }


                    JSONObject owner = c.getJSONObject("owner");
                    img_url = owner.getString("avatar_url");
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
            repo_name.setText(user_name);
            repo_desc.setText(description);
            repo_project.setText(html_url);
            Picasso.with(RepoDetailsActivity.this).load(String.valueOf(img_url)).placeholder(R.drawable.ic_launcher).into(image);

            CustomGridAdapter customGridAdapter=new CustomGridAdapter(RepoDetailsActivity.this,activityModels);
            gridView.setAdapter(customGridAdapter);
            customGridAdapter.notifyDataSetChanged();
        }
    }
}
