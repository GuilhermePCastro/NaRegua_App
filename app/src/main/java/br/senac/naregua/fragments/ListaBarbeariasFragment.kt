package br.senac.naregua.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.naregua.R
import br.senac.naregua.api.API
import br.senac.naregua.databinding.CardlistabarbeariaBinding
import retrofit2.Callback
import br.senac.naregua.databinding.FragmentListaBarbeariasBinding
import br.senac.naregua.functions.montaShimmerPicaso
import br.senac.naregua.functions.msg
import br.senac.naregua.model.Barbearia
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

private var NOME = " "
private var CIDADE = " "
private var UF = " "
private var BAIRRO = " "


class ListaBarbeariasFragment : Fragment() {

    lateinit var  binding: FragmentListaBarbeariasBinding

    lateinit var ctx: Context
    lateinit var containerFrag: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListaBarbeariasBinding.inflate(inflater)

        if (container != null) {
            ctx = container.context
        }

        if (container != null) {
            containerFrag = container
        }

        binding.swipere.setOnRefreshListener {
            atualizaBarbearias()
        }

        atualizaBarbearias()

        binding.btnFiltar.setOnClickListener {

            containerFrag?.let {
                parentFragmentManager.beginTransaction().replace(it.id, FiltroBarbeariaFragment()).addToBackStack("Filtro").commit()
            }

        }

        return binding.root
    }

    override fun onResume(){
        super.onResume()

        NOME = " "
        CIDADE = " "
        UF = " "
        BAIRRO = " "

        atualizaBarbearias()
    }

    fun atualizaBarbearias(){

        val callback = object : Callback<List<Barbearia>> {

            override fun onResponse(call: Call<List<Barbearia>>, response: Response<List<Barbearia>>) {

                if(response.isSuccessful){
                    val barbearias = response.body()
                    atualizarUI(barbearias)
                    CarregaOff()
                }else{
                    msg(binding.containerLista,"Não é possível atualizar as barbearias")
                    Log.e("ERROR", response.errorBody().toString())
                }

            }

            override fun onFailure(call: Call<List<Barbearia>>, t: Throwable) {
                msg(binding.containerLista,"Não é possível se conectar ao servidor")
                Log.e("ERROR", "Falha ao executar serviço", t)

            }

        }
        API(ctx).barbearia.filtro(NOME, CIDADE, UF, BAIRRO).enqueue(callback)
        CarregaOn()

    }

    fun atualizarUI(lista: List<Barbearia>?){

        binding.containerLista.removeAllViews()

        lista?.forEach {

            val cardBinding = CardlistabarbeariaBinding.inflate(layoutInflater)

            cardBinding.txtNomeBarbearia.text   = it.ds_nome
            cardBinding.txtEndereco.text        = it.ds_endereco + ", " + it.ds_numero
            cardBinding.txtBairro.text          = it.ds_bairro
            cardBinding.txtCep.text             = "CEP " + it.ds_cep
            cardBinding.txtCidade.text          = it.ds_cidade + " - " + it.ds_uf

            //Montando o shimmer para o picaso usar
            var sDrawable = montaShimmerPicaso()

            if(it.hx_logo.isNotEmpty()){
                Picasso.get()
                    .load(it.hx_logo)
                    .placeholder(sDrawable)
                    .error(R.drawable.no_imagem)
                    .into(cardBinding.imgBarbearia)
            }

            var barbeariaId = it.id

            binding.containerLista.addView(cardBinding.root)

            cardBinding.btnDetalhes.setOnClickListener{
                containerFrag?.let {
                    parentFragmentManager.beginTransaction().replace(it.id,  PerfilBarbeariaFragment.newInstance(barbeariaId))
                        .addToBackStack("PerfilBarbearia").commit()
                }
            }
        }

    }

    fun CarregaOn(){
        binding.shimmer.visibility          = View.VISIBLE
        binding.scrollViewLista.visibility  = View.GONE
        binding.swipere.isRefreshing        = true
    }

    fun CarregaOff(){
        binding.shimmer.visibility          = View.GONE
        binding.scrollViewLista.visibility  = View.VISIBLE
        binding.swipere.isRefreshing        = false
    }

    companion object{
        @JvmStatic
        fun newInstance(nome: String, cidade: String, uf: String, bairro: String) =
            ListaBarbeariasFragment().apply {
                arguments = Bundle().apply {
                    NOME = nome
                    CIDADE = cidade
                    UF = uf
                    BAIRRO = bairro
                }
            }
    }



}