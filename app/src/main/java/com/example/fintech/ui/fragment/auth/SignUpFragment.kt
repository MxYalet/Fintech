package com.example.fintech.ui.fragment.auth

import com.example.fintech.ui.activity.HomeActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.fintech.R
import com.example.fintech.databinding.FragmentSignUpBinding
import com.example.fintech.ui.fragment.auth.viewModel.SignUpViewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    lateinit var progressDialog: AlertDialog

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = signUpViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeProgressDialog()

        binding.signUpButton.setOnClickListener {
            progressDialog.show()
              signUpViewModel.signUpUser()
        }
        signUpViewModel.signUpResult.observe(viewLifecycleOwner, Observer { isSuccess ->
            progressDialog?.dismiss()
            if (isSuccess) {
                Toast.makeText(context, "Sign-up successful", Toast.LENGTH_SHORT).show()
                val userName = signUpViewModel.name.value ?: ""
                val intent = Intent(activity, HomeActivity::class.java).apply {
                    putExtra("USER_NAME", userName)
                }
                startActivity(intent)
            }
        })

        signUpViewModel.signUpError.observe(viewLifecycleOwner, Observer { errorMessage ->
            progressDialog?.dismiss()
            errorMessage?.let {
                Toast.makeText(context, "Sign-up failed: $it", Toast.LENGTH_SHORT).show()
                signUpViewModel.resetError()
            }
        })

        binding.signinTextView.setOnClickListener {
            val secondFragment = SignInFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, secondFragment)
                .addToBackStack(null)
                .commit()
        }


        binding.loginCloseButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.popBackStack()
                }
            })
        }
    }

    private fun initializeProgressDialog() {
        val builder = requireContext().let { AlertDialog.Builder(it,R.style.WrapContentDialog) }

        val customView: View = layoutInflater.inflate(R.layout.progress_bar,null)

        builder.setView(customView)

        builder.setCancelable(false)

        progressDialog  = builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
