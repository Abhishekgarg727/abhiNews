package com.abhishek.news.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.abhishek.news.BR
import com.abhishek.news.R
import com.abhishek.news.base.BaseActivity
import com.abhishek.news.base.BaseBackPressSensitveFragment
import com.abhishek.news.databinding.DashboardActivityBinding
import com.abhishek.news.utils.ClassUtility

class DashboardActivity :
    BaseActivity<DashboardActivityBinding, DashboardViewModel, DashboardNavigator>() {

    private var fragmentList: List<Fragment>? = null
    private var fragmentManager: FragmentManager? = null
    private var activeFragment: Fragment? = null
    private var backExitAllowed = false
    private var isDoubleBackClick = false
    private val DOUBLE_CLICK_TIME_OUT = 2 * 1000.toLong()

    override var mViewModel: DashboardViewModel
        get() = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        set(value) {
        }

    override val layoutId: Int
        get() = R.layout.dashboard_activity

    override val bindingVariable: Int
        get() = BR.homeViewModel

    override fun initialSetup() {
        //..
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        if(currentFragment.navController.currentDestination?.label?.equals("HomeFragment") == true){
            if (isDoubleBackClick) {
                finish()
            }
            isDoubleBackClick = true
            ClassUtility.shortToast("Press back once again to exit")
            Handler().postDelayed({ isDoubleBackClick = false }, DOUBLE_CLICK_TIME_OUT)
        }else{
            val fragmentList = supportFragmentManager.fragments
            var backPressHandled : Boolean = false
            for (f in fragmentList) {
                if (f is BaseBackPressSensitveFragment<*,*,*>) {
                    backPressHandled = (f as BaseBackPressSensitveFragment<*,*,*>).onBackPressed()
                    if (backPressHandled) {
                        break
                    }
                }
            }
            if(!backPressHandled){
                super.onBackPressed()
            }
        }




    }

}
