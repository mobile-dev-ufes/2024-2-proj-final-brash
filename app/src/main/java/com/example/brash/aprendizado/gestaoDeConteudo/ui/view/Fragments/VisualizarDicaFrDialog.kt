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
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarBaralhoPublicoVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarDicaVM
import com.example.brash.databinding.GtcHomeFrMoverBaralhoBinding
import com.example.brash.databinding.GtcHomeFrVisualizarBaralhoBinding
import com.example.brash.databinding.GtcListarBaralhoPublicoAcBinding
import com.example.brash.databinding.GtcListarBaralhoPublicoFrVisualizarBaralhoBinding
import com.example.brash.databinding.GtcListarCartaoFrVisualizarCartaoBinding
import com.example.brash.databinding.GtcListarDicaFrVisualizarDicaBinding

class VisualizarDicaFrDialog() : DialogFragment() {

    private var _binding: GtcListarDicaFrVisualizarDicaBinding? = null
    private val binding get() = _binding!!

    private lateinit var listarDicaVM: ListarDicaVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarDicaFrVisualizarDicaBinding.inflate(inflater, container, false)
        return binding.root
    }

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
            Toast.makeText(requireContext(), "Erro: Dica não encontrado em VisualizarDica!", Toast.LENGTH_SHORT).show()
        }
        // Configurar os observadores para LiveData
        setObservers()
        setOnClickListeners()

        Log.d("HomeDialogs", "VisualizarDica iniciado")
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){
        binding.ListarDicaFrVisualizarDicaButtonCancelar.setOnClickListener{
            dismiss()
        }
        binding.ListarDicaFrVisualizarDicaButtonConfirmar.setOnClickListener{
            val hintText = binding.ListarDicaFrVisualizarDicaInputTexto.text.toString()
            listarDicaVM.dicaEmFoco.value?.let { cartao ->
                listarDicaVM.editarDica(cartao, hintText) {
                    dismiss()
                    Toast.makeText(requireContext(), "Dica Editada", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(requireContext(), "Nenhum Dica selecionado", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
