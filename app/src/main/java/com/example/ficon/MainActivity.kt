package com.example.ficon

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ficon.asking_coarse_fragments.viewmodel.LOG
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel : SharedViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.sharingAppLink.observe(this){
            when(it){
                "Try Again" -> {
                    Toast.makeText(this,"Sharing Failed",Toast.LENGTH_SHORT).show()
                }
                "Connection Error" -> {
                    Toast.makeText(this,"Connection Error",Toast.LENGTH_SHORT).show()
                }
                else -> shareAppIntent(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v(LOG,"share Button clicked")
        viewModel.getSharingLinkFromRealtimeDataBase(Application())
        return super.onOptionsItemSelected(item)
    }

    private fun shareAppIntent(link: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT,"SGG Books & PYQ Download Kare is Link Se ✌\n$link")
        startActivity(Intent.createChooser(shareIntent,"Share App"))
    }

}