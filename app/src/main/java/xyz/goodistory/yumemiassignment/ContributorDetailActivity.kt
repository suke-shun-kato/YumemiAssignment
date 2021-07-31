package xyz.goodistory.yumemiassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView

class ContributorDetailActivity : AppCompatActivity() {
    companion object {
        const val BUNDLE_NAME_LOGIN = "login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contributor_detail)

        // TODO 暫定
        findViewById<AppCompatTextView>(R.id.aaaaa).text = intent.getStringExtra(BUNDLE_NAME_LOGIN)
    }
}