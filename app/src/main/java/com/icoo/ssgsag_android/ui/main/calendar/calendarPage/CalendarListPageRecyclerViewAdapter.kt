package com.icoo.ssgsag_android.ui.main.calendar.calendarPage

import android.util.Log
import android.view.*
import android.view.View.GONE
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.icoo.ssgsag_android.R
import com.icoo.ssgsag_android.data.model.schedule.Schedule
import com.icoo.ssgsag_android.databinding.ItemCalendarListBinding
import com.icoo.ssgsag_android.util.extensionFunction.setSafeOnClickListener
import kotlinx.android.synthetic.main.item_calendar_list.*

class CalendarListPageRecyclerViewAdapter(
    var itemList: ArrayList<Schedule>
) : RecyclerView.Adapter<CalendarListPageRecyclerViewAdapter.ViewHolder>() {

    private var listener: OnScheduleItemClickListener? = null

    fun setOnScheduleItemClickListener(listener: OnScheduleItemClickListener) {
        this.listener = listener
    }

    fun replaceAll(array: ArrayList<Schedule>?) {
        if(array?.size == 0)
            itemList.clear()

        if (itemList.size > 0) {
            for (i in itemList.indices) {
                if (i > 0 &&
                    itemList[i].posterEndDate.substring(8, 10)
                    == itemList[i - 1].posterEndDate.substring(8, 10)) {
                    itemList[i].isAlone = false
                }else{
                    itemList[i].isAlone = true
                }

                if(i < itemList.size-1 &&
                    itemList[i].posterEndDate.substring(8,10) == itemList[i+1].posterEndDate.substring(8,10)){
                    itemList[i].isLast = false
                }else{
                    itemList[i].isLast = true
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<ItemCalendarListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_calendar_list, parent, false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBinding.schedule = itemList[position]
        holder.dataBinding.root.setSafeOnClickListener {
            listener?.onItemClicked(itemList[position].posterIdx)
        }

        holder.dataBinding.itemCalendarListIvFavorite.setSafeOnClickListener {
            listener?.onBookmarkClicked(itemList[position].posterIdx, itemList[position].isFavorite)
        }

        holder.dataBinding.itemCalendarListIvSelector.setSafeOnClickListener {

            if(itemList[position].selectType == 2) { // 선택된 상태
                holder.dataBinding.itemCalendarListIvSelector.isSelected = false
                itemList[position].selectType = 1 // 취소
            }
            else { // 선택되지 않은 상태
                holder.dataBinding.itemCalendarListIvSelector.isSelected = true
                itemList[position].selectType = 2 // 선택
            }

            listener?.onSelectorClicked(itemList[position].posterIdx, itemList[position].posterName,
                holder.dataBinding.itemCalendarListIvSelector.isSelected)
        }
    }

    override fun getItemId(position: Int): Long {
        return itemList[position].posterIdx.toLong()
    }

    fun getItemDate(position: Int): String{
        return itemList[position].posterEndDate.substring(0, 7)
    }

    inner class ViewHolder(val dataBinding: ItemCalendarListBinding) : RecyclerView.ViewHolder(dataBinding.root)

    interface OnScheduleItemClickListener {
        fun onItemClicked(posterIdx: Int)
        fun onBookmarkClicked(posterIdx: Int, isFavorite: Int)
        fun onSelectorClicked(posterIdx: Int, posterName: String, isSelected:Boolean)
    }

    fun setSelectType(type: Int){

        for(i in itemList.indices)
            itemList[i].selectType = type
    }

    fun getSelectType() : Boolean {
        for (i in itemList.indices) {
            if (itemList[i].selectType == 2) {
                return true
            }
        }
        return false
    }

}