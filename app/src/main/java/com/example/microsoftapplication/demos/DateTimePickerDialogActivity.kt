/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.example.microsoftapplication.demos

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.jakewharton.threetenabp.AndroidThreeTen
import com.microsoft.officeuifabric.datetimepicker.DateRangeMode
import com.microsoft.officeuifabric.datetimepicker.DateTimePickerDialog
import com.microsoft.officeuifabric.datetimepicker.DateTimePickerDialog.Mode
import com.microsoft.officeuifabric.datetimepicker.OnDateTimePickedListener
import com.microsoft.officeuifabric.util.DateStringUtils
import com.microsoft.officeuifabric.util.accessibilityManager
import com.microsoft.officeuifabric.util.isAccessibilityEnabled
import com.example.microsoftapplication.DemoActivity
import com.example.microsoftapplication.R
import kotlinx.android.synthetic.main.activity_date_time_picker_dialog.*
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime
import java.lang.IllegalStateException

class DateTimePickerDialogActivity : DemoActivity(), OnDateTimePickedListener {
    companion object {
        private const val TAG_DATE_PICKER = "datePicker"
        private const val TAG_DATE_TIME_PICKER = "dateTimePicker"
        private const val TAG_DATE_TIME_RANGE_PICKER = "dateTimeRangePicker"
        private const val TAG_END_DATE_PICKER = "endDatePicker"
        private const val TAG_START_DATE_PICKER = "startDatePicker"

        private const val DATE_TIME = "dateTime"
        private const val DURATION_DATE = "durationDate"
        private const val DURATION_DATE_TIME = "durationDateTime"
        private const val START_DATE = "startDate"
        private const val START_DATE_TIME = "startDateTime"

        private const val SINGLE_MODE_TAG = "singleModeDialogTag"
        private const val DIALOG_TAG = "dialogTag"
    }

    enum class DatePickerType(val buttonId: Int, val tag: String, val mode: Mode, val dateRangeMode: DateRangeMode) {
        DATE(R.id.date_picker_button, TAG_DATE_PICKER, Mode.DATE, DateRangeMode.NONE),
        DATE_TIME(R.id.date_time_picker_date_selected_button, TAG_DATE_TIME_PICKER, Mode.DATE_TIME, DateRangeMode.NONE),
        TIME_DATE(R.id.date_time_picker_time_selected_button, TAG_DATE_TIME_PICKER, Mode.TIME_DATE, DateRangeMode.NONE),
        START_DATE(R.id.date_range_start_button, TAG_START_DATE_PICKER, Mode.DATE, DateRangeMode.START),
        END_DATE(R.id.date_range_end_button, TAG_END_DATE_PICKER, Mode.DATE, DateRangeMode.END),
        START_DATE_TIME(R.id.date_time_range_start_button, TAG_DATE_TIME_RANGE_PICKER, Mode.DATE_TIME, DateRangeMode.START)
    }

    override val contentLayoutId: Int
        get() = R.layout.activity_date_time_picker_dialog

    private var dateTime: ZonedDateTime? = null
    private var durationDate: Duration = Duration.ZERO
    private var durationDateTime: Duration = Duration.ZERO
    private var startDate: ZonedDateTime? = null
    private var startDateTime: ZonedDateTime? = null

    private var dateRangeMode: DateRangeMode = DateRangeMode.NONE
    private var singleModeDialogTag: String? = null
    private var dialogTag: String? = null
        set(value) {
            if (dateRangeMode == DateRangeMode.NONE) {
                singleModeDialogTag = value
            }
            field = value
        }

