package com.example.worddictionaryapp.mainscreen

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.worddictionaryapp.R
import com.example.worddictionaryapp.adapter.DictionaryAdapter
import com.example.worddictionaryapp.database.DictionaryDataBase
import com.example.worddictionaryapp.databinding.FragmentMainBinding
import com.example.worddictionaryapp.swipe.SwipeToDeleteCallback
import kotlinx.coroutines.runBlocking

class MainFragment : Fragment() {
    private lateinit var navController: NavController
    private var fragmentMainBinding: FragmentMainBinding? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: DictionaryAdapter

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
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding.lifecycleOwner = this
        val wordList = mainViewModel.getWordsFromDataBase()


        adapter =  DictionaryAdapter(this, mainViewModel)
        binding.RecyclerViewWordList.layoutManager = LinearLayoutManager(context)
        binding.RecyclerViewWordList.adapter = adapter
        val swipeguesture = object : SwipeToDeleteCallback(this){
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                when (direction){
                    ItemTouchHelper.RIGHT -> runBlocking {
                        Toast.makeText(requireContext(), "RIGHT." , Toast.LENGTH_SHORT).show()
                        adapter.switchToActive(viewHolder.layoutPosition)

                    }
                    ItemTouchHelper.LEFT -> runBlocking {
                        Toast.makeText(requireContext(), "LEFT." , Toast.LENGTH_SHORT).show()
                        adapter.switchToInActive(viewHolder.layoutPosition)
                    }
                }
            }
        }

        val touchHelper  = ItemTouchHelper(swipeguesture)
        touchHelper.attachToRecyclerView(binding.RecyclerViewWordList)
        mainViewModel.allWords.observe(viewLifecycleOwner, Observer { word ->
            adapter.setData(word)
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
                R.id.active -> {

                    adapter.activeFilter(1)
                    navController.navigate(R.id.action_mainFragment_self)
                }
                R.id.inactive -> {
                    adapter.inActiveFilter(2)
                    navController.navigate(R.id.action_mainFragment_self)
                }
                else -> {
                    adapter.showAllFilter(0)
                    navController.navigate(R.id.action_mainFragment_self)
                }

            }
//        )
        return true
    }
}