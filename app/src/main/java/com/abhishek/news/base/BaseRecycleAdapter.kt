package com.abhishek.news.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Abhishek Garg on 17/05/20 - https://www.linkedin.com/in/abhishekgarg727/
 */
/**
 * @param <T> data binding object of the item layout
</T> */
abstract class BaseRecyclerAdapter<T : ViewDataBinding> :
    RecyclerView.Adapter<BaseRecyclerAdapter<T>.BaseViewHolder>() {

    private lateinit var mViewDataBinding: T

    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * @return Binding Variable
     */
    abstract val bindingVariable: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        mViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
        return BaseViewHolder(mViewDataBinding)
    }

    /**
     * Base ViewHolder Class for all Adapters
     */

    inner class BaseViewHolder(var mViewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(mViewDataBinding.root) {

        /**
         * method binds the holder view to data object
         * @param mObject this is the single instance
         * of the data set
         */
        fun bind(mObject: Any?) {
            mViewDataBinding.setVariable(bindingVariable, mObject)
            mViewDataBinding.executePendingBindings()
        }

    }

    /**
     * this method returns the viewBinding object
     * to access the UI elements
     */
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}