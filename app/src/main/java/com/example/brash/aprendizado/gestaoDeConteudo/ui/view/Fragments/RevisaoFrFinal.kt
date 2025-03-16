package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.RevisaoCartaoVM
import com.example.brash.databinding.GtcRevisaoFrFinalBinding

/**
 * Fragment responsible for displaying the final screen of the card review session.
 *
 * This fragment provides a button to return to the home activity after completing the card review session.
 *
 * @constructor Creates an instance of [RevisaoFrFinal].
 */
class RevisaoFrFinal : Fragment(R.layout.gtc_revisao_fr_final) {

    private var _binding : GtcRevisaoFrFinalBinding? = null
    private val binding get() = _binding!!

    private val revisaoCartaoVM: RevisaoCartaoVM by activityViewModels()

    /**
     * Called when the fragment's view is created.
     *
     * This method inflates the layout using ViewBinding and initializes the ViewModel. It also sets up
     * click listeners for buttons in the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate the fragment's view.
     * @param container The parent view that the fragment's UI will be attached to.
     * @param savedInstanceState A [Bundle] containing the fragment's previously saved state.
     * @return The root view of the fragment.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = GtcRevisaoFrFinalBinding.inflate(inflater, container, false)

        return binding.root
    }
    /**
     * Called after the fragment's view has been created.
     *
     * This method sets up observers and click listeners for the UI elements in the fragment.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A [Bundle] containing the fragment's previously saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setObservers()
        setOnClickListeners()
    }
    /**
     * Sets up observers for the LiveData variables in the ViewModel.
     *
     * This method is currently empty but can be used to observe data changes and update the UI accordingly.
     */
    private fun setObservers(){

    }
    /**
     * Sets up click listeners for the buttons in the fragment.
     *
     * This includes a button that finishes the activity and returns to the home screen.
     */
    private fun setOnClickListeners(){
        binding.RevisaoCartaoAcButtonVoltarHome.setOnClickListener {
            requireActivity().finish()
        }
    }
    /**
     * Cleans up the fragment's view by setting the binding to null.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}