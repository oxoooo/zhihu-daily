/*
 *         DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                     Version 2, December 2004
 *
 *  Copyright (C) 2015 XiNGRZ <xxx@oxo.ooo>
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   0. You just DO WHAT THE FUCK YOU WANT TO.
 *
 */

package ooo.oxo.zhihu.daily;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import ooo.oxo.zhihu.daily.api.ApiBuilder;
import ooo.oxo.zhihu.daily.api.StoryApi;
import ooo.oxo.zhihu.daily.api.model.News;
import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.refresher)
    SwipeRefreshLayout refresher;

    @Bind(R.id.content)
    RecyclerView content;

    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        refresher.setColorSchemeResources(R.color.accent);
        refresher.setOnRefreshListener(this);

        adapter = new NewsAdapter(this) {
            @Override
            protected void onItemClick(View v, News.Abstract story) {
                openStory(v, story);
            }
        };

        content.setLayoutManager(new LinearLayoutManager(this));
        content.setAdapter(adapter);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        ApiBuilder.create(StoryApi.class).latest().enqueue(new Callback<News>() {
            @Override
            public void onResponse(Response<News> response) {
                refresher.setRefreshing(false);
                adapter.setNews(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                refresher.setRefreshing(false);
            }
        });
    }

    private void openStory(View v, News.Abstract story) {
        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra(StoryActivity.EXTRA_ID, story.id);
        intent.putExtra(StoryActivity.EXTRA_TITLE, story.title);
        startActivity(intent);
    }

}
