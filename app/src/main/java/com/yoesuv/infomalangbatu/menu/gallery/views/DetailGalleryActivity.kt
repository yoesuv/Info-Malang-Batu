package com.yoesuv.infomalangbatu.menu.gallery.views

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ActivityDetailGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.CustomDetailGalleryViewModelFactory
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.DetailGalleryViewModel

class DetailGalleryActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_DATA_GALLERY = "extra_data_gallery"
    }

    private lateinit var binding: ActivityDetailGalleryBinding
    private lateinit var viewModel: DetailGalleryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.scale_close)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_gallery)
        val galleryModel: GalleryModel? = intent?.getParcelableExtra(EXTRA_DATA_GALLERY)
        viewModel = ViewModelProviders.of(this, CustomDetailGalleryViewModelFactory(galleryModel, application)).get(DetailGalleryViewModel::class.java)
        binding.gallery = viewModel

        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId==android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.scale_open, R.anim.slide_out_bottom)
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbarDetailGallery?.toolbarInclude)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbarDetailGallery?.textViewToolbarInclude?.text = getString(R.string.detail_gallery)
    }

}