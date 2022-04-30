package com.example.worddictionaryapp.adapter

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.worddictionaryapp.R
import com.example.worddictionaryapp.database.Dictionary
import com.example.worddictionaryapp.databinding.MainWordListBinding
import com.example.worddictionaryapp.mainscreen.MainFragment

class DictionaryAdapter(
    private var c: MainFragment,


) : RecyclerView.Adapter<DictionaryAdapter.MyViewHolder>(){
    private var wordList = emptyList<Dictionary>()
    inner class MyViewHolder(var v: MainWordListBinding): RecyclerView.ViewHolder(v.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.i("onCreateViewHolder", "Creating RecyclerView")
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<MainWordListBinding>(inflater, R.layout.main_word_list, parent, false)

//        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_word_list, parent,false))
        return  MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i("onBindViewHolder", "ON VIEW BINDING RecyclerView")
        val currentWord = wordList[position]
//        holder.itemView.findViewById<TextView>(R.id.word).text = wordList[position].word
//        holder.itemView.findViewById<TextView>(R.id.short_def1).text = wordList[position].shortDef1
//        holder.itemView.findViewById<TextView>(R.id.short_def2).text = wordList[position].shortDef2
//        holder.itemView.findViewById<TextView>(R.id.short_def3).text = wordList[position].shortDef3
        holder.v.shortDef3.text = currentWord.shortDef3
        holder.v.shortDef2.text = currentWord.shortDef2
        holder.v.shortDef1.text = currentWord.shortDef1
        holder.v.word.text = currentWord.word
        Log.e(ContentValues.TAG, "HAS img2 — ${currentWord.img}")
        Glide.with(c)
            .load(currentWord.img)
            .override(1600, 1600)
            .fitCenter()
            .into(holder.itemView.findViewById(R.id.word_img))
    }


    override fun getItemCount(): Int {
        return wordList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(word: List<Dictionary>?) {
        Log.i("MainViewModel", "setData ${word!!.size}")
        Log.i("MainViewModel", "setData ${word.size}")
        Log.i("MainViewModel", "setData ${word[0].word}")
        Log.i("MainViewModel", "setData) ${word.size}")
        this.wordList = word
        notifyDataSetChanged()
    }
}