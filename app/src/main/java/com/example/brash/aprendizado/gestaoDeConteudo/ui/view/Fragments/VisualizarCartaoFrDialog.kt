package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.databinding.GtcListarCartaoFrVisualizarCartaoBinding

/**
 * DialogFragment that allows users to view and edit a specific "cartão" (card).
 *
 * This fragment displays the details of a "cartão" and provides an interface to edit its question and answer.
 * It interacts with the [ListarCartaoVM] ViewModel to manage the data related to cards.
 *
 * @constructor Creates an instance of [VisualizarCartaoFrDialog].
 */
class VisualizarCartaoFrDialog() : DialogFragment() {

    private var _binding: GtcListarCartaoFrVisualizarCartaoBinding? = null
    private val binding get() = _binding!!

    private lateinit var listarCartaoVM: ListarCartaoVM
    /**
     * DialogFragment that allows users to view and edit a specific "cartão" (card).
     *
     * This fragment displays the details of a "cartão" and provides an interface to edit its question and answer.
     * It interacts with the [ListarCartaoVM] ViewModel to manage the data related to cards.
     *
     * @constructor Creates an instance of [VisualizarCartaoFrDialog].
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarCartaoFrVisualizarCartaoBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * Called after the fragment's view has been created.
     *
     * This method configures the UI elements by populating them with data from the ViewModel,
     * if available. It also sets up click listeners for the confirm and cancel buttons.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A [Bundle] containing the fragment's previously saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarCartaoVM = ViewModelProvider(requireActivity())[ListarCartaoVM::class.java]
        //Log.d("HomeDialogs", "homeVM iniciado")

        //homeVM.baralhoEmFoco.value?.let {
            //binding.HomeFrVisualizarBaralhoInputTitulo.setText(it.nome)
            //binding.HomeFrVisualizarBaralhoInputDescricao.setText(it.descricao)
            //binding.HomeFrVisualizarBaralhoInputCartoesNovos.setText(String.format(it.cartoesNovosPorDia.toString()))
        //}
        //binding.HomeFrVisualizarBaralhoInputDescricao.setText("123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B")

        listarCartaoVM.cartaoEmFoco.value?.let {
            // Se o valor não for null, preenche os campos
            binding.ListarCartaoFrVisualizarCartaoInputPergunta.setText(it.pergunta)
            binding.ListarCartaoFrVisualizarCartaoInputResposta.setText(it.resposta)
            //binding.Listar HomeFrVisualizarBaralhoInputDescricao.setText(it.descricao)
            //binding.HomeFrVisualizarBaralhoInputCartoesNovos.setText(String.format(it.cartoesNovosPorDia.toString()))
        } ?: run {
            // Se o valor for null, exibe uma mensagem de erro
            //Toast.makeText(requireContext(), "Erro: Baralho não encontrado em VisualizarBaralhoHome!", Toast.LENGTH_SHORT).show()
        }
        // Configurar os observadores para LiveData
        setObservers()
        setOnClickListeners()

        Log.d("HomeDialogs", "VisualizarBaralho iniciado")
    }
    /**
     * Called when the fragment is started.
     *
     * This method adjusts the dialog window layout to make it fullscreen with wrap content.
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    /**
     * Configures the observers for the ViewModel.
     * Currently, no observers are set, but it can be extended for observing LiveData.
     */
    private fun setObservers(){

    }
    /**
     * Sets up click listeners for the buttons in the fragment's UI.
     *
     * This includes handling the cancel and confirm buttons.
     */
    private fun setOnClickListeners(){
        binding.ListarCartaoFrVisualizarCartaoButtonCancelar.setOnClickListener{
            dismiss()
        }
        binding.ListarCartaoFrVisualizarCartaoButtonConfirmar.setOnClickListener{
            val cardQuestion = binding.ListarCartaoFrVisualizarCartaoInputPergunta.text.toString()
            val cardAnswer = binding.ListarCartaoFrVisualizarCartaoInputResposta.text.toString()
            //Toast.makeText(requireContext(), "Cartão a editar", Toast.LENGTH_SHORT).show()
            listarCartaoVM.cartaoEmFoco.value?.let { cartao ->
                listarCartaoVM.editarCartao(cartao, cardQuestion, cardAnswer) {
                    dismiss()
                    //Toast.makeText(requireContext(), "Cartão Editado", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                //Toast.makeText(requireContext(), "Nenhum Cartão selecionado", Toast.LENGTH_SHORT).show()
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
