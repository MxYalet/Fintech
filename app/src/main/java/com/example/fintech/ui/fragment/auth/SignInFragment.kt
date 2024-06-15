package com.example.fintech.ui.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fintech.R
import com.example.fintech.data.repository.AuthRepository
import com.example.fintech.databinding.FragmentSignInBinding
import com.example.fintech.ui.activity.HomeActivity
import com.example.fintech.ui.fragment.auth.viewModel.SignInViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    lateinit var progressDialog: AlertDialog

    // Initialize the UserRepository and SignInViewModel
    private val userRepository = AuthRepository(FirebaseAuth.getInstance())
    private val signInViewModel: SignInViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
                    return SignInViewModel(userRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeProgressDialog()

        binding.loginButton.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Email and password are required", Toast.LENGTH_SHORT).show()
            } else {
                progressDialog.show()
                signInViewModel.signInUser(email, password)
            }
        }

        binding.signupTextView.setOnClickListener {
            val secondFragment = SignUpFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, secondFragment)
                .addToBackStack(null)
                .commit()
        }

        signInViewModel.loginResult.observe(viewLifecycleOwner, Observer { isSuccess ->
            progressDialog.dismiss()
            if (isSuccess) {
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })

        signInViewModel.loginError.observe(viewLifecycleOwner, Observer { errorMessage ->
            progressDialog.dismiss()
            errorMessage?.let {
                Toast.makeText(context, "Login failed: $it", Toast.LENGTH_SHORT).show()
                signInViewModel.resetError()
            }
        })
    }

    private fun initializeProgressDialog() {
        val builder = requireContext().let { AlertDialog.Builder(it, R.style.WrapContentDialog) }
        val customView: View = layoutInflater.inflate(R.layout.progress_bar, null)
        builder.setView(customView)
        builder.setCancelable(false)
        progressDialog = builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
