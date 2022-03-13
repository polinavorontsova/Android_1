package com.example.feedthenyusha.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedthenyusha.FeedTheCatApplication
import com.example.feedthenyusha.R
import com.example.feedthenyusha.UserProfileViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class FeedResultsFragment : Fragment() {

    private val viewModel: FeedResultsViewModel by activityViewModels {
        FeedResultsViewModelFactory(
            (activity?.application as FeedTheCatApplication).database.dao()
        )
    }

    private val userProfileViewModel: UserProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.feed_results_fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val feedResultsAdapter = FeedResultsRecyclerViewAdapter()
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = feedResultsAdapter
            }
        }
        lifecycle.coroutineScope.launch {
            userProfileViewModel.user.value?.displayName?.let { it ->
                viewModel.playerResults(it).collect {
                    feedResultsAdapter.fillData(it)
                }
            }
        }
    }
}