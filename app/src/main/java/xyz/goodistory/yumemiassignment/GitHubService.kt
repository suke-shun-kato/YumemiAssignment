package xyz.goodistory.yumemiassignment

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("repos/googlesamples/android-architecture-components/contributors")
    fun getContributors(): Call<List<Contributor>>

    @GET("users/{login}")
    fun getUser(@Path("login") login: String): Call<User>

    data class Contributor(
        val login: String,
        val id: Int,
        val node_id: String,
        val avatar_url: String,
        val gravatar_id: String,
        val url: String,
        val html_url: String,
        val followers_url: String,
        val following_url: String,
        val gists_url: String,
        val starred_url: String,
        val subscriptions_url: String,
        val organizations_url: String,
        val repos_url: String,
        val events_url: String,
        val received_events_url: String,
        val type: String,
        val site_admin: Boolean,
        val contributions: Int,
        )

    data class User(
        val login: String,
        val id: Int,
        val node_id: String,
        val avatar_url: String,
        val gravatar_id: String,
        val url: String,
        val html_url: String,
        val followers_url: String,
        val following_url: String,
        val gists_url: String,
        val starred_url: String,
        val subscriptions_url: String,
        val organizations_url: String,
        val repos_url: String,
        val events_url: String,
        val received_events_url: String,
        val type: String,
        val site_admin: Boolean,
        val name: String,
        val company: String,
        val blog: String,
        val location: String,
        val email: String?,
        val hireable: String?,
        val bio: String?,
        val twitter_username: String,
        val public_repos: Int,
        val public_gists: Int,
        val followers: Int,
        val following: Int,
        val created_at: String,
        val updated_at: String
    )
}