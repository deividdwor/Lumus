package com.lumossmart.lumossmarthome.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.Ambiente
import com.lumossmart.lumossmarthome.model.Dispositivo
import kotlinx.android.synthetic.main.list_group.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.collections.HashMap

class ExpandableAdapter(context: Context?, expandableListTitle: List<Ambiente>, expandableListDetail: HashMap<Ambiente, List<Dispositivo>>) : BaseExpandableListAdapter(){

    private var context: Context? = context
    private var expandableListTitle: List<Ambiente>? = expandableListTitle
    private var expandableListDetail: HashMap<Ambiente, List<Dispositivo>>? = expandableListDetail

    override fun getChild(listPosition: Int, expandedListPosition: Int): Dispositivo? {
        var get: Ambiente = this.expandableListTitle!![listPosition]
        var list: List<Dispositivo>? = expandableListDetail!![get]
        return list?.get(expandedListPosition)
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val dispositivo = getChild(listPosition, expandedListPosition)

        val viewCriada = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        viewCriada!!.expandedListItem.text = dispositivo!!.nome

        return viewCriada
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.expandableListDetail?.get(this.expandableListTitle!![listPosition])!!.size
    }

    override fun getGroup(listPosition: Int): Ambiente? {
        return this.expandableListTitle?.get(listPosition)
    }

    override fun getGroupCount(): Int {
        return this.expandableListTitle?.size!!
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup): View {
        val Ambiente = getGroup(listPosition)

        val viewCriada = LayoutInflater.from(context).inflate(R.layout.list_group, parent, false)

        viewCriada!!.listTitle.text = Ambiente!!.nome

        return viewCriada
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }

}