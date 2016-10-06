package com.stzemo.kotlinfirst

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.stzemo.kotlinfirst.adapters.weatherAdapter.WeatherAdapter
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    private var listDays: ArrayList<String> = ArrayList()
//    private var recyclerViewWeather: RecyclerView? // TODO Make this work, initialize variable without any assign to it
//    private var weatherAdapter: WeatherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            Log.d("PlaceholderFragment", "btn press")}

        val recyclerViewWeather = findViewById(R.id.rvWeather) as RecyclerView
        FetchWeatherTask().execute()

        listDays.add("Monday")
        listDays.add("Tuesday")
        listDays.add("Wednesday")
        listDays.add("Thursday")
        listDays.add("Friday")
        listDays.add("Suturday")
        listDays.add("Sunday")

        val weatherAdapter = WeatherAdapter(listDays)
        recyclerViewWeather.setAdapter(weatherAdapter)
        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recyclerViewWeather.setLayoutManager(mLayoutManager)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    class FetchWeatherTask : AsyncTask<Void?, Void?, Void>() {

        override fun doInBackground(vararg p0: Void?): Void? {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            var urlConnection: HttpURLConnection? = null
            var reader: BufferedReader? = null

            // Will contain the raw JSON response as a string.
            var forecastJsonStr: String?


            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                val url: URL = URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=Kiev&mode=json&units=metric&cnt=7&APPID=3eb83e4dba6a510029ff53974c868c51")
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.setRequestMethod("GET")
                urlConnection.connect()
                // Read the input stream into a String
                val inputStream: InputStream = urlConnection.getInputStream()
                val buffer: StringBuffer = StringBuffer()
                if (inputStream == null) {
                    // Nothing to do.
                    return null
                }
                reader = BufferedReader(InputStreamReader(inputStream))

                var line: String?
//                while ((line = reader.readLine()) != null) {
//                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                    // But it does make debugging a *lot* easier if you print out the completed
//                    // buffer for debugging.
//                    buffer.append(line + "\n")
//                }
                do {
                    line = reader.readLine()
                    if (line == null)
                        break
                    buffer.append(line + "\n")
                } while (true)

                if (buffer.length == 0) {
                    // Stream was empty.  No point in parsing.
                    return null
                }
                forecastJsonStr = buffer.toString()
                Log.d("PlaceholderFragment", "Text = " + forecastJsonStr)
            } catch (e: IOException) {
                Log.e("PlaceholderFragment", "Error " + e)
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        Log.d("PlaceholderFragment", "Error closing stream", e)
                    }
                }
            }

            return null
        }

    }
}
