package com.example.words.screens.search


import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.words.R
import com.example.words.database.WordDatabase
import com.example.words.databinding.FragmentSearchWordBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.runBlocking

// The fragment is responsible for handling events. These events include responding to
// user input and data changes through observation of view model data changes.
class SearchWordFragment : Fragment() {
    private val TAG = javaClass.simpleName
    private lateinit var layout: View
    private lateinit var viewModel: SearchWordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchWordBinding.inflate(inflater)
        layout = inflater.inflate(R.layout.fragment_search_word, container, false)
        val application = requireNotNull(activity).application
        val dao = WordDatabase.getInstance(application).wordDao
        val viewModelFactory = SearchWordViewModelFactory(dao)

        viewModel = ViewModelProvider(this, viewModelFactory)[SearchWordViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        mAdapter = SuggestListAdapter()
//        binding.suggestedList.adapter = mAdapter

        val searchButton = layout.findViewById<Button>(R.id.search_button)
        searchButton.setOnClickListener {
            wordSearch()
        }

        // Observe the wordDef so that when a word is found (an exact match of the user input)
        // by the Dictionary API we navigate to the add word screen.
        viewModel.wordDef.observe(viewLifecycleOwner) { word ->
            if (null != word) {
                findNavController()
                    .navigate(
                        SearchWordFragmentDirections.actionSearchWordFragmentToAddWordFragment(word)
                    )
                Log.d(TAG, "Observed ${viewModel.wordDef.value}")
                // This sets the wordDef property to null so that the observer doesn't get notified
                // again when navigating back to the search screen
                viewModel.resetWordDef()
            }
        }

        // Observe the suggestedWords so that when a list of suggested words is
        // returned by the API we can display this on the search word screen.
        viewModel.suggestedWords.observe(viewLifecycleOwner) { suggestedWords ->
            if (null != suggestedWords) {
                Log.e(
                    ContentValues.TAG,
                    "*******************SuggestListAdapter observe NOT NULL  *************:"
                )
                Log.e(
                    ContentValues.TAG,
                    "*******************SuggestListAdapter observe NOT NULL  *************: $suggestedWords"
                )
                binding.suggestedList.adapter = SuggestListAdapter(
                    this,
                    suggestedWords, viewModel
                )
                binding.suggestedList.setHasFixedSize(true)
            }
        }

//        viewModel.wordInDb.observe(viewLifecycleOwner, Observer {  inDB ->
//            if (inDB) {
//                Snackbar.make(fragmentSearchWordsBinding!!.rootLayout, "Word Already Stored", Snackbar.LENGTH_LONG).show()
//            }
//        })

//        viewModel._newarrayofWords.observe(this.viewLifecycleOwner) {
//            // A new LiveData object has to be observed because the filter
//            // has changed. Remove any observers that may already exist.
//            viewModel.suggestedWords.removeObservers(this.viewLifecycleOwner)
//            viewModel.suggestedWords.observe(this.viewLifecycleOwner) { list ->
//                Log.e(ContentValues.TAG, "******************* _newarrayofWords observe(viewLifecycleOwner) *************: $list")
//                mAdapter.submitList(list)
//            }
//        }

        return layout
    }


    private fun wordSearch() = runBlocking {
        // search for the word that the user entered
        val searchInput: TextInputLayout = layout.findViewById(R.id.search_input)
        val searchWord = searchInput.editText?.text.toString()
        if (viewModel.isWordInDictionary(searchWord)){
            Toast.makeText(requireContext(), "Already Stored." , Toast.LENGTH_SHORT).show()
        } else{
            // The search is performed by the view model, because the view model
            // is responsible for data access
            viewModel.performWordSearch(searchWord)
        }
    }
}