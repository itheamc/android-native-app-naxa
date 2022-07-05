package com.itheamc.naxatestapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.itheamc.naxatestapp.adapters.EntryViewAdapter
import com.itheamc.naxatestapp.databinding.FragmentEntriesBinding
import com.itheamc.naxatestapp.pagination.EntryLoadStateAdapter
import com.itheamc.naxatestapp.viewmodels.MainViewModel
import com.itheamc.naxatestapp.viewmodels.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.notify

private const val TAG = "EntriesFragment"

class EntriesFragment : Fragment() {
    private var _binding: FragmentEntriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEntriesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Initializing the main repository
         */
        val repository = (requireActivity().application as NaxaApplication).mainRepository

        /**
         * Initializing View model
         */
        val viewModel: MainViewModel by viewModels(factoryProducer = {
            MainViewModelFactory(
                repository
            )
        })

        /**
         * Initializing EntryViewAdapter
         */
        val entriesAdapter = EntryViewAdapter(
            clickListener = {
                it?.let {

                    val bundle = Bundle()
                    bundle.putString("link", it.link)

                    findNavController().navigate(
                        resId = R.id.action_entriesFragment_to_entryFragment,
                        args = bundle
                    )
                }
            }
        )

        /**
         * Setting entriesAdapter as a entryRecyclerView adapter
         */
        binding.entryRecyclerView.adapter = entriesAdapter.withLoadStateFooter(
            EntryLoadStateAdapter()
        )

        /**
         * Listening the flow data and passing to the entryAdapter to show in the recyclerview
         */
        lifecycleScope.launchWhenCreated {
            viewModel.entries.collectLatest {
                entriesAdapter.submitData(lifecycle, it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}