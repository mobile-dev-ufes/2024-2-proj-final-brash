package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesCartaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.CriarCartaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.OpcoesDeBuscaListarCartaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaCartaoAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnCartaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.databinding.GtcListarCartaoAcBinding
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication

/**
 * Activity for listing and interacting with cards within a deck.
 *
 * This activity displays a list of cards associated with a specific deck. It allows the
 * user to view, filter, and create new cards. The activity uses a ViewModel to manage the
 * data and updates the UI via LiveData. It also includes a listener for interactions with
 * the cards and a search functionality to filter the card list.
 *
 * @constructor Creates an instance of [ListarCartaoAC].
 */
class ListarCartaoAC : AppCompatActivity() {

    private lateinit var binding: GtcListarCartaoAcBinding
    private lateinit var listarCartaoVM: ListarCartaoVM
    private lateinit var appVM: AppVM

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : ListaCartaoAdapter

    /**
     * Called when the activity is first created.
     *
     * Initializes the layout, ViewModel, RecyclerView for displaying the card list,
     * and sets up the listener for card interactions. It also loads the deck's data
     * and fetches the cards associated with the deck.
     *
     * @param savedInstanceState A [Bundle] containing the activity's previously saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcListarCartaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listarCartaoVM = ViewModelProvider(this)[ListarCartaoVM::class.java]
        appVM = (application as MyApplication).appSharedInformation


        // Inicializando o listener diretamente
        val listener = object : OnCartaoListener {
            override fun onClick(c: Cartao) {
                //Toast.makeText(applicationContext, c.pergunta, Toast.LENGTH_SHORT).show()
                listarCartaoVM.setCartaoEmFoco(c)
                AcoesCartaoFrDialog().show(supportFragmentManager, "VisualizarCartaoDialog")
            }
        }

        // Inicializando o adapter com o listener
        adapter = ListaCartaoAdapter().apply {
            setListener(listener) // Garanta que o listener seja configurado
        }

        recyclerView = binding.ListarCartaoAcRecycleViewResultadoBusca
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter // Defina o adapter na RecyclerView


        appVM.baralhoEmAC.value?.let {
            binding.ListarCartaoAcTextViewTitulo.text = it.nome
            listarCartaoVM.setBaralhoOwner(it)
        } ?: run {
            //Toast.makeText(applicationContext, "Baralho não encontrado para nomear o título.", Toast.LENGTH_SHORT).show()
        }

        setOnClickListeners()
        setObservers()

        listarCartaoVM.getAllCartoes()
    }

    /**
     * Sets click listeners for the activity's UI elements.
     *
     * Binds actions to buttons and UI elements, such as the search input, search options
     * button, create card button, and the back button.
     */
    private fun setOnClickListeners(){

        binding.ListarCartaoAcInputDePesquisa.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                // O usuário pressionou "Done"
                listarCartaoVM.updateFilterCartaoList(binding.ListarCartaoAcInputDePesquisa.text.toString())

                true // Retorna true para indicar que o evento foi tratado
            } else {
                false // Permite que o evento continue propagando
            }
        }

        binding.ListarCartaoAcImageViewOpcoesDeBusca.setOnClickListener {
            OpcoesDeBuscaListarCartaoFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }
        binding.ListarCartaoAcButtonCriar.setOnClickListener{
            CriarCartaoFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }
        binding.ListarCartaoAcImageViewRetornar.setOnClickListener {
            finish()
        }

    }

    /**
     * Sets up the observers for LiveData from the ViewModel.
     *
     * Observes changes in the list of cards and the search options, updating the RecyclerView
     * adapter and filtering the card list accordingly.
     */
    private fun setObservers(){
        //loginVM.erroMessageLD.observe(this, Observer{
            //binding.LoginAcTextViewErroEntrar.text = it
            //binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        //})
        listarCartaoVM.cartaoListSort.observe(this, Observer { cartaoListSort ->

            adapter.updateCartaoList(cartaoListSort)
        })

        listarCartaoVM.opcoesDeBusca.observe(this, Observer{
            //Toast.makeText(applicationContext, "Opcoes de busca foram alteradas", Toast.LENGTH_SHORT).show()
            listarCartaoVM.updateFilterCartaoList(binding.ListarCartaoAcInputDePesquisa.text.toString())
        })
    }
    private fun intentToCadastrarContaActivity(){
        //val intent = Intent(this, CadastrarContaAC::class.java)
        //startActivity(intent)
    }

    /**
     * Called when the activity is stopped.
     *
     * This method is invoked when the activity is no longer in the foreground. It is used
     * to handle any cleanup tasks when the activity is stopped.
     */
    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

    //private fun intentToPerfilActivity(){
        //val intent = Intent(this, PerfilAC::class.java)
        //startActivity(intent)
    //}

}