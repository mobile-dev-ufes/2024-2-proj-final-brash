package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarAnotacaoVM
import com.example.brash.databinding.GtcListarAnotacaoFrAcoesAnotacaoBinding
import com.example.brash.utilsGeral.UtilsGeral

/**
 * A DialogFragment that provides actions related to a specific "Anotação" (Note).
 * It allows the user to view, delete, or perform other actions on the note.
 * The note to be acted upon is determined by the "anotacaoEmFoco" property in the ViewModel.
 *
 * @constructor Creates an instance of `AcoesAnotacaoFrDialog`.
 */
class AcoesAnotacaoFrDialog() : DialogFragment() {

    // Binding object to interact with views in the layout using ViewBinding
    private var _binding: GtcListarAnotacaoFrAcoesAnotacaoBinding? = null
    private val binding get() = _binding!!

    // ViewModel for managing data related to "Anotação" (Note)
    private lateinit var listarAnotacaoVM: ListarAnotacaoVM

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
        _binding = GtcListarAnotacaoFrAcoesAnotacaoBinding.inflate(inflater, container, false)
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

        // Initialize the ViewModel
        listarAnotacaoVM = ViewModelProvider(requireActivity())[ListarAnotacaoVM::class.java]

        setOnClickListeners()

    }

    /**
     * Placeholder method for setting observers for the ViewModel (currently unused).
     */
    private fun setObservers(){

    }

    /**
     * Sets up the click listeners for the "view" and "delete" actions related to the note.
     * Each option performs a specific action, such as viewing or deleting the note.
     */
    private fun setOnClickListeners(){

        // When the "View Note" option is clicked, dismiss the current dialog and show the "View Note" dialog
        binding.ListarAnotacaoFrAcoesAnotacaoTextViewVisualizarAnotacao.setOnClickListener {
            dismiss()
            if (!activity?.isFinishing!! && !activity?.isDestroyed!!) {
                Log.d("HomeDialogs", "Tentando mostrar o diálogo visualizarBaralho")
                VisualizarAnotacaoFrDialog().show(parentFragmentManager, "VisualizarBaralhoDialog")
            }
        }
        // When the "Delete Note" option is clicked, show a confirmation dialog before deleting the note
        binding.ListarAnotacaoFrAcoesAnotacaoTextViewExcluirAnotacao.setOnClickListener{
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir essa Anotação??",{
                // If a note is selected, delete it using the ViewModel
                listarAnotacaoVM.anotacaoEmFoco.value?.let { it ->
                    listarAnotacaoVM.excluirAnotacao(it){
                        dismiss()
                    }
                } ?: run {
                    //Toast.makeText(context, "Nenhuma Anotacao selecionada", Toast.LENGTH_SHORT).show()
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

    /**
     * Starts the ListarCartaoAC activity to view the associated cards of the note.
     */
    private fun intentToListarCartaoActivity(){
        val intent = Intent(requireContext(), ListarCartaoAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent) // Start the activity
    }
}
