package com.abhishek.news.ui.dashboard.home

import android.app.Dialog
import androidx.lifecycle.MutableLiveData
import com.abhishek.news.api.ApiHandler
import com.abhishek.news.api.RetrofitInstance
import com.abhishek.news.base.BaseViewModel
import com.abhishek.news.constants.ApiConstants
import com.abhishek.news.model.Article
import com.abhishek.news.model.TopHeadlinesModel
import com.abhishek.news.utils.ClassUtility

class HomeViewModel : BaseViewModel<HomeNavigator>() {

    var progressBar: Dialog? = null
    var feedsPageNumber: Int = 1
    var feedsAvailablePages: Int = 0
    var feedsInitialCallFlag = true

    var storiesPageNumber: Int = 1
    var storiesAvailablePages: Int = 0
    var storiesInitialCallFlag = true

    var headlinesPageNumber: Int = 1
    var headlinesAvailablePages: Int = 0
    var headlinesInitialCallFlag = true

    var feedsMutableLiveDataList: MutableLiveData<MutableList<HomeFeedsItemViewModel>> =
        MutableLiveData(
            mutableListOf()
        )
    var storiesMutableLiveDataList: MutableLiveData<MutableList<HomeStoriesItemViewModel>> =
        MutableLiveData(
            mutableListOf()
        )
    var headlinesMutableLiveDataList: MutableLiveData<MutableList<HomeHeadlinesItemViewModel>> =
        MutableLiveData(
            mutableListOf()
        )

    fun fetchHeadlinesFromServer() {
        if (headlinesPageNumber < headlinesAvailablePages || headlinesInitialCallFlag) {
            if (headlinesInitialCallFlag) headlinesInitialCallFlag = false
            ApiHandler<TopHeadlinesModel>()
                .onStart { progressBar?.show() }
                .doApiCall(
                    RetrofitInstance.getApiService()
                        .getTopHeadlines(
                            pageNumber = headlinesPageNumber,
                            category = ApiConstants.GENERAL.value
                        )
                )
                .addHandler(getHandlerForFetchHeadline())
                .build()
        }
    }

    fun fetchStoriesFromServer() {
        if (storiesPageNumber < storiesAvailablePages || storiesInitialCallFlag) {
            if (storiesInitialCallFlag) storiesInitialCallFlag = false
            ApiHandler<TopHeadlinesModel>()
                .onStart { progressBar?.show() }
                .doApiCall(
                    RetrofitInstance.getApiService()
                        .getTopHeadlines(
                            pageNumber = storiesPageNumber,
                            category = ApiConstants.ENTERTAINMENT.value
                        )
                )
                .addHandler(getHandlerForFetchStories())
                .build()
        }
    }

    fun fetchFeedsFromServer() {
        if (feedsPageNumber < feedsAvailablePages || feedsInitialCallFlag) {
            if (feedsInitialCallFlag) feedsInitialCallFlag = false
            ApiHandler<TopHeadlinesModel>()
                .onStart { progressBar?.show() }
                .doApiCall(
                    RetrofitInstance.getApiService()
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
                storiesPageNumber++
                if (`object` != null && !`object`.getArticles().isNullOrEmpty()) {

                    storiesAvailablePages = `object`.getTotalResults()

                    val list: MutableList<HomeStoriesItemViewModel> =
                        storiesMutableLiveDataList.value ?: arrayListOf()

                    for (article: Article in `object`.getArticles()) {
                        val item = HomeStoriesItemViewModel(article)
                        list.add(item)
                    }
                    storiesMutableLiveDataList.value = list
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
                feedsPageNumber++
                if (`object` != null && !`object`.getArticles().isNullOrEmpty()) {

                    feedsAvailablePages = `object`.getTotalResults()

                    val list: MutableList<HomeFeedsItemViewModel> =
                        feedsMutableLiveDataList.value ?: arrayListOf()

                    for (article: Article in `object`.getArticles()) {
                        val item = HomeFeedsItemViewModel(article)
                        list.add(item)
                    }
                    progressBar?.dismiss()
                    feedsMutableLiveDataList.value = list
                }
            }

            override fun onFail(`object`: ApiHandler.Error?) {
                feedsAvailablePages = feedsPageNumber
                progressBar?.dismiss()
            }
        }
    }

    private fun getHandlerForFetchHeadline(): ApiHandler.Handle<TopHeadlinesModel> {
        return object : ApiHandler.Handle<TopHeadlinesModel> {
            override fun onSuccess(`object`: TopHeadlinesModel?) {
                progressBar?.dismiss()
                headlinesPageNumber++
                if (`object` != null && !`object`.getArticles().isNullOrEmpty()) {

                    headlinesAvailablePages = `object`.getTotalResults()

                    val list: MutableList<HomeHeadlinesItemViewModel> =
                        headlinesMutableLiveDataList.value ?: arrayListOf()

                    for (article: Article in `object`.getArticles()) {
                        val item = HomeHeadlinesItemViewModel(
                            article,
                            ClassUtility.getRandomDarkColor()
                        )
                        list.add(item)
                    }
                    headlinesMutableLiveDataList.value ?: arrayListOf()
                }
            }

            override fun onFail(`object`: ApiHandler.Error?) {
                headlinesAvailablePages = headlinesPageNumber
                progressBar?.dismiss()
            }
        }
    }

}
