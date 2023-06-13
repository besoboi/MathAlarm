package com.example.mathalarm.presentation.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mathalarm.databinding.FragmentMathBinding
import com.example.mathalarm.presentation.ViewModelFactory
import com.example.mathalarm.presentation.adapters.AlarmApp
import javax.inject.Inject

class MathFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as AlarmApp).component
    }

    private lateinit var viewModel: MathViewModel

    private var optionsTextViews = mutableListOf<TextView>()

    private var _binding : FragmentMathBinding ? = null
    private val binding: FragmentMathBinding
        get() = _binding ?: throw RuntimeException("binding is null")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[MathViewModel::class.java]
        viewModel.startGame()
        getTextViewsOptions()
        setupClickListenersToOptions()
        observeViewModel()
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMathBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getTextViewsOptions() {
        with(binding) {
            optionsTextViews.add(tvOption1)
            optionsTextViews.add(tvOption2)
            optionsTextViews.add(tvOption3)
            optionsTextViews.add(tvOption4)
            optionsTextViews.add(tvOption5)
            optionsTextViews.add(tvOption6)
        }
    }

    private fun setupClickListenersToOptions() {
        for (textView in optionsTextViews) {
            textView.setOnClickListener {
                if (viewModel.chooseAnswer(textView.text.toString().toInt())) {
                    requireActivity().finish()
                }
            }
        }
    }

    private fun setupTextToOptions(answers: List<Int>) {
        for (i in answers.indices) {
            optionsTextViews[i].text = answers[i].toString()
        }
    }

    private fun observeViewModel(){
        viewModel.question.observe(viewLifecycleOwner) {
            with(binding) {
                tvSum.text = it.sum.toString()
                tvLeftNumber.text = it.visibleNumber.toString()
                setupTextToOptions(it.answers)
            }
        }
    }

    companion object {
        fun newInstance() = MathFragment()
    }
}