package com.developer.julio.dangeki_plus.adaptadores

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.developer.julio.dangeki_plus.R
import com.squareup.picasso.Picasso.*

import com.developer.julio.dangeki_plus.DataClases.animesInicio
import com.developer.julio.dangeki_plus.singleton.VolleyS
import com.squareup.picasso.Picasso

/**
 * Created by julio on 07/03/2018.
 */
class InicioRecyclerAdapter : RecyclerView.Adapter<InicioRecyclerAdapter.ViewHolder>() {
    //variables
    var animeList : MutableList<animesInicio> = ArrayList()
   lateinit var contex : Context
    //constructor
    fun InicioRecyclerAdapter(animesList2: MutableList<animesInicio>,context2: Context){
        this.animeList = animesList2
        this.contex = context2

    }
    override fun getItemCount() :Int {
        return animeList.size
    }

    override fun onBindViewHolder(holder:ViewHolder?, position: Int) {
        var item = animeList.get(position)
        holder?.bind(item,contex)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
        var layoutInflater = LayoutInflater.from(parent?.context)
        return ViewHolder(layoutInflater.inflate(R.layout.incio_row,parent,false))
    }

    class ViewHolder(view : View):RecyclerView.ViewHolder(view) {
        var txtTitulo = view.findViewById<TextView>(R.id.txtTituloPubIncio)
        var txtDescripcion = view.findViewById<TextView>(R.id.txtDescripcionPubInicio)
        var imagenPub = view.findViewById<ImageView>(R.id.imagePublicationInicio)


        fun bind(anime: animesInicio,context: Context){
            txtTitulo.text=anime.titulo
            //esto sirve para lograr visualizar el texto de la descripcion sin las partes de html
            //primero evalua la version del sistema android y utiliza una version antigua o nueva
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                txtDescripcion.text = Html.fromHtml(anime.descripcion,Html.FROM_HTML_MODE_LEGACY)
            }else{
                txtDescripcion.text = Html.fromHtml(anime.descripcion)
            }
            var url = anime.imagen
            Log.i("imagen ",url)
            getImage(url,context)
           //Picasso.with(context).load(getImage(url,context.applicationContext)).into(imagenPub)
        }
        fun getImage(url:String,context2: Context){
            var imagen :String
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,
                    Response.Listener { response ->
                      imagen = response.getJSONObject("media_details").getJSONObject("sizes").getJSONObject("full").getString("source_url")
                        Log.i("imagen",imagen)
                       setImage(imagen,context2)
                    },
                    Response.ErrorListener { error -> error.printStackTrace()  })
            VolleyS.getInstance(context2.applicationContext).addToRequestQueue(jsonObjectRequest)

        }
        fun setImage(url:String,context: Context){

            Picasso.with(context).load(url).into(imagenPub)
        }
    }

}