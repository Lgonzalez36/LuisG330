package com.example.worddictionaryapp.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.worddictionaryapp.R
import com.example.worddictionaryapp.database.Dictionary
import com.example.worddictionaryapp.database.DictionaryDataBase
import com.example.worddictionaryapp.database.DictionaryDataBaseDao
import com.example.worddictionaryapp.database.Word
import com.example.worddictionaryapp.databinding.FragmentAddWordBinding
import com.example.worddictionaryapp.mainscreen.MainViewModel
import com.example.worddictionaryapp.mainscreen.MainViewModelFactory
import com.example.worddictionaryapp.viewmodel.DictionayViewModel
import kotlinx.coroutines.runBlocking


class AddWordFragment : Fragment() {
    private lateinit var navController: NavController
    private var fragmentAddWordBinding: FragmentAddWordBinding? = null
    private lateinit var words: ArrayList<Word>
    private lateinit var dictionaryDao: DictionaryDataBaseDao
    private lateinit var mainViewModel : MainViewModel
    private val viewModel : DictionayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentAddWordBinding.inflate(inflater, container, false)
        fragmentAddWordBinding = binding

        val application = requireNotNull(this.activity).application
        dictionaryDao = DictionaryDataBase.getInstance(application).dictionaryDataBaseDao()

        val viewModelFactory = MainViewModelFactory(dictionaryDao, application)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        words = ArrayList()
        if (viewModel.currentWordFromQuery!!.img.isEmpty()){
            fragmentAddWordBinding!!.wordImg.setImageResource(R.drawable.ic_no_image_available)
            Log.e(TAG, "img — ${viewModel.currentWordFromQuery!!.img}")
        }else{
            Log.e(TAG, "HAS img2 — ${viewModel.currentWordFromQuery!!.img}")
            Glide.with(this)
                .load(viewModel.currentWordFromQuery!!.img)
                .override(1600, 1600)
                .fitCenter()
                .into(fragmentAddWordBinding!!.wordImg)
        }

        fragmentAddWordBinding!!.word.text = viewModel.currentWordFromQuery!!.word
//        fragmentAddWordBinding!!.word.text = viewModel.currentWordFromQuery!!.img
        fragmentAddWordBinding!!.shortDef1.text = viewModel.currentWordFromQuery!!.shortDef1
        fragmentAddWordBinding!!.shortDef2.text = viewModel.currentWordFromQuery!!.shortDef2
        fragmentAddWordBinding!!.shortDef3.text = viewModel.currentWordFromQuery!!.shortDef3
        fragmentAddWordBinding!!.addWordBtn.setOnClickListener { addWordToDataBase() }
    }

    private fun addWordToDataBase() = runBlocking {
        val newData = Dictionary(
            viewModel.currentWordFromQuery!!.word,
            viewModel.currentWordFromQuery!!.shortDef1,
            viewModel.currentWordFromQuery!!.shortDef2,
            viewModel.currentWordFromQuery!!.shortDef3,
            viewModel.currentWordFromQuery!!.img,
            viewModel.currentWordFromQuery!!.status
        )
        mainViewModel.addWord(newData)
        navController.navigate(R.id.action_addWordFragment_to_mainFragment)
//            dictionaryDao.insert(newData)

        // Then
//        val dictionary = dictionaryDao.get(newData.word)
//        dictionary?.word?.let { assert(it == viewModel.currentWordFromQuery!!.word) }
//        Log.e(TAG, "******************* Word *************: ${dictionary?.word}")
//        Log.e(TAG, "******************* Def1 *************: ${dictionary?.shortDef1}")
//        Log.e(TAG, "******************* Def2 *************: ${dictionary?.shortDef2}")
//        Log.e(TAG, "******************* Def3 *************: ${dictionary?.shortDef3}")
//        Log.e(TAG, "******************* img *************: ${dictionary?.img}")
//        Log.e(TAG, "******************* status *************: ${dictionary?.status}")
//        val list = dictionaryDao.getAllWords()

    }
}