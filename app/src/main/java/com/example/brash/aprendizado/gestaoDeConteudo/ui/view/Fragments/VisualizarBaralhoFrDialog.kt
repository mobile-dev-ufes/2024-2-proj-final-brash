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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaPastaAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrMoverBaralhoBinding
import com.example.brash.databinding.GtcHomeFrVisualizarBaralhoBinding

class VisualizarBaralhoFrDialog() : DialogFragment() {

    private var _binding: GtcHomeFrVisualizarBaralhoBinding? = null
    private val binding get() = _binding!!

    lateinit var homeVM: HomeVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrVisualizarBaralhoBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setObservers(){

    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
