package xyz.goodistory.yumemiassignment

import android.annotation.SuppressLint
import android.content.Context
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


class MainActivity : AppCompatActivity() {
    companion object {
        fun requestAndShowContributors(adapter: ContributorsListAdapter, context: Context) {
            val retrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_endpoint))
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(GitHubService::class.java)

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
        val listAdapter = ContributorsListAdapter(mutableListOf())
        findViewById<RecyclerView>(R.id.contributor_list).run {
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
    class ContributorsListAdapter(private val contributorRowList: MutableList<ContributorRow>)
        : RecyclerView.Adapter<ContributorsListAdapter.ViewHolder>() {

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
            viewHolder.loginTextView.text = contributorRowList[position].login
            viewHolder.idTextView.text = contributorRowList[position].id.toString()
        }

        override fun getItemCount() = contributorRowList.size

    }
}