package com.mytech.basiclayoutmanagerexample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mytech.basiclayoutmanagerexample.R
import com.mytech.basiclayoutmanagerexample.adapter.MoviesAdapter
import com.mytech.basiclayoutmanagerexample.custom.CustomLinearSnapLayoutManager
import com.mytech.basiclayoutmanagerexample.custom.CustomVerticalLayoutManager
import com.mytech.basiclayoutmanagerexample.model.MovieModel


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val movieList = ArrayList<MovieModel>()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        moviesAdapter = MoviesAdapter(movieList)

        val layoutManager = CustomVerticalLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

//        val layoutManager = CustomLinearSnapLayoutManager(requireContext())
//        recyclerView.layoutManager = layoutManager

//        val layoutManager = LinearLayoutManager(requireContext())
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        recyclerView.layoutManager = layoutManager

//        val layoutManager = GridLayoutManager(requireContext(),2)
//        recyclerView.layoutManager = layoutManager

//        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)
//        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = moviesAdapter
        prepareMovieData()
        return view
    }

    private fun prepareMovieData() {
        var movie =
            MovieModel("Mad Max: Fury Road", "Action & Adventure", "2015", R.drawable.mad_max)
        movieList.add(movie)
        movie = MovieModel("Inside Out", "Animation, Kids & Family", "2015", R.drawable.inside)
        movieList.add(movie)
        movie = MovieModel(
            "Star Wars: Episode VII - The Force Awakens",
            "Action",
            "2015",
            R.drawable.start_wars
        )
        movieList.add(movie)
        movie = MovieModel("Shaun the Sheep", "Animation", "2015", R.drawable.shaun_the_sheep)
        movieList.add(movie)
        movie = MovieModel("The Martian", "Science Fiction & Fantasy", "2015", R.drawable.martian)
        movieList.add(movie)
        movie = MovieModel(
            "Mission: Impossible Rogue Nation",
            "Action",
            "2015",
            R.drawable.mision_imposible
        )
        movieList.add(movie)
        movie = MovieModel("Up", "Animation", "2009", R.drawable.up)
        movieList.add(movie)
        movie = MovieModel("Star Trek", "Science Fiction", "2009", R.drawable.start_trek)
        movieList.add(movie)
        movie = MovieModel("The LEGO Movie", "Animation", "2014", R.drawable.lego)
        movieList.add(movie)
        movie = MovieModel("Iron Man", "Action & Adventure", "2008", R.drawable.iron_man)
        movieList.add(movie)
        movie = MovieModel("Aliens", "Science Fiction", "1986", R.drawable.aliens)
        movieList.add(movie)
        movie = MovieModel("Chicken Run", "Animation", "2000", R.drawable.chicken)
        movieList.add(movie)
        movie = MovieModel(
            "Back to the Future",
            "Science Fiction",
            "1985",
            R.drawable.back_to_the_future
        )
        movieList.add(movie)
        movie =
            MovieModel("Raiders of the Lost Ark", "Action & Adventure", "1981", R.drawable.indiana)
        movieList.add(movie)
        movie = MovieModel("Goldfinger", "Action & Adventure", "1965", R.drawable.goldfinger)
        movieList.add(movie)
        movie = MovieModel(
            "Guardians of the Galaxy",
            "Science Fiction & Fantasy",
            "2014",
            R.drawable.guardians
        )
        movieList.add(movie)
        moviesAdapter.notifyDataSetChanged()
    }
}