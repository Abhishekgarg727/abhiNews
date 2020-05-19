package com.abhishek.news.ui.dashboard.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.abhishek.news.BR
import com.abhishek.news.R
import com.abhishek.news.base.BaseBackPressSensitveFragment
import com.abhishek.news.base.BaseFragment
import com.abhishek.news.databinding.NewsDetailsFragmentBinding
import com.abhishek.news.ui.dashboard.DashboardActivity

class NewsDetailsFragment :
    BaseBackPressSensitveFragment<NewsDetailsFragmentBinding, NewsDetailsViewModel, NewsDetailsNavigator>(),
    NewsDetailsNavigator {

    companion object {
        fun newInstance() = NewsDetailsFragment()
    }

    override var mViewModel: NewsDetailsViewModel
        get() = ViewModelProviders.of(this).get(NewsDetailsViewModel::class.java)
        set(value) {}
    override val layoutId: Int
        get() = R.layout.news_details_fragment
    override val bindingVariable: Int
        get() = BR.newsDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        init()
        setupActionBarBackPress()
    }

    private fun init() {
        if (mContext != null) {
            mViewModel.setNavigator(this)
            val args = arguments ?: Bundle()
            val article = NewsDetailsFragmentArgs.fromBundle(args).article
            mViewModel.article.postValue(article)
            mViewModel.categoryName.postValue(article.getSource().getName())
        }
    }

    override fun onBackPressed() : Boolean {
        findNavController().navigateUp()
        return true;
    }


    private fun setupActionBarBackPress() {
        viewDataBinding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }


}
