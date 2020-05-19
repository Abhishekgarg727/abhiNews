package com.abhishek.news.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Base Activity for easy implementation of
 * Data binding and MVVM
 *
 * @param <T> Data binding object of the activity
 * @param <V> ViewModel object #must extend the BaseView Model
</V></T> */

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<N>, N > : AppCompatActivity() {
    /**
     * Override to set DataBinding
     * @return DataBinding instance
     */
    lateinit var mViewDataBinding: T
    /**
     * Override to set view model
     * @return view model instance
     */
    abstract var mViewModel: V

    /**
     * Override to set Layout Resource id
     * @return layout Id
     */
    @get:LayoutRes
    abstract val layoutId: Int
    abstract val bindingVariable: Int

    /**
     * override this method for initialView setup
     */
    abstract fun initialSetup()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        initialSetup()
    }

    /**
     * method to perform the data binding
     * and set the view model variable
     */
    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mViewDataBinding.setVariable(bindingVariable, mViewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
    }
}