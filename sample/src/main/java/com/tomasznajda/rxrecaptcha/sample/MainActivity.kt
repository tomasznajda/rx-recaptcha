package com.tomasznajda.rxrecaptcha.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.jakewharton.rxbinding2.view.clicks
import com.tomasznajda.rxrecaptcha.ReCaptcha
import kotlinx.android.synthetic.main.activity_main.*

const val YOUR_SITE_KEY = "enter your site key here"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnReCaptcha
            .clicks()
            .flatMapSingle { ReCaptcha(this@MainActivity).verify(YOUR_SITE_KEY) }
            .doOnNext { printToken(token = it) }
            .doOnError { printError(error = it) }
            .retry()
            .subscribe()
    }

    private fun printToken(token: String) =
            with(token) {
                println(token)
                toast("Token: $this")
            }

    private fun printError(error: Throwable) =
            with(error) {
                printStackTrace()
                message?.let { toast(it) } ?: toast(toString())
            }

    private fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}
