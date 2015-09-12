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

package ooo.oxo.zhihu.daily.api;

import ooo.oxo.zhihu.daily.api.model.News;
import ooo.oxo.zhihu.daily.api.model.Story;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface StoryApi {

    @GET("stories/latest")
    Call<News> latest();

    @GET("story/{id}")
    Call<Story> get(@Path("id") int id);

}
