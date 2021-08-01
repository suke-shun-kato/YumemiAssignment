package xyz.goodistory.yumemiassignment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.goodistory.yumemiassignment.ContributorDetailActivity.Companion.BUNDLE_NAME_LOGIN


class MainActivity : AppCompatActivity() {
    companion object {
        fun requestAndShowContributors(adapter: ContributorsListAdapter, context: Context) {
            val service = Common.getGitHubService(context)

            val response = service.getContributors()
            response.enqueue(object : Callback<List<GitHubService.Contributor>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<List<GitHubService.Contributor>>,
                    response: Response<List<GitHubService.Contributor>>
                ) {
                    if (!response.isSuccessful) {
                        return
                    }

                    val contributors:List<GitHubService.Contributor>? = response.body()
                    adapter.run {
                        contributors?.forEach {
                            addRow(ContributorsListAdapter.ContributorRow(it.login, it.id))
                        }
                        notifyDataSetChanged()
                    }

                }

                override fun onFailure(call: Call<List<GitHubService.Contributor>>, t: Throwable?) {
                }

            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // リサイクラービューの設定
        val listAdapter = ContributorsListAdapter(mutableListOf(), this)
        findViewById<RecyclerView>(R.id.contributor_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            this.adapter = listAdapter
        }





        // APIでコントリビューター情報を取得して表示
        requestAndShowContributors(listAdapter, this)
    }


    /**
     * ContributorsList の RecyclerView.Adapter
     */
    class ContributorsListAdapter(
        private val contributorRowList: MutableList<ContributorRow>,
        private val context: Context
        ): RecyclerView.Adapter<ContributorsListAdapter.ViewHolder>() {

        /**
         * 1行分のデータを保持するクラス
         */
        data class ContributorRow(val login: String, val id: Int)

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val loginTextView: AppCompatTextView = view.findViewById(R.id.contributors_list_item_login)
            val idTextView: AppCompatTextView = view.findViewById(R.id.contributors_list_item_id)

        }

        fun addRow(row: ContributorRow) {
            contributorRowList.add(row)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_contributor, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            // 行に文字を表示
            viewHolder.loginTextView.text = contributorRowList[position].login
            viewHolder.idTextView.text = contributorRowList[position].id.toString()

            // クリックしたらページ飛ぶようにした（暫定） TODO ちゃんとする
            viewHolder.loginTextView.setOnClickListener {
                val intent = Intent(context, ContributorDetailActivity::class.java).apply {
                    putExtra(BUNDLE_NAME_LOGIN, contributorRowList[position].login)
                }
                context.startActivity(intent)
            }
        }

        override fun getItemCount() = contributorRowList.size

    }
}