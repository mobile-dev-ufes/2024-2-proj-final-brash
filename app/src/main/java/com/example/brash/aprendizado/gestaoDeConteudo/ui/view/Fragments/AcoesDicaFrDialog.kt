package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarDicaVM
import com.example.brash.databinding.GtcListarDicaFrAcoesDicaBinding
import com.example.brash.utilsGeral.UtilsGeral

/**
 * A DialogFragment that provides actions related to a specific "Dica" (Tip).
 * It allows the user to view or delete the tip. When the delete option is selected, a confirmation dialog is shown.
 * The tip to be acted upon is determined by the "dicaEmFoco" property in the ViewModel.
 *
 * @constructor Creates an instance of `AcoesDicaFrDialog`.
 */
class AcoesDicaFrDialog() : DialogFragment() {

    // Binding object for accessing views from the layout using ViewBinding
    private var _binding: GtcListarDicaFrAcoesDicaBinding? = null
    private val binding get() = _binding!!

    // ViewModel for managing data related to "Dica" (Tip)
    private lateinit var listarDicaVM: ListarDicaVM

    /**
     * Inflates the fragment's layout using ViewBinding and returns the root view.
     *
     * @param inflater The LayoutInflater used to inflate the layout.
     * @param container The container to attach the view to.
     * @param savedInstanceState The saved instance state.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        _binding = GtcListarDicaFrAcoesDicaBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called after the view has been created. Initializes the ViewModel and sets up click listeners.
     *
     * @param view The root view of the fragment's layout.
     * @param savedInstanceState The saved instance state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel for managing tips
        listarDicaVM = ViewModelProvider(requireActivity())[ListarDicaVM::class.java]

        setOnClickListeners()

    }

    /**
     * Placeholder for setting up observers for the ViewModel (currently unused).
     */
    private fun setObservers(){

    }

    /**
     * Sets up the click listeners for the "view" and "delete" actions related to the tip.
     * The delete action triggers a confirmation dialog before proceeding.
     */
    private fun setOnClickListeners(){

        // When the "View Tip" option is clicked, dismiss the current dialog and show the "View Tip" dialog
        binding.ListarDicaFrAcoesDicaTextViewVisualizarDica.setOnClickListener {
            dismiss()
            // Check if the activity is still running, then show the "View Tip" dialog
            if (!activity?.isFinishing!! && !activity?.isDestroyed!!) {
                Log.d("HomeDialogs", "Tentando mostrar o diálogo visualizarDica")
                VisualizarDicaFrDialog().show(parentFragmentManager, "VisualizarDicaDialog")
            }
        }
        // When the "Delete Tip" option is clicked, show a confirmation dialog
        binding.ListarDicaFrAcoesDicaTextViewExcluirDica.setOnClickListener {
            // Show a confirmation dialog before deleting the tip
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir essa Dica??",{
                // If a tip is selected, delete it using the ViewModel
                listarDicaVM.dicaEmFoco.value?.let { dica ->
                    listarDicaVM.excluirDica(dica){
                        dismiss()
                    }
                } ?: run {
                    //Toast.makeText(context, "Nenhuma Dica selecionada", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            })
        }
    }

    /**
     * Cleans up the binding to avoid memory leaks when the view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
