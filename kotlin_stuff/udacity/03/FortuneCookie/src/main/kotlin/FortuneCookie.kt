//import okhttp3.OkHttpClient
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

// Data class to represent the JSON response. Save the "fortune" field as "text"
data class Fortune (@SerializedName("fortune") var text: String = ""){}

class FortuneCookie {

    private var endpoint: String
    private var request: Request
    private var client: OkHttpClient

    constructor(endpoint: String){
        this.endpoint = endpoint
        request = Request.Builder()
            .url(endpoint)
            .build()
        client = OkHttpClient()
    }

    fun getEndpoint(): String = this.endpoint

    fun getFortune(): String {
        val response: Response = client.newCall(request).execute()
        var fortune: Fortune = Gson().fromJson(response.body?.string(), Fortune::class.java)
        return fortune.text
    }

}