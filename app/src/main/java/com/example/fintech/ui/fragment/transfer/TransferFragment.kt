package com.example.fintech.ui.fragment.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fintech.databinding.FragmentTransferBinding
import com.example.fintech.ui.activity.HomeViewModel
import com.example.fintech.data.repository.TransferRepository

class TransferFragment : Fragment() {

    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!

    private val transferViewModel: TransferViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TransferViewModel::class.java)) {
                    return TransferViewModel(TransferRepository()) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val accounts = listOf("Account 1")
        val banks = listOf("First Bank", "GTBank", "UBA", "Zenith Bank", "Access Bank")

        // Set up the drop-down list for banks
        val bankAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, banks)
        binding.timeText.setAdapter(bankAdapter)

        // Set up the drop-down list for accounts
        val accountAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, accounts)
        binding.endTimeText.setAdapter(accountAdapter)

        binding.makeTransferButton.setOnClickListener {
            val bank = binding.timeText.text.toString()
            val transferFrom = binding.endTimeText.text.toString()
            val accountNumber = binding.comment.text.toString()
            val amountString = binding.amountText.text.toString()

            val amount = amountString.toDoubleOrNull() ?: 0.0

            if (bank.isEmpty() || transferFrom.isEmpty() || accountNumber.isEmpty() || amount <= 0.0) {
                Toast.makeText(context, "All inputs are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                transferViewModel.makeTransfer(bank, transferFrom, accountNumber, amount)
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        transferViewModel.transferResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "Transfer successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Transfer failed", Toast.LENGTH_SHORT).show()
            }
        }

        transferViewModel.transferError.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, "Transfer failed: $it", Toast.LENGTH_SHORT).show()
                transferViewModel.resetError()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
