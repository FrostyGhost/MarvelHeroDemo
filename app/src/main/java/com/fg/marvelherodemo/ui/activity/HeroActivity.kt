package com.fg.marvelherodemo.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fg.marvelherodemo.R
import com.fg.marvelherodemo.Utils
import com.fg.marvelherodemo.apimodel.Character
import com.fg.marvelherodemo.networking.CharactersRepository
import com.fg.marvelherodemo.ui.adapters.MarvelHeroAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import com.fg.marvelherodemo.App
import androidx.appcompat.app.AppCompatActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import android.app.AlertDialog


class HeroActivity : AppCompatActivity(), MarvelHeroAdapter.Callback, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var list : List<Character>

    private var sp: Boolean = false
    private lateinit var repository: CharactersRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
       super.onCreate(savedInstanceState)

        sp = getPreferences(Context.MODE_PRIVATE).getBoolean("pref_first_launch", true)

        repository = Utils.CharactersRepositoryProvider.provideSCharactersRepository()

        //loadHeroFromApi()

        rvHeroList.layoutManager = LinearLayoutManager(this)
        swipeRefresh.setOnRefreshListener(this)


        loadData()
    }


    private fun loadData(){
        //просто опрацюю всі сценарії, є інтернет нема\бд пуста чи ні.. і тд
        if (sp && Utils.isConnected(applicationContext)){
            loadHeroFromApi()
            val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
            val prefName = "pref_first_launch"
            sharedPreferences.edit().putBoolean(prefName, false).apply()
        }else if (!sp && Utils.isConnected(applicationContext)){
            loadHeroFromApi()
        }else if (!sp && !Utils.isConnected(applicationContext)) {
            progressBar2.visibility = View.GONE
            val db = App.getInstance().database
            val employeeDao = db.employeeDao()

            list = employeeDao.getAll()
            val adapter = MarvelHeroAdapter(list)
            rvHeroList.adapter = adapter
            adapter.setListener(this)
            swipeRefresh.isRefreshing = false
        }else {showCustomDialog()}
    }

    @SuppressLint("CheckResult")
    fun loadHeroFromApi(){
        if (Utils.isConnected(applicationContext)){
            repository.getCharacters2()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe ({
                        result ->
                        if (result == null){
                            Log.d("Result", "null")
                        }else{
                            Log.d("Result", ""+ result.code + " = "   + "\n"
                                    + " = "+ result.data.results!!.size)

//                            list = result.data.results!!
                            val adapter = MarvelHeroAdapter(result.data.results!!)
                            rvHeroList.adapter = adapter
                            adapter.setListener(this)
                            progressBar2.visibility = View.GONE
                            swipeRefresh.isRefreshing = false

                            if (sp){
                                addInDB(result.data.results!!)
                            }
                        }
                    }, { error ->
                        Log.d("Result", "AAAAAAAAAAA_6ida + ${error.message.toString()}")
                        error.printStackTrace()
                    })

        }else {
            showCustomDialog()
        }
    }

    override fun onRefresh() {
        Log.d("Result", "SWIPE")

//       loadHeroFromApi()
         loadData()
    }


    private fun addInDB(list : List<Character>){
        val db = App.getInstance().database
        val employeeDao = db.employeeDao()

        for (x in 0 until list.size) {
            employeeDao.insert(list[x])
        }
        Log.d("Result", "ADD_to_DB")

    }

    override fun onItemClicked(item: Int) {
        val i = Intent(this, HeroDescriptionActivity::class.java)
        i.putExtra("heroID", item)
        startActivity(i)
    }


    private fun showCustomDialog() {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.connection_dialog, viewGroup, false)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        dialogView.setOnClickListener { alertDialog.dismiss()  }
        alertDialog.show()
    }
}