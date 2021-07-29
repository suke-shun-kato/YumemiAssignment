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

class MainActivity : AppCompatActivity() {
    companion object {
        /**
         * Contributorのリストを初期化
         */
        fun initContributorList(list: RecyclerView, context: Context) {
            list.setHasFixedSize(true)
            list.layoutManager = LinearLayoutManager(context)
            list.adapter = ContributorsListAdapter(
                listOf(ContributorsListAdapter.Row("aaaa", "bbbb"),
                    ContributorsListAdapter.Row("ccc", "ddddddddddddddddddddddddddddddddddd"),
                    ContributorsListAdapter.Row("eeee", "ffffffffffffffffffff")))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = findViewById<RecyclerView>(R.id.contributor_list)
        initContributorList(list, this)
    }


    /**
     * ContributorsList の RecyclerView.Adapter
     */
    class ContributorsListAdapter(private val contributorList: List<Row>) : RecyclerView.Adapter<ContributorsListAdapter.ViewHolder>() {
        class Row(val login: String, val id: String)

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val loginTextView: AppCompatTextView = view.findViewById(R.id.contributors_list_item_login)
            val idTextView: AppCompatTextView = view.findViewById(R.id.contributors_list_item_id)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_contributor, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.loginTextView.text = contributorList[position].login
            viewHolder.idTextView.text = contributorList[position].id
        }

        override fun getItemCount() = contributorList.size

    }
}