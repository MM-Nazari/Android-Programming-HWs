package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.WeatherAppTheme
import com.example.iranweatherforcast.R
import com.example.weatherapp.ui.theme.Shapes
import java.io.IOException
import java.util.Locale

var selectedCity = ""
var latitude = 0.0
var longitude = 0.0

class MainActivity : ComponentActivity() {
    object LocManager{
        lateinit var locationManager: LocationManager
    }
    object GeoCoder{
        lateinit var geocoder: Geocoder
    }
    object Instance{
        lateinit var context: Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Instance.context = this
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavComposable()
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun getLocationAutomatically(){
    if(ContextCompat.checkSelfPermission(MainActivity.Instance.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        MainActivity.LocManager.locationManager = MainActivity.Instance.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        MainActivity.GeoCoder.geocoder = Geocoder(MainActivity.Instance.context, Locale.getDefault())
        val location =
            MainActivity.LocManager.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
            latitude = location.latitude
            longitude = location.longitude
            try {
                val addresses: MutableList<Address>? =
                    MainActivity.GeoCoder.geocoder.getFromLocation(latitude, longitude, 1)
                if (addresses != null) {
                    if (addresses.isNotEmpty()) {
                        selectedCity = addresses[0].locality
                        Toast.makeText(
                            MainActivity.Instance.context,
                            "selected city: $selectedCity", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            MainActivity.Instance.context,
                            "There is no such city", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        MainActivity.Instance.context,
                        "There is no such city", Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(
                    MainActivity.Instance.context,
                    "Turn on location", Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                MainActivity.Instance.context,
                "Turn on location", Toast.LENGTH_SHORT
            ).show()
        }
    }
    else{
        Toast.makeText(
            MainActivity.Instance.context,
            "Confirm location permission", Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun Menu(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(250.dp))
        Button(
            onClick = {getLocationAutomatically()},
            modifier
                .requiredHeight(100.dp)
                .requiredWidth(200.dp),
            shape = Shapes.readLocationButtons
        ) {
            Text(stringResource(R.string.auto),
                style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = modifier.height(12.dp))
        Button(
            onClick = {navController.navigate("ChooseCity")},
            modifier
                .requiredHeight(100.dp)
                .background(Color(R.color.purple_200))
                .requiredWidth(200.dp),
            shape = Shapes.readLocationButtons
        ) {
            Text(stringResource(R.string.list),
                style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = modifier.height(12.dp))
        Button(
            onClick = {navController.navigate("ShowWeatherPollution")},
            modifier
                .requiredHeight(100.dp)
                .requiredWidth(200.dp),
            shape = Shapes.readLocationButtons
        ) {
            Text(stringResource(R.string.res),
                style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCity(navController: NavController, modifier: Modifier = Modifier){
    val firstItems = arrayOf("Tehran", "Alborz", "Qazvin", "Mazandaran", "Ilam")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var secondItems = arrayOf("")
    var secondExpanded by remember { mutableStateOf(false) }
    var selectedTown by remember { mutableStateOf("") }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(150.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                firstItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(12.dp))
        ExposedDropdownMenuBox(
            expanded = secondExpanded,
            onExpandedChange = {
                secondExpanded = !secondExpanded
            }
        ) {
            TextField(
                value = selectedTown,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = secondExpanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = secondExpanded,
                onDismissRequest = { expanded = false }
            ) {
                when(selectedText){
                    "Tehran" -> secondItems = arrayOf("Shahriar", "Ghods", "Malard")
                    "Alborz" -> secondItems = arrayOf("Fardis", "Mohammad Shahr", "Meshkin Dasht")
                    "Qazvin" -> secondItems = arrayOf("Qazvin", "Takestan", "Mohammadieh")
                    "Mazandaran" -> secondItems = arrayOf("Sari", "Babol", "Amol")
                    "Ilam" -> secondItems = arrayOf("Ilam", "Ivan", "Mehran")
                }
                secondItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedTown = item
                            expanded = false
                            selectedCity = selectedTown
                            if(selectedCity != "") {
                                Toast.makeText(
                                    MainActivity.Instance.context,
                                    "selected city: $selectedCity", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
            }
        }
    }
}


var emptyList: List<Weather> = listOf()

@Composable
fun ShowWeatherPollution(navController: NavController, modifier: Modifier = Modifier){

    LaunchedEffect(true) {
        val response = RetrofitInstance.api.getWeather()
        Log.d("test",response.toString())
        Log.e("test",response.toString())
        emptyList = response.body()!!

    }

    Text(emptyList.toString())
    /*
    val coroutineScope = rememberCoroutineScope()
    val response = {
        coroutineScope.launch {
            RetrofitInstance.api.getWeather()
        }
    }
    Text(response.toString())
    */
}

@Composable
fun NavComposable(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Menu"
    ){
        composable("Menu"){ Menu(navController) }
        composable("ChooseCity"){ ChooseCity(navController) }
        composable("ShowWeatherPollution"){ ShowWeatherPollution(navController) }
    }
}





/*
class CurrentWeatherdata(// set the data types of values that we want to extract
    var placename: String, var desc: String, var temp: Int, var clouds: Int
) {

    companion object {
        @Throws(JSONException::class)
        fun serialize(json: String?): CurrentWeatherdata {
            val root = JSONObject(json)
            val name = root.getString("name")
            val temp = root.getJSONObject("main").getInt("temp")
            val clouds = root.getJSONObject("clouds").getInt("all")
            var desc = ""
            val weatherarray = root.getJSONArray("weather")
            if (weatherarray.length() > 0) {
                desc = (weatherarray[0] as JSONObject).getString("description")
            }
            return CurrentWeatherdata(name, desc, temp, clouds)
        }
    }
}

const val baseURL = "http://api.openweathermap.org/data/2.5/weather"
const val API_key = "fa1be7c55be9f3b7522698134708ecb2"
var URL: String? = null
var cityname: EditText? = null

private fun generateWeatherbycity() {
    URL =
        BaseURL + "?q=" + cityname!!.text + "&lang=en&units=metric&appid=" + API_key
}

private fun generateweatherbylocation() {
    lastrequestURL =
        MainActivity.URL + "?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&lang=en&units=metric&appid=" + getString(
            R.string.API_KEY
        )
}
*/

/*
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
*/
/*
interface WeatherApiService {
    @GET("photos")
    suspend fun getData(): List<WeatherData>
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}
*/

/*
private fun loaddata(): Pair<CurrentWeatherdata?, String?>? {
    var result: Pair<CurrentWeatherdata?, String?> = Pair<Any?, Any?>(null, "ERROR")
    var inputStream: InputStream? = null
    try {
        val url = URL(URL)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connectTimeout = 15000
        conn.readTimeout = 10000
        conn.doInput = true
        conn.connect()
        when (conn.responseCode) {
            HttpURLConnection.HTTP_OK -> {
                inputStream = BufferedInputStream(conn.inputStream)
                result = Pair<Any?, Any?>(
                    CurrentWeatherdata.serialize(
                        org.apache.commons.io.IOUtils.toString(
                            inputStream,
                            "UTF-8"
                        )
                    ), "OK"
                ).toString()
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> result = Pair(null, "NOT AUTHORIZED")
            HttpURLConnection.HTTP_NOT_FOUND -> result = Pair(null, "NOT FOUND")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: JSONException) {
        e.printStackTrace()
    } finally {
        if (inputStream != null) {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return result
}
*/