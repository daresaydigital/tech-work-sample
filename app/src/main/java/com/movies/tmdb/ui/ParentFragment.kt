package com.movies.tmdb.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.movies.tmdb.R


abstract class ParentFragment(val layout: Int) : Fragment(layout) {


    lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    abstract fun startViewActions()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        setAppBaeTittle()
        startViewActions()

    }


    private fun setAppBaeTittle() {
        when (layout) {
            R.layout.fragment_movies_listing ->  setViewTittle(getString(R.string.most_popular))
            R.layout.fragment_movie_details -> setViewTittle(getString(R.string.movie_details))
            R.layout.fragment_reviews_listing -> setViewTittle(getString(R.string.reviews))
            R.layout.fragment_review_details -> setViewTittle(getString(R.string.review_details))
            R.layout.fragment_offline_movies -> setViewTittle(getString(R.string.likes))
        }
    }

       fun setViewTittle(title:String)
     {
         (activity as AppCompatActivity?)!!.supportActionBar!!.title =title
     }

    override fun onPrepareOptionsMenu(menu: Menu) {
        when (layout) {
            R.layout.fragment_movies_listing -> hideShowBarIcons(true, menu)
            else -> {
                hideShowBarIcons(false, menu)
            }
        }

        super.onPrepareOptionsMenu(menu)
    }


    /**
     * @param hideIcons variable to show or hide the icons , false will hide icons else will show icons.
     */

    private fun hideShowBarIcons(hideIcons: Boolean, menu: Menu) {
        menu.findItem(R.id.fav).isVisible = hideIcons
        menu.findItem(R.id.filter).isVisible = hideIcons
    }


}




















