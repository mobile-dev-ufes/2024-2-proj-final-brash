package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaListarCartao
import com.example.brash.databinding.GtcHomeFrOpcoesDeBuscaBinding
import com.example.brash.databinding.GtcListarCartaoFrOpcoesDeBuscaBinding


class OpcoesDeBuscaListarCartaoFrDialog: DialogFragment() {

    private var _binding: GtcListarCartaoFrOpcoesDeBuscaBinding? = null
    private val binding get() = _binding!!

    lateinit var listarCartaoVM: ListarCartaoVM


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarCartaoFrOpcoesDeBuscaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HomeDialogs", "OPCOES DE BUSCA onViewCreated Inicio")

        listarCartaoVM = ViewModelProvider(requireActivity()).get(ListarCartaoVM::class.java)

        setupSpinners()
        setObservers()
        setOnClickListeners()

        Log.d("HomeDialogs", "OPCOES DE BUSCA onViewCreated Final")
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setupSpinners(){

        val filtrarAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            com.example.brash.R.array.nuc_listar_cartao_spinner_filtrar,
            android.R.layout.simple_spinner_item
        )
        filtrarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ListarCartaoFrOpcoesDeBuscaSpinnerFiltrar.adapter = filtrarAdapter
    }

    private fun setObservers() {
        listarCartaoVM.opcoesDeBusca.observe(viewLifecycleOwner, Observer {
            binding.ListarCartaoFrOpcoesDeBuscaSpinnerFiltrar.setSelection(it.filtrar.toIndex())
            //TODO:: requisitar sort se houver texto na busca?
        })
    }

    private fun setOnClickListeners() {

        binding.HomeFrOpcoesDeBuscaButtonCancelar.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Cancelar Configurações de Busca", Toast.LENGTH_SHORT).show()
        }

        binding.HomeFrOpcoesDeBuscaButtonAplicar.setOnClickListener {
            listarCartaoVM.updateOpcoesDeBusca(
                binding.ListarCartaoFrOpcoesDeBuscaSpinnerFiltrar.selectedItemPosition.toFiltroDeBuscaListarCartao()
            )
            dismiss()
            Toast.makeText(requireContext(), "Aplicar Configurações de Busca", Toast.LENGTH_SHORT).show()
        }
    }


    private fun FiltroDeBuscaListarCartao.toIndex(): Int {
        return FiltroDeBuscaListarCartao.entries.indexOf(this)
    }

    private fun Int.toFiltroDeBuscaListarCartao(): FiltroDeBuscaListarCartao {
        return FiltroDeBuscaListarCartao.entries.toTypedArray().getOrElse(this) { FiltroDeBuscaListarCartao.PERGUNTA }
    }
}