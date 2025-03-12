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
import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.utils.OrdemDeBuscaHome
import com.example.brash.databinding.GtcHomeFrOpcoesDeBuscaBinding




class OpcoesDeBuscaHomeFrDialog: DialogFragment() {

    private var _binding: GtcHomeFrOpcoesDeBuscaBinding? = null
    private val binding get() = _binding!!

    lateinit var homeVM: HomeVM


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrOpcoesDeBuscaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HomeDialogs", "OPCOES DE BUSCA onViewCreated Inicio")

        homeVM = ViewModelProvider(requireActivity()).get(HomeVM::class.java)

        setupSpinners()
        setObservers()
        setOnClickListeners()

        Log.d("HomeDialogs", "OPCOES DE BUSCA onViewCreated Final")
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setupSpinners() {
        val ordenarAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            com.example.brash.R.array.nuc_home_spinner_ordenar,
            android.R.layout.simple_spinner_item
        )
        ordenarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.HomeFrOpcoesDeBuscaSpinnerOrdenar.adapter = ordenarAdapter

        val filtrarAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            com.example.brash.R.array.nuc_home_spinner_filtrar,
            android.R.layout.simple_spinner_item
        )
        filtrarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.HomeFrOpcoesDeBuscaSpinnerFiltrar.adapter = filtrarAdapter
    }

    private fun setObservers() {
        homeVM.opcoesDeBusca.observe(viewLifecycleOwner, Observer {
            binding.HomeFrOpcoesDeBuscaSpinnerOrdenar.setSelection(it.ordenar.toIndex())
            binding.HomeFrOpcoesDeBuscaSpinnerFiltrar.setSelection(it.filtrar.toIndex())
        })
    }

    private fun setOnClickListeners() {

        binding.HomeFrOpcoesDeBuscaButtonCancelar.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Cancelar Configurações de Busca", Toast.LENGTH_SHORT).show()
        }

        binding.HomeFrOpcoesDeBuscaButtonAplicar.setOnClickListener {
            homeVM.updateOpcoesDeBusca(
                binding.HomeFrOpcoesDeBuscaSpinnerOrdenar.selectedItemPosition.toOrdemDeBuscaHome(),
                binding.HomeFrOpcoesDeBuscaSpinnerFiltrar.selectedItemPosition.toFiltroDeBuscaHome()
            )
            dismiss()
            Toast.makeText(requireContext(), "Aplicar Configurações de Busca", Toast.LENGTH_SHORT).show()
        }
    }

    private fun OrdemDeBuscaHome.toIndex(): Int {
        return OrdemDeBuscaHome.entries.indexOf(this)
    }

    private fun FiltroDeBuscaHome.toIndex(): Int {
        return FiltroDeBuscaHome.entries.indexOf(this)
    }

    private fun Int.toOrdemDeBuscaHome(): OrdemDeBuscaHome {
        return OrdemDeBuscaHome.entries.toTypedArray().getOrElse(this) { OrdemDeBuscaHome.ALFANUMERICO }
    }

    private fun Int.toFiltroDeBuscaHome(): FiltroDeBuscaHome {
        return FiltroDeBuscaHome.entries.toTypedArray().getOrElse(this) { FiltroDeBuscaHome.TODOS }
    }
}