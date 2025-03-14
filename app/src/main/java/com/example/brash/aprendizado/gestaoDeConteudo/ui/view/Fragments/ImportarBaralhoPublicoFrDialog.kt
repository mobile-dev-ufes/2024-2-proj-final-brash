package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarBaralhoPublicoVM
import com.example.brash.databinding.GtcListarBaralhoPublicoFrImportarBaralhoBinding
import com.example.brash.databinding.GtcListarBaralhoPublicoFrVisualizarBaralhoBinding


class ImportarBaralhoPublicoFrDialog() : DialogFragment() {

    private var _binding: GtcListarBaralhoPublicoFrImportarBaralhoBinding? = null
    private val binding get() = _binding!!

    lateinit var listarBaralhoPublicoVM: ListarBaralhoPublicoVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarBaralhoPublicoFrImportarBaralhoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarBaralhoPublicoVM = ViewModelProvider(requireActivity()).get(ListarBaralhoPublicoVM::class.java)
        //Log.d("HomeDialogs", "homeVM iniciado")

        listarBaralhoPublicoVM.baralhoPublicoEmFoco.value?.let {
            // Se o valor não for null, preenche os campos
            binding.ListarBaralhoPublicoFrImportarBaralhoInputNovoNome.setText(it.nome)
            binding.ListarBaralhoPublicoFrImportarBaralhoTextViewNomeOriginal.text = it.nome
        } ?: run {
            // Se o valor for null, exibe uma mensagem de erro
            Toast.makeText(requireContext(), "Erro: Baralho não encontrado em VisualizarBaralhoHome!", Toast.LENGTH_SHORT).show()
        }
        // Configurar os observadores para LiveData
        setObservers()
        setOnClickListeners()

        Log.d("HomeDialogs", "VisualizarBaralho iniciado")
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){
        binding.ListarBaralhoPublicoFrImportarBaralhoButtonCancelar.setOnClickListener{
            dismiss()
        }
        binding.ListarBaralhoPublicoFrImportarBaralhoButtonImportar.setOnClickListener{
            //TODO:: Lógica de importar/copiar baralho
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
