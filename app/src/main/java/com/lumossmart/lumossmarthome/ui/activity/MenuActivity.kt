package com.lumossmart.lumossmarthome.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.lumossmart.lumossmarthome.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import kotlinx.android.synthetic.main.nav_header_menu.view.*
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_lista_ambientes.*

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var mGoogleApiClient: GoogleApiClient
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val fragment = this.supportFragmentManager.findFragmentByTag("DetalhesAmbiente")
            if(fragment != null){
                val key = fragment.arguments!!.getString("key")
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_content, NovoDispositivo.newInstance(key), "NovoDispositivo")
                        .commit()
            }else{
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_content, NovoAmbiente.newInstance(), "NovoAmbientes")
                        .commit()
            }

        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)


        val fragment = this.supportFragmentManager.findFragmentByTag("NovoDispositivo")
        if(fragment != null){
            val key = fragment.arguments!!.getString("key")
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, NovoDispositivo.newInstance(key), "DetalhesAmbiente")
                    .commit()
        }else{
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, listaAmbientes.newInstance(), "listaAmbientes")
                    .commit()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        mGoogleApiClient.connect()
        super.onStart()

        val extras = intent.extras
        if(extras != null){
            val nome = extras.getString("nome")
            val email = extras.getString("email")
            val foto = extras.getString("foto")

            val nav = this.nav_view
            var headerView = nav.getHeaderView(0)
            headerView.email_user.text = email
            headerView.name.text = nome
            var fotoPerfil = headerView.foto

            Picasso.with(this@MenuActivity)
                    .load(foto)
                    .into(fotoPerfil)
        }
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
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return setings()
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.ambientes -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_content, listaAmbientes.newInstance(), "listaAmbientes")
                        .commit()
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.logout -> {
                val mAuth = FirebaseAuth.getInstance()
                mAuth.signOut()
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun setings():Boolean{
        val mDatabase = FirebaseDatabase.getInstance().getReference("casa/ambiente")
        val fragment = this.supportFragmentManager.findFragmentByTag("DetalhesAmbiente")
        if(fragment != null){
            val ambiente = mDatabase.child(fragment.arguments!!.getString("key"))
            ambiente.removeValue()

            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, listaAmbientes.newInstance(), "listaAmbientes")
                    .commit()
        }

        return true
    }
}
