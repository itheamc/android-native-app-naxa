package com.itheamc.naxatestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.itheamc.naxatestapp.adapters.EntryViewAdapter
import com.itheamc.naxatestapp.databinding.FragmentEntriesBinding
import com.itheamc.naxatestapp.pagination.EntryLoadStateAdapter
import com.itheamc.naxatestapp.viewmodels.MainViewModel
import com.itheamc.naxatestapp.viewmodels.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        val repository = (requireActivity().application as NaxaApplication).mainRepository
        val viewModel: MainViewModel by viewModels(factoryProducer = {
            MainViewModelFactory(
                repository
            )
        })

        val adapter = EntryViewAdapter()
        binding.entryRecyclerView.adapter = adapter.withLoadStateFooter(
            EntryLoadStateAdapter()
        )

        lifecycleScope.launch {
            viewModel.entries.collectLatest {
                adapter.submitData(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}