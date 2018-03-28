package com.developer.julio.dangeki_plus
import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

/**
 * Created by julio on 08/03/2018.
 */
class Peticiones {
    fun obtenerImagen(url:String): String{

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        var imagenFull = ""
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                 imagenFull = gson.fromJson(body,Imagen::class.java).source_url
                Log.i("cuerpo de la peticion",body)

                Log.i("dato de imagen",imagenFull)
            }
        })
       return imagenFull
    }
   data class Imagen(var source_url : String)

}