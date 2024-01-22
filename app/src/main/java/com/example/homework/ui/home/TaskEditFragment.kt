package com.example.homework.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.homework.databinding.FragmentTaskEditBinding
import com.example.homework.ui.home.TaskEditFragmentDirections

class TaskEditFragment : Fragment() {

    private var _binding: FragmentTaskEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ...

        setFragmentResultListener(KEY1) { _, bundle ->
            val resultText = bundle.getString("argtext", "")
            val position = bundle.getInt("position", -1)
            val addTask = bundle.getBoolean("addtask", false)

            if (resultText.isNotEmpty()) {
                if (addTask) {
                    // Add new task
                    val action = TaskEditFragmentDirections.actionTaskEditFragmentToNavigationHome(
                        argtext = resultText,
                        position = position,
                        addtask = true
                    )
                    findNavController().navigate(action)
                } else {
                    // Update existing task
                    val action = TaskEditFragmentDirections.actionTaskEditFragmentToNavigationHome(
                        argtext = resultText,
                        position = position,
                        addtask = false
                    )
                    findNavController().navigate(action)
                }
            }
        }

        binding.saveButton.setOnClickListener {
            val taskDescription = binding.taskAddDes.text.toString()

            val resultBundle = Bundle().apply {
                putString("argtext", taskDescription)
                putInt("position", arguments?.getInt("position", -1) ?: -1)
                putBoolean("addtask", arguments?.getBoolean("addtask") ?: false)
            }

            setFragmentResult(KEY1, resultBundle)


            findNavController().navigateUp()
        }
    }

    companion object {
        const val KEY1 = "KEY"
    }
}
