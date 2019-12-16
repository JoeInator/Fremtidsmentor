package com.nissen.johannes.fremtidsmentor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.list_personal_option_item.view.*

class PersonalSettingsAdapter (context: Context, private val Settings: ArrayList<String>): BaseAdapter() {

    private val mContext: Context

    init {
        mContext = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val main = layoutInflater.inflate(R.layout.list_personal_option_item, parent,false)
        val option = getItem(position) as String
        main.optionsBtn.text = option
        main.optionsBtn.id = getItemId(position).toInt()
        if (option.equals(Settings[4]) or option.equals(Settings[3]) or option.isEmpty()) {
            main.skibable.visibility = View.GONE
        }
        return main
    }

    override fun getItem(position: Int): Any {
        return Settings[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return Settings.size
    }


}