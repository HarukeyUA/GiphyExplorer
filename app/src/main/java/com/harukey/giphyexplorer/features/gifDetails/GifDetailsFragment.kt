package com.harukey.giphyexplorer.features.gifDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.harukey.giphyexplorer.R
import com.harukey.giphyexplorer.databinding.GifDetailsFragmentBinding
import com.harukey.giphyexplorer.features.gifDetails.list.GifsDetailsPagingAdapter
import com.harukey.giphyexplorer.utils.extensions.observe
import com.harukey.giphyexplorer.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifDetailsFragment : Fragment(R.layout.gif_details_fragment) {

    private val binding by viewBinding(GifDetailsFragmentBinding::bind)
    private val viewModel: GifsDetailsViewModel by viewModels()
    private val args: GifDetailsFragmentArgs by navArgs()
    private val adapter by lazy {
        GifsDetailsPagingAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gifPager.adapter = adapter
        observeState()

        if (savedInstanceState == null) {
            restorePagerPosition()
        }
    }

    private fun observeState() {
        observe(viewModel.gifsFlow) {
            adapter.submitData(it)
        }
    }

    private fun restorePagerPosition() {
        binding.gifPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.gifPager.setCurrentItem(args.offset, false)
                binding.gifPager.unregisterOnPageChangeCallback(this)
            }
        })
    }
}
