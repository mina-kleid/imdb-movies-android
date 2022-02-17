package com.mina.movie.ui.main.tabs

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mina.movie.search.MovieListFragment


internal class TabCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return TAB_COUNT
    }


    override fun createFragment(position: Int): Fragment {

        return TABS[position].fragment
    }

    companion object {
        const val TAB_COUNT: Int = 2
        val TABS: List<Tab> = listOf(
            Tab("Search", MovieListFragment()),
            Tab("Favorites", Fragment())
        )
    }

}