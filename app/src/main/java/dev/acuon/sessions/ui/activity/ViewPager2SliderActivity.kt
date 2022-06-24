package dev.acuon.sessions.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityViewPager2SliderBinding
import dev.acuon.sessions.model.Image
import dev.acuon.sessions.ui.adapter.ImageAdapter
import dev.acuon.sessions.utils.ActivityUtils
import kotlin.math.abs

class ViewPager2SliderActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewPager2SliderBinding
    private lateinit var imageList: ArrayList<Image>
    private lateinit var imageAdapter: ImageAdapter
    private val sliderHandler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPager2SliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageList = ArrayList()
        imageList.add(Image(R.drawable.image1))
        imageList.add(Image(R.drawable.image2))
        imageList.add(Image(R.drawable.image3))
        imageList.add(Image(R.drawable.image4))

        val transformer = CompositePageTransformer()
        transformer.apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }
        }

        binding.viewPagerImageSlider.apply {
            imageAdapter = ImageAdapter(imageList, this)
            adapter = imageAdapter
            offscreenPageLimit = 3
            clipChildren = false
            clipToPadding = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(transformer)
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable, 2000)
                }
            })
        }
    }

    private val sliderRunnable = Runnable {
        binding.viewPagerImageSlider.apply {
            currentItem += 1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        ActivityUtils.intent(item.itemId, this)
        return true
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.removeCallbacks(sliderRunnable, 2000)
    }
}