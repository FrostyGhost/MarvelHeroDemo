package com.fg.marvelherodemo.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fg.marvelherodemo.R
import com.fg.marvelherodemo.apimodel.Character


class MarvelHeroAdapter (private val items: List<Character>) : RecyclerView.Adapter<MarvelHeroAdapter.MarvelHeroHolder>()  {

    private var mListener : MarvelHeroAdapter.Callback? = null

    fun setListener(callback: MarvelHeroAdapter.Callback){
        mListener = callback
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelHeroHolder {
        return MarvelHeroHolder(LayoutInflater.from(parent.context).inflate(R.layout.hero_item,parent,false))
    }


    override fun onBindViewHolder(holder: MarvelHeroHolder, position: Int) {
        val item = items[position]
        val root = holder.itemView

        holder.heroImage = root.findViewById(R.id.imHero)
        holder.nameHero = root.findViewById(R.id.txName)
        holder.comics = root.findViewById(R.id.txComics)
        holder.comicsNum = root.findViewById(R.id.txComicsNum)
        holder.series = root.findViewById(R.id.txSeries)
        holder.seriesNum = root.findViewById(R.id.txSeriesNum)
        holder.heroItem = root.findViewById(R.id.heroItem)


        //є баг з глайдом на 9+ дроїді, силка з хттп кидає помилку, з хттпс все ок, тому проводжу заміну))
        //https://stackoverflow.com/questions/45940861/android-8-cleartext-http-traffic-not-permitted
        val imageUrl = "${item.thumbnail?.path}/standard_medium.jpg".replace("http://","https://")
        Log.d("ImageUrl", imageUrl)

        item.thumbnail
        holder.nameHero.text = item.name
        //MARVEL LIST
        holder.comicsNum.text  = item.comics!!.items!!.size.toString()
        holder.seriesNum.text = item.series!!.items!!.size.toString()

//        holder.comicsNum.text  = item.comics!!.size.toString()
//        holder.seriesNum.text = item.series!!.size.toString()

        Glide.with(holder.itemView.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.heroImage)

        holder.heroImage.setOnClickListener {
            mListener?.onItemClicked(items[position].id!!)
        }
    }

    inner class MarvelHeroHolder(view : View) : RecyclerView.ViewHolder(view) {
        lateinit var heroImage: ImageView
        lateinit var nameHero: TextView
        lateinit var comics: TextView
        lateinit var comicsNum: TextView
        lateinit var series: TextView
        lateinit var seriesNum: TextView
        lateinit var heroItem: LinearLayout
    }

    interface Callback{
        fun onItemClicked(item : Int)
    }
}