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

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import ooo.oxo.zhihu.daily.api.ApiBuilder;
import ooo.oxo.zhihu.daily.api.StoryApi;
import ooo.oxo.zhihu.daily.api.model.Story;
import pocketknife.BindExtra;
import pocketknife.PocketKnife;
import retrofit.Callback;
import retrofit.Response;

public class StoryActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TITLE = "title";

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.image)
    ImageView image;

    @Bind(R.id.content)
    WebView content;

    @BindColor(R.color.primary)
    int colorPrimary;

    @BindExtra(EXTRA_ID)
    int id;

    @BindExtra(EXTRA_TITLE)
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story);

        ButterKnife.bind(this);
        PocketKnife.bindExtras(this);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });

        collapsingToolbar.setTitle(title);

        ApiBuilder.create(StoryApi.class).get(id).enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Response<Story> response) {
                populate(response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void populate(Story story) {
        if (story.image != null) {
            populateImage(story.image);
        }

        StringBuilder html = new StringBuilder("<DOCTYPE html><html><head><meta charset=\"utf8\">");

        for (String style : story.css) {
            html.append("<link rel=\"stylesheet\" href=\"");
            html.append(style);
            html.append("\">");
        }

        html.append("<style>.headline .img-place-holder { display: none; }</style>");

        html.append("</head><body>");
        html.append(story.body);
        html.append("</body></html>");

        content.loadDataWithBaseURL(story.shareUrl, html.toString(), "text/html", null, null);
    }

    private void populateImage(String url) {
        Picasso.with(this)
                .load(url)
                .fit().centerCrop()
                .into(image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.story, menu);
        return true;
    }

}
