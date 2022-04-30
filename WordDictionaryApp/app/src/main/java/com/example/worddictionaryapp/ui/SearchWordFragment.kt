package com.example.worddictionaryapp.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.worddictionaryapp.R
import com.example.worddictionaryapp.database.DictionaryDataBase
import com.example.worddictionaryapp.database.DictionaryDataBaseDao
import com.example.worddictionaryapp.database.Word
import com.example.worddictionaryapp.databinding.FragmentSearchWordBinding
import com.example.worddictionaryapp.mainscreen.MainViewModel
import com.example.worddictionaryapp.network.DictionaryApi
import com.example.worddictionaryapp.viewmodel.DictionayViewModel
import kotlinx.coroutines.runBlocking
import org.json.JSONArray


class SearchWordFragment : Fragment() {
    private lateinit var navController: NavController
    private var fragmentSearchWordBinding: FragmentSearchWordBinding? = null
    private lateinit var dictionaryDao: DictionaryDataBaseDao
    private lateinit var words: ArrayList<Word>
    private val viewModel : DictionayViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchWordBinding.inflate(inflater, container, false)
        fragmentSearchWordBinding = binding
        val application = requireNotNull(this.activity).application
        dictionaryDao = DictionaryDataBase.getInstance(application).dictionaryDataBaseDao()
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        words = ArrayList()
        listForSearch()
    }

    private fun listForSearch() {
        fragmentSearchWordBinding!!.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                fragmentSearchWordBinding!!.searchView.clearFocus()
                // search for word in data base
                    // if in database then notify the user that it is already in the database
                    // return to main fragment
                Log.e(TAG, "******************* IN onQueryTextSubmit*************:")
                // if not
                getWordFromAPI(query)
                    // call api
                    // check results
                    // if okay then we nav to AddWordFragment
                    // if multiple words then wait for user to select the words and get results again
                    // if no words display why
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun getWordFromAPI(query: String?) = runBlocking {
        Log.e(TAG, "******************* IN getWordFromAPI*************:")
        val response = query?.let { DictionaryApi.retrofitService.getWord(it) }
        val jsonString = response?.body()
        if (jsonString?.startsWith("[{\"meta\":") == true){
            val wordDef = parseJsonToWord(query, jsonString)
//            dictionaryDao.insert(wordDef as Dictionary)
//
//            // Then
//            val dictionary = dictionaryDao.get(query)
//            assert(dictionary?.word.equals(query))
//
//            Log.e(TAG, "******************* Word *************: ${dictionary?.word}")
//            Log.e(TAG, "******************* Def1 *************: ${dictionary?.shortDef1}")
//            Log.e(TAG, "******************* Def2 *************: ${dictionary?.shortDef2}")
//            Log.e(TAG, "******************* Def3 *************: ${dictionary?.shortDef3}")
//            Log.e(TAG, "******************* img *************: ${dictionary?.img}")
//            Log.e(TAG, "******************* status *************: ${dictionary?.status}")
//            val list = dictionaryDao.getAllWords()
//            Log.e(TAG, "******************* List Size 1 *************: ${list.value!!.size}")
//            dictionaryDao.clear()
//            Log.e(TAG, "******************* List Size 2*************: ${list.value!!.size}")
            if (query.contentEquals(words[0].word)) {
                Log.e(TAG, "******************* contentEquals: TRUE *************:")
                viewModel.currentWordFromQuery = words[0]
                navController.navigate(R.id.action_searchWordFragment_to_addWordFragment)
            }
        }
    }

    private fun parseJsonToWord(wordID: String, jsonString: String): Any {
        val BASE_URL = "https://www.merriam-webster.com/assets/mw/static/art/dict/"
        val imgPath = "$BASE_URL$wordID.gif"
        Log.e(TAG, "******************* IN parseJsonToWord*************:")
        val json = JSONArray(jsonString)
        val entry = json.getJSONObject(0)
        val shortdef = entry.getJSONArray("shortdef")

        val word = when (shortdef.length()){
            0 -> Word(wordID, "No Definition available", "", "", imgPath, false)
            1 -> Word(wordID, shortdef.getString(0), "", "", "", false)
            2 -> Word(wordID, shortdef.getString(0), shortdef.getString(1), "", imgPath, false)
            else -> Word(
                wordID, shortdef.getString(0), shortdef.getString(1), shortdef.getString(2), imgPath, false
            )
        }
        words.add(word)
        return word
    }
}