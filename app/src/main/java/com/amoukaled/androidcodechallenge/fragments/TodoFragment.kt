package com.amoukaled.androidcodechallenge.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.amoukaled.androidcodechallenge.R
import com.amoukaled.androidcodechallenge.databinding.FragmentTodoBinding
import com.amoukaled.androidcodechallenge.viewModels.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TodoFragment : Fragment() {

    companion object {
        const val TODO_ID_KEY = "k237663431"
    }

    private var binding: FragmentTodoBinding? = null
    private val model: TodoViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    /**
     * Initializes the views
     */
    private fun initViews() {
        arguments?.getLong(TODO_ID_KEY)?.let { todoId ->
            lifecycleScope.launchWhenCreated {
                model.todoStateFlow.collect { todoList ->
                    todoList?.find {
                        it.id == todoId
                    }?.let { nTodo ->
                        binding?.apply {
                            titleTV.text = nTodo.title
                            idTV.text = nTodo.id.toString()
                            userIDTV.text = nTodo.userId.toString()
                            completedIV.setImageDrawable(
                                if (nTodo.completed) {
                                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_check)
                                } else {
                                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_cross)
                                }
                            )
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