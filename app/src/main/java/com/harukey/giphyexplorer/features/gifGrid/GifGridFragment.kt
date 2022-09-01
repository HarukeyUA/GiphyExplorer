package com.harukey.giphyexplorer.features.gifGrid

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.harukey.giphyexplorer.features.gifGrid.list.GifsPagingAdapter
import com.harukey.giphyexplorer.R
import com.harukey.giphyexplorer.databinding.GifGridFragmentBinding
import com.harukey.giphyexplorer.utils.extensions.observe
import com.harukey.giphyexplorer.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifGridFragment : Fragment(R.layout.gif_grid_fragment) {

    private val viewModel: GifGridViewModel by viewModels()

    private val adapter by lazy {
        GifsPagingAdapter()
    }

    private val binding by viewBinding(GifGridFragmentBinding::bind)

    private val errorSnackBar by lazy {
        Snackbar.make(
            requireContext(),
            binding.root,
            getString(R.string.generic_error_message),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(getString(R.string.retry_loading_action)) {
                adapter.refresh()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.photoGrid.adapter = adapter

        observeState()
        setListeners()
    }

    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }

        binding.searchInputEditText.doAfterTextChanged {
            viewModel.performSearch(it.toString())
        }
    }

    private fun observeState() {
        observe(viewModel.gifsFlow) { gifs ->
            adapter.submitData(gifs)
        }

        observe(adapter.loadStateFlow) { loadState ->
            when (loadState.refresh) {
                is LoadState.NotLoading -> {
                    binding.swipeRefresh.isRefreshing = false
                    errorSnackBar.dismiss()
                }
                LoadState.Loading -> {
                    if (!binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = true
                    }
                    errorSnackBar.dismiss()
                }
                is LoadState.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    errorSnackBar.show()
                }
            }
        }
    }
}
