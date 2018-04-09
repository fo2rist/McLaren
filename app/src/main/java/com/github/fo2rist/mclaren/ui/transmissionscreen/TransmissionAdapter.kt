package com.github.fo2rist.mclaren.ui.transmissionscreen

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.models.TransmissionItem

class TransmissionAdapter(
        private val items: ArrayList<TransmissionItem> = ArrayList()
) : RecyclerView.Adapter<TransmissionAdapter.TransmissionViewHolder>() {

    class TransmissionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageText = view.findViewById<TextView>(R.id.message_text)

        fun displayItem(item: TransmissionItem) {
            messageText.text = item.message
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransmissionViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_transmission, parent, false)
        return TransmissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransmissionViewHolder, position: Int) {
        holder.displayItem(items.get(position))
    }

    fun setItems(transmissionItems: List<TransmissionItem>) {
        this.items.clear()
        this.items.addAll(transmissionItems)
        notifyDataSetChanged()
    }
}
