package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarDicaAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.databinding.GtcListarCartaoFrAcoesCartaoBinding
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication
import com.example.brash.utilsGeral.UtilsGeral

/**
 * A DialogFragment that provides actions related to a specific "Cartão" (Card).
 * It allows the user to view, delete, or view associated tips of the card. When the delete option is selected, a confirmation dialog is shown.
 * The card to be acted upon is determined by the "cartaoEmFoco" property in the ViewModel.
 *
 * @constructor Creates an instance of `AcoesCartaoFrDialog`.
 */
class AcoesCartaoFrDialog() : DialogFragment() {

    // Binding object to interact with views in the layout using ViewBinding
    private var _binding: GtcListarCartaoFrAcoesCartaoBinding? = null
    private val binding get() = _binding!!


    // ViewModel for managing data related to "Cartão" (Card)
    private lateinit var listarCartaoVM: ListarCartaoVM

    // ViewModel for managing application-wide shared data
    private lateinit var appVM: AppVM

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
        _binding = GtcListarCartaoFrAcoesCartaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called after the view has been created. Initializes the ViewModels and sets up click listeners.
     *
     * @param view The root view of the fragment's layout.
     * @param savedInstanceState The saved instance state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModels
        listarCartaoVM = ViewModelProvider(requireActivity())[ListarCartaoVM::class.java]
        appVM = (requireActivity().application as MyApplication).appSharedInformation

        setOnClickListeners()
    }

    /**
     * Placeholder method for setting observers for the ViewModel (currently unused).
     */
    private fun setObservers(){

    }

    /**
     * Sets up the click listeners for the "view", "view tips", and "delete" actions related to the card.
     * The delete action triggers a confirmation dialog before proceeding.
     */
    private fun setOnClickListeners(){

        // When the "View Card" option is clicked, dismiss the current dialog and show the "View Card" dialog
        binding.ListarCartaoFrAcoesCartaoTextViewVisualizarCartao.setOnClickListener {
            dismiss() // Dismiss the current dialog
            // Check if the activity is still running, then show the "View Card" dialog
            if (!activity?.isFinishing!! && !activity?.isDestroyed!!) {
                Log.d("HomeDialogs", "Tentando mostrar o diálogo visualizarBaralho")
                VisualizarCartaoFrDialog().show(parentFragmentManager, "VisualizarBaralhoDialog")
            }
        }
        // When the "View Tips" option is clicked, dismiss the current dialog, set the current card in the shared ViewModel,
        // and navigate to the ListarDicaAC activity
        binding.ListarCartaoFrAcoesCartaoTextViewVisualizarDicas.setOnClickListener {
            dismiss()
            listarCartaoVM.cartaoEmFoco.value?.let {
                appVM.setCartaoEmAC(it)
            } ?: run {
                //Toast.makeText(context, "Não foi possível carregar o cartão para dica.", Toast.LENGTH_SHORT).show()
            }
            intentToListarDicaActivity()
            //Toast.makeText(requireContext(), "Visualizar Cartões", Toast.LENGTH_SHORT).show()
        }
        // When the "Delete Card" option is clicked, show a confirmation dialog before deleting the card
        binding.ListarCartaoFrAcoesCartaoTextViewExcluirCartao.setOnClickListener {
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir esse Cartão??",{
                listarCartaoVM.cartaoEmFoco.value?.let { cartao ->
                    listarCartaoVM.excluirCartao(cartao){
                        dismiss()
                    }
                } ?: run {
                    //Toast.makeText(context, "Nenhum Cartão selecionada", Toast.LENGTH_SHORT).show()
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
     * Starts the ListarDicaAC activity to view tips associated with the card.
     */
    private fun intentToListarDicaActivity(){
        val intent = Intent(requireContext(), ListarDicaAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent) // Start the activity
    }
}
