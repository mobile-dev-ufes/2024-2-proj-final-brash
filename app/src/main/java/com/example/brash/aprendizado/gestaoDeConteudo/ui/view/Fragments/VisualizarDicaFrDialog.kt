package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarDicaVM
import com.example.brash.databinding.GtcListarDicaFrVisualizarDicaBinding

/**
 * DialogFragment that allows users to view and edit a specific "dica" (tip).
 *
 * This fragment displays the details of a "dica" and provides an interface to edit its text.
 * It observes and interacts with the [ListarDicaVM] ViewModel to manage the data related to dicas.
 *
 * @constructor Creates an instance of [VisualizarDicaFrDialog].
 */
class VisualizarDicaFrDialog() : DialogFragment() {

    private var _binding: GtcListarDicaFrVisualizarDicaBinding? = null
    private val binding get() = _binding!!

    private lateinit var listarDicaVM: ListarDicaVM
    /**
     * Called when the fragment's view is created.
     *
     * This method inflates the layout using ViewBinding and initializes the ViewModel.
     * It also populates the UI with data if available from the ViewModel.
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
        _binding = GtcListarDicaFrVisualizarDicaBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * Called after the view has been created.
     *
     * This method is used to configure the UI elements and populate them with data from the ViewModel,
     * if available. It also sets up click listeners for the confirm and cancel buttons.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A [Bundle] containing the fragment's previously saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarDicaVM = ViewModelProvider(requireActivity())[ListarDicaVM::class.java]
        //Log.d("HomeDialogs", "homeVM iniciado")

        //homeVM.baralhoEmFoco.value?.let {
            //binding.HomeFrVisualizarBaralhoInputTitulo.setText(it.nome)
            //binding.HomeFrVisualizarBaralhoInputDescricao.setText(it.descricao)
            //binding.HomeFrVisualizarBaralhoInputCartoesNovos.setText(String.format(it.cartoesNovosPorDia.toString()))
        //}
        //binding.HomeFrVisualizarBaralhoInputDescricao.setText("123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B")

        listarDicaVM.dicaEmFoco.value?.let {
            // Se o valor não for null, preenche os campos
            binding.ListarDicaFrVisualizarDicaInputTexto.setText(it.texto)
            //binding.Listar HomeFrVisualizarBaralhoInputDescricao.setText(it.descricao)
            //binding.HomeFrVisualizarBaralhoInputCartoesNovos.setText(String.format(it.cartoesNovosPorDia.toString()))
        } ?: run {
            // Se o valor for null, exibe uma mensagem de erro
            //Toast.makeText(requireContext(), "Erro: Dica não encontrado em VisualizarDica!", Toast.LENGTH_SHORT).show()
        }
        // Configurar os observadores para LiveData
        setObservers()
        setOnClickListeners()

        Log.d("HomeDialogs", "VisualizarDica iniciado")
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
        binding.ListarDicaFrVisualizarDicaButtonCancelar.setOnClickListener{
            dismiss()
        }
        binding.ListarDicaFrVisualizarDicaButtonConfirmar.setOnClickListener{
            val hintText = binding.ListarDicaFrVisualizarDicaInputTexto.text.toString()
            listarDicaVM.dicaEmFoco.value?.let { cartao ->
                listarDicaVM.editarDica(cartao, hintText) {
                    dismiss()
                    //Toast.makeText(requireContext(), "Dica Editada", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                //Toast.makeText(requireContext(), "Nenhum Dica selecionado", Toast.LENGTH_SHORT).show()
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
