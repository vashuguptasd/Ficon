package com.example.ficon.asking_coarse_fragments.copyrightFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ficon.R
import com.example.ficon.databinding.FragmentCopyRightBinding

class CopyRightFragment : Fragment() {
    private lateinit var binding : FragmentCopyRightBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCopyRightBinding.inflate(layoutInflater)

        binding.button.setOnClickListener {
            sendEmailIntent()
        }

        return binding.root

    }
    // send email intent
    private fun sendEmailIntent() {
        val emailIntent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "ficonsupport@gmail.com", null
            )
        )
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "add my course ")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "add my coarse")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

}