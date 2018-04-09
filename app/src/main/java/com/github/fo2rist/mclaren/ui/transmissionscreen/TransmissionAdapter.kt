package com.github.fo2rist.mclaren.ui.transmissionscreen

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.models.TransmissionItem
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.DRIVER_A_TO_PIT
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.DRIVER_B_TO_PIT
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.MESSAGE_ABOUT_DRIVER_A
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.MESSAGE_ABOUT_DRIVER_B
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.MESSAGE_GENERAL
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.PIT_TO_DRIVER_A
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.PIT_TO_DRIVER_B
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.PIT_TO_EVERYONE

class TransmissionAdapter(
        private val items: ArrayList<TransmissionItem> = ArrayList()
) : RecyclerView.Adapter<TransmissionAdapter.TransmissionViewHolder>() {

    class TransmissionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageText = view.findViewById<TextView>(R.id.message_text)
        val messageTitle = view.findViewById<TextView>(R.id.title_text)

        fun displayItem(item: TransmissionItem) {
            messageText.text = item.message
            messageTitle.text = getTitleFor(item)
        }

        private fun getTitleFor(item: TransmissionItem): CharSequence {
            return when(item.type) {
                DRIVER_A_TO_PIT, DRIVER_B_TO_PIT ->
                    item.driverName ?: ""
                PIT_TO_DRIVER_A, PIT_TO_DRIVER_B ->
                    getString(R.string.title_pit_to_racer_format, item.driverName ?: "")
                PIT_TO_EVERYONE ->
                    getString(R.string.title_pitwall)
                MESSAGE_ABOUT_DRIVER_A, MESSAGE_ABOUT_DRIVER_B, MESSAGE_GENERAL ->
                    getString(R.string.title_announcement)
                else ->
                    ""
            }
        }

        private fun getString(@StringRes stringId: Int, vararg formatArgs: String) =
                itemView.context.getString(stringId, *formatArgs)
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
