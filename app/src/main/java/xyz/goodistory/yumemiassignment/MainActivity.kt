package xyz.goodistory.yumemiassignment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.goodistory.yumemiassignment.ContributorDetailActivity.Companion.BUNDLE_NAME_LOGIN
import xyz.goodistory.yumemiassignment.http.Contributor
import xyz.goodistory.yumemiassignment.http.GitHubService


class MainActivity : AppCompatActivity() {
    companion object {
        fun requestAndShowContributors(adapter: ContributorsListAdapter, context: Context) {
            val service = GitHubService.getGitHubService(context)

            val response = service.getContributors()
            response.enqueue(object : Callback<List<Contributor>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<List<Contributor>>,
                    response: Response<List<Contributor>>
                ) {
                    if (!response.isSuccessful) {
                        return
                    }

                    val contributors:List<Contributor>? = response.body()
                    adapter.run {
                        contributors?.forEach {
                            addRow(it)
                        }
                        notifyDataSetChanged()
                    }

                }

                override fun onFailure(call: Call<List<Contributor>>, t: Throwable?) {
                }

            })
        }

        /**
         * ??????????????????????????????????????????????????????????????????????????????????????????
         */
        fun initList(listAdapter: ContributorsListAdapter, activity: AppCompatActivity) {
            activity.findViewById<RecyclerView>(R.id.contributor_list).apply {
                setHasFixedSize(true)

                // ????????????????????????
                layoutManager = LinearLayoutManager(activity)
                
                // item??????????????????
                addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
                adapter = listAdapter
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ????????????????????????????????????
        val listAdapter = ContributorsListAdapter(mutableListOf(), this)
        initList(listAdapter, this)

        // API?????????????????????????????????????????????????????????
        requestAndShowContributors(listAdapter, this)
    }


    /**
     * ContributorsList ??? RecyclerView.Adapter
     */
    class ContributorsListAdapter(
        private val contributorList: MutableList<Contributor>,
        private val context: Context
        ): RecyclerView.Adapter<ContributorsListAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val iconImage: AppCompatImageView = view.findViewById(R.id.contributors_list_item_icon)
            val loginTextView: AppCompatTextView = view.findViewById(R.id.contributors_list_item_login)
            val typeTextView: AppCompatTextView = view.findViewById(R.id.contributors_list_item_type_text)
            val contributionsTextView: AppCompatTextView = view.findViewById(R.id.contributors_list_item_contributions_text)
            val detailButton: AppCompatButton = view.findViewById(R.id.contributors_list_item_detail)
        }

        fun addRow(row: Contributor) {
            contributorList.add(row)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_contributor, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            // ??????????????????????????????
            Picasso.get()
                .load(contributorList[position].avatar_url)
                // ???????????????????????????????????????????????????
                .placeholder(R.drawable.no_image)
                // ????????????????????????????????????
                .error(R.drawable.no_image)
                .into(viewHolder.iconImage);

            // ?????????????????????
            viewHolder.loginTextView.text = contributorList[position].login
            viewHolder.typeTextView.text = contributorList[position].type
            viewHolder.contributionsTextView.text = contributorList[position].contributions.toString()

            // ???????????????????????????????????????????????????????????????????????????
            viewHolder.detailButton.setOnClickListener {
                val intent = Intent(context, ContributorDetailActivity::class.java).apply {
                    putExtra(BUNDLE_NAME_LOGIN, contributorList[position].login)
                }
                context.startActivity(intent)
            }
        }

        override fun getItemCount() = contributorList.size

    }
}