package xyz.goodistory.yumemiassignment.http

import android.content.Context
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import xyz.goodistory.yumemiassignment.R

interface GitHubService {
    @GET("repos/googlesamples/android-architecture-components/contributors")
    fun getContributors(): Call<List<Contributor>>

    @GET("users/{login}")
    fun getUser(@Path("login") login: String): Call<User>

    companion object {
        fun getGitHubService(context: Context): GitHubService {
            val retrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_endpoint))
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(GitHubService::class.java)
        }
    }
}