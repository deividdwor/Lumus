package com.lumossmart.lumossmarthome.ui.adapter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.Momento
import kotlinx.android.synthetic.main.item_momento.view.*

class ListaMomentoAdapter(private val momentos: List<Momento>,
                          private val contexto: Context?) : RecyclerView.Adapter<ListaMomentoAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(contexto).inflate(R.layout.item_momento, parent, false)

        return MyViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val momento = momentos[position]

        holder.setData(momento, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun setData(momento: Momento, position: Int) {
            if(momento.nome == ""){
                itemView.momentoCard.visibility = View.INVISIBLE
            }else{
                itemView.momentoNome.text = momento.nome
                itemView.momentoIcone.setImageDrawable(contexto!!.getDrawableByName(momento.icone))
                itemView.momentoCor.setImageDrawable(contexto!!.getDrawableByName(momento.cor))
                itemView.momentoFundoIcone.setImageDrawable(contexto!!.getDrawableByName(momento.cor))

                itemView.momentoCardIcone.setOnClickListener{ _ ->
                    val mDatabaseDevices = FirebaseDatabase.getInstance().getReference("casa/dispositivos")
                    for (m in momento.dispositivos){
                        mDatabaseDevices.child(m.id).child("ligado").setValue(m.acao)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return momentos.size
    }
}