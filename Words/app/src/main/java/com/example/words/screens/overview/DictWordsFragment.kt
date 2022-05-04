package com.example.words.screens.overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.words.R
import com.example.words.database.WordDatabase
import com.example.words.databinding.FragmentDictWordsBinding
import com.example.words.network.WordsFilter
import com.example.words.swipe.SwipeToDeleteCallback
import kotlinx.coroutines.runBlocking

class DictWordsFragment : Fragment() {

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    lateinit var viewModel: DictWordsViewModel
    private lateinit var mAdapter: DictWordsListAdapter
    private var fragmentDictWordsBinding: FragmentDictWordsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentDictWordsBinding = FragmentDictWordsBinding.inflate(inflater)
        fragmentDictWordsBinding!!.filterLabel.text = "Showing All"
        val application = requireNotNull(this.activity).application
        val dao = WordDatabase.getInstance(application).wordDao

        val viewModelFactory = DictWordsViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[DictWordsViewModel::class.java]

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        fragmentDictWordsBinding!!.lifecycleOwner = this
        fragmentDictWordsBinding!!.viewModel = viewModel

        mAdapter = DictWordsListAdapter(dao)
        fragmentDictWordsBinding!!.dictWords.layoutManager = LinearLayoutManager(context)
        setHasOptionsMenu(true)
        viewModel.dictWords.observe(this.viewLifecycleOwner) { list ->
            mAdapter.submitList(list)
        }

        fragmentDictWordsBinding!!.dictWords.adapter = mAdapter

        viewModel.currentFilter.observe(this.viewLifecycleOwner) {
            // A new LiveData object has to be observed because the filter
            // has changed. Remove any observers that may already exist.
            viewModel.dictWords.removeObservers(this.viewLifecycleOwner)
            viewModel.dictWords.observe(this.viewLifecycleOwner) { list ->
                mAdapter.submitList(list)
            }
        }


        val swipeguesture = object : SwipeToDeleteCallback(this){
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                when (direction){
                    ItemTouchHelper.RIGHT -> runBlocking {
                        Toast.makeText(requireContext(), "RIGHT. Changed to Active" , Toast.LENGTH_SHORT).show()
                        mAdapter.switchToActive(viewHolder.layoutPosition)

                    }
                    ItemTouchHelper.LEFT -> runBlocking {
                        Toast.makeText(requireContext(), "LEFT. Changed to InActive" , Toast.LENGTH_SHORT).show()
                        mAdapter.switchToInActive(viewHolder.layoutPosition)
                    }
                }
            }
        }

        val touchHelper  = ItemTouchHelper(swipeguesture)
        touchHelper.attachToRecyclerView(fragmentDictWordsBinding!!.dictWords)
        return fragmentDictWordsBinding!!.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_word_menu) {
            findNavController().navigate(DictWordsFragmentDirections.actionDictWordsFragmentToSearchWordFragment())
        } else {
            viewModel.changeFilter(
                when (item.itemId) {
                    R.id.show_active_menu -> {
                        updateUI(1)
                        WordsFilter.SHOW_ACTIVE
                    }
                    R.id.show_inactive_menu -> {
                        updateUI(2)
                        WordsFilter.SHOW_INACTIVE
                    }
                    else -> {
                        updateUI(3)
                        WordsFilter.SHOW_ALL
                    }
                }
            )
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(i: Int) {
        when (i) {
            1-> {
                fragmentDictWordsBinding!!.filterLabel.text = "Showing All Active"
            }
            2 -> {
                fragmentDictWordsBinding!!.filterLabel.text = "Showing All Inactive"
            }
            else -> {
                fragmentDictWordsBinding!!.filterLabel.text = "Showing All"
            }
        }
    }
}