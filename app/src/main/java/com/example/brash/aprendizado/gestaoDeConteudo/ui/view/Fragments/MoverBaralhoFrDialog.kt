package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaPastaAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrMoverBaralhoBinding
import androidx.lifecycle.Observer

import com.example.brash.utilsGeral.Constants

/**
 * A DialogFragment that allows the user to move a "Baralho" (Deck) to a different "Pasta" (Folder).
 * It displays a list of folders in a RecyclerView, and the user can select a folder to move the selected deck to.
 * The dialog includes "Move" and "Cancel" buttons to perform the action or dismiss the dialog.
 *
 * @constructor Creates an instance of `MoverBaralhoFrDialog`.
 */
class MoverBaralhoFrDialog() : DialogFragment() {

    private var _binding: GtcHomeFrMoverBaralhoBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter : ListaPastaAdapter

    lateinit var homeVM: HomeVM

    /**
     * Inflates the layout for the fragment and initializes the view binding.
     *
     * @param inflater The LayoutInflater object used to inflate the layout.
     * @param container The parent view that the fragment's UI will be attached to.
     * @param savedInstanceState A Bundle containing data saved during a previous instance of the fragment.
     * @return The root view of the fragment's layout.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrMoverBaralhoBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Sets up the ViewModel, adapter, observers, and click listeners for the dialog.
     * It also initializes the RecyclerView with the list of folders and configures the item listener.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A Bundle containing data saved during a previous instance of the fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]
        adapter = ListaPastaAdapter(homeVM.baralhoEmFoco.value?.pasta)


        //if(homeVM.baralhoEmFoco.value?.pasta == null){
            //setBackgroundLayoutRaiz()
        //}
        // Configurar RecyclerView com Adapter
        binding.HomeFrMoverBaralhoRecycleViewListaPastas.layoutManager = LinearLayoutManager(context)
        binding.HomeFrMoverBaralhoRecycleViewListaPastas.adapter = adapter

        // Configuração do listener do produto
        val listener = object : OnPastaListener {
            override fun onClick(p: Pasta) {
                //Toast.makeText(context, p.nome, Toast.LENGTH_SHORT).show()
                //resetBackgroundLayoutRaiz()
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
    /**
     * Configures observers for LiveData changes, such as folder list and success/error messages.
     */
    private fun setObservers(){
        homeVM.listPastaMsg.observe(viewLifecycleOwner, Observer {
            when (it) {
                Constants.BD_MSGS.SUCCESS -> {
                    //Toast.makeText(context, "R.string.success_search", Toast.LENGTH_SHORT).show()
                }
                Constants.BD_MSGS.NOT_FOUND -> {
                    //Toast.makeText(context, "R.string.not_found_search", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //Toast.makeText(context, "R.string.fail_search", Toast.LENGTH_SHORT).show()
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
    /**
     * Sets the click listeners for the "Cancel" and "Move" buttons.
     * The "Move" button will move the selected deck to the chosen folder.
     */
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
                        //Toast.makeText(requireContext(), "Mover Baralho", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                } ?: run {
                    //Toast.makeText(requireContext(), "Nenhum baralho selecionado", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            } ?: run {
                //Toast.makeText(requireContext(), "Nenhuma pasta selecionada para mover o baralho", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
        //binding.HomeFrMoverBaralhoLayoutRaiz.setOnClickListener {
            //Toast.makeText(context, "Root Clicado", Toast.LENGTH_SHORT).show()
            //clickPastaRaiz()
        //}
    }
    /**
     * Cleans up the view binding to prevent memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
    /**
     * Resets the selected folder and clears the current folder selection in the ViewModel.
     */
    private fun clickPastaRaiz(){
        //setBackgroundLayoutRaiz()
        adapter.resetSelectedItem()
        homeVM.resetPastaEmMover()
    }

    /*
    private fun setBackgroundLayoutRaiz(){
        binding.HomeFrMoverBaralhoLayoutRaiz.setBackgroundColor(
            ContextCompat.getColor(requireContext(), getColorSetMoverBaralho())
        )
    }

    fun resetBackgroundLayoutRaiz(){
        binding.HomeFrMoverBaralhoLayoutRaiz.setBackgroundColor(
            ContextCompat.getColor(requireContext(), getColorResetMoverBaralho())
        )
    }*/

}
