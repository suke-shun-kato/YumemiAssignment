package xyz.goodistory.yumemiassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.goodistory.yumemiassignment.http.GitHubService
import xyz.goodistory.yumemiassignment.http.User

class ContributorDetailActivity : AppCompatActivity() {
    private lateinit var iconImage: AppCompatImageView
    private lateinit var loginTextView: AppCompatTextView
    private lateinit var nameTextView: AppCompatTextView
    private lateinit var typeTextView: AppCompatTextView
    private lateinit var locationTextView: AppCompatTextView
    private lateinit var companyTextView: AppCompatTextView


    companion object {
        const val BUNDLE_NAME_LOGIN = "login"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contributor_detail)

        // ビューをセット
        iconImage = findViewById(R.id.contributor_detail_icon)
        loginTextView = findViewById(R.id.contributor_detail_login)

        nameTextView = findViewById(R.id.contributor_detail_name_text)
        typeTextView = findViewById(R.id.contributor_detail_type_text)
        locationTextView = findViewById(R.id.contributor_detail_location_text)
        companyTextView = findViewById(R.id.contributor_detail_company_text)


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
        response.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (!response.isSuccessful) {
                    return
                }

                val user: User = response.body() ?: return

                // アイコン画像をセット
                Picasso.get()
                    .load(user.avatar_url)
                    .into(iconImage)

                // 文字列をセット
                loginTextView.text = user.login

                nameTextView.text = user.name
                locationTextView.text = user.location
                typeTextView.text = user.type
                companyTextView.text = user.company
            }

            override fun onFailure(call: Call<User>, t: Throwable?) {
            }

        })

    }
}