package com.example.worddictionaryapp.mainscreen

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worddictionaryapp.R
import com.example.worddictionaryapp.adapter.DictionaryAdapter
import com.example.worddictionaryapp.database.Dictionary
import com.example.worddictionaryapp.database.DictionaryDataBase
import com.example.worddictionaryapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var navController: NavController
    private var fragmentMainBinding: FragmentMainBinding? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        fragmentMainBinding = binding
        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application
        val dataSource = DictionaryDataBase.getInstance(application).dictionaryDataBaseDao()

        val viewModelFactory = MainViewModelFactory(dataSource, application)
        val mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding.lifecycleOwner = this
        val wordList = mainViewModel.getWordsFromDataBase()

//
//        Log.i("MainViewModel", "!wordList!!.isNullOrEmpty1() ${wordList.size}")
//        Log.i("MainViewModel", "word.isNullOrEmpty1() ${wordList[0].word}")
//        Log.i("MainViewModel", "!wordList!!.isNullOrEmpty222() ${wordList.size}")
        val adapter =  DictionaryAdapter(this)
        binding.RecyclerViewWordList.layoutManager = LinearLayoutManager(context)
        binding.RecyclerViewWordList.adapter = adapter

        mainViewModel.allWords.observe(viewLifecycleOwner, Observer { word ->
            adapter?.setData(word)
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        viewModel.updateFilter(
            when (item.itemId) {
                R.id.add_word -> navController.navigate(R.id.action_mainFragment_to_searchWordFragment)
//                R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
//                else -> MarsApiFilter.SHOW_ALL
            }
//        )
        return true
    }
}