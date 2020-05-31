package com.abhishek.news.ui.dashboard.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.news.BR
import com.abhishek.news.R
import com.abhishek.news.base.BaseFragment
import com.abhishek.news.customViews.recycleView.LinearLayoutSpaceDecoration
import com.abhishek.news.databinding.HomeFragmentBinding
import com.abhishek.news.model.Article
import com.abhishek.news.utils.ClassUtility


class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel, HomeNavigator>(),
    HomeNavigator, HomeFeedsItemNavigator, HomeStoriesItemNavigator, HomeHeadlinesItemNavigator {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var headlineAdapter: HomeHeadlinesAdapter? = null
    private var feedsAdapter: HomeFeedsAdapter? = null
    private var storiesAdapter: HomeStoriesAdapter? = null

    override var mViewModel: HomeViewModel
        get() = ViewModelProvider(this).get(HomeViewModel::class.java)
        set(value) {}

    override val layoutId: Int
        get() = R.layout.home_fragment

    override val bindingVariable: Int
        get() = BR.homeViewModel

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        init()
    }

    private fun init() {
        if (mContext != null) {
            initViewModel()
            setupListAdapters()
            setupAdapterDataObserver()
            fetchDataFromServer()
            setupHeadlinesRecycleView()
            setupStoriesRecycleView()
            setupFeedsRecycleView()
        }
    }


    private fun initViewModel() {
        mViewModel.getNavigator() ?: mViewModel.setNavigator(this)
        mViewModel.progressBar = mViewModel.progressBar
            ?: ClassUtility.getCustomProgressBar(mContext)
    }

    private fun setupListAdapters() {
        // headlines
        headlineAdapter = headlineAdapter ?: mViewModel.headlinesMutableLiveDataList.value?.let {
            HomeHeadlinesAdapter(it, mContext, this)
        }
        // stories
        storiesAdapter = storiesAdapter ?: mViewModel.storiesMutableLiveDataList.value?.let {
            HomeStoriesAdapter(it, mContext, this)
        }
        // feeds
        feedsAdapter = feedsAdapter ?: mViewModel.feedsMutableLiveDataList.value?.let {
            HomeFeedsAdapter(it, mContext, this)
        }
    }

    private fun setupAdapterDataObserver() {
        viewDataBinding.lifecycleOwner?.let {
            mViewModel.feedsMutableLiveDataList.observe(it, Observer {
                feedsAdapter?.notifyDataSetChanged()
            })
            mViewModel.storiesMutableLiveDataList.observe(it, Observer {
                storiesAdapter?.notifyDataSetChanged()
            })
            mViewModel.headlinesMutableLiveDataList.observe(it, Observer {
                headlineAdapter?.notifyDataSetChanged()
            })
        }
    }

    private fun fetchDataFromServer() {
        if (mViewModel.headlinesMutableLiveDataList.value.isNullOrEmpty())
            mViewModel.fetchHeadlinesFromServer()

        if (mViewModel.storiesMutableLiveDataList.value.isNullOrEmpty())
            mViewModel.fetchStoriesFromServer()

        if (mViewModel.feedsMutableLiveDataList.value.isNullOrEmpty())
            mViewModel.fetchFeedsFromServer()
    }

    private fun setupHeadlinesRecycleView() {
        val linearLayout: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.articleNewsListRecycleView.layoutManager = linearLayout
        viewDataBinding.articleNewsListRecycleView.setHasFixedSize(true)
        //  headlineAdapter?.setHasStableIds(true)
        viewDataBinding.articleNewsListRecycleView.addItemDecoration(
            LinearLayoutSpaceDecoration(8, 8, 4)
        )
        viewDataBinding.articleNewsListRecycleView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollHorizontally(1)) {
                    mViewModel.fetchHeadlinesFromServer()
                }
            }
        })
        viewDataBinding.articleNewsListRecycleView.adapter = headlineAdapter
    }

    private fun setupStoriesRecycleView() {
        val linearLayout: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.storiesNewsListRecycleView.layoutManager = linearLayout
        viewDataBinding.storiesNewsListRecycleView.setHasFixedSize(true)
        viewDataBinding.storiesNewsListRecycleView.addItemDecoration(
            LinearLayoutSpaceDecoration(8, 8, 4)
        )
        viewDataBinding.storiesNewsListRecycleView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollHorizontally(1)) {
                    mViewModel.fetchStoriesFromServer()
                }
            }
        })
        viewDataBinding.storiesNewsListRecycleView.adapter = storiesAdapter
    }

    private fun setupFeedsRecycleView() {
        val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(context)
        viewDataBinding.feedNewsListRecycleView.layoutManager = linearLayout
        viewDataBinding.feedNewsListRecycleView.setHasFixedSize(true)
        viewDataBinding.feedNewsListRecycleView.addItemDecoration(
            LinearLayoutSpaceDecoration(4, 8, 100)
        )
        viewDataBinding.feedNewsListRecycleView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    mViewModel.fetchFeedsFromServer()
                }
            }
        })
        viewDataBinding.feedNewsListRecycleView.adapter = feedsAdapter
    }

    override fun onFeedItemClick(view: View, article: Article) {
        openDetailsFragment(view, article)
    }

    override fun onStoriesItemClick(view: View, article: Article) {
        openDetailsFragment(view, article)
    }

    override fun onHeadlinesItemClick(view: View, article: Article) {
        openDetailsFragment(view, article)
    }

    private fun openDetailsFragment(view: View, article: Article) {
        val direction = HomeFragmentDirections.actionHomeFragmentToNewsDetailsFragment(article)
        findNavController().navigate(direction)
    }
}
