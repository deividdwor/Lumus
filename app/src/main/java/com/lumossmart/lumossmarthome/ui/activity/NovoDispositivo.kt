package com.lumossmart.lumossmarthome.ui.activity


import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.*
import com.lumossmart.lumossmarthome.ui.adapter.*
import kotlinx.android.synthetic.main.fragment_novo_dispositivo.view.*
import kotlinx.android.synthetic.main.item_dispositivo.view.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

private lateinit var wifiManager : WifiManager
private lateinit var config: Config

class NovoDispositivo : Fragment() {

    companion object {
        fun newInstance(ambiente: Ambiente):NovoDispositivo {
            val args = Bundle()
            args.putSerializable("ambiente", ambiente)
            var novoDispositivo = NovoDispositivo()
            novoDispositivo.arguments = args
            return novoDispositivo
        }
    }


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val policy : StrictMode.ThreadPolicy  =  StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        val mDatabaseDevices = FirebaseDatabase.getInstance().getReference("casa/config")

        mDatabaseDevices.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {  }

            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    for(a in p0.children){
                        config = a.getValue(Config::class.java)!!
                    }
                }
            }

        })

        val inflate = inflater.inflate(R.layout.fragment_novo_dispositivo, container, false)

        val cores = CorEnum.values()
        val icones = IconeDispositivoEnum.values()
        val nomes = nomeDispositivoEnum.values()

        inflate.corDispositivo.adapter = CorAdapter(cores, context)
        inflate.iconeDispositivo.adapter = IconeDispositivoAdapter(icones, context!!.getDrawableByName("@android:color/holo_green_dark"), context)
        inflate.nomeDispositivoSpinner.adapter = NomeDispositivoAdapter(nomes, context)

        var idCurrentIcone = 0

        inflate.nomeDispositivoSpinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: String = inflate.nomeDispositivoSpinner.selectedItem as String

                inflate.item_dispositivo.item_dispositivo_nome.text = item
            }

        }

        inflate.corDispositivo.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: CorEnum = inflate.corDispositivo.selectedItem as CorEnum

                inflate.item_dispositivo.item_dispositivo_cor.setImageDrawable(context!!.getDrawableByName(item.cor))
                inflate.iconeDispositivo.adapter = IconeDispositivoAdapter(icones, context!!.getDrawableByName(item.cor), context)
                inflate.iconeDispositivo.setSelection(idCurrentIcone)
            }

        }

        inflate.iconeDispositivo.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = inflate.iconeDispositivo.selectedItem as IconeDispositivoEnum

                inflate.item_dispositivo.item_dispositivo_icone.setImageDrawable(context!!.getDrawableByName(item.icone))
                idCurrentIcone = position
            }

        }

            inflate.btnSalvaDispositivo.setOnClickListener { view ->
                val mDatabase = FirebaseDatabase.getInstance().getReference("casa/dispositivos")
                // Creating new user node, which returns the unique key value
                // new user node would be /users/$userid/
                val dispositivoId = mDatabase.push().key

                val cor = inflate.corDispositivo.selectedItem as CorEnum
                val nome = inflate.nomeDispositivoSpinner.selectedItem as String
                val icone = inflate.iconeDispositivo.selectedItem as IconeDispositivoEnum
                val ligado = inflate.onOff.isChecked
                val ambiente = arguments!!.get("ambiente") as Ambiente
                val dispositivo = Dispositivo(cor.cor, nome, icone.icone, ambiente.id, ligado)

                // pushing user to 'users' node using the userId
                if(configuraDispositivo(dispositivoId) == 200){
                    mDatabase.child(dispositivoId).setValue(dispositivo)
                }else{

                }

                activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_content, DetalhesAmbiente.newInstance(ambiente), "DetalhesAmbiente")
                        .commit()
            }

        return inflate
    }


    private fun checkPermission(permission: String): Boolean {
        val checkPermission = ContextCompat.checkSelfPermission(context!!, permission)
        return checkPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun sendPostRequest(config: Config?, id:String): Int {

        var reqParam = URLEncoder.encode("rede", "UTF-8") + "=" + URLEncoder.encode(config!!.rede, "UTF-8")
        reqParam += "&" + URLEncoder.encode("senha", "UTF-8") + "=" + URLEncoder.encode(config!!.senha , "UTF-8")
        reqParam += "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode("/$id", "UTF-8")
        val mURL = URL("http://192.168.4.1")

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "POST"

            val wr = OutputStreamWriter(outputStream)
            wr.write(reqParam)
            wr.flush()

            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                println("Response : $response")
            }
            return responseCode
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun configuraDispositivo(dispositivoId: String): Int {
        if (checkPermission(Manifest.permission.ACCESS_WIFI_STATE)) {
            wifiManager = context!!.getSystemService(Context.WIFI_SERVICE) as WifiManager

            var l  = WifiConfiguration()
            l.SSID = "\"Lumus\"";
            l.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiManager.addNetwork(l)

            val configuredNetworks = wifiManager.configuredNetworks
            for(c in configuredNetworks) {
                if(c.SSID == l.SSID) {
                    wifiManager.disconnect()
                    wifiManager.enableNetwork(c.networkId, true);
                    wifiManager.reconnect()
                    break
                }
            }
            while (wifiManager.connectionInfo.ssid != "Lumus"){

            }
            return sendPostRequest(config, dispositivoId)

        }
        return 404
    }
}
