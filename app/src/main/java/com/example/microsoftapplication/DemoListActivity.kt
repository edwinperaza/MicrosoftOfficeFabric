package com.example.microsoftapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.officeuifabric.listitem.ListItemDivider
import com.microsoft.officeuifabric.listitem.ListItemView
import kotlinx.android.synthetic.main.activity_demo_list.*
import kotlinx.android.synthetic.main.demo_list.*

/**
 * This activity presents a list of [Demo]s, which when touched,
 * lead to a subclass of [DemoActivity] representing demo details.
 */
class DemoListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Launch Screen: Setting the theme here removes the launch screen, which was added to this activity
        // by setting the theme to App.Launcher in the manifest.
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_demo_list)

        setSupportActionBar(toolbar)
        toolbar.title = title
        toolbar.subtitle = com.microsoft.officeuifabric.BuildConfig.VERSION_NAME

        demo_list.adapter = DemoListAdapter()
        demo_list.addItemDecoration(ListItemDivider(this, DividerItemDecoration.VERTICAL))

//        Initilizer.init(application)
    }

    private class DemoListAdapter : RecyclerView.Adapter<DemoListAdapter.ViewHolder>() {
        private val onClickListener = View.OnClickListener { view ->
            val demo = view.tag as Demo
            val intent = Intent(view.context, demo.demoClass.java)
            intent.putExtra(DemoActivity.DEMO_ID, demo.id)
            view.context.startActivity(intent)
        }

        override fun getItemCount(): Int = DEMOS.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val listItemView = ListItemView(parent.context)
            listItemView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            return ViewHolder(listItemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val demo = DEMOS[position]
            holder.listItem.title = demo.title
            with(holder.itemView) {
                tag = demo
                setOnClickListener(onClickListener)
            }
        }

        private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val listItem: ListItemView = view as ListItemView
        }
    }
}
