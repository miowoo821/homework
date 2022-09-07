package com.leander.homework

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leander.homework.model.Weather
import java.util.*


/* Created on 2022/9/8 */

class WeatherListAdapter (var mContext: Context,
                          mData: ArrayList<Weather>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val IMG_TYPE = 1
    private val CONTENT_TYPE = 0

    private var mValue: ArrayList<Weather> = ArrayList()

    interface OnItemClickListener {
        fun onItemClick(obj: Weather, position: Int)
    }
    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.mItemClickListener = itemClickListener
    }

    private var mItemClickListener: OnItemClickListener? = null


    init {
        for (i in 0 until mData.size) {
            if (i % 3 == 2) {
                mValue.add(Weather("", "", "", ""))
            }
            mValue.add(mData[i])
        }
    }

    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tStartTime: TextView? = null
        var tEndTime: TextView? = null
        var tTemperature: TextView? = null
        var llContainer: LinearLayout? = null

        init {
            tEndTime = itemView.findViewById(R.id.tEndTime)
            tStartTime = itemView.findViewById(R.id.tStartTime)
            tTemperature = itemView.findViewById(R.id.tTemperature)
            llContainer = itemView.findViewById(R.id.llContainer)

        }

    }

    class ImgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgImg: ImageView? = null

        init {
            imgImg = itemView.findViewById(R.id.imgImg)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (mValue[position].startTime.isEmpty()) {
            IMG_TYPE
        } else {
            CONTENT_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val viewContent = LayoutInflater.from(mContext)
                    ?.inflate(R.layout.recyclerview_weather_content, parent, false)
                ContentViewHolder(viewContent!!)

            }

            else -> {
                val viewImg = LayoutInflater.from(mContext)
                    ?.inflate(R.layout.recyclerview_weather_img, parent, false)
                ImgViewHolder(viewImg!!)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentViewHolder) {
            holder.tStartTime?.text = mValue[position].startTime
            holder.tEndTime?.text = mValue[position].endTime
            holder.tTemperature?.text = mValue[position].temperature + mValue[position].type
            mItemClickListener?.let {
                holder.itemView.setOnClickListener { mItemClickListener!!.onItemClick(mValue[position], position) }
            }
        } else if (holder is ImgViewHolder) {
            mItemClickListener?.let {
                holder.itemView.setOnClickListener{}
            }
        }
    }

    override fun getItemCount(): Int {
        return mValue.size
    }

}