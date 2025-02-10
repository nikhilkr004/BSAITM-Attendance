package com.example.bsaitmattendance.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.bsaitmattendance.R

class Color_Piker(private val colors: List<Int>, private val listener: OnColorSelectedListener) :
    RecyclerView.Adapter<Color_Piker.ColorViewHolder>() {

    private var selectedPosition = -1

    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //this is circle  image view for showing the
        val colorCircle: ImageView = itemView.findViewById(R.id.colorCircle)
        val checkIcon: ImageView = itemView.findViewById(R.id.checkIcon)

        init {
            itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                listener.onColorSelected(colors[adapterPosition])
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.color_item, parent, false)
        return ColorViewHolder(view)
    }

    interface OnColorSelectedListener {
        fun onColorSelected(color: Int)

    }
    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        // Set color dynamically
//        holder.colorCircle.setColorFilter(colors[position])

        holder.colorCircle.setBackgroundColor(colors[position])

        // Show check icon only on the selected position
        if (position == selectedPosition) {
            holder.checkIcon.visibility = View.VISIBLE
        } else {
            holder.checkIcon.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = colors.size
}

