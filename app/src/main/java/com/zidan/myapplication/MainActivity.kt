package com.zidan.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var listMeme: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var memes: MutableList<Meme> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listMeme = findViewById(R.id.rv_meme)
        progressBar = findViewById(R.id.pg_bar)
        swipeRefreshLayout = findViewById(R.id.sr_layout)

        listMeme.layoutManager = GridLayoutManager(this, 3)
        adapter = MainAdapter(memes){
            startActivity<DetailActivity>("img" to it.img)
        }
        listMeme.adapter = adapter

        presenter = MainPresenter(this)
        presenter.getMemes()

        swipeRefreshLayout.onRefresh {
            presenter.getMemes()
        }
    }

    override fun showMemeList(data: List<Meme>) {
        memes.clear()
        memes.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }
}