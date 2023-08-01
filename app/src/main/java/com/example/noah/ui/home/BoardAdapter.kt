package com.example.noah.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.noah.MainActivity
import com.example.noah.R

class BoardAdapter( private val dataList: List<HomeViewModel>) : RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_title_text)
        val contentsTextView: TextView = itemView.findViewById(R.id.item_contents_text)

    }
    //아이템 클릭했을 때
    interface OnItemClickListner{
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListner: OnItemClickListner){
        this.itemClickListner=onItemClickListner
    }

    private lateinit var itemClickListner:OnItemClickListner


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return BoardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        //holder.bind(dataList[position],context)
        val data = dataList[position]
        holder.titleTextView.text = data.title
        holder.contentsTextView.text = data.contents

        holder.itemView.setOnClickListener{
            itemClickListner.onClick(it,position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}