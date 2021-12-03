package app.platinummzadat.qa.views.root.product_search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import app.platinummzadat.qa.MzActivity
import app.platinummzadat.qa.R
import app.platinummzadat.qa.trendingSearch
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*
import org.json.JSONArray
import raj.nishin.wolfpack.hideKeyboard
import raj.nishin.wolfpack.itemOffset
import raj.nishin.wolfpack.layoutManager
import raj.nishin.wolfpack.wlog

class ProductSearchActivity : MzActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rvSearch layoutManager LinearLayoutManager(this)
        rvSearch itemOffset 24
        wlog(trendingSearch)

        val searchData = ArrayList<String>().apply {
            with(JSONArray(trendingSearch)){
                for (i in 0 until length()){
                    this@apply.add(getString(i))
                }
            }

        }
        rvSearch.adapter = ProductTrendingSearchAdapter(searchData) {

            submitQuery(it)
        }
        /*rvSearch.adapter = TrendingSearchAdapter(ArrayList<String>().apply {
            add("Mercedes Benz C-300")
            add("BMW X5 2019")
            add("Jaguar XF")
            add("Porsche Panamera")
            add("Ferrari 458 Italia")
            add("Tesla Model - S")
            add("Farady Future FF-91")
        }) {
            submitQuery(it)
        }*/
    }

    private fun submitQuery(query: String) {
        hideKeyboard()

        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("query", query)
        })
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        with(((menu.findItem(R.id.actionSearch).actionView) as SearchView)) {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (null != query) {
                        submitQuery(query)
                        //Toast.makeText(applicationContext,""+query,Toast.LENGTH_LONG).show()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {

                    return false
                }

            })
            isIconified = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSearch -> {
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        hideKeyboard()
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
