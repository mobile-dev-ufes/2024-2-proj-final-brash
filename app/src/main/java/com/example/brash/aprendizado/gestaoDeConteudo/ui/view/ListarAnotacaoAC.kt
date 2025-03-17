package com.example.brash.aprendizado.gestaoDeConteudo.ui.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesAnotacaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.CriarAnotacaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaAnotacaoAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnAnotacaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarAnotacaoVM
import com.example.brash.databinding.GtcListarAnotacaoAcBinding
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication

/**
 * Activity for listing annotations associated with a deck and allowing interaction with them.
 *
 * This activity displays a list of annotations that the user can interact with,
 * including viewing more details or creating new annotations. The activity uses a ViewModel
 * to manage data and LiveData for updating the UI. It supports actions like viewing, creating,
 * and managing annotations within a specific deck.
 *
 * @constructor Creates an instance of [ListarAnotacaoAC].
 */
class ListarAnotacaoAC : AppCompatActivity() {

    private lateinit var binding: GtcListarAnotacaoAcBinding
    private lateinit var listarAnotacaoVM: ListarAnotacaoVM
    private lateinit var appVM: AppVM

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : ListaAnotacaoAdapter

    /**
     * Called when the activity is first created.
     *
     * Initializes the layout, ViewModel, RecyclerView for displaying the annotations,
     * and sets up the listener for interactions with annotations. It also fetches
     * the list of annotations to display.
     *
     * @param savedInstanceState A [Bundle] containing the activity's previously saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcListarAnotacaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listarAnotacaoVM = ViewModelProvider(this)[ListarAnotacaoVM::class.java]
        appVM = (application as MyApplication).appSharedInformation


        // Inicializando o listener diretamente
        val listener = object : OnAnotacaoListener {
            override fun onClick(anotacao: Anotacao) {
                //Toast.makeText(applicationContext, anotacao.nome, Toast.LENGTH_SHORT).show()
                listarAnotacaoVM.setAnotacaoEmFoco(anotacao)
                AcoesAnotacaoFrDialog().show(supportFragmentManager, "VisualizarCartaoDialog")
            }
        }

        // Inicializando o adapter com o listener
        adapter = ListaAnotacaoAdapter().apply {
            setListener(listener) // Garanta que o listener seja configurado
        }
        recyclerView = binding.ListarAnotacaoAcRecycleViewResultadoBusca
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter // Defina o adapter na RecyclerView

        setOnClickListeners()
        setObservers()

        appVM.baralhoEmAC.value?.let {
            listarAnotacaoVM.setBaralhoOwner(it)
            binding.ListarAnotacaoAcTextViewTitulo.text = it.nome
        } ?: run {
            //Toast.makeText(applicationContext, "Baralho não encontrado para obter anotacoes.", Toast.LENGTH_SHORT).show()
        }

        listarAnotacaoVM.getAllAnotacoes()


    }

    /**
     * Sets click listeners for the activity's UI elements.
     *
     * Binds actions to buttons and UI elements, such as the "Create Annotation" button
     * and the back button for returning to the previous screen.
     */
    private fun setOnClickListeners(){

        binding.ListarAnotacaoAcImageViewRetornar.setOnClickListener {
            finish()
        }
        binding.ListarAnotacaoAcButtonCriar.setOnClickListener{
            CriarAnotacaoFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }

    }

    /**
     * Sets up the observers for LiveData from the ViewModel.
     *
     * Observes changes in the list of annotations and updates the RecyclerView adapter
     * accordingly with the latest list of annotations.
     */
    private fun setObservers(){
        //loginVM.erroMessageLD.observe(this, Observer{
            //binding.LoginAcTextViewErroEntrar.text = it
            //binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        //})
        listarAnotacaoVM.anotacaoList.observe(this, Observer { anotacaoList ->
            adapter.updateAnotacaoList(anotacaoList)
        })
    }

    /**
     * Called when the activity is stopped.
     *
     * This method is invoked when the activity is no longer in the foreground, allowing for any
     * necessary cleanup when the activity is stopped.
     */
    private fun intentToCadastrarContaActivity(){
        //val intent = Intent(this, CadastrarContaAC::class.java)
        //startActivity(intent)
    }

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