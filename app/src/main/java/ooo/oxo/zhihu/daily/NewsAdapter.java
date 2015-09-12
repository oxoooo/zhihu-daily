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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import ooo.oxo.zhihu.daily.api.model.News;

public abstract class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final int TYPE_DATE = 1;
    private static final int TYPE_STORY = 2;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);

    private final Context context;
    private final LayoutInflater inflater;

    private News news;

    public NewsAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setNews(News news) {
        this.news = news;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_DATE) {
            return new DateViewHolder(inflater.inflate(R.layout.item_date, parent, false));
        } else {
            return new StoryViewHolder(inflater.inflate(R.layout.item_story, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_DATE) {
            onBindDateViewHolder((DateViewHolder) holder);
        } else {
            onBindStoryViewHolder((StoryViewHolder) holder, position - 1);
        }
    }

    private void onBindDateViewHolder(DateViewHolder holder) {
        holder.date.setText(DATE_FORMAT.format(news.date));
    }

    private void onBindStoryViewHolder(StoryViewHolder holder, int position) {
        News.Abstract story = news.stories.get(position);

        holder.title.setText(story.title);
        holder.title.setTransitionName("title_" + story.id);

        Picasso.with(context).load(story.images.get(0)).into(holder.image);
        holder.image.setTransitionName("image_" + story.id);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_DATE : TYPE_STORY;
    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.stories.size() + 1;
    }

    private void handleItemClick(View v, int position) {
        onItemClick(v, news.stories.get(position - 1));
    }

    protected abstract void onItemClick(View v, News.Abstract story);

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class DateViewHolder extends ViewHolder {

        @Bind(R.id.date)
        TextView date;

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class StoryViewHolder extends ViewHolder {

        @Bind(R.id.title)
        TextView title;

        @Bind(R.id.image)
        ImageView image;

        public StoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleItemClick(v, getAdapterPosition());
                }
            });
        }

    }

}
