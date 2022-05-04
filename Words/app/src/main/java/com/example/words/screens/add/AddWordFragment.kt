package com.example.words.screens.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.words.R
import com.example.words.database.WordDatabase
import com.example.words.databinding.FragmentAddWordBinding

class AddWordFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application = requireNotNull(activity).application
        val binding = FragmentAddWordBinding.inflate(inflater)
        val dao = WordDatabase.getInstance(application).wordDao

        binding.lifecycleOwner = this.viewLifecycleOwner

        // Get the word object from the argument passed to it so that it can
        // be passed the view model via the view model factory.
        val word = AddWordFragmentArgs.fromBundle(requireArguments()).wordDef

        val viewModelFactory = AddWordViewModelFactory(word, application, dao)

        // The ViewModelProvider uses the factory to create the view model.
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory
        )[AddWordViewModel::class.java]

        val viewModel = binding.viewModel
        // Navigates back to the search fragment
        binding.searchAgain.setOnClickListener {
            findNavController().navigate(R.id.action_addWordFragment_to_searchWordFragment)
        }

        // TODO setup a listener for the add button. The listener code should call a function
        // on the view model to add the word to the database.
        binding.addWord.setOnClickListener {
            viewModel!!.addWord()
            findNavController().navigate(R.id.action_addWordFragment_to_searchWordFragment)
        }

        return binding.root
    }

}