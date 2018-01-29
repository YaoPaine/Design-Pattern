package com.yaopaine.designpattern

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val url: String = "https://www.google.co.jp/imgres?imgurl=https://cdn.worldvectorlogo.com/logos/react.svg&imgrefurl=https://worldvectorlogo.com/zh/logo/react&h=2246&w=2500&tbnid=Og0SjWKQ-7SwpM:&tbnh=144&tbnw=160&usg=__F7AXWKhEIq0bAhSGFjUlujvJSAQ%3D&vet=10ahUKEwiImp7IqffYAhWBOZQKHTgBDLYQ_B0IkwEwCg..i&docid=2XBuf649NiQuFM&itg=1&client=ubuntu&sa=X&ved=0ahUKEwiImp7IqffYAhWBOZQKHTgBDLYQ_B0IkwEwCg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()

        BasicApp.getImageLoader().displayImage(url, findViewById(R.id.sample_iv))

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
