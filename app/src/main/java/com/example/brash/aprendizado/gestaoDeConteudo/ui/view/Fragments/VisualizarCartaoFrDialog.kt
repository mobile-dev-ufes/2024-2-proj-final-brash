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
import com.example.brash.databinding.GtcHomeFrMoverBaralhoBinding
import com.example.brash.databinding.GtcHomeFrVisualizarBaralhoBinding
import com.example.brash.databinding.GtcListarBaralhoPublicoAcBinding
import com.example.brash.databinding.GtcListarBaralhoPublicoFrVisualizarBaralhoBinding
import com.example.brash.databinding.GtcListarCartaoFrVisualizarCartaoBinding

class VisualizarCartaoFrDialog() : DialogFragment() {

    private var _binding: GtcListarCartaoFrVisualizarCartaoBinding? = null
    private val binding get() = _binding!!

    private lateinit var listarCartaoVM: ListarCartaoVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarCartaoFrVisualizarCartaoBinding.inflate(inflater, container, false)
        return binding.root
    }

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
        binding.ListarCartaoFrVisualizarCartaoButtonCancelar.setOnClickListener{
            dismiss()
        }
        binding.ListarCartaoFrVisualizarCartaoButtonConfirmar.setOnClickListener{
            val cardQuestion = binding.ListarCartaoFrVisualizarCartaoInputPergunta.text.toString()
            val cardAnswer = binding.ListarCartaoFrVisualizarCartaoInputResposta.text.toString()
            listarCartaoVM.cartaoEmFoco.value?.let { cartao ->
                listarCartaoVM.editarCartao(cartao, cardQuestion, cardAnswer) {
                    dismiss()
                    Toast.makeText(requireContext(), "Cartão Editado", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(requireContext(), "Nenhum Cartão selecionado", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
