package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesDicaFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.CriarDicaFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaDicaAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnDicaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarDicaVM
import com.example.brash.databinding.GtcListarDicaAcBinding
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication

/**
 * Activity for listing tips related to a specific card.
 *
 * This activity manages the listing of all tips associated with a card and allows the user
 * to view, create, and interact with the tips. The activity uses a ViewModel to fetch the
 * data and updates the UI via LiveData. It also includes a listener for interactions with
 * the tips, and manages RecyclerView for displaying the list of tips.
 *
 * @constructor Creates an instance of [ListarDicaAC].
 */
class ListarDicaAC : AppCompatActivity() {

    private lateinit var binding: GtcListarDicaAcBinding
    private lateinit var listarDicaVM: ListarDicaVM
    private lateinit var appVM: AppVM

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : ListaDicaAdapter

    /**
     * Called when the activity is first created.
     *
     * Initializes the layout, ViewModel, and RecyclerView for displaying the tips list.
     * Also sets up the listener for tip interactions and starts fetching the data for
     * the tips related to the current card.
     *
     * @param savedInstanceState A [Bundle] containing the activity's previously saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcListarDicaAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listarDicaVM = ViewModelProvider(this)[ListarDicaVM::class.java]
        appVM = (application as MyApplication).appSharedInformation


        // Inicializando o listener diretamente
        val listener = object : OnDicaListener {
            override fun onClick(d: Dica) {
                //Toast.makeText(applicationContext, d.texto, Toast.LENGTH_SHORT).show()
                listarDicaVM.setDicaEmFoco(d)
                AcoesDicaFrDialog().show(supportFragmentManager, "VisualizarCartaoDialog")
            }
        }

        // Inicializando o adapter com o listener
        adapter = ListaDicaAdapter().apply {
            setListener(listener) // Garanta que o listener seja configurado
        }

        recyclerView = binding.ListarCartaoAcRecycleViewResultadoBusca
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter // Defina o adapter na RecyclerView

        setOnClickListeners()
        setObservers()

        appVM.cartaoEmAC.value?.let {
            listarDicaVM.setCartaoOwner(it)
        } ?: run {
            //Toast.makeText(applicationContext, "Cartao não encontrado para obter dicas.", Toast.LENGTH_SHORT).show()
        }
        listarDicaVM.getAllDicas()


    }

    /**
     * Sets click listeners for the activity's UI elements.
     *
     * Binds actions to the buttons and UI elements in the layout, such as the "back"
     * button and the "create tip" button.
     */
    private fun setOnClickListeners(){

        binding.ListarDicaAcImageViewRetornar.setOnClickListener {
            finish()
        }
        binding.ListarDicaAcButtonCriar.setOnClickListener{
            CriarDicaFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }

    }

    /**
     * Sets up the observers for LiveData from the ViewModel.
     *
     * Observes changes in the list of tips and updates the RecyclerView adapter when
     * the data is fetched or changed.
     */
    private fun setObservers(){
        //loginVM.erroMessageLD.observe(this, Observer{
            //binding.LoginAcTextViewErroEntrar.text = it
            //binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        //})
        listarDicaVM.dicaList.observe(this, Observer { dicaList ->
            adapter.updateDicaList(dicaList)

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