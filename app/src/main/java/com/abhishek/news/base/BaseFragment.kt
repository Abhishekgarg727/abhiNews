package com.abhishek.news.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by Abhishek Garg on 17/05/20 - https://www.linkedin.com/in/abhishekgarg727/
 */

/**
 * Base Fragment for easy implementation of
 * Data binding and MVVM
 * @param <T> Data binding object of the fragment
 * @param <V> ViewModel object #must extend the BaseView Model
 * */
abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<N>,N> : Fragment() {

    var mContext : Context? = null

    /**
     * Override to set Data binding
     *
     * @return DataBinding instance
     */
    lateinit var viewDataBinding: T

    /**
     * Override to set view model
     *
     * @return view model instance
     */
    abstract var mViewModel: V

    /**
     * Override to set Layout Resource id
     *
     * @return layout Id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    abstract val bindingVariable: Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        performBinding()
        return viewDataBinding.getRoot()
    }

    /**
     * method to perform the data binding
     * and set the view model variable
     */
    private fun performBinding() {
        viewDataBinding.setVariable(bindingVariable, mViewModel)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
    }
}
