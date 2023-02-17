package com.gustavo.pokedex.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gustavo.pokedex.R
import com.gustavo.pokedex.databinding.FragmentPokeItemBinding
import com.gustavo.pokedex.model.Pokemon
import com.gustavo.pokedex.util.typeEnum.Dark
import com.gustavo.pokedex.util.typeEnum.bug
import com.gustavo.pokedex.util.typeEnum.dragon
import com.gustavo.pokedex.util.typeEnum.eletric
import com.gustavo.pokedex.util.typeEnum.fairy
import com.gustavo.pokedex.util.typeEnum.fighting
import com.gustavo.pokedex.util.typeEnum.fire
import com.gustavo.pokedex.util.typeEnum.flying
import com.gustavo.pokedex.util.typeEnum.ghost
import com.gustavo.pokedex.util.typeEnum.grass
import com.gustavo.pokedex.util.typeEnum.ground
import com.gustavo.pokedex.util.typeEnum.ice
import com.gustavo.pokedex.util.typeEnum.normal
import com.gustavo.pokedex.util.typeEnum.poison
import com.gustavo.pokedex.util.typeEnum.psychic
import com.gustavo.pokedex.util.typeEnum.rock
import com.gustavo.pokedex.util.typeEnum.steel
import com.gustavo.pokedex.util.typeEnum.water
import java.util.*

class PokemonAdapter(
    private val values: List<Pokemon?>,
    private val context: Context
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentPokeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        item?.let { pokemon ->
            Glide.with(holder.img).load(pokemon.imageUrl).into(holder.img)
            holder.id.text = pokemon.formattedId()
            holder.name.text = pokemon.formattedName
            holder.firstPokeType.text = pokemon.types[0].name.uppercase(Locale.ROOT)
            setPokemonTypeColor(pokemon, holder.firstPokeType, 0)

            if (pokemon.types.size > 1) {
                holder.containerSecondType.visibility = View.VISIBLE
                holder.secondPokeType.text = pokemon.types[1].name.uppercase(Locale.ROOT)
                setPokemonTypeColor(pokemon, holder.secondPokeType, 1)
            } else {
                holder.containerSecondType.visibility = View.GONE
            }
        }
    }

    private fun setPokemonTypeColor(
        pokemon: Pokemon,
        holder: TextView,
        index: Int
    ) {
        when (pokemon.types[index].name) {
            normal -> holder.setBackgroundColor(context.getColor(R.color.normal))
            fighting -> holder.setBackgroundColor(context.getColor(R.color.fighting))
            flying -> holder.setBackgroundColor(context.getColor(R.color.flying))
            poison -> holder.setBackgroundColor(context.getColor(R.color.poison))
            ground -> holder.setBackgroundColor(context.getColor(R.color.ground))
            rock -> holder.setBackgroundColor(context.getColor(R.color.rock))
            bug -> holder.setBackgroundColor(context.getColor(R.color.bug))
            ghost -> holder.setBackgroundColor(context.getColor(R.color.ghost))
            steel -> holder.setBackgroundColor(context.getColor(R.color.steel))
            fire -> holder.setBackgroundColor(context.getColor(R.color.fire))
            water -> holder.setBackgroundColor(context.getColor(R.color.water))
            grass -> holder.setBackgroundColor(context.getColor(R.color.grass))
            eletric -> holder.setBackgroundColor(context.getColor(R.color.eletric))
            psychic -> holder.setBackgroundColor(context.getColor(R.color.psychic))
            ice -> holder.setBackgroundColor(context.getColor(R.color.ice))
            dragon -> holder.setBackgroundColor(context.getColor(R.color.dragon))
            Dark -> holder.setBackgroundColor(context.getColor(R.color.Dark))
            fairy -> holder.setBackgroundColor(context.getColor(R.color.fairy))
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentPokeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img = binding.pokeImg
        val id = binding.pokeId
        val name = binding.pokeName
        val firstPokeType = binding.pokeType1
        val secondPokeType = binding.pokeType2
        val containerSecondType = binding.containerType2
    }
}