/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.example.microsoftapplication.demos

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.microsoftapplication.DemoActivity
import com.example.microsoftapplication.R
import com.google.android.material.snackbar.Snackbar
import com.microsoft.officeuifabric.persona.PersonaChipView
import kotlinx.android.synthetic.main.activity_demo_detail.*
import kotlinx.android.synthetic.main.activity_persona_chip_view.*

class PersonaChipViewActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_persona_chip_view

    private val personaChipViewListener = object : PersonaChipView.Listener {
        override fun onSelected(selected: Boolean) { }

        override fun onClicked() {
            Snackbar.make(root_view, getString(R.string.persona_chip_example_click), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDisabledPersonaChip()
        persona_chip_example_basic.listener = personaChipViewListener
        persona_chip_example_no_icon.listener = personaChipViewListener
        persona_chip_example_error.listener = personaChipViewListener
        persona_chip_example_error.hasError = true
    }

    private fun createDisabledPersonaChip() {
        val personaChipView = PersonaChipView(this)
        personaChipView.isEnabled = false
        personaChipView.name = resources.getString(R.string.persona_name_kat_larsson)
        personaChipView.email = resources.getString(R.string.persona_email_kat_larsson)
        personaChipView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        persona_chip_layout.addView(personaChipView)
    }
}