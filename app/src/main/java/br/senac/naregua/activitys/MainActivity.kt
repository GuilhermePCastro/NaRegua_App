package br.senac.naregua.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.senac.naregua.R
import br.senac.naregua.databinding.ActivityMainBinding
import br.senac.naregua.fragments.ListaBarbeariasFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager.beginTransaction()
            .replace(R.id.containerPrincipal, ListaBarbeariasFragment())
            .addToBackStack("fragListaBarbearia").commit()
    }

}