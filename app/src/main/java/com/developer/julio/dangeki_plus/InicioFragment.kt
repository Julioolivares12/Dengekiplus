package com.developer.julio.dangeki_plus

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.util.Log
// import de librerias de terceros
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.developer.julio.dangeki_plus.DataClases.animesInicio
import com.developer.julio.dangeki_plus.adaptadores.InicioRecyclerAdapter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject


import kotlinx.android.synthetic.main.fragment_inicio.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
//import de mis clases
import com.developer.julio.dangeki_plus.Peticiones
import com.developer.julio.dangeki_plus.singleton.VolleyS
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import java.io.IOException
import kotlin.math.log

class InicioFragment : Fragment() {


    private var mParam1: String? = null
    private var mParam2: String? = null
    
    private lateinit var progressBar : ProgressBar
    private var mListener: OnFragmentInteractionListener? = null
    lateinit var anim : ObjectAnimator
    var adapter : InicioRecyclerAdapter = InicioRecyclerAdapter()
   var animeList : MutableList<animesInicio> = ArrayList()
    lateinit var animesRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view : View = inflater!!.inflate(R.layout.fragment_inicio,container,false)
      /*
      *   progressBar = view.findViewById(R.id.barCarga)
        anim = ObjectAnimator.ofInt(progressBar,"progress",0,100)*/
        val url="http://dengeki-plus.me/wp-json/wp/v2/posts/"
        getData(view,url,context)

        return view

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

    }
    override fun onResume() {
        super.onResume()
    }

    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface OnFragmentInteractionListener {

        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"


        fun newInstance(param1: String, param2: String): InicioFragment {
            val fragment = InicioFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
    //mi metodos
    fun getData(view: View,url:String,context: Context?){
        var jsonObject: JSONObject
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,url,null,Response.Listener {
            response ->
            for (i in 0 until response.length()){
                jsonObject = response.getJSONObject(i)
                var id = jsonObject.getInt("id")
                var title = jsonObject.getJSONObject("title").getString("rendered")
                var descripcion = jsonObject.getJSONObject("excerpt").getString("rendered")
                var imagen = jsonObject.getString("featured_media")
                var imagenUrl = "http://dengeki-plus.me/wp-json/wp/v2/media/"+imagen

               var  animes = animesInicio(id,title,descripcion,imagenUrl)

                animeList.add(animes)
                adapter.InicioRecyclerAdapter(animeList,context!!.applicationContext)
                animesRecyclerView = view.findViewById(R.id.animesInicio)
                animesRecyclerView.setHasFixedSize(true)
                animesRecyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
                animesRecyclerView.layoutManager = LinearLayoutManager(context!!.applicationContext)
                animesRecyclerView.adapter=adapter
            }

            Log.i("animes lista",animeList.toString())
        },Response.ErrorListener { error -> error.printStackTrace()  })
        VolleyS.getInstance(context!!.applicationContext).addToRequestQueue(jsonArrayRequest)
    }

  }
// Required empty public constructor


