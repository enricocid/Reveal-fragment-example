package com.iven.awesometest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.iven.awesometest.fragments.DetailsFragment
import com.iven.awesometest.fragments.ItemsFragment
import com.iven.awesometest.fragments.SettingsFragment
import com.iven.awesometest.ui.ThemeHelper
import com.iven.awesometest.ui.UIControlInterface
import kotlinx.android.synthetic.main.main_activity.*

const val FRAGMENTS_NUMBER = 2

class MainActivity : AppCompatActivity(), UIControlInterface {

    //fragments
    private lateinit var mItemsFragment: ItemsFragment
    private lateinit var mSettingsFragment: SettingsFragment
    private lateinit var mDetailsFragment: DetailsFragment

    //views
    private lateinit var mViewPager: ViewPager
    private lateinit var mTabsLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        //init views
        getViews()

        initializeViewPager()
    }

    private fun getViews() {

        mViewPager = pager
        mTabsLayout = tab_layout
    }

    private fun initializeViewPager() {

        mItemsFragment = ItemsFragment.newInstance()
        mSettingsFragment = SettingsFragment.newInstance()


        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mViewPager.offscreenPageLimit = 1
        mViewPager.adapter = pagerAdapter

        mTabsLayout.apply {
            setupWithViewPager(mViewPager)
            getTabAt(0)?.setIcon(ThemeHelper.getTabIcon(0))
            getTabAt(1)?.setIcon(ThemeHelper.getTabIcon(1))
        }

    }

    private fun handleOnNavigationItemSelected(itemId: Int): Fragment {
        return when (itemId) {
            0 -> getFragmentForIndex(0)
            else -> getFragmentForIndex(1)
        }
    }

    private fun getFragmentForIndex(index: Int): Fragment {
        return when (index) {
            0 -> mItemsFragment
            else -> mSettingsFragment
        }
    }

    private fun openDetailsFragment(
        selectedItem: String
    ) {

        mDetailsFragment =
            DetailsFragment.newInstance(
                selectedItem
            )
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(
                R.id.container,
                mDetailsFragment, DetailsFragment.TAG_SELECTED_ITEM
            )
            .commit()
    }

    override fun onItemSelected(selectedItem: String) {
        openDetailsFragment(
            selectedItem
        )
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = FRAGMENTS_NUMBER

        override fun getItem(position: Int): Fragment {
            return handleOnNavigationItemSelected(position)
        }
    }
}
