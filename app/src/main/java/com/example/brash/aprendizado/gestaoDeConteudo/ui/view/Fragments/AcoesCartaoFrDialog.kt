package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
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
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarDicaAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.RevisaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaPastaAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.databinding.GtcHomeFrAcoesAdicionaisBinding
import com.example.brash.databinding.GtcHomeFrAcoesBaralhoBinding
import com.example.brash.databinding.GtcHomeFrMoverBaralhoBinding
import com.example.brash.databinding.GtcListarCartaoFrAcoesCartaoBinding
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr
import com.example.brash.nucleo.ui.view.PerfilAC
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication
import com.example.brash.utilsGeral.UtilsGeral

class AcoesCartaoFrDialog() : DialogFragment() {

    private var _binding: GtcListarCartaoFrAcoesCartaoBinding? = null
    private val binding get() = _binding!!


    lateinit var listarCartaoVM: ListarCartaoVM
    private lateinit var appVM: AppVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarCartaoFrAcoesCartaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarCartaoVM = ViewModelProvider(requireActivity()).get(ListarCartaoVM::class.java)
        appVM = (requireActivity().application as MyApplication).appSharedInformation
        setOnClickListeners()

    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.ListarCartaoFrAcoesCartaoTextViewVisualizarCartao.setOnClickListener {
            dismiss()
            if (!activity?.isFinishing!! && !activity?.isDestroyed!!) {
                Log.d("HomeDialogs", "Tentando mostrar o diálogo visualizarBaralho")
                VisualizarCartaoFrDialog().show(parentFragmentManager, "VisualizarBaralhoDialog")
            }
        }
        binding.ListarCartaoFrAcoesCartaoTextViewVisualizarDicas.setOnClickListener {
            dismiss()
            listarCartaoVM.cartaoEmFoco.value?.let {
                appVM.setCartaoEmAC(it)
            } ?: run {
                Toast.makeText(context, "Não foi possível carregar o cartão para dica.", Toast.LENGTH_SHORT).show()
            }
            intentToListarDicaActivity()
            //Toast.makeText(requireContext(), "Visualizar Cartões", Toast.LENGTH_SHORT).show()
        }
        binding.ListarCartaoFrAcoesCartaoTextViewExcluirCartao.setOnClickListener {
            dismiss()
            
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir esse Cartão??",{
                Toast.makeText(requireContext(), "Excluir Cartao", Toast.LENGTH_SHORT).show()
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }

    private fun intentToRevisaoActivity(){
        val intent = Intent(requireContext(), RevisaoAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent)
    }
    private fun intentToListarDicaActivity(){
        val intent = Intent(requireContext(), ListarDicaAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent)
    }
}
