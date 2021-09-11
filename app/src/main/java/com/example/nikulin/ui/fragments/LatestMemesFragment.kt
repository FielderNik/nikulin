package com.example.nikulin.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nikulin.appComponent
import com.example.nikulin.databinding.FragmentLatestMemesBinding
import com.example.nikulin.domain.entities.MemesTypes
import com.example.nikulin.ui.adapters.LatestMemesAdapter
import com.example.nikulin.ui.adapters.SnapHelperOneByOne
import com.example.nikulin.ui.viewmodels.LastMemesViewModel
import javax.inject.Inject


class LatestMemesFragment : Fragment() {

    companion object {
        fun newInstance() = LatestMemesFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentLatestMemesBinding
    private lateinit var latestMemesAdapter: LatestMemesAdapter
    private lateinit var viewModel: LastMemesViewModel

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LastMemesViewModel::class.java)
        getLatestMemes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLatestMemesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        latestMemesAdapter = LatestMemesAdapter()
        val linearSnapHelper = SnapHelperOneByOne()
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvLatestMemes.layoutManager = layoutManager
        binding.rvLatestMemes.adapter = latestMemesAdapter
        linearSnapHelper.attachToRecyclerView(binding.rvLatestMemes)

        viewModel.latestMemes.observe(viewLifecycleOwner) { latestMemes ->
            latestMemesAdapter.refresh(latestMemes)

            hideProgressBar()

            binding.rvLatestMemes.visibility = View.VISIBLE
            binding.llFailure.visibility = View.GONE

            viewModel.currentPositionLatestMemes.observe(viewLifecycleOwner) { currentPosition ->
                binding.btnBackLatestMems.isEnabled = currentPosition != 0
                binding.btnNextLatestMems.isEnabled = currentPosition != latestMemes.size - 1
                binding.rvLatestMemes.smoothScrollToPosition(currentPosition)
            }
        }

        viewModel.failureLiveData.observe(viewLifecycleOwner) { failure ->
            binding.rvLatestMemes.visibility = View.GONE
            binding.llFailure.visibility = View.VISIBLE
            hideProgressBar()
        }

        binding.btnNextLatestMems.setOnClickListener {
            viewModel.currentPositionLatestMemes.postValue(viewModel.currentPositionLatestMemes.value?.plus(1))

            if (layoutManager.findLastVisibleItemPosition() == latestMemesAdapter.latestMemes.size - 2) {
                getLatestMemes()
            }
        }

        binding.btnBackLatestMems.setOnClickListener {
            viewModel.currentPositionLatestMemes.postValue(viewModel.currentPositionLatestMemes.value?.minus(1))
        }

        binding.btnRepeat.setOnClickListener {
            getLatestMemes()
        }

        // отключаем touchListener у recycler
        binding.rvLatestMemes.setOnTouchListener { v, event -> true }


    }

    private fun getLatestMemes() {
        viewModel.getMemes(MemesTypes.LATEST.remoteName)
    }

    private fun showProgressBar() {
        binding.pbMemes.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbMemes.visibility = View.GONE
    }


}