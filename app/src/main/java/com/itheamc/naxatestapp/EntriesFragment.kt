package com.itheamc.naxatestapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.itheamc.naxatestapp.adapters.EntryViewAdapter
import com.itheamc.naxatestapp.databinding.FragmentEntriesBinding
import com.itheamc.naxatestapp.pagination.EntryLoadStateAdapter
import com.itheamc.naxatestapp.viewmodels.MainViewModel
import com.itheamc.naxatestapp.viewmodels.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest

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
         * Getting the main repository instance from the application
         */
        val repository = (requireActivity().application as NaxaApplication).mainRepository

        /**
         * Initializing View model with repository instance
         */
        val viewModel: MainViewModel by viewModels(factoryProducer = {
            MainViewModelFactory(
                repository
            )
        })

        /**
         * Initializing EntryViewAdapter and handling onClick callback
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
         * Setting entriesAdapter to entryRecyclerView adapter
         */
        binding.entryRecyclerView.adapter = entriesAdapter.withLoadStateFooter(
            EntryLoadStateAdapter()
        )

        /**
         * Listening the data flow and passing it to the entryAdapter so as to show on recyclerview
         */
        lifecycleScope.launchWhenCreated {
            viewModel.entries.collectLatest {
                entriesAdapter.submitData(lifecycle, it)
            }
        }


        /**
         * Listening the error that flow in case any error or exceptions occurs
         */
        lifecycleScope.launchWhenCreated {
            viewModel.error.collectLatest {
                it?.let { err ->
                    Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    /**
     * Setting binding to null whenever view was destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}