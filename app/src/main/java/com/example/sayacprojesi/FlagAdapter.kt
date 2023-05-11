package com.example.sayacprojesi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler_view_flag.view.*

class FlagAdapter(val flagList : ArrayList<FlagModel>) : RecyclerView.Adapter<FlagAdapter.FlagViewHolder> (){
    class FlagViewHolder(view : View) : RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view_flag,parent,false)
        return FlagViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlagViewHolder, position: Int) {
        holder.itemView.txtId.text = flagList[position].id.toString()
        holder.itemView.txtText.text = flagList[position].text
        holder.itemView.txtTime.text = flagList[position].time
    }

    override fun getItemCount(): Int {
        return flagList.size
    }
}