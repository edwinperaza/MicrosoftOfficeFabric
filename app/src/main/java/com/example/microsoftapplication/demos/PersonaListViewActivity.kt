/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.example.microsoftapplication.demos

import android.os.Bundle
import com.microsoft.officeuifabric.persona.IPersona
import com.microsoft.officeuifabric.persona.PersonaListView
import com.microsoft.officeuifabric.snackbar.Snackbar
import com.example.microsoftapplication.DemoActivity
import com.example.microsoftapplication.R
import com.example.microsoftapplication.util.createPersonaList
import kotlinx.android.synthetic.main.activity_demo_detail.*
import kotlinx.android.synthetic.main.activity_persona_list_view.*

class PersonaListViewActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_persona_list_view

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        persona_list_view_example.personas = createPersonaList(this)
        persona_list_view_example.onItemClickedListener = object : PersonaListView.OnItemClickedListener {
            override fun onItemClicked(persona: IPersona) {
                Snackbar.make(
                    root_view,
                    "You clicked on the cell for ${persona.name}, ${persona.subtitle}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}