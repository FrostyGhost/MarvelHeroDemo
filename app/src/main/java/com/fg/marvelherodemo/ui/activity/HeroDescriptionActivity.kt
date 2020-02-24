package com.fg.marvelherodemo.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.fg.marvelherodemo.R
import com.fg.marvelherodemo.Utils
import com.fg.marvelherodemo.apimodel.Character
import com.fg.marvelherodemo.networking.CharactersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_hero_description.*
import kotlinx.android.synthetic.main.toolbar.*
import android.graphics.drawable.BitmapDrawable

/*
   В завданні записано, що завантаження деталей про персонажа, відбувається за запитом,
   тому не реалізовував тут завантаження з бд
*/

class HeroDescriptionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var repository: CharactersRepository
    private var heroId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_description)

        heroId = intent.getIntExtra("heroID", 0)
        Log.d("Result", "$heroId")
        repository = Utils.CharactersRepositoryProvider.provideSCharactersRepository()

        loadHeroFromApi()

        imComics.setOnClickListener {
            if (svComics.visibility == View.VISIBLE){
                svComics.visibility = View.GONE
                imComics.rotation = -90f
            }else {
                svComics.visibility = View.VISIBLE
                imComics.rotation = 90f
            }
        }

        imSeries.setOnClickListener {
            if (svSeries.visibility == View.VISIBLE){
                svSeries.visibility = View.GONE
                imSeries.rotation = -90f
            }else {
                svSeries.visibility = View.VISIBLE
                imSeries.rotation = 90f
            }
        }

        imLinks.setOnClickListener {
            if (svLinks.visibility == View.VISIBLE){
                svLinks.visibility = View.GONE
                imLinks.rotation = -90f
            }else {
                svLinks.visibility = View.VISIBLE
                imLinks.rotation = 90f
            }
        }


        //є невеличкий пролаг, якщо мало інформації про персонажа та зашвидко скролити
        detailsList.overScrollMode = NestedScrollView.OVER_SCROLL_NEVER
        detailsList.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
           if ((scrollY-oldScrollY)>30){
               imHeroAvatar.visibility = View.GONE


               imHeroAvatar.invalidate()
               val drawable = imHeroAvatar.getDrawable() as BitmapDrawable
               val bitmap = drawable.bitmap
               tools.setBackgroundColor(getColor(bitmap))
           }else if(scrollY<=oldScrollY){
               imHeroAvatar.visibility = View.VISIBLE
           }
        })

        ivToolbarBack.visibility = View.VISIBLE
        ivToolbarBack.setOnClickListener { onBackPressed() }
    }

    //алгоритм за яким отримуємо дані про середнє значення кольору
    private fun getColor(bitmap: Bitmap): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }


    @SuppressLint("CheckResult")
    fun loadHeroFromApi(){
        repository.getCharactersFromId(heroId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    result ->
                    if (result == null){
                        Log.d("Result", "null")
                    }else{
                        initHero(character = result.data.results!![0])
                    }
                }, { error ->
                    error.printStackTrace()
                })
    }

    private fun initHero(character: Character){
        txDescription.text = character.description

        tvToolbarName.text = character.name

        val imageUrl = "${character.thumbnail?.path}.jpg".replace("http://","https://")
        Glide.with(this)
                .load(imageUrl)
                .into(imHeroAvatar)

            character.comics

        for (x in 0 until character.comics!!.items!!.size){
            createItemList(llComicsList, character.comics!!.items!![x].name!!)
        }
        for (x in 0 until character.series!!.items!!.size){
            createItemList(llSeriesList, character.series!!.items!![x].name!!)
        }
        for (x in 0 until character.urls!!.size){
            createLink(llLinksList, character.urls!![x].url!!)
        }
    }


    private fun createItemList(container:LinearLayout, string: String){
        val textItem = TextView(this)
        textItem.text = string
        textItem.setPadding(16,0,0,0)
        container.addView(textItem)
    }

    private fun createLink(container:LinearLayout, string: String){
        val textItem = TextView(this)
        textItem.text = string
        textItem.setOnClickListener(this)
        textItem.setPadding(16,0,0,0)
        container.addView(textItem)
    }
    override fun onClick(v: View?) {
        val i = Intent(this, WebActivity::class.java)
        v as TextView
        i.putExtra("URL", v.text )
        startActivity(i)    }

}
