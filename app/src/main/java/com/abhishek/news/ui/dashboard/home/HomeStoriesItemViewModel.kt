package com.abhishek.news.ui.dashboard.home

import android.view.View
import com.abhishek.news.base.BaseViewModel
import com.abhishek.news.model.Article

/**
 * Created by Abhishek Garg on 18/05/20 - https://www.linkedin.com/in/abhishekgarg727/
 */
class HomeStoriesItemViewModel(
    val article: Article
) : BaseViewModel<HomeStoriesItemNavigator>() {

    fun openDetailScreen(view: View) {
        getNavigator()?.onStoriesItemClick(view, article)
    }
}