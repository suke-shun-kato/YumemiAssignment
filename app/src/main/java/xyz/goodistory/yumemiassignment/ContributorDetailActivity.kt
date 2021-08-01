package xyz.goodistory.yumemiassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContributorDetailActivity : AppCompatActivity() {
    private lateinit var loginTextView: AppCompatTextView
    private lateinit var idTextView: AppCompatTextView
    private lateinit var locationTextView: AppCompatTextView
    private lateinit var companyTextView: AppCompatTextView


    companion object {
        const val BUNDLE_NAME_LOGIN = "login"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contributor_detail)

        // ビューをセット
        loginTextView = findViewById(R.id.contributor_detail_login)
        idTextView = findViewById(R.id.contributor_detail_id)
        locationTextView = findViewById(R.id.contributor_detail_location)
        companyTextView = findViewById(R.id.contributor_detail_company)


        // ユーザー情報を表示
        val login = intent.getStringExtra(BUNDLE_NAME_LOGIN)
        if (login != null) {
            requestAndShowUser(login)
        }
    }

    private fun requestAndShowUser(login: String) {
        val service = GitHubService.getGitHubService(this)

        // リクエスト
        val response = service.getUser(login)

        // レスポンスを処理
        response.enqueue(object : Callback<GitHubService.User> {
            override fun onResponse(
                call: Call<GitHubService.User>,
                response: Response<GitHubService.User>
            ) {
                if (!response.isSuccessful) {
                    return
                }

                // ビューにセット
                val user: GitHubService.User = response.body() ?: return
                loginTextView.text = user.login
                idTextView.text = user.id.toString()
                locationTextView.text = user.location
                companyTextView.text = user.company
            }

            override fun onFailure(call: Call<GitHubService.User>, t: Throwable?) {
            }

        })

    }
}