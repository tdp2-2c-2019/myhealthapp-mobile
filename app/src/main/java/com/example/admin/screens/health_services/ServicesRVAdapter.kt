package com.example.admin.screens.health_services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.databinding.HealthServiceItemBinding
import com.example.admin.models.HealthService
import java.util.*

class ServicesRVAdapter(
    private var items: ArrayList<HealthService>
) : RecyclerView.Adapter<ServicesRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HealthServiceItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun replaceData(newItems: ArrayList<HealthService>) {
        items = newItems
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: HealthServiceItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(service: HealthService) {
            binding.service = service
            binding.executePendingBindings()
        }
    }

}