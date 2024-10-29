package com.eliezer.marvel_search_api.domain.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MyOnScrolledListener(private val invoke : ()->Unit) : RecyclerView.OnScrollListener() {
    var position= 0
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisible = layoutManager.findLastVisibleItemPosition() + 1
        val endHasBeenReached = lastVisible >= totalItemCount
        this.position = dy -1
        if (totalItemCount > 0 && endHasBeenReached) {
            invoke.invoke()
        }
    }
}