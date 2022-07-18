package dev.acuon.sessions.ui

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ItemLayoutCalendarBinding
import dev.acuon.sessions.model.Day
import dev.acuon.sessions.utils.Extensions.visible
import dev.acuon.sessions.viewmodel.CalendarVM
import java.time.LocalDate
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class CalendarDaysAdapter(
    private var dateList: ArrayList<Day>,
    private val clickListener: DateClickListener,
    private val currentDate: String,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: CalendarVM
) : RecyclerView.Adapter<CalendarDaysAdapter.ViewHolder>() {

    val bool = BooleanArray(dateList.size)
    private val itemViewList = ArrayList<ItemLayoutCalendarBinding>()
    private var selectedDate: LocalDate? = null
    private var curDate: List<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = DataBindingUtil.inflate<ItemLayoutCalendarBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_layout_calendar,
            parent,
            false
        )
        itemViewList.add(viewBinding)
        return ViewHolder(viewBinding, clickListener, lifecycleOwner, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = dateList[position]
        holder.setData(day)
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    inner class ViewHolder(
        private val viewBinding: ItemLayoutCalendarBinding,
        private val clickListener: DateClickListener,
        private val lifeOwner: LifecycleOwner,
        private val viewModel: CalendarVM
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        internal fun setData(day: Day) {
            viewBinding.apply {
                tvDay.text = day.date.toString()
                if (!day.monthDate) {
                    tvDay.setTextColor(
                        ContextCompat.getColor(
                            viewBinding.root.context,
                            R.color.grey
                        )
                    );
                }
                //yyyy-MM-dd
                var today = currentDate.substring(0, currentDate.length - 2)
                today += day.date.toString()

                selectedDate = LocalDate.now()
                curDate = selectedDate.toString().split("-")

                viewModel.getReminderByDate(today).observe(lifeOwner, androidx.lifecycle.Observer {
                    if (day.monthDate && it.isNotEmpty()) {
                        eventsCardView.visible()
                    }
                })

                val ar = currentDate.split("-")
                if (curDate!![2] == dateList[adapterPosition].date.toString() && curDate!![0] == ar[0] && curDate!![1] == ar[1]) {
                    bool[adapterPosition] = true
                    viewBinding.rlDate.setBackgroundResource(R.drawable.bg_date_current)
                    viewBinding.tvDay.setTextColor(Color.parseColor("#FFFFFFFF"))
                }

                if (day.monthDate) {
                    rlDate.setOnClickListener {
                        if (bool[adapterPosition]) {
                            if (day.date.toString() == curDate!![2]) {
                                viewBinding.rlDate.setBackgroundResource(R.drawable.bg_date_current)
                                viewBinding.tvDay.setTextColor(Color.parseColor("#FFFFFFFF"))
                            }
                        } else {
                            boolCheck()
                            if (day.date.toString() == curDate!![2]) {
                                viewBinding.rlDate.setBackgroundResource(R.drawable.bg_date_current)
                                viewBinding.tvDay.setTextColor(Color.parseColor("#FFFFFFFF"))
                            } else {
                                rlDate.setBackgroundResource(R.drawable.bg_date_selected)
                            }
                            bool[adapterPosition] = true
                        }
                        clickListener.onDateClicked(today, adapterPosition)
                    }
                }
            }
        }

        private fun boolCheck() {
            for (i in 0 until dateList.size) {
                if (bool[i] && i != adapterPosition) {
                    if (itemViewList[i].tvDay.text.toString() == curDate!![2]) {
//                        itemViewList[i].tvDay.setTextColor(Color.parseColor("#527FF3"))
//                        itemViewList[i].rlDate.setBackgroundResource(0)
                    } else {
                        itemViewList[i].rlDate.setBackgroundResource(0)
                    }
                    bool[i] = false
                }
            }

        }

    }
}