    init {
        // Initialization of ThreeTenABP maybe required in order to set ZonedDateTime for DateTimePickerDialog
        AndroidThreeTen.init(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {
            dateTime = it.getSerializable(DATE_TIME) as? ZonedDateTime
            durationDate = it.getSerializable(DURATION_DATE) as Duration
            durationDateTime = it.getSerializable(DURATION_DATE_TIME) as Duration
            startDate = it.getSerializable(START_DATE) as? ZonedDateTime
            startDateTime = it.getSerializable(START_DATE_TIME) as? ZonedDateTime
            singleModeDialogTag = it.getString(SINGLE_MODE_TAG)
            dialogTag = it.getString(DIALOG_TAG)

            updateDateTimeText()
            updateDateRangeText()
            updateDateTimeRangeText()
        }

        DatePickerType.values().forEach { picker ->
            findViewById<Button>(picker.buttonId).setOnClickListener {
                dateRangeMode = picker.dateRangeMode
                dialogTag = picker.tag
                val dialog = DateTimePickerDialog.newInstance(
                    this,
                    picker.mode,
                    picker.dateRangeMode,
                    getDateTime(),
                    getDuration()
                )
                dialog.show(supportFragmentManager, picker.tag)
            }
        }

        updateButtonsForAccessibility(isAccessibilityEnabled)

        accessibilityManager.addAccessibilityStateChangeListener {
            updateButtonsForAccessibility(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(DATE_TIME, dateTime)
        outState.putSerializable(DURATION_DATE, durationDate)
        outState.putSerializable(DURATION_DATE_TIME, durationDateTime)
        outState.putSerializable(START_DATE, startDate)
        outState.putSerializable(START_DATE_TIME, startDateTime)
        outState.putString(SINGLE_MODE_TAG, singleModeDialogTag)
        outState.putString(DIALOG_TAG, dialogTag)
    }

    override fun onDateTimePicked(dateTime: ZonedDateTime, duration: Duration) {
        when (dialogTag) {
            TAG_DATE_PICKER, TAG_DATE_TIME_PICKER -> {
                this.dateTime = dateTime
                updateDateTimeText()
            }
            TAG_START_DATE_PICKER, TAG_END_DATE_PICKER -> {
                startDate = dateTime
                durationDate = duration
                updateDateRangeText()
            }
            TAG_DATE_TIME_RANGE_PICKER -> {
                startDateTime = dateTime
                durationDateTime = duration
                updateDateTimeRangeText()
            }
        }
    }

    private fun updateButtonsForAccessibility(accessibilityEnabled: Boolean) {
        if (accessibilityEnabled) {
            date_time_picker_time_selected_button.visibility = View.GONE
            date_time_picker_date_selected_button.setText(R.string.date_time_picker_dialog_date_time_button)
        } else {
            date_time_picker_time_selected_button.visibility = View.VISIBLE
            date_time_picker_date_selected_button.setText(R.string.date_time_picker_dialog_calendar_date_time_button)
        }
    }

    private fun updateDateTimeText() {
        val tag = singleModeDialogTag ?: return
        val dateTime = dateTime ?: return
        if (tag == TAG_DATE_PICKER)
            date_text_view.text = DateStringUtils.formatDateWithWeekDay(this, dateTime)
        else
            date_text_view.text = DateStringUtils.formatFullDateTime(this, dateTime)
    }

    private fun updateDateRangeText() {
        val startDate = startDate ?: return
        start_date_text_view.text = DateStringUtils.formatDateWithWeekDay(this, startDate)

        val endDate = startDate.plus(durationDate)
        end_date_text_view.text = DateStringUtils.formatDateWithWeekDay(this, endDate)
    }

    private fun updateDateTimeRangeText() {
        val startDateTime = startDateTime ?: return
        start_date_time_text_view.text = DateStringUtils.formatFullDateTime(this, startDateTime)

        val endDateTime = startDateTime.plus(durationDateTime)
        end_date_time_text_view.text = DateStringUtils.formatFullDateTime(this, endDateTime)
    }

    private fun getDateTime(): ZonedDateTime =
        when (dialogTag) {
            TAG_DATE_PICKER, TAG_DATE_TIME_PICKER -> dateTime ?: ZonedDateTime.now()
            TAG_START_DATE_PICKER, TAG_END_DATE_PICKER -> startDate ?: ZonedDateTime.now()
            TAG_DATE_TIME_RANGE_PICKER -> startDateTime ?: ZonedDateTime.now()
            else -> throw IllegalStateException("dialogTag expected")
        }

    private fun getDuration(): Duration =
        when (dialogTag) {
            TAG_START_DATE_PICKER, TAG_END_DATE_PICKER -> durationDate
            TAG_DATE_TIME_RANGE_PICKER -> durationDateTime
            else -> Duration.ZERO
        }
}