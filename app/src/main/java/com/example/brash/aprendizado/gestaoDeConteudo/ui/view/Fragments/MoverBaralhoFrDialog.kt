package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaPastaAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrMoverBaralhoBinding
import com.example.brash.nucleo.ui.viewModel.LoginVM
import com.example.brash.nucleo.utils.UtilsFoos
import androidx.lifecycle.Observer
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaBaralhoPublicoAdapter

import com.example.brash.utilsGeral.Constants

class MoverBaralhoFrDialog() : DialogFragment() {

    private var _binding: GtcHomeFrMoverBaralhoBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter : ListaPastaAdapter

    lateinit var homeVM: HomeVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrMoverBaralhoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        homeVM = ViewModelProvider(requireActivity()).get(HomeVM::class.java)
        adapter = ListaPastaAdapter()


        // Configurar RecyclerView com Adapter
        binding.HomeFrMoverBaralhoRecycleViewListaPastas.layoutManager = LinearLayoutManager(context)
        binding.HomeFrMoverBaralhoRecycleViewListaPastas.adapter = adapter

        // Configuração do listener do produto
        val listener = object : OnPastaListener {
            override fun onClick(p: Pasta) {
                Toast.makeText(context, p.nome, Toast.LENGTH_SHORT).show()
            }
        }

        adapter.setListener(listener)

        // Configurar os observadores para LiveData
        setObservers()
        setOnClickListeners()

        // Chamar o métod da ViewModel para obter os dados
        homeVM.getAllPastas()
        Log.d("HomeDialogs", "MOVER BARALHO PASSOU DO GETALLPASTAS")

    }

    private fun setObservers(){
        homeVM.listPastaMsg.observe(viewLifecycleOwner, Observer {
            when (it) {
                Constants.BD_MSGS.SUCCESS -> {
                    Toast.makeText(context, "R.string.success_search", Toast.LENGTH_SHORT).show()
                }
                Constants.BD_MSGS.NOT_FOUND -> {
                    Toast.makeText(context, "R.string.not_found_search", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context, "R.string.fail_search", Toast.LENGTH_SHORT).show()
                }
            }
        })

        homeVM.pastaList.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.updateProdList(it)
                Log.d("ListaPastaAdapter", "Lista não vazia recebida")
            } else {
                Log.d("ListaPastaAdapter", "Lista vazia recebida")
            }

            Log.d("ListaPastaAdapter", "Observado Mudança")
        })
    }

    private fun setOnClickListeners(){
        binding.HomeFrMoverBaralhoButtonMover.setOnClickListener {
            //TODO:: Fazer a verificação da criação da pasta
            Toast.makeText(context, "Mover Pasta", Toast.LENGTH_SHORT).show()
        }
        binding.HomeFrMoverBaralhoLayoutRaiz.setOnClickListener {
            Toast.makeText(context, "Root Clicado", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }

}
