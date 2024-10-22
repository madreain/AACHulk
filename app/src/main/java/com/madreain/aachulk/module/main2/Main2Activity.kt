package com.madreain.aachulk.module.main2

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.madreain.aachulk.R
import com.madreain.aachulk.consts.RouteUrls
import com.madreain.aachulk.module.dashboard.DashboardFragment
import com.madreain.aachulk.module.home.HomeFragment
import com.madreain.aachulk.module.notifications.NotificationsFragment
import com.madreain.libhulk.components.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main2.*

@Route(path = RouteUrls.Main2)
class Main2Activity : BaseActivity(R.layout.activity_main2),
    BottomNavigationView.OnNavigationItemSelectedListener {


    override fun init(savedInstanceState: Bundle?) {
        navigation.setOnNavigationItemSelectedListener(this)
        viewpager.setOffscreenPageLimit(3)
        viewpager.setAdapter(object :
            FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return TabFragment.values().size
            }

            override fun getItem(position: Int): Fragment {
                return TabFragment.values()[position].fragment()
            }
        })
        /**
         * viewpager左右滑的切换
         */
        viewpager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                navigation.menu.getItem(position).isChecked = true
            }
        })

    }


    /**
     * BottomNavigationView的选中
     */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        (findViewById(R.id.viewpager) as ViewPager).currentItem = TabFragment.from(
            menuItem.getItemId()
        ).ordinal
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        TabFragment.onDestroy()
    }

    /**
     * TabFragment
     *
     */

    private enum class TabFragment(@IdRes menuId: Int, clazz: Class<out Fragment>) {
        home(R.id.navigation_home, HomeFragment::class.java),
        dashboard(R.id.navigation_dashboard, DashboardFragment::class.java),
        notifications(R.id.navigation_notifications, NotificationsFragment::class.java);

        private var fragment: Fragment? = null
        private val menuId: Int
        private val clazz: Class<out Fragment>
        fun fragment(): Fragment {
            if (fragment == null) {
                fragment = try {
                    clazz.newInstance()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Fragment()
                }
            }
            return fragment!!
        }

        companion object {
            fun from(itemId: Int): TabFragment {
                for (fragment in TabFragment.values()) {
                    if (fragment.menuId == itemId) {
                        return fragment
                    }
                }
                return home
            }

            fun onDestroy() {
                for (fragment in TabFragment.values()) {
                    fragment.fragment = null
                }
            }
        }

        init {
            this.menuId = menuId
            this.clazz = clazz
        }
    }

}