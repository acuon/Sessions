package dev.acuon.sessions.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dev.acuon.sessions.ApplicationClass
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityImageBinding
import dev.acuon.sessions.ui.post.viewmodel.PostViewModel
import dev.acuon.sessions.ui.post.viewmodel.PostViewModelFactory
import dev.acuon.sessions.ui.postImage.ImageAdapter
import dev.acuon.sessions.ui.postImage.model.PostImageItem
import dev.acuon.sessions.ui.postImage.viewmodel.ImageViewModel
import dev.acuon.sessions.ui.postImage.viewmodel.ImageViewModelFactory
import dev.acuon.sessions.utils.ActivityUtils
import dev.acuon.sessions.utils.Extensions.showToast
import dev.acuon.sessions.utils.layoutmanager.SpanSize
import dev.acuon.sessions.utils.layoutmanager.SpannedGridLayoutManager

class ImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageBinding
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var list: ArrayList<PostImageItem>
    private lateinit var viewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        setRecyclerView()
        getData()
    }
    private fun setRecyclerView() {
        list = ArrayList()
        imageAdapter = ImageAdapter()
        val spannedGridLayoutManager = SpannedGridLayoutManager(orientation = SpannedGridLayoutManager.Orientation.VERTICAL, spans = 3)
        spannedGridLayoutManager.itemOrderIsStable = true
        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position ->
            when {
                position == 0 -> {
                    /**
                     * 150f is now static
                     * should calculate programmatically in runtime
                     * for to manage header hight for different resolution devices
                     */
                    SpanSize(3, 1/*, 150f*/)
                }
                position % 7 == 1 ->
                    SpanSize(2, 2)
                else ->
                    SpanSize(1, 1)
            }
        }
        binding.rcvPostImages.adapter = imageAdapter
//        binding.rcvPostImages.layoutManager = GridLayoutManager(this, 4)
        binding.rcvPostImages.layoutManager = spannedGridLayoutManager
        imageAdapter.differ.submitList(list)
    }

    private fun initViewModel() {
        val repository = (application as ApplicationClass).repository
        viewModel =
            ViewModelProvider(this, ImageViewModelFactory(repository))[ImageViewModel::class.java]
    }

    private fun getData() {
        list.clear()
        viewModel.images.observe(this, Observer {
            list.addAll(it)
            this.showToast(list.size.toString())
            imageAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        ActivityUtils.intent(item.itemId, this)
        return true
    }
}