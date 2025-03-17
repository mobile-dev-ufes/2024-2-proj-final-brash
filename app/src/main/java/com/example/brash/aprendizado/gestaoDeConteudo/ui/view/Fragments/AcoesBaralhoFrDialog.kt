package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarAnotacaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.RevisaoCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrAcoesBaralhoBinding
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication
import com.example.brash.utilsGeral.UtilsGeral

/**
 * A DialogFragment that provides actions related to a specific "Baralho" (Deck).
 * It allows the user to view, delete, or perform other actions on the deck such as viewing associated cards,
 * annotations, or reviewing the deck.
 * The deck to be acted upon is determined by the "baralhoEmFoco" property in the ViewModel.
 *
 * @constructor Creates an instance of `AcoesBaralhoFrDialog`.
 */
class AcoesBaralhoFrDialog() : DialogFragment() {

    // Binding object to interact with views in the layout using ViewBinding
    private var _binding: GtcHomeFrAcoesBaralhoBinding? = null
    private val binding get() = _binding!!

    // ViewModel for managing data related to "Baralho" (Deck)
    lateinit var homeVM: HomeVM

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
        _binding = GtcHomeFrAcoesBaralhoBinding.inflate(inflater, container, false)
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
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]
        appVM = (requireActivity().application as MyApplication).appSharedInformation

        setOnClickListeners()
    }

    /**
     * Placeholder method for setting observers for the ViewModel (currently unused).
     */
    private fun setObservers(){

    }

    /**
     * Sets up the click listeners for the "view", "view cards", "view annotations", "review", "move", and "delete" actions
     * related to the deck. Each option performs a specific action and might trigger a new dialog or activity.
     */
    private fun setOnClickListeners(){
        // When the "View Deck" option is clicked, dismiss the current dialog and show the "View Deck" dialog
        binding.HomeFrAcoesBaralhoTextViewVisualizarBaralho.setOnClickListener {
            dismiss()
            Log.d("HomeDialogs", "Tentando mostrar o diálogo visualizarBaralho")
            VisualizarBaralhoFrDialog().show(parentFragmentManager, "VisualizarBaralhoDialog")
        }
        // When the "View Cards" option is clicked, dismiss the current dialog, set the current deck in the shared ViewModel,
        // and navigate to the ListarCartaoAC activity to view the associated cards
        binding.HomeFrAcoesBaralhoTextViewVisualizarCartoes.setOnClickListener {
            dismiss()
            homeVM.baralhoEmFoco.value?.let {
                appVM.setBaralhoEmAC(it)
            } ?: run {
                //Toast.makeText(context, "Não foi possível carregar o baralho para cartão.", Toast.LENGTH_SHORT).show()
            }
            intentToListarCartaoActivity()
            //Toast.makeText(requireContext(), "Visualizar Cartões", Toast.LENGTH_SHORT).show()
        }
        // When the "View Annotations" option is clicked, dismiss the current dialog, set the current deck in the shared ViewModel,
        // and navigate to the ListarAnotacaoAC activity to view the associated annotations
        binding.HomeFrAcoesBaralhoTextViewVisualizarAnotacoes.setOnClickListener {
            dismiss()
            homeVM.baralhoEmFoco.value?.let {
                appVM.setBaralhoEmAC(it)
            } ?: run {
                //Toast.makeText(context, "Não foi possível carregar o baralho para anotação.", Toast.LENGTH_SHORT).show()
            }
            intentToListarAnotacaoActivity()
        }
        // When the "Review Deck" option is clicked, dismiss the current dialog, set the current deck in the shared ViewModel,
        // and navigate to the RevisaoCartaoAC activity to review the cards in the deck
        binding.HomeFrAcoesBaralhoTextViewRevisarBaralho.setOnClickListener {
            dismiss()
            homeVM.baralhoEmFoco.value?.let {
                appVM.setBaralhoEmAC(it)
            } ?: run {
                //Toast.makeText(context, "REVISAOAC--->Não foi possível carregar o baralho para revisão.", Toast.LENGTH_SHORT).show()
            }
            intentToRevisaoCartaoActivity()
        }
        // When the "Move Deck" option is clicked, dismiss the current dialog and show the "Move Deck" dialog
        binding.HomeFrAcoesBaralhoTextViewMoverBaralho.setOnClickListener {
            dismiss()
            //Toast.makeText(requireContext(), "Mover Baralho", Toast.LENGTH_SHORT).show()
            if (!activity?.isFinishing!! && !activity?.isDestroyed!!) {
                Log.d("ListaPastaAdapter", "Tentando mostrar o diálogo")
                MoverBaralhoFrDialog().show(parentFragmentManager, "MoverBaralhoDialog")
            }
        }
        // When the "Delete Deck" option is clicked, show a confirmation dialog before deleting the deck
        binding.HomeFrAcoesBaralhoTextViewExcluirBaralho.setOnClickListener {
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir esse Baralho??",{
                homeVM.baralhoEmFoco.value?.let { baralho ->
                    homeVM.excluirBaralho(baralho){
                        dismiss()
                    }
                } ?: run {
                    //Toast.makeText(context, "Nenhum Baralho selecionada", Toast.LENGTH_SHORT).show()
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
     * Starts the RevisaoCartaoAC activity to review the cards in the deck.
     */
    private fun intentToRevisaoCartaoActivity(){
        val intent = Intent(requireContext(), RevisaoCartaoAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent) // Start the activity
    }

    /**
     * Starts the ListarCartaoAC activity to view the associated cards of the deck.
     */
    private fun intentToListarCartaoActivity(){
        val intent = Intent(requireContext(), ListarCartaoAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent) // Start the activity
    }

    /**
     * Starts the ListarAnotacaoAC activity to view the associated annotations of the deck.
     */
    private fun intentToListarAnotacaoActivity(){
        val intent = Intent(requireContext(), ListarAnotacaoAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent) // Start the activity
    }
}
