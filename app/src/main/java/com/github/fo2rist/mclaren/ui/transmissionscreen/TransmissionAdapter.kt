package com.github.fo2rist.mclaren.ui.transmissionscreen

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
                DRIVER_A_TO_PIT, DRIVER_B_TO_PIT -> {
                    messageTitle.gravity = Gravity.RIGHT
                    messageText.gravity = Gravity.RIGHT
                }
                PIT_TO_DRIVER_A, PIT_TO_DRIVER_B -> {
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
                DRIVER_A_TO_PIT, DRIVER_B_TO_PIT ->
                    item.driverName ?: ""
                PIT_TO_DRIVER_A, PIT_TO_DRIVER_B ->
                    getString(R.string.title_pit_to_racer_format, item.driverName ?: "")
                PIT_TO_EVERYONE ->
                    getString(R.string.title_pitwall)
                MESSAGE_ABOUT_DRIVER_A, MESSAGE_ABOUT_DRIVER_B, MESSAGE_GENERAL ->
                    getString(R.string.title_announcement)
            }
        }

        private fun setImagesFor(item: TransmissionItem) {
            driverImage.setImageResource(when (item.type) {
                DRIVER_A_TO_PIT -> R.drawable.driver_alonso
                DRIVER_B_TO_PIT -> R.drawable.driver_vandoorne
                else -> 0
            })

            when (item.type) {
                DRIVER_A_TO_PIT, DRIVER_B_TO_PIT -> {
                    driverImage.visibility = View.VISIBLE
                    pitwallImage.visibility = View.INVISIBLE
                }
                PIT_TO_DRIVER_A, PIT_TO_DRIVER_B -> {
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

    fun setItems(transmissionItems: List<TransmissionItem>) {
        this.items.clear()
        this.items.addAll(transmissionItems)
        notifyDataSetChanged()
    }
}
