package com.harukey.giphyexplorer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.harukey.giphyexplorer.databinding.ActivityMainBinding
import com.harukey.giphyexplorer.utils.NavigationDispatcher
import com.harukey.giphyexplorer.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationDispatcher: NavigationDispatcher

    private val navHost by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
    }

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launchWhenStarted {
            navigationDispatcher.navigationCommandEvents.collectLatest {
                it.invoke(navHost.navController)
            }
        }
    }
}
