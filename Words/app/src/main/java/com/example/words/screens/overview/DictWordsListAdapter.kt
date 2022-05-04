package com.example.words.screens.overview

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.words.database.WordDao
import com.example.words.databinding.DictWordItemBinding
import com.example.words.entity.Word
import kotlinx.coroutines.runBlocking

class DictWordsListAdapter(var dao: WordDao) : ListAdapter<Word, DictWordsListAdapter.WordViewHolder>(DiffCallback) {
//    private var list = emptyList<Word>()
    class WordViewHolder(
        private var binding: DictWordItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) {
            Log.e(ContentValues.TAG, "******************* bind  *************: ")
            binding.word = word
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        Log.e(ContentValues.TAG, "******************* onCreateViewHolder  *************: ")
        return WordViewHolder(DictWordItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        Log.e(ContentValues.TAG, "******************* onBindViewHolder  *************: ")
        val word = getItem(position)
//        checkWord(word)

        holder.bind(word)
    }


    fun switchToActive(layoutPosition: Int) = runBlocking {
        Log.e(ContentValues.TAG, "******************* Changed to Active before *************: ${getItem(layoutPosition).id}")
        Log.e(ContentValues.TAG, "******************* Changed to Active before *************: ${getItem(layoutPosition).active}")
        getItem(layoutPosition).active = true
        dao.updateWord(getItem(layoutPosition))
        Log.e(ContentValues.TAG, "******************* Changed to Active after  *************: ${getItem(layoutPosition).id}")
        Log.e(ContentValues.TAG, "******************* Changed to Active after *************: ${getItem(layoutPosition).active}")
    }

    fun switchToInActive(layoutPosition: Int)  = runBlocking {
        Log.e(ContentValues.TAG, "******************* Changed to IN Active before *************: ${getItem(layoutPosition).id}")
        Log.e(ContentValues.TAG, "******************* Changed to IN Active before *************: ${getItem(layoutPosition).active}")
        getItem(layoutPosition).active = false
        dao.updateWord(getItem(layoutPosition))
        Log.e(ContentValues.TAG, "******************* Changed to IN Active after *************: ${getItem(layoutPosition).id}")
        Log.e(ContentValues.TAG, "******************* Changed to IN Active after *************: ${getItem(layoutPosition).active}")
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            Log.e(ContentValues.TAG, "******************* areItemsTheSame  *************: ")
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            Log.e(ContentValues.TAG, "******************* areContentsTheSame  *************: ")
            return oldItem.id == newItem.id
        }
    }
}