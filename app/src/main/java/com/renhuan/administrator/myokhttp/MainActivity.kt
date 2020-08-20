package com.renhuan.administrator.myokhttp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.renhuan.okhttplib.utils.Renhuan
import com.wuyr.activitymessenger.ActivityMessenger
import kotlinx.android.synthetic.main.activity_main2.*
import me.jingbin.library.adapter.BaseByViewHolder
import me.jingbin.library.adapter.BaseRecyclerAdapter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val adapter = RecyclerAdapter()
        recyclerView.adapter = adapter

        //fill with empty objects
        val list: MutableList<ExpansionModel> = ArrayList()
        for (i in 0..29) {
            list.add(ExpansionModel())
        }
        adapter.setItems(list)
    }

    companion object {
        fun startAction(context: Context) {
            ActivityMessenger.startActivity<MainActivity>(context)
        }
    }

    class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>() {
        private val list: MutableList<ExpansionModel> = ArrayList()
        private val expansionsCollection = ExpansionLayoutCollection()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
            return RecyclerHolder.buildFor(parent)
        }

        override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
            holder.bind(list[position])
            expansionsCollection.add(holder.expansionLayout)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setItems(items: List<ExpansionModel>?) {
            list.addAll(items!!)
            notifyDataSetChanged()
        }

        class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var expansionLayout: ExpansionLayout

            fun bind(expansionModel: ExpansionModel?) {
                expansionLayout.collapse(expansionModel?.isExpansion!!)
                expansionLayout.addListener { _, expanded ->

                    println("-------" + expanded)
                    expansionModel.isExpansion = expanded
                }
            }

            companion object {
                private val LAYOUT: Int = R.layout.item_main
                fun buildFor(viewGroup: ViewGroup): RecyclerHolder {
                    return RecyclerHolder(LayoutInflater.from(viewGroup.context).inflate(LAYOUT, viewGroup, false))
                }
            }

            init {
                expansionLayout = itemView.findViewById(R.id.expansionLayout)
            }
        }

        init {
            expansionsCollection.openOnlyOne(true)
        }
    }
}