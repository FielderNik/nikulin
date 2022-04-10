package com.devlife.nikulin.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devlife.nikulin.appComponent
import com.devlife.nikulin.databinding.FragmentHotMemesBinding
import com.devlife.nikulin.domain.entities.MemesTypes
import com.devlife.nikulin.presentation.adapters.LatestMemesAdapter
import com.devlife.nikulin.presentation.adapters.SnapHelperOneByOne
import com.devlife.nikulin.presentation.viewmodels.HotMemesViewModel
import javax.inject.Inject


class HotMemesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: HotMemesViewModel
    private lateinit var binding: FragmentHotMemesBinding
    private lateinit var memesAdapter: LatestMemesAdapter

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HotMemesViewModel::class.java)
        getHotMemes()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotMemesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        memesAdapter = LatestMemesAdapter()
        val linearSnapHelper = SnapHelperOneByOne()
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHotMemes.layoutManager = layoutManager
        binding.rvHotMemes.adapter = memesAdapter
        linearSnapHelper.attachToRecyclerView(binding.rvHotMemes)

        viewModel.hotMemes.observe(viewLifecycleOwner) { latestMemes ->
            memesAdapter.refresh(latestMemes)
            hideProgressBar()

            binding.rvHotMemes.visibility = View.VISIBLE
            binding.llFailure.visibility = View.GONE

            viewModel.currentPositionLive.observe(viewLifecycleOwner) { currentPosition ->
                binding.btnBackHotMems.isEnabled = currentPosition != 0
                binding.btnNextHotMems.isEnabled = currentPosition != latestMemes.size - 1
                binding.rvHotMemes.smoothScrollToPosition(currentPosition)
            }
        }


        viewModel.failureHotMemesLiveData.observe(viewLifecycleOwner) { failure ->
            binding.rvHotMemes.visibility = View.GONE
            binding.llFailure.visibility = View.VISIBLE
            hideProgressBar()
        }

        binding.btnNextHotMems.setOnClickListener {
            viewModel.currentPositionLive.postValue(viewModel.currentPositionLive.value?.plus(1))

            if (layoutManager.findLastVisibleItemPosition() == memesAdapter.latestMemes.size - 2) {
                getHotMemes()
            }
        }

        binding.btnBackHotMems.setOnClickListener {
            viewModel.currentPositionLive.postValue(viewModel.currentPositionLive.value?.minus(1))
        }

        // отключаем touchListener у recycler
        binding.rvHotMemes.setOnTouchListener { v, event -> true }

        binding.btnRepeat.setOnClickListener {
            getHotMemes()
        }

    }

    private fun getHotMemes() {
        viewModel.getMemes(MemesTypes.HOT.remoteName)
    }

    private fun showProgressBar() {
        binding.pbMemes.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbMemes.visibility = View.GONE
    }


    companion object {
        @JvmStatic
        fun newInstance() = HotMemesFragment()
    }
}