package com.developer.julio.dangeki_plus.singleton

import com.android.volley.RequestQueue
import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

/**
 * Created by julio on 27/03/2018.
 */
class VolleyS(context: Context) {
    companion object {
        @Volatile
        private var instace: VolleyS? = null
        fun getInstance(context2: Context) = instace ?: synchronized(this){
            instace ?:VolleyS(context2)
        }
    }
    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,object : ImageLoader.ImageCache{
            private val cache = LruCache<String,Bitmap>(20)
            override fun getBitmap(url: String?): Bitmap {
                return cache.get(url)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                cache.put(url,bitmap)
            }
        })
    }
    val requestQueue : RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T>addToRequestQueue(req:Request<T>){
        requestQueue.add(req)
    }
}