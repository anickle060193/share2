package com.adamnickle.share2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    companion object
    {
        private const val OUTLINE_URL = "https://outline.com/"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent?.getStringExtra(Intent.EXTRA_TEXT)?.let { url ->
            if(URLUtil.isValidUrl(url))
            {
                openLinkInOutline(url)

                finish()
            }
            else
            {
                link.setText(url)
            }
        }

        link.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?)
            {
                updateShareButton()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
            }
        })

        updateShareButton()

        share.setOnClickListener {
            getLinkUrl()?.let { url ->
                openLinkInOutline(url)
            }
        }
    }

    private fun getLinkUrl(): String?
    {
        return link.text?.toString()
            ?.let { url -> URLUtil.guessUrl(url) }
            .let { url ->
                when
                {
                    URLUtil.isValidUrl(url) -> url
                    else -> null
                }
            }
    }

    private fun updateShareButton()
    {
        share.isEnabled = (getLinkUrl() != null)
    }

    private fun openLinkInOutline(url: String)
    {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(OUTLINE_URL + url)))
    }
}
