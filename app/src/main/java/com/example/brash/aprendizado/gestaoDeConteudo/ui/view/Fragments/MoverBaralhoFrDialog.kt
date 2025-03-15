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
import androidx.core.content.ContextCompat
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
import com.example.brash.aprendizado.gestaoDeConteudo.utils.getColorResetMoverBaralho
import com.example.brash.aprendizado.gestaoDeConteudo.utils.getColorSetMoverBaralho

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
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]
        adapter = ListaPastaAdapter(homeVM.baralhoEmFoco.value?.pasta)


        if(homeVM.baralhoEmFoco.value?.pasta == null){
            setBackgroundLayoutRaiz()
        }
        // Configurar RecyclerView com Adapter
        binding.HomeFrMoverBaralhoRecycleViewListaPastas.layoutManager = LinearLayoutManager(context)
        binding.HomeFrMoverBaralhoRecycleViewListaPastas.adapter = adapter

        // Configuração do listener do produto
        val listener = object : OnPastaListener {
            override fun onClick(p: Pasta) {
                Toast.makeText(context, p.nome, Toast.LENGTH_SHORT).show()

                resetBackgroundLayoutRaiz()
                homeVM.setPastaEmMover(p)
            }
        }

        adapter.setListener(listener)

        // Configurar os observadores para LiveData
        setObservers()
        setOnClickListeners()

        // Chamar o métod da ViewModel para obter os dados
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
        binding.HomeFrMoverBaralhoButtonCancelar.setOnClickListener {
            dismiss()
        }
        binding.HomeFrMoverBaralhoButtonMover.setOnClickListener {
            //TODO:: Fazer a verificação de mover da pasta e atualizar em HomeAC
            //Toast.makeText(context, "Mover Para " + (homeVM.pastaEmMover.value?.nome ?: "Root"), Toast.LENGTH_SHORT).show()

            homeVM.pastaEmMover.value?.let { pasta ->
                homeVM.baralhoEmFoco.value?.let { baralho ->
                    homeVM.moverBaralho(pasta, baralho) {
                        Toast.makeText(requireContext(), "Mover Baralho", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                } ?: run {
                    Toast.makeText(requireContext(), "Nenhum baralho selecionado", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            } ?: run {
                Toast.makeText(requireContext(), "Nenhuma pasta selecionada para mover o baralho", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
        binding.HomeFrMoverBaralhoLayoutRaiz.setOnClickListener {
            Toast.makeText(context, "Root Clicado", Toast.LENGTH_SHORT).show()
            clickPastaRaiz()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }

    private fun clickPastaRaiz(){
        setBackgroundLayoutRaiz()
        adapter.resetSelectedItem()
        homeVM.resetPastaEmMover()
    }

    private fun setBackgroundLayoutRaiz(){
        binding.HomeFrMoverBaralhoLayoutRaiz.setBackgroundColor(
            ContextCompat.getColor(requireContext(), getColorSetMoverBaralho())
        )
    }

    fun resetBackgroundLayoutRaiz(){
        binding.HomeFrMoverBaralhoLayoutRaiz.setBackgroundColor(
            ContextCompat.getColor(requireContext(), getColorResetMoverBaralho())
        )
    }

}
