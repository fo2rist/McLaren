package com.github.fo2rist.mclaren.ui.transmissionscreen

import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.models.TransmissionItem
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.NORRIS_TO_PIT
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.MESSAGE_FROM_GUEST
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.MESSAGE_GENERAL
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.PIT_TO_NORRIS
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.PIT_TO_RICCIARDO
import com.github.fo2rist.mclaren.models.TransmissionItem.Type.RICCIARDO_TO_PIT

/**
 * Adapter for messages in the race life transmission (team communications).
 */
internal class TransmissionAdapter(
    private val items: ArrayList<TransmissionItem> = ArrayList()
) : RecyclerView.Adapter<TransmissionAdapter.TransmissionViewHolder>() {

    class TransmissionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageText = view.findViewById<TextView>(R.id.message_text)
        val messageTitle = view.findViewById<TextView>(R.id.title_text)
        val driverImage = view.findViewById<ImageView>(R.id.driver_avatar_image)
        val pitwallImage = view.findViewById<ImageView>(R.id.pit_avatar_image)

        fun displayItem(item: TransmissionItem) {
            setMessageFor(item)
            setTitleFor(item)
            setImagesFor(item)
            alignTextFor(item)
        }

        private fun alignTextFor(item: TransmissionItem) {
            when (item.type) {
                RICCIARDO_TO_PIT, NORRIS_TO_PIT -> {
                    messageTitle.gravity = Gravity.RIGHT
                    messageText.gravity = Gravity.RIGHT
                }
                PIT_TO_RICCIARDO, PIT_TO_NORRIS -> {
                    messageTitle.gravity = Gravity.LEFT
                    messageText.gravity = Gravity.LEFT
                }
                else -> {
                    messageTitle.gravity = Gravity.CENTER_HORIZONTAL
                    messageText.gravity = Gravity.LEFT
                }
            }
        }

        private fun setMessageFor(item: TransmissionItem) {
            messageText.text = item.message
        }

        private fun setTitleFor(item: TransmissionItem) {

            messageTitle.text = when (item.type) {
                RICCIARDO_TO_PIT ->
                    getString(R.string.transmission_short_name_ricciardo)
                NORRIS_TO_PIT ->
                    getString(R.string.transmission_short_name_norris)
                PIT_TO_RICCIARDO ->
                    getString(
                            R.string.transmission_item_title_pit_to_racer_format,
                            getString(R.string.transmission_short_name_ricciardo))
                PIT_TO_NORRIS ->
                    getString(
                            R.string.transmission_item_title_pit_to_racer_format,
                            getString(R.string.transmission_short_name_norris))
                MESSAGE_GENERAL ->
                    getString(R.string.transmission_item_title_pitwall)
                MESSAGE_FROM_GUEST ->
                    getString(R.string.transmission_item_title_guest_format, item.guestName)
            }
        }

        private fun setImagesFor(item: TransmissionItem) {
            driverImage.setImageResource(when (item.type) {
                RICCIARDO_TO_PIT -> R.drawable.driver_portrait_ricciardo
                NORRIS_TO_PIT -> R.drawable.driver_portrait_norris
                else -> 0
            })

            when (item.type) {
                RICCIARDO_TO_PIT, NORRIS_TO_PIT -> {
                    driverImage.visibility = View.VISIBLE
                    pitwallImage.visibility = View.INVISIBLE
                }
                PIT_TO_RICCIARDO, PIT_TO_NORRIS -> {
                    driverImage.visibility = View.INVISIBLE
                    pitwallImage.visibility = View.VISIBLE
                }
                else -> {
                    driverImage.visibility = View.GONE
                    pitwallImage.visibility = View.GONE
                }
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

    fun setItems(newItems: List<TransmissionItem>): Boolean {
        val hasNewerItems = hasNewerItems(newItems)
        val diff: DiffUtil.DiffResult = DiffUtil.calculateDiff(createComparisonCallback(this.items, newItems), false)
        this.items.clear()
        this.items.addAll(newItems)
        diff.dispatchUpdatesTo(this)
        return hasNewerItems
    }

    @Suppress("ReturnCount")
    private fun hasNewerItems(newItems: List<TransmissionItem>): Boolean {
        if (items.isEmpty()) {
            return newItems.isNotEmpty()
        }
        if (newItems.isEmpty()) {
            return false
        }

        return items[0].dateTime < newItems[0].dateTime
    }

    //TODO duplicates [FeedAdapter]. Extract common code.
    private fun createComparisonCallback(
        oldItems: List<TransmissionItem>, newItems: List<TransmissionItem>
    ) = object : DiffUtil.Callback() {
        override fun getOldListSize() = oldItems.size


        override fun getNewListSize() = newItems.size


        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areContentsTheSame(oldItemPosition, newItemPosition)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }
    }
}
