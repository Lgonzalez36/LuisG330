package com.example.worddictionaryapp.adapter

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.worddictionaryapp.R
import com.example.worddictionaryapp.database.Dictionary
import com.example.worddictionaryapp.databinding.MainWordListBinding
import com.example.worddictionaryapp.mainscreen.MainFragment
import com.example.worddictionaryapp.mainscreen.MainViewModel

class DictionaryAdapter(
    private var c: MainFragment,
    var mainViewModel: MainViewModel,


    ) : RecyclerView.Adapter<DictionaryAdapter.MyViewHolder>(){
    private var filter = 0
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

        if (currentWord.status){
            holder.v.statusImg.visibility = View.VISIBLE
        }
        else {
            holder.v.statusImg.visibility = View.GONE
        }

        if (filter == 0){
            filterStatus(holder, position)
        }

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

    private fun filterStatus(holder: DictionaryAdapter.MyViewHolder, position: Int) {
        
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

    suspend fun switchToActive(position: Int) {
        wordList[position].status = true
        mainViewModel.updateData(wordList[position])
    }

    suspend fun switchToInActive(position: Int) {
        wordList[position].status = false
        mainViewModel.updateData(wordList[position])
    }

    fun activeFilter() {
        filter = 1
    }

    fun inActiveFilter() {
        filter = 2
    }

    fun showAllFilter() {
        filter = 0
    }
}