package xyz.goodistory.yumemiassignment

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
//    companion object {
//        /**
//         * Contributorのリストを初期化
//         */
//        fun initContributorList(list: RecyclerView, context: Context) {
//            list.run {
//                setHasFixedSize(true)
//                layoutManager = LinearLayoutManager(context)
//                adapter = ContributorsListAdapter(
//                    mutableListOf()
//                )
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ContributorsListAdapter(mutableListOf())
        findViewById<RecyclerView>(R.id.contributor_list).run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }


        // TODO URL は string へ
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GitHubService::class.java)

        val response = service.getContributors()
        response.enqueue(object : Callback<List<GitHubService.Contributor>> {
            override fun onResponse(
                call: Call<List<GitHubService.Contributor>>,
                response: Response<List<GitHubService.Contributor>>
            ) {
                if (!response.isSuccessful) {
                    return
                }

                val contributors:List<GitHubService.Contributor>? = response.body()
                contributors?.forEach {
                    adapter.addRow(ContributorsListAdapter.ContributorRow(it.login, it.id))
                }
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<GitHubService.Contributor>>, t: Throwable?) {
            }

        })
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