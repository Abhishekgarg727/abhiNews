package com.abhishek.news.ui.dashboard.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
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

    private var headlineList: MutableList<HomeHeadlinesItemViewModel> = mutableListOf()
    private var headlineAdapter: HomeHeadlinesAdapter? = null

    private var feedsList: MutableList<HomeFeedsItemViewModel> = mutableListOf()
    private var feedsAdapter: HomeFeedsAdapter? = null

    private var storiesList: MutableList<HomeStoriesItemViewModel> = mutableListOf()
    private var storiesAdapter: HomeStoriesAdapter? = null

    override var mViewModel: HomeViewModel
        get() = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        set(value) {
        }

    override val layoutId: Int
        get() = R.layout.home_fragment

    override val bindingVariable: Int
        get() = BR.homeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun updateHeadLines(list: List<HomeHeadlinesItemViewModel>) {
        if (!list.isNullOrEmpty()) {
            if (mViewModel.headlinesPageNumber == 1) headlineList.clear()
            headlineList.addAll(list)
            headlineAdapter?.notifyDataSetChanged()
        }
    }

    override fun updateFeeds(list: List<HomeFeedsItemViewModel>) {
        if (!list.isNullOrEmpty()) {
            if (mViewModel.feedsPageNumber == 1) feedsList.clear()
            feedsList.addAll(list)
            feedsAdapter?.notifyDataSetChanged()
        }
    }

    override fun updateStories(list: List<HomeStoriesItemViewModel>) {
        if (!list.isNullOrEmpty()) {
            if (mViewModel.storiesPageNumber == 1) storiesList.clear()
            storiesList.addAll(list)
            storiesAdapter?.notifyDataSetChanged()
        }
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        init()
    }


    private fun init() {
        if (mContext != null) {
            initViewModel()
            setupListAdapters()
            fetchDataFromServer()
            setupHeadlinesRecycleView()
            setupStoriesRecycleView()
            setupFeedsRecycleView()
        }
    }

    private fun initViewModel() {
        mViewModel.getNavigator() ?: mViewModel.setNavigator(this)
        mViewModel.progressBar =
            mViewModel.progressBar ?: ClassUtility.getCustomProgressBar(mContext)
    }

    private fun setupListAdapters() {
        headlineAdapter = headlineAdapter ?: HomeHeadlinesAdapter(headlineList, mContext, this)
        storiesAdapter = storiesAdapter ?: HomeStoriesAdapter(storiesList, mContext, this)
        feedsAdapter = feedsAdapter ?: HomeFeedsAdapter(feedsList, mContext, this)
    }

    private fun fetchDataFromServer() {
        if (headlineList.isNullOrEmpty())
            mViewModel.fetchHeadlinesFromServer(mContext ?: requireContext(), true)

        if (storiesList.isNullOrEmpty())
            mViewModel.fetchStoriesFromServer(mContext ?: requireContext(), true)

        if (feedsList.isNullOrEmpty())
            mViewModel.fetchFeedsFromServer(mContext ?: requireContext(), true)
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
                    mViewModel.fetchHeadlinesFromServer(mContext ?: requireContext())
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
                    mViewModel.fetchStoriesFromServer(mContext ?: requireContext())
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
                    mViewModel.fetchFeedsFromServer(mContext ?: requireContext())
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
