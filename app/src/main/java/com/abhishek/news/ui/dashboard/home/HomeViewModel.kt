package com.abhishek.news.ui.dashboard.home

import android.app.Dialog
import android.content.Context
import com.abhishek.news.api.ApiHandler
import com.abhishek.news.api.RetrofitInstance
import com.abhishek.news.base.BaseViewModel
import com.abhishek.news.constants.ApiConstants
import com.abhishek.news.model.Article
import com.abhishek.news.model.TopHeadlinesModel
import com.abhishek.news.utils.ClassUtility

class HomeViewModel : BaseViewModel<HomeNavigator>() {

    var progressBar: Dialog? = null
    var feedsPageNumber: Int = 1;
    var feedsAvailablePages: Int = 0;

    var storiesPageNumber: Int = 1;
    var storiesAvailablePages: Int = 0;

    var headlinesPageNumber: Int = 1;
    var headlinesAvailablePages: Int = 0;

    fun fetchHeadlinesFromServer(context: Context, initialCall: Boolean = false) {
        if (initialCall) {
            headlinesPageNumber = 1
            headlinesAvailablePages = 0
        } else {
            headlinesPageNumber++
        }
        if (initialCall || headlinesPageNumber < headlinesAvailablePages) {
            ApiHandler<TopHeadlinesModel>(context)
                .onStart { progressBar?.show() }
                .doApiCall(
                    RetrofitInstance.getApiService(context)
                        .getTopHeadlines(
                            pageNumber = headlinesPageNumber,
                            category = ApiConstants.GENERAL.value
                        )
                )
                .addHandler(getHandlerForFetchHeadline(context))
                .build()
        }
    }

    fun fetchStoriesFromServer(context: Context, initialCall: Boolean = false) {
        if (initialCall) {
            storiesPageNumber = 1
            storiesAvailablePages = 0
        } else {
            storiesPageNumber++
        }
        if (initialCall || storiesPageNumber < storiesAvailablePages) {
            ApiHandler<TopHeadlinesModel>(context)
                .onStart { progressBar?.show() }
                .doApiCall(
                    RetrofitInstance.getApiService(context)
                        .getTopHeadlines(
                            pageNumber = storiesPageNumber,
                            category = ApiConstants.ENTERTAINMENT.value
                        )
                )
                .addHandler(getHandlerForFetchStories())
                .build()
        }
    }

    fun fetchFeedsFromServer(context: Context, initialCall: Boolean = false) {
        if (initialCall) {
            feedsPageNumber = 1
            feedsAvailablePages = 0
        } else {
            feedsPageNumber++
        }
        if (initialCall || feedsPageNumber < feedsAvailablePages) {
            ApiHandler<TopHeadlinesModel>(context)
                .onStart { progressBar?.show() }
                .doApiCall(
                    RetrofitInstance.getApiService(context)
                        .getTopHeadlines(
                            pageNumber = feedsPageNumber,
                            category = ApiConstants.SPORTS.value
                        )
                )
                .addHandler(getHandlerForFetchFeeds())
                .build()
        }
    }

    private fun getHandlerForFetchStories(): ApiHandler.Handle<TopHeadlinesModel> {
        return object : ApiHandler.Handle<TopHeadlinesModel> {
            override fun onSuccess(`object`: TopHeadlinesModel?) {
                progressBar?.dismiss()
                if (`object` != null && !`object`.getArticles().isNullOrEmpty()) {
                    storiesAvailablePages = `object`.getTotalResults()
                    val list: MutableList<HomeStoriesItemViewModel> = arrayListOf()
                    for (article: Article in `object`.getArticles()) {
                        val item = HomeStoriesItemViewModel(article)
                        list.add(item)
                    }
                    getNavigator()?.updateStories(list)
                }
            }

            override fun onFail(`object`: ApiHandler.Error?) {
                storiesAvailablePages = storiesPageNumber
                progressBar?.dismiss()
            }
        }
    }

    private fun getHandlerForFetchFeeds(): ApiHandler.Handle<TopHeadlinesModel> {
        return object : ApiHandler.Handle<TopHeadlinesModel> {
            override fun onSuccess(`object`: TopHeadlinesModel?) {
                progressBar?.dismiss()
                if (`object` != null && !`object`.getArticles().isNullOrEmpty()) {
                    feedsAvailablePages = `object`.getTotalResults()
                    val list: MutableList<HomeFeedsItemViewModel> = arrayListOf()
                    for (article: Article in `object`.getArticles()) {
                        val item = HomeFeedsItemViewModel(article)
                        list.add(item)
                    }
                    getNavigator()?.updateFeeds(list)
                }
            }

            override fun onFail(`object`: ApiHandler.Error?) {
                feedsAvailablePages = feedsPageNumber
                progressBar?.dismiss()
            }
        }
    }

    private fun getHandlerForFetchHeadline(context: Context): ApiHandler.Handle<TopHeadlinesModel> {
        return object : ApiHandler.Handle<TopHeadlinesModel> {
            override fun onSuccess(`object`: TopHeadlinesModel?) {
                progressBar?.dismiss()
                if (`object` != null && !`object`.getArticles().isNullOrEmpty()) {
                    headlinesAvailablePages = `object`.getTotalResults()
                    val list: MutableList<HomeHeadlinesItemViewModel> = arrayListOf()
                    for (article: Article in `object`.getArticles()) {
                        val item = HomeHeadlinesItemViewModel(
                            article,
                            ClassUtility.getRandomDarkColor(context)
                        )
                        list.add(item)
                    }
                    getNavigator()?.updateHeadLines(list)
                }
            }

            override fun onFail(`object`: ApiHandler.Error?) {
                headlinesAvailablePages = headlinesPageNumber
                progressBar?.dismiss()
            }
        }
    }

}
