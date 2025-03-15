package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarAnotacaoVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarBaralhoPublicoVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.databinding.GtcHomeFrMoverBaralhoBinding
import com.example.brash.databinding.GtcHomeFrVisualizarBaralhoBinding
import com.example.brash.databinding.GtcListarAnotacaoFrVisualizarAnotacaoBinding
import com.example.brash.databinding.GtcListarBaralhoPublicoAcBinding
import com.example.brash.databinding.GtcListarBaralhoPublicoFrVisualizarBaralhoBinding
import com.example.brash.databinding.GtcListarCartaoFrVisualizarCartaoBinding

class VisualizarAnotacaoFrDialog() : DialogFragment() {

    private var _binding: GtcListarAnotacaoFrVisualizarAnotacaoBinding? = null
    private val binding get() = _binding!!

    private lateinit var listarAnotacaoVM: ListarAnotacaoVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarAnotacaoFrVisualizarAnotacaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarAnotacaoVM = ViewModelProvider(requireActivity())[ListarAnotacaoVM::class.java]
        //Log.d("HomeDialogs", "homeVM iniciado")

        //homeVM.baralhoEmFoco.value?.let {
            //binding.HomeFrVisualizarBaralhoInputTitulo.setText(it.nome)
            //binding.HomeFrVisualizarBaralhoInputDescricao.setText(it.descricao)
            //binding.HomeFrVisualizarBaralhoInputCartoesNovos.setText(String.format(it.cartoesNovosPorDia.toString()))
        //}
        //binding.HomeFrVisualizarBaralhoInputDescricao.setText("123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B")

        listarAnotacaoVM.anotacaoEmFoco.value?.let {
            // Se o valor não for null, preenche os campos
            binding.ListarAnotacaoFrVisualizarAnotacaoInputNome.setText(it.nome)
            binding.ListarAnotacaoFrVisualizarAnotacaoInputTexto.setText(it.texto)
            //binding.Listar HomeFrVisualizarBaralhoInputDescricao.setText(it.descricao)
            //binding.HomeFrVisualizarBaralhoInputCartoesNovos.setText(String.format(it.cartoesNovosPorDia.toString()))
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
        binding.ListarAnotacaoFrVisualizarAnotacaoButtonCancelar.setOnClickListener{
            dismiss()
        }
        binding.ListarAnotacaoFrVisualizarAnotacaoButtonConfirmar.setOnClickListener{
            //TODO:: Lógica de importar/copiar baralho
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
