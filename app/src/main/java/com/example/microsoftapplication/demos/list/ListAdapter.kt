/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.example.microsoftapplication.demos.list

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.microsoftapplication.R
import com.microsoft.officeuifabric.listitem.ListItemView
import com.microsoft.officeuifabric.listitem.ListSubHeaderView
import com.microsoft.officeuifabric.snackbar.Snackbar
import java.util.*

class ListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private enum class ViewType {
        SUB_HEADER, ITEM
    }

    var listItems = ArrayList<IBaseListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewTypeOrdinal: Int): RecyclerView.ViewHolder {
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return when (ViewType.values()[viewTypeOrdinal]) {
            ViewType.SUB_HEADER -> {
                val subHeaderView = ListSubHeaderView(context)
                subHeaderView.layoutParams = lp
                ListSubHeaderViewHolder(subHeaderView)
            }
            ViewType.ITEM -> {
                val listItemView = ListItemView(context)
                listItemView.layoutParams = lp
                ListItemViewHolder(listItemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listItem = listItems[position]

        if (listItem is IListSubHeader)
            (holder as? ListSubHeaderViewHolder)?.setListSubHeader(listItem)

        if (listItem is IListItem)
            (holder as? ListItemViewHolder)?.setListItem(listItem)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        (holder as? ListItemViewHolder)?.clearCustomViews()
    }

    override fun getItemCount(): Int = listItems.size

    override fun getItemViewType(position: Int): Int {
        return if (listItems[position] is ListSubHeader)
            ViewType.SUB_HEADER.ordinal
        else
            ViewType.ITEM.ordinal
    }

    private inner class ListItemViewHolder : RecyclerView.ViewHolder {
        private val listItemView: ListItemView

        constructor(view: ListItemView) : super(view) {
            listItemView = view
            listItemView.setOnClickListener {
                Snackbar.make(listItemView, context.resources.getString(R.string.list_item_click), Snackbar.LENGTH_SHORT).show()
            }
        }

        fun setListItem(listItem: IListItem) {
            listItemView.setListItem(listItem)
        }

        fun clearCustomViews() {
            listItemView.customView = null
            listItemView.customAccessoryView = null
        }
    }

    private class ListSubHeaderViewHolder : RecyclerView.ViewHolder {
        private val listSubHeaderView: ListSubHeaderView

        constructor(view: ListSubHeaderView) : super(view) {
            listSubHeaderView = view
        }

        fun setListSubHeader(listSubHeader: IListSubHeader) {
            listSubHeaderView.setListSubHeader(listSubHeader)
        }
    }
}

fun ListItemView.setListItem(listItem: IListItem) {
    title = listItem.title
    subtitle = listItem.subtitle
    footer = listItem.footer

    titleMaxLines = listItem.titleMaxLines
    subtitleMaxLines = listItem.subtitleMaxLines
    footerMaxLines = listItem.footerMaxLines

    titleTruncateAt = listItem.titleTruncateAt
    subtitleTruncateAt = listItem.subtitleTruncateAt
    footerTruncateAt = listItem.footerTruncateAt

    customView = listItem.customView
    customViewSize = listItem.customViewSize
    customAccessoryView = listItem.customAccessoryView

    layoutDensity = listItem.layoutDensity
}

fun ListSubHeaderView.setListSubHeader(listSubHeader: IListSubHeader) {
    title = listSubHeader.title
    titleColor = listSubHeader.titleColor
    customAccessoryView = listSubHeader.customAccessoryView
}