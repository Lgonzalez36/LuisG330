package com.example.words.screens.search

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.words.R


class SuggestListAdapter(
    var context: SearchWordFragment,
    var suggestedWords: List<String>,
    var viewModel: SearchWordViewModel
) : RecyclerView.Adapter<SuggestListAdapter.SuggestListViewHolder>()
 {

    class SuggestListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.word_suggested)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestListViewHolder {
        Log.e(ContentValues.TAG, "*******************SuggestListAdapter onCreateViewHolder  *************: ")
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.suggested_word_item, parent, false)
        return SuggestListViewHolder(adapterLayout)
    }


    override fun onBindViewHolder(holder: SuggestListViewHolder, position: Int) {
        Log.e(ContentValues.TAG, "*******************SuggestListAdapter onBindViewHolder  *************: ${ suggestedWords[position]}")
        holder.textView.text = suggestedWords[position]
    }


     override fun getItemCount(): Int {
        return suggestedWords.size
     }

 }