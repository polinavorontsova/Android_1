package com.example.feedthenyusha.game

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.feedthenyusha.FeedTheCatApplication
import com.example.feedthenyusha.R
import com.example.feedthenyusha.UserProfileViewModel
import com.example.feedthenyusha.database.model.FeedResult
import com.example.feedthenyusha.databinding.GameFragmentBinding
import com.example.feedthenyusha.signin.SignInFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant

class GameFragment : Fragment() {

    private var _binding: GameFragmentBinding? = null

    private val binding get() = _binding!!

    private val userProfileViewModel: UserProfileViewModel by activityViewModels()

    private val viewModel: GameViewModel by activityViewModels {
        GameViewModelFactory(
            (activity?.application as FeedTheCatApplication).database.dao(),
            userProfileViewModel.user
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToSingInIfNotAuthorized()

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.catImageView.setOnLongClickListener { showSnackBar(getString(R.string.feed_me_text)) }
        binding.feedButton.setOnClickListener { viewModel.updateSatiety() }
        binding.fab.setOnClickListener { shareResults() }
        viewModel.satiety.observe(viewLifecycleOwner) { animateCatIfNecessary(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.developer -> findNavController()
                .navigate(GameFragmentDirections.actionGameFragmentToAboutDeveloperFragment())
            R.id.sign_out -> userProfileViewModel.resetUser(binding.root.context)
            R.id.save -> saveResult()
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveResult() {
        val playerName = userProfileViewModel.user.value?.displayName ?: "None"
        val satiety = viewModel.satiety.value ?: 0
        val feedResult = FeedResult(playerName, Instant.now().toEpochMilli(), satiety)
        viewModel.addResult(feedResult).invokeOnCompletion {
            showSnackBar(getString(R.string.save_success_text))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSnackBar(text: String): Boolean {
        Snackbar.make(
            binding.root,
            text,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }

    private fun shareResults() {
        val message = """
            Result of the ${userProfileViewModel.user.value?.displayName} is ${viewModel.satiety.value}
            Let's play `Feed the Nuysha` together
            Link to game: `https://some-link.com`
        """.trimIndent()
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "image/text-plain"
        }, null)
        startActivity(share)
    }

    private fun animateCatIfNecessary(satiety: Int) {
        if (satiety != 0 && satiety % 15 == 0) {
            val animation = RotateAnimation(0f, 360f, 250f, 0f)
            animation.interpolator = LinearInterpolator()
            animation.repeatCount = Animation.INFINITE
            animation.duration = 1000
            binding.catImageView.startAnimation(animation)
            lifecycleScope.launch {
                delay(1000)
                binding.catImageView.animation = null
            }
        }
    }

    private fun navigateToSingInIfNotAuthorized() {
        userProfileViewModel.setUser(GoogleSignIn.getLastSignedInAccount(binding.root.context))

        val navController = findNavController()
        userProfileViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user == null) {
                navController.navigate(R.id.signInFragment)
            }
        }

        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(SignInFragment.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry) { success ->
                if (!success) {
                    val startDestination = navController.graph.startDestinationId
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(startDestination, true)
                        .build()
                    navController.navigate(startDestination, null, navOptions)
                }
            }
    }
}