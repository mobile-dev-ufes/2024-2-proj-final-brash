package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrRenomearPastaBinding

/**
 * A DialogFragment that allows the user to rename a folder (Pasta).
 * It provides a UI to input a new name and confirm the action.
 * This dialog interacts with the `HomeVM` ViewModel to update the folder's name.
 *
 * @constructor Creates an instance of `RenomearPastaFrDialog`.
 */
class RenomearPastaFrDialog() : DialogFragment() {

    private var _binding: GtcHomeFrRenomearPastaBinding? = null
    private val binding get() = _binding!!
    lateinit var homeVM: HomeVM

    /**
     * Inflates the layout for the fragment and initializes the view binding.
     *
     * @param inflater The LayoutInflater object used to inflate the layout.
     * @param container The parent view that the fragment's UI will be attached to.
     * @param savedInstanceState A Bundle containing data saved during a previous instance of the fragment.
     * @return The root view of the fragment's layout.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrRenomearPastaBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * Sets up the initial values for the fragment's UI and binds the `HomeVM` ViewModel.
     * Also sets up observers and click listeners for the buttons.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A Bundle containing data saved during a previous instance of the fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]
        Log.d("HomeDialogs", "homeVM iniciado")

        homeVM.pastaEmFoco.value?.let {
            binding.HomeFrRenomearPastaInput.setText(it.nome)
        }

        // Configurar os observadores para LiveData
        setObservers()
        setOnClickListeners()

        Log.d("HomeDialogs", "VisualizarBaralho iniciado")
    }
    /**
     * Configures the dialog's window size when it starts.
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    /**
     * Sets up observers for LiveData. Currently left empty, can be extended for future use.
     */
    private fun setObservers(){

    }
    /**
     * Sets the click listeners for the buttons in the layout.
     */
    private fun setOnClickListeners(){
        binding.HomeFrRenomearPastaButtonCancelar.setOnClickListener {
            dismiss()
        }
        binding.HomeFrRenomearPastaButtonConfirmar.setOnClickListener {

            //TODO:: Fazer verificação de se eh nome único, se for pode confirmar
            homeVM.pastaEmFoco.value?.let { pasta ->
                val novoNome = binding.HomeFrRenomearPastaInput.text.toString()
                homeVM.editarPasta(pasta, novoNome){
                    //Toast.makeText(requireContext(), "Editar Pasta", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            } ?: run {
                //Toast.makeText(requireContext(), "Nenhuma pasta selecionada", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

    }
    /**
     * Cleans up the binding reference to avoid memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
