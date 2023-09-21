package com.armaganaysu.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.armaganaysu.kotlincountries.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

//Extension

/*fun String.myExtension(myParameter : String){
    println(myParameter)
}*/

fun ImageView.downloadFromMyUrl(url: String?, progressDrawable: CircularProgressDrawable){

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}

fun placeholderProgressBar(myContext : Context) : CircularProgressDrawable{
    return CircularProgressDrawable(myContext).apply {
        strokeWidth = 8f //float olarak istiyor
        centerRadius = 40f
        start()
    }
}