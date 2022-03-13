package com.example.feedthenyusha.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedthenyusha.R
import com.example.feedthenyusha.database.model.FeedResult

/**
 * A fragment representing a list of Items.
 */
class FeedResultsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.feed_results_fragment_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = FeedResultsRecyclerViewAdapter(
                    listOf(
                        FeedResult("go", 273462746274, 41),
                        FeedResult("go", 273462746274, 41),
                        FeedResult("go", 273462746274, 41),
                        FeedResult("go", 273462746274, 41)
                    )
                )
            }
        }
        return view
    }
}