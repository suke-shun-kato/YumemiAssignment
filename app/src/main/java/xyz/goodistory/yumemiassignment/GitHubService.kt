package xyz.goodistory.yumemiassignment

import retrofit2.Call
import retrofit2.http.GET

interface GitHubService {
    @GET("repos/googlesamples/android-architecture-components/contributors")
    fun getContributors(): Call<List<Contributor>>

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
}