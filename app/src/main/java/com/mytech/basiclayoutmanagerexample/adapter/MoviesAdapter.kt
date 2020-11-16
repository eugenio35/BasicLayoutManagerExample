package com.mytech.basiclayoutmanagerexample.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.mytech.basiclayoutmanagerexample.R
import com.mytech.basiclayoutmanagerexample.custom.CustomVerticalLayoutManager
import com.mytech.basiclayoutmanagerexample.model.MovieModel

internal class MoviesAdapter(private var moviesList: List<MovieModel>) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var year: TextView = view.findViewById(R.id.year)
        var genre: TextView = view.findViewById(R.id.genre)
        var poster: ImageView = view.findViewById(R.id.poster)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(CustomVerticalLayoutManager.TAG, "onBindViewHolder")
        val movie = moviesList[position]
        holder.poster.setImageResource(movie.poster)
        holder.title.text = movie.title
        holder.genre.text = movie.genre
        holder.year.text = movie.year
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}