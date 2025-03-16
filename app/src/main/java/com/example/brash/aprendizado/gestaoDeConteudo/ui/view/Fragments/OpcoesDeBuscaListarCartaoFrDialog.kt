package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaListarCartao
import com.example.brash.databinding.GtcListarCartaoFrOpcoesDeBuscaBinding

/**
 * A DialogFragment that allows the user to configure search options for listing flashcards.
 * It includes a spinner to filter the cards and buttons to either apply or cancel the settings.
 *
 * @constructor Creates an instance of `OpcoesDeBuscaListarCartaoFrDialog`.
 */
class OpcoesDeBuscaListarCartaoFrDialog: DialogFragment() {

    private var _binding: GtcListarCartaoFrOpcoesDeBuscaBinding? = null
    private val binding get() = _binding!!

    private lateinit var listarCartaoVM: ListarCartaoVM

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
        _binding = GtcListarCartaoFrOpcoesDeBuscaBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * Sets up the ViewModel, spinners, observers, and click listeners for the dialog.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A Bundle containing data saved during a previous instance of the fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HomeDialogs", "OPCOES DE BUSCA onViewCreated Inicio")

        listarCartaoVM = ViewModelProvider(requireActivity())[ListarCartaoVM::class.java]

        setupSpinners()
        setObservers()
        setOnClickListeners()

        Log.d("HomeDialogs", "OPCOES DE BUSCA onViewCreated Final")
    }
    /**
     * Configures the dialog's window size when it starts.
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    /**
     * Sets up the spinner for selecting the filter option by fetching the options from resources.
     */
    private fun setupSpinners(){

        val filtrarAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            com.example.brash.R.array.nuc_listar_cartao_spinner_filtrar,
            android.R.layout.simple_spinner_item
        )
        filtrarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ListarCartaoFrOpcoesDeBuscaSpinnerFiltrar.adapter = filtrarAdapter
    }
    /**
     * Sets up the observer to update the spinner selection based on the current search options.
     */
    private fun setObservers() {
        listarCartaoVM.opcoesDeBusca.observe(viewLifecycleOwner, Observer {
            binding.ListarCartaoFrOpcoesDeBuscaSpinnerFiltrar.setSelection(it.filtrar.toIndex())
            //TODO:: requisitar sort se houver texto na busca?
        })
    }
    /**
     * Sets the click listeners for the "Apply" and "Cancel" buttons in the dialog.
     */
    private fun setOnClickListeners() {

        binding.HomeFrOpcoesDeBuscaButtonCancelar.setOnClickListener {
            dismiss()
            //Toast.makeText(requireContext(), "Cancelar Configurações de Busca", Toast.LENGTH_SHORT).show()
        }

        binding.HomeFrOpcoesDeBuscaButtonAplicar.setOnClickListener {
            listarCartaoVM.updateOpcoesDeBusca(
                binding.ListarCartaoFrOpcoesDeBuscaSpinnerFiltrar.selectedItemPosition.toFiltroDeBuscaListarCartao()
            )
            dismiss()
            //Toast.makeText(requireContext(), "Aplicar Configurações de Busca", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Extension function to convert a `FiltroDeBuscaListarCartao` to its index representation.
     *
     * @return The index of the `FiltroDeBuscaListarCartao` entry in the list.
     */
    private fun FiltroDeBuscaListarCartao.toIndex(): Int {
        return FiltroDeBuscaListarCartao.entries.indexOf(this)
    }
    /**
     * Extension function to convert an integer index to its corresponding `FiltroDeBuscaListarCartao` entry.
     *
     * @return The `FiltroDeBuscaListarCartao` entry corresponding to the index.
     */
    private fun Int.toFiltroDeBuscaListarCartao(): FiltroDeBuscaListarCartao {
        return FiltroDeBuscaListarCartao.entries.toTypedArray().getOrElse(this) { FiltroDeBuscaListarCartao.PERGUNTA }
    }
}