package com.abhishek.news.ui.dashboard.detail

import androidx.lifecycle.MutableLiveData
import com.abhishek.news.base.BaseViewModel
import com.abhishek.news.model.Article

class NewsDetailsViewModel : BaseViewModel<NewsDetailsNavigator>() {

    var article: MutableLiveData<Article> = MutableLiveData(Article())
    var categoryName:  MutableLiveData<String>  = MutableLiveData("")
}
