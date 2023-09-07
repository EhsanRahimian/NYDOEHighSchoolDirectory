package com.example.nydoehighschooldirectory.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nydoehighschooldirectory.databinding.SchoolRowBinding
import com.example.nydoehighschooldirectory.model.School

class SchoolAdapter : ListAdapter<School, SchoolAdapter.SchoolViewHolder>(SchoolDiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(school: School)
    }
    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SchoolRowBinding.inflate(inflater, parent, false)
        return SchoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        val school = getItem(position)
        holder.bind(school)
    }

    inner class SchoolViewHolder(private val binding: SchoolRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            //itemView.setOnClickListener {
                binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val school = getItem(position)
                    listener?.onItemClick(school)
                }
            }
        }

        fun bind(school: School) {
            binding.schoolNameTextView.text = school.schoolName
            binding.cityTextView.text = school.city
            binding.zipTextView.text = school.zip
        }

    }

    class SchoolDiffCallback : DiffUtil.ItemCallback<School>() {
        override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem.dbn == newItem.dbn
        }

        override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem == newItem
        }
    }
}
