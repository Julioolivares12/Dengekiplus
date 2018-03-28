package com.developer.julio.dangeki_plus

import android.app.Fragment
import android.app.FragmentManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.app_bar_principal.*


class PrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,Anime1080Fragment.OnFragmentInteractionListener , InicioFragment.OnFragmentInteractionListener {
    lateinit var fragment : android.support.v4.app.Fragment
     var t : Boolean = false

    lateinit var linearLayout: LinearLayout
    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        setSupportActionBar(toolbar)
        fragment = InicioFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.incio->{
                fragment = InicioFragment()
                t = true
            }
            R.id.anime1080 ->{
                fragment = Anime1080Fragment()
                t = true
            }
            R.id.anime720->{

            }
        }
        if (t){
            supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
            item.isChecked = true
            supportActionBar!!.title=item.title
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
