package com.abhishek.news.ui.dashboard.home

import android.content.Context
import android.view.ViewGroup
import com.abhishek.news.BR
import com.abhishek.news.R
import com.abhishek.news.base.BaseRecyclerAdapter
import com.abhishek.news.databinding.HeadlineNewsCardItemBinding

/**
 * Created by Abhishek Garg on 18/05/20 - https://www.linkedin.com/in/abhishekgarg727/
 */
class HomeHeadlinesAdapter(
    var list: List<HomeHeadlinesItemViewModel>,
    var context: Context?,
    var homeHeadlinesItemNavigator: HomeHeadlinesItemNavigator
) :
    BaseRecyclerAdapter<HeadlineNewsCardItemBinding>() {


    override val layoutId: Int
        get() = R.layout.headline_news_card_item

    override val bindingVariable: Int
        get() = BR.headlineNewsCardItemViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val viewModel: HomeHeadlinesItemViewModel = list[position]
        viewModel.setNavigator(homeHeadlinesItemNavigator)
        holder.bind(viewModel)
    }

}