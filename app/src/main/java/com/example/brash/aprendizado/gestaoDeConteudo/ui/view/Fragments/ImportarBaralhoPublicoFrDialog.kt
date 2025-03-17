package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarBaralhoPublicoVM
import com.example.brash.databinding.GtcListarBaralhoPublicoFrImportarBaralhoBinding

/**
 * A DialogFragment that allows the user to import a public deck ("Baralho Público").
 * It provides an interface to view the original deck's name and set a new name for the imported deck.
 * The dialog includes buttons for "Import" and "Cancel" actions.
 *
 * @constructor Creates an instance of `ImportarBaralhoPublicoFrDialog`.
 */
class ImportarBaralhoPublicoFrDialog() : DialogFragment() {

    private var _binding: GtcListarBaralhoPublicoFrImportarBaralhoBinding? = null
    private val binding get() = _binding!!

    private lateinit var listarBaralhoPublicoVM: ListarBaralhoPublicoVM

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
        _binding = GtcListarBaralhoPublicoFrImportarBaralhoBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Sets up the ViewModel, populates the input fields with the deck's name,
     * and configures the observers and click listeners for the dialog.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A Bundle containing data saved during a previous instance of the fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarBaralhoPublicoVM = ViewModelProvider(requireActivity())[ListarBaralhoPublicoVM::class.java]
        //Log.d("HomeDialogs", "homeVM iniciado")

        listarBaralhoPublicoVM.baralhoPublicoEmFoco.value?.let {
            // Se o valor não for null, preenche os campos
            binding.ListarBaralhoPublicoFrImportarBaralhoInputNovoNome.setText(it.nomeBaralho)
            binding.ListarBaralhoPublicoFrImportarBaralhoTextViewNomeOriginal.text = it.nomeBaralho
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
     * Adjusts the dialog size when the fragment starts.
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    /**
     * Set up the observers for LiveData (currently empty).
     * This function can be expanded if needed to observe changes in LiveData.
     */
    private fun setObservers(){

    }
    /**
     * Sets the click listeners for the "Cancel" and "Import" buttons.
     * The "Import" button triggers the deck import process.
     */
    private fun setOnClickListeners(){
        binding.ListarBaralhoPublicoFrImportarBaralhoButtonCancelar.setOnClickListener{
            dismiss()
        }
        binding.ListarBaralhoPublicoFrImportarBaralhoButtonImportar.setOnClickListener{
            //TODO:: Lógica de importar/copiar baralho
            listarBaralhoPublicoVM.baralhoPublicoEmFoco.value?.let { it1 ->
                val novoNome = binding.ListarBaralhoPublicoFrImportarBaralhoInputNovoNome.text.toString()
                listarBaralhoPublicoVM.importarBaralhoPublico(it1, novoNome) {
                    //Toast.makeText(requireContext(), "Baralho público importado", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            } ?: run {
                //Toast.makeText(requireContext(), "Nenhum baralho selecionado", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

    }
    /**
     * Cleans up the view binding to prevent memory leaks when the view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
