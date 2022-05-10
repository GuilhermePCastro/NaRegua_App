package br.senac.naregua.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.naregua.databinding.FragmentFiltroBarbeariaBinding

class FiltroBarbeariaFragment : Fragment() {

    lateinit var  binding: FragmentFiltroBarbeariaBinding

    lateinit var ctx: Context
    lateinit var containerFrag: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFiltroBarbeariaBinding.inflate(inflater)

        if (container != null) {
            ctx = container.context
        }

        if (container != null) {
            containerFrag = container
        }

        binding.btnBuscar.setOnClickListener {
            filtrar()
        }

        return binding.root
    }

    fun filtrar(){


        val lnNome = binding.edtNomeBarb.text.toString()
        val lnBairro = binding.edtBairro.text.toString()
        val lnCidade = binding.edtCidade.text.toString()
        val lnUF = binding.edtUF.text.toString()

        containerFrag?.let {
            parentFragmentManager.beginTransaction().replace(it.id,  ListaBarbeariasFragment
                .newInstance(lnNome, lnCidade, lnUF, lnBairro))
                .commit()
        }

    }

}