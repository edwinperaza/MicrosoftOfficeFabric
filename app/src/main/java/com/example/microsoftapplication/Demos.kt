package com.example.microsoftapplication

import com.example.microsoftapplication.demos.*
import java.util.*
import kotlin.reflect.KClass

const val AVATAR_VIEW = "AvatarView"
const val BASIC_INPUTS = "Basic Inputs"
const val BOTTOM_SHEET = "BottomSheet"
const val CALENDAR_VIEW = "CalendarView"
const val DATE_TIME_PICKER_DIALOG = "DateTimePickerDialog"
const val DRAWER = "Drawer"
const val LIST_ITEM_VIEW = "ListItemView"
const val PEOPLE_PICKER_VIEW = "PeoplePickerView"
const val PERSONA_CHIP_VIEW = "PersonaChipView"
const val PERSONA_LIST_VIEW = "PersonaListView"
const val PERSONA_VIEW = "PersonaView"
const val PROGRESS = "Progress"
const val SNACKBAR = "Snackbar"
const val TEMPLATE_VIEW = "TemplateView"
const val TOOLTIP = "Tooltip"
const val TYPOGRAPHY = "Typography"

val DEMOS = arrayListOf(
    Demo(AVATAR_VIEW, AvatarViewActivity::class),
    Demo(BASIC_INPUTS, BasicInputsActivity::class),
    Demo(BOTTOM_SHEET, BottomSheetActivity::class),
    Demo(CALENDAR_VIEW, CalendarViewActivity::class),
    Demo(DATE_TIME_PICKER_DIALOG, DateTimePickerDialogActivity::class),
    Demo(DRAWER, DrawerActivity::class),
    Demo(LIST_ITEM_VIEW, ListItemViewActivity::class),
    Demo(PEOPLE_PICKER_VIEW, PeoplePickerViewActivity::class),
    Demo(PERSONA_CHIP_VIEW, PersonaChipViewActivity::class),
    Demo(PERSONA_LIST_VIEW, PersonaListViewActivity::class),
    Demo(PERSONA_VIEW, PersonaViewActivity::class),
    Demo(PROGRESS, ProgressActivity::class),
    Demo(SNACKBAR, SnackbarActivity::class),
    Demo(TEMPLATE_VIEW, TemplateViewActivity::class),
    Demo(TOOLTIP, TooltipActivity::class),
    Demo(TYPOGRAPHY, TypographyActivity::class)
)

data class Demo(val title: String, val demoClass: KClass<out DemoActivity>) {
    val id: UUID = UUID.randomUUID()
}