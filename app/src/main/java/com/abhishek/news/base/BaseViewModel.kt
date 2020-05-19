package com.abhishek.news.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

/**
 * Created by Abhishek Garg on 17/05/20 - https://www.linkedin.com/in/abhishekgarg727/
 */

/**
 * This class is base calls for all viewModels
 * this provides callback between viewModel
 * and activity/fragment
 *
 * @param <N> is a callback interface provides
 */
open class BaseViewModel<N> : ViewModel() {

    private var navigator: WeakReference<N>? = null

    fun getNavigator(): N? {
        return navigator?.get()
    }

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }
}
