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
import com.example.nikulin.databinding.FragmentTopMemesBinding
import com.example.nikulin.domain.entities.MemesTypes
import com.example.nikulin.ui.adapters.LatestMemesAdapter
import com.example.nikulin.ui.adapters.SnapHelperOneByOne
import com.example.nikulin.ui.viewmodels.BestMemesViewModel
import javax.inject.Inject


class TopMemesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: BestMemesViewModel
    private lateinit var binding: FragmentTopMemesBinding
    private lateinit var memesAdapter: LatestMemesAdapter


    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BestMemesViewModel::class.java)
        getTopMemes()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopMemesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        memesAdapter = LatestMemesAdapter()
        val linearSnapHelper = SnapHelperOneByOne()
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBestMemes.layoutManager = layoutManager
        binding.rvBestMemes.adapter = memesAdapter
        linearSnapHelper.attachToRecyclerView(binding.rvBestMemes)

        viewModel.bestMemes.observe(viewLifecycleOwner) { latestMemes ->
            memesAdapter.refresh(latestMemes)
            hideProgressBar()
            binding.rvBestMemes.visibility = View.VISIBLE
            binding.llFailure.visibility = View.GONE


            viewModel.currentPositionBestMemes.observe(viewLifecycleOwner) { currentPosition ->
                binding.btnBackBestMems.isEnabled = currentPosition != 0
                binding.btnNextBestMems.isEnabled = currentPosition != latestMemes.size - 1
                binding.rvBestMemes.smoothScrollToPosition(currentPosition)
            }
        }

        viewModel.failureBestMemesLiveData.observe(viewLifecycleOwner) { failure ->
            binding.rvBestMemes.visibility = View.GONE
            binding.llFailure.visibility = View.VISIBLE
            hideProgressBar()
        }


        binding.btnNextBestMems.setOnClickListener {
            viewModel.currentPositionBestMemes.postValue(viewModel.currentPositionBestMemes.value?.plus(1))

            if (layoutManager.findLastVisibleItemPosition() == memesAdapter.latestMemes.size - 2) {
                getTopMemes()
            }
        }

        binding.btnBackBestMems.setOnClickListener {
            viewModel.currentPositionBestMemes.postValue(viewModel.currentPositionBestMemes.value?.minus(1))
        }

        // отключаем touchListener у recycler
        binding.rvBestMemes.setOnTouchListener { v, event -> true }

        binding.btnRepeat.setOnClickListener {
            getTopMemes()
        }
    }

    private fun getTopMemes() {
        viewModel.getMemes(MemesTypes.TOP.remoteName)
    }


    private fun showProgressBar() {
        binding.pbMemes.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbMemes.visibility = View.GONE
    }


    companion object {
        @JvmStatic
        fun newInstance() = TopMemesFragment()
    }
}