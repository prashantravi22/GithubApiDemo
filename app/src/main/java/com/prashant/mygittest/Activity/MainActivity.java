package com.prashant.mygittest.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.prashant.mygittest.HttpHandler;
import com.prashant.mygittest.Adapter.MainRecyAdap;
import com.prashant.mygittest.Model.ActivityModel;
import com.prashant.mygittest.R;
import com.prashant.mygittest.RecytemClick;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ravi on 22-11-2017.
 */

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    RecyclerView act_recyecler;
    MainRecyAdap firstRA;
    private List<ActivityModel> userlist;
    String maingiturl="https://api.github.com/search/repositories?q=";
    String appendurl="&sort=stars&order=desc";
    FloatingActionButton fb;
    FloatingActionMenu fm;
    String[] namefilter={"sort by fork","sort by star","sort by score","sort by watcher"};
    ArrayList<FloatingActionButton> floatingActionButtonArrayList = new ArrayList<FloatingActionButton>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        act_recyecler =(RecyclerView)findViewById(R.id.act_recycler);
        act_recyecler.setHasFixedSize(true);
        act_recyecler.setLayoutManager( new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
        act_recyecler.setItemAnimator(new DefaultItemAnimator());
        act_recyecler.addOnItemTouchListener(new RecytemClick(this, new RecytemClick.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                  String repo=userlist.get(position).getRepo();
                  // Toast.makeText(MainActivity.this,"RRR  repo "+repo,Toast.LENGTH_SHORT).show();
                  Intent intent=new Intent( MainActivity.this,RepoDetailsActivity.class);
                  intent.putExtra("Repo",repo);
                  startActivity(intent);
            }
        }));

        fm = (FloatingActionMenu)findViewById(R.id.menu_item);

        for (int i = 0; i < namefilter.length; i++) {
            fb = new FloatingActionButton(MainActivity.this);
            fb.setImageDrawable(getResources().getDrawable(R.drawable.keep));
            fb.setLabelText(namefilter[i]);
            fb.setColorNormal(getResources().getColor(R.color.colorAccentblue));
            fb.setColorPressed(getResources().getColor(R.color.colorPrimaryDark));
            fb.setButtonSize(FloatingActionButton.SIZE_MINI);
            fb.setClickable(true);

            floatingActionButtonArrayList.add(fb);

            final int finalI = i;
            fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (finalI==0)
                    {
                        Collections.sort(userlist, new Comparator<ActivityModel>() {
                            @Override
                            public int compare(ActivityModel lhs, ActivityModel rhs) {
                                return lhs.getFork_count().compareTo(rhs.getFork_count());
                            }
                        });

                        firstRA=new MainRecyAdap(getApplicationContext(),userlist);
                    }
                    else if(finalI==1)
                    {
                        Collections.sort(userlist, new Comparator<ActivityModel>() {
                            @Override
                            public int compare(ActivityModel lhs, ActivityModel rhs) {
                                return lhs.getStargazers_count().compareTo(rhs.getStargazers_count());
                            }
                        });

                        firstRA=new MainRecyAdap(getApplicationContext(),userlist);
                    }
                    else if(finalI==2)
                    {
                        Collections.sort(userlist, new Comparator<ActivityModel>() {
                            @Override
                            public int compare(ActivityModel lhs, ActivityModel rhs) {
                                return lhs.getScore().compareTo(rhs.getScore());
                            }
                        });

                        firstRA=new MainRecyAdap(getApplicationContext(),userlist);
                    }
                    else if(finalI==3)
                    {
                        Collections.sort(userlist, new Comparator<ActivityModel>() {
                            @Override
                            public int compare(ActivityModel lhs, ActivityModel rhs) {
                                return lhs.getWatcher().compareTo(rhs.getWatcher());
                            }
                        });

                        firstRA=new MainRecyAdap(getApplicationContext(),userlist);

                    }
                    act_recyecler.setAdapter(firstRA);
                    firstRA.notifyDataSetChanged();

                }
            });
            fm.addMenuButton(fb);
        }

        new GetGITData().execute(maingiturl+"android"+appendurl);
      //  new GetGITData().execute("https://api.github.com/search/repositories?q=tetris+language:android&sort=stars&order=desc");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        try {
            final MenuItem item = menu.findItem(R.id.action_search);
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
            searchView.setQueryHint("search repo. like android,php");

            searchView.setOnQueryTextListener(this);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    query = query.trim();
                    if("" == query || 0 == query.length()){
                        query = "android";
                    }
                    new GetGITData().execute(maingiturl+ URLEncoder.encode(query) +appendurl);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private class GetGITData extends AsyncTask<String, Void, Void> {
        private String TAG = MainActivity.class.getSimpleName();
        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(String... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(arg0[0]);

            if (jsonStr != null) {
                try {
                    userlist= new ArrayList<ActivityModel>();
                    JSONObject jsonRootObject = new JSONObject(jsonStr);
                    JSONArray firstArray = jsonRootObject.getJSONArray("items");

                    for (int i = 0; i < firstArray.length(); i++) {

                        JSONObject c = firstArray.getJSONObject(i);
                        String user = c.getString("name");
                        String full_name = c.getString("full_name");
                        String watchers = c.getString("watchers_count");
                        String stargazers_count = c.getString("stargazers_count");
                        String forks_count = c.getString("forks_count");
                        String score = c.getString("score");
                        String url = c.getString("url");

                        JSONObject owner = c.getJSONObject("owner");
                        String image = owner.getString("avatar_url");
                        // String repos_url = owner.getString("repos_url");

                         ActivityModel activityModel=new ActivityModel();
                         activityModel.setUser(user);
                         activityModel.setFullname(full_name);
                         activityModel.setWatcher(watchers);
                         activityModel.setImage(image);
                         activityModel.setRepo(url);
                         activityModel.setStargazers_count(stargazers_count);
                         activityModel.setFork_count(forks_count);
                         activityModel.setScore(score);
                        userlist.add(activityModel);
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


            firstRA=new MainRecyAdap(getApplicationContext(),userlist);
            act_recyecler.setAdapter(firstRA);
            firstRA.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.filter) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {

        final List<ActivityModel> filteredModelList = filter(userlist, query);
        firstRA.setItems(filteredModelList);
        firstRA.notifyDataSetChanged();
        act_recyecler.scrollToPosition(0);

        return true;
    }

    private List<ActivityModel> filter(List<ActivityModel> datas, String newText) {
        newText = newText.toLowerCase();

        final List<ActivityModel> filteredModelList = new ArrayList<>();
        for (ActivityModel data : datas) {
            final String text = data.getFullname().toLowerCase();
            final String user = data.getUser().toLowerCase();
            if (text.contains(newText) || user.contains(newText))
            {
                filteredModelList.add(data);
            }
        }
        return filteredModelList;
    }

}
