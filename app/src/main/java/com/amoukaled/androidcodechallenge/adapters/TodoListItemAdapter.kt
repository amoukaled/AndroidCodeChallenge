package com.amoukaled.androidcodechallenge.adapters

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amoukaled.androidcodechallenge.R
import com.amoukaled.androidcodechallenge.fragments.TodoFragment
import com.amoukaled.androidcodechallenge.databinding.TodoListItemBinding
import com.amoukaled.androidcodechallenge.models.Todo

class TodoListItemAdapter(
    private var todos: List<Todo>,
    private val activity: Activity
) : RecyclerView.Adapter<TodoListItemAdapter.TodoListItemViewHolder>() {

    // create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListItemViewHolder {
        return TodoListItemViewHolder(TodoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), activity)
    }

    // bind the view
    override fun onBindViewHolder(holder: TodoListItemViewHolder, position: Int) {
        val todo = todos[position]
        holder.bind(todo)
    }

    override fun getItemCount(): Int = todos.size

    // Diff utils class
    class TodoItemDiffUtils(
        private val oldList: List<Todo>,
        private val newList: List<Todo>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    // refreshes the adapter
    fun refreshAdapter(newList: List<Todo>) {
        val oldList = this.todos
        val result = DiffUtil.calculateDiff(TodoItemDiffUtils(oldList, newList))

        this.todos = newList
        result.dispatchUpdatesTo(this)
    }

    class TodoListItemViewHolder(private val binding: TodoListItemBinding, private val activity: Activity) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.apply {
                titleTV.text = todo.title

                root.apply {
                    // set background
                    background = if (todo.completed) {
                        AppCompatResources.getDrawable(context, R.drawable.dialog_background_green)
                    } else {
                        AppCompatResources.getDrawable(context, R.drawable.dialog_background)
                    }

                    // navigate to the tod'o fragment
                    setOnClickListener {
                        val navController = Navigation.findNavController(activity, R.id.nav_host_fragment_main)
                        val bundle = Bundle().apply {
                            putLong(TodoFragment.TODO_ID_KEY, todo.id)
                        }
                        navController.navigate(R.id.nav_todo, bundle)
                    }
                }
            }
        }
    }

}