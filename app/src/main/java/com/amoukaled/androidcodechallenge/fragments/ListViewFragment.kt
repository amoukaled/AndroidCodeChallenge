package com.amoukaled.androidcodechallenge.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amoukaled.androidcodechallenge.adapters.TodoListItemAdapter
import com.amoukaled.androidcodechallenge.databinding.FragmentListViewBinding
import com.amoukaled.androidcodechallenge.models.ApiResource
import com.amoukaled.androidcodechallenge.viewModels.ListViewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListViewFragment : Fragment() {

    private var binding: FragmentListViewBinding? = null
    private lateinit var todoAdapter: TodoListItemAdapter
    private val model: ListViewViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListViewBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRV()
        collectTodos()
        initSwipeToRefresh()
    }

    /**
     * Init swipe to refresh
     */
    private fun initSwipeToRefresh() {
        binding?.refreshSW?.apply {
            setOnRefreshListener {
                model.refreshTodos()
                isRefreshing = false
            }
        }
    }

    /**
     * Init [FragmentListViewBinding.todoRV]
     */
    private fun initRV() {
        binding?.apply {
            todoAdapter = TodoListItemAdapter(model.todoStateFlow.value.data ?: emptyList(), requireActivity())
            todoRV.adapter = todoAdapter
            todoRV.layoutManager = LinearLayoutManager(requireContext())
        }
        model.refreshTodos()
    }

    /**
     * Collects [ListViewViewModel.todoStateFlow]
     */
    private fun collectTodos() {
        lifecycleScope.launchWhenCreated {
            model.todoStateFlow.collect { event ->
                when (event) {
                    is ApiResource.Error -> {
                        binding?.apply {
                            todoRV.isGone = true
                            todoPB.isGone = true
                            errorTV.isVisible = true
                            errorTV.text = event.message
                        }
                    }
                    is ApiResource.Loading -> {
                        binding?.apply {
                            todoRV.isGone = true
                            todoPB.isVisible = true
                            errorTV.isGone = true
                        }
                    }
                    is ApiResource.Success -> {
                        binding?.apply {
                            todoRV.isVisible = true
                            todoPB.isGone = true
                            errorTV.isGone = true
                            event.data?.let(todoAdapter::refreshAdapter)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}