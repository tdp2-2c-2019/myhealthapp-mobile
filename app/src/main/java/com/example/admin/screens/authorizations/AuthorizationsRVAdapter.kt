package com.example.admin.screens.authorizations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.databinding.AuthorizationItemBinding
import com.example.admin.models.Authorization
import java.util.*

class AuthorizationsRVAdapter(
    private var items: ArrayList<Authorization>
) : RecyclerView.Adapter<AuthorizationsRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AuthorizationItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun replaceData(newItems: ArrayList<Authorization>) {
        items = newItems
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: AuthorizationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(authorization: Authorization) {
            binding.authorization = authorization
            //binding.card.setOnClickListener { activity.goToDetail(service.id) }
            binding.executePendingBindings()
        }
    }

}