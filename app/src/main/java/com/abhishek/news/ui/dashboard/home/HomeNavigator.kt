package com.abhishek.news.ui.dashboard.home

import android.view.View
import com.abhishek.news.model.Article

/**
 * Created by Abhishek Garg on 17/05/20 - https://www.linkedin.com/in/abhishekgarg727/
 */
interface HomeNavigator {

    fun updateHeadLines(list: List<HomeHeadlinesItemViewModel>)

    fun updateFeeds(list: List<HomeFeedsItemViewModel>)

    fun updateStories(list: List<HomeStoriesItemViewModel>)
}