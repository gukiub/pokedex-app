package com.gustavo.pokedex.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gustavo.pokedex.PokemonViewModel
import com.gustavo.pokedex.PokemonViewModelFactory
import com.gustavo.pokedex.databinding.FragmentPokedexListBinding
import com.gustavo.pokedex.model.Pokemon

class PokedexFragment : Fragment() {

    private var _binding: FragmentPokedexListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this, PokemonViewModelFactory())[PokemonViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokedexListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.pokemons.observe(viewLifecycleOwner) {
            loadRecyclerView(it)
        }
    }

    private fun loadRecyclerView(pokemons: List<Pokemon?>) {
        with(binding) {
            list.layoutManager = LinearLayoutManager(context)
            list.adapter = PokemonAdapter(pokemons, requireContext())
            binding.loading.visibility = View.GONE
        }
    }
}