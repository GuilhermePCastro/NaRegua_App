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
import br.senac.naregua.databinding.FragmentPerfilBarbeariaBinding
import br.senac.naregua.functions.msg
import br.senac.naregua.model.Barbearia
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.net.Uri
import br.senac.naregua.functions.trataCel


private const val BARBEARIA_ID = "id"

class PerfilBarbeariaFragment : Fragment() {

    lateinit var  binding: FragmentPerfilBarbeariaBinding
    lateinit var containerPro: ViewGroup
    lateinit var ctx: Context

    //Parâmetros
    private var barbId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            barbId = it.getInt(BARBEARIA_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBarbeariaBinding.inflate(layoutInflater)

        if (container != null) {
            ctx = container.context
        }

        atualizaPerfil()

        return binding.root
    }

    fun atualizaPerfil(){

        val callback = object : Callback<List<Barbearia>> {

            override fun onResponse(call: Call<List<Barbearia>>, response: Response<List<Barbearia>>) {

                CarregaOff()
                if(response.isSuccessful){
                    val barbearias = response.body()
                    if (barbearias != null) {
                        atualizarUI(barbearias)

                    }

                }else{
                    msg(binding.frameLayout3,"Não é possível carregar os dados da barbearia")
                    Log.e("ERROR", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<List<Barbearia>>, t: Throwable) {
                msg(binding.frameLayout3,"Não é possível se conectar ao servidor")
                Log.e("ERROR", "Falha ao executar serviço", t)

            }
        }

        if (barbId != null) {
            API(ctx).barbearia.show(barbId!!).enqueue(callback)
            CarregaOn()
        }

    }

    fun atualizarUI(barbearia: List<Barbearia>){

        barbearia?.forEach {

            binding.nomeBarbearia.text      = it.ds_nome
            binding.txtEnderecoPerfil.text  = it.ds_endereco + ", " + it.ds_numero
            binding.txtBairroPerfil.text    = it.ds_bairro
            binding.txtCidadePerfil.text    = it.ds_cidade + " - " + it.ds_uf
            binding.txtCepPerfil.text       = "CEP " + it.ds_cep
            binding.txtComplemento.text     = it.ds_completo
            binding.txtCelular.text         = "Celular: " + it.ds_celular
            if(it.ds_telefone != ""){
                binding.txtTelefone.text        = "Telefone :" + it.ds_telefone
            }else{
                binding.txtTelefone.text        = it.ds_telefone
            }

            val celular = it.ds_celular

            binding.btnWhats.setOnClickListener {
                val url = "https://api.whatsapp.com/send?phone=55" + trataCel(celular)
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

        }

    }

    fun CarregaOn(){
        binding.progressBar.visibility  = View.VISIBLE
        binding.cardPerfil.visibility   = View.INVISIBLE
        binding.btnWhats.visibility     = View.INVISIBLE
    }

    fun CarregaOff(){
        binding.progressBar.visibility  = View.GONE
        binding.cardPerfil.visibility   = View.VISIBLE
        binding.btnWhats.visibility     = View.VISIBLE
    }

    companion object{
        @JvmStatic
        fun newInstance(id: Int) =
            PerfilBarbeariaFragment().apply {
                arguments = Bundle().apply {
                    putInt(BARBEARIA_ID, id)
                }
            }
    }




}