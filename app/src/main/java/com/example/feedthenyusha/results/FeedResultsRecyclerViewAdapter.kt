package com.example.feedthenyusha.results

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.feedthenyusha.database.model.FeedResult
import com.example.feedthenyusha.databinding.FeedResultsFragmentBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FeedResultsRecyclerViewAdapter :
    RecyclerView.Adapter<FeedResultsRecyclerViewAdapter.ViewHolder>() {

    private val values = ArrayList<FeedResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FeedResultsFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.satiety.text = item.satiety.toString()
        holder.time.text = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault()).format(Instant.ofEpochMilli(item.time))
    }

    override fun getItemCount() = values.size

    fun fillData(data: List<FeedResult>) {
        values.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: FeedResultsFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val satiety: TextView = binding.satiety
        val time: TextView = binding.time
    }
}