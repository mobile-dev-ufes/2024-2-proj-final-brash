package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrVisualizarBaralhoBinding

/**
 * DialogFragment that allows users to view and edit the details of a "baralho" (deck).
 *
 * This fragment displays the details of a deck and provides options to edit it.
 * It interacts with the [HomeVM] ViewModel to manage data related to the deck.
 *
 * @constructor Creates an instance of [VisualizarBaralhoFrDialog].
 */
class VisualizarBaralhoFrDialog() : DialogFragment() {

    private var _binding: GtcHomeFrVisualizarBaralhoBinding? = null
    private val binding get() = _binding!!

    lateinit var homeVM: HomeVM
    /**
     * Called when the fragment's view is created.
     *
     * This method inflates the layout using ViewBinding and initializes the ViewModel.
     * It also populates the UI with data from the ViewModel if available.
     *
     * @param inflater The LayoutInflater object that can be used to inflate the fragment's view.
     * @param container The parent view that the fragment's UI will be attached to.
     * @param savedInstanceState A [Bundle] containing the fragment's previously saved state.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrVisualizarBaralhoBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * Called after the fragment's view has been created.
     *
     * This method configures the UI elements by populating them with data from the ViewModel,
     * if available. It also sets up click listeners for the buttons.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A [Bundle] containing the fragment's previously saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]
        Log.d("HomeDialogs", "homeVM iniciado")

        homeVM.baralhoEmFoco.value?.let {
            // Se o valor não for null, preenche os campos
            binding.HomeFrVisualizarBaralhoInputTitulo.setText(it.nome)
            binding.HomeFrVisualizarBaralhoInputDescricao.setText(it.descricao)
            binding.HomeFrVisualizarBaralhoInputCartoesNovos.setText(String.format(it.cartoesNovosPorDia.toString()))
        } ?: run {
            // Se o valor for null, exibe uma mensagem de erro
            //Toast.makeText(requireContext(), "Erro: Baralho não encontrado em VisualizarBaralhoHome!", Toast.LENGTH_SHORT).show()
        }
        //binding.HomeFrVisualizarBaralhoInputDescricao.setText("123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B")


        // Configurar os observadores para LiveData
        setObservers()
        setOnClickListeners()

        Log.d("HomeDialogs", "VisualizarBaralho iniciado")
    }
    /**
     * Called when the fragment is started.
     *
     * This method adjusts the layout of the dialog window to make it fullscreen with wrap content.
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    /**
     * Configures the observers for the ViewModel.
     * Currently, no observers are set, but this can be extended for observing LiveData.
     */
    private fun setObservers(){

    }
    /**
     * Sets up click listeners for the buttons in the fragment's UI.
     *
     * This includes handling the cancel and confirm buttons.
     */
    private fun setOnClickListeners(){

        binding.HomeFrVisualizarBaralhoButtonCancelar.setOnClickListener {
            dismiss()
        }
        binding.HomeFrVisualizarBaralhoButtonConfirmar.setOnClickListener {
            val deckName = binding.HomeFrVisualizarBaralhoInputTitulo.text.toString()
            val deckDescription = binding.HomeFrVisualizarBaralhoInputDescricao.text.toString()
            val deckCardsPerDay = binding.HomeFrVisualizarBaralhoInputCartoesNovos.text.toString().ifEmpty { "0" }.toInt()
            val deckPublic = binding.HomeFrVisualizarBaralhoCheckBoxPublico.isChecked
            homeVM.baralhoEmFoco.value?.let { baralho ->
                homeVM.editarBaralho(baralho, deckName, deckDescription, deckCardsPerDay, deckPublic) {
                    dismiss()
                    //Toast.makeText(requireContext(), "Baralho Editado", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                //Toast.makeText(requireContext(), "Nenhum baralho selecionado", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

    }
    /**
     * Called when the fragment's view is destroyed.
     *
     * This method is used to clean up resources and avoid memory leaks by setting the binding to null.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
