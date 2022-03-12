package com.example.feedthenyusha.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.example.feedthenyusha.UserProfileViewModel
import com.example.feedthenyusha.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

private const val TAG = "SignInFragment"

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null

    private val binding get() = _binding!!

    private val viewModel: UserProfileViewModel by activityViewModels()

    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle.set(LOGIN_SUCCESSFUL, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(binding.root.context, gso)

        with(binding) {
            signInButton.setSize(SignInButton.SIZE_STANDARD)
            signInButton.setOnClickListener {
                startActivityForResult(client.signInIntent, RC_SIGN_IN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            viewModel.setUser(account)
            savedStateHandle.set(LOGIN_SUCCESSFUL, true)
            findNavController().popBackStack()
        } catch (e: ApiException) {
            Log.w(TAG, "Sign-In failed with code = ${e.statusCode}", e)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 0
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }
}