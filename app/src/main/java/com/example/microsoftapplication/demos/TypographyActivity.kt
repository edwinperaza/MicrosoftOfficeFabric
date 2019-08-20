/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.example.microsoftapplication.demos

import android.os.Bundle
import androidx.core.widget.TextViewCompat
import com.example.microsoftapplication.DemoActivity
import com.example.microsoftapplication.R
import kotlinx.android.synthetic.main.activity_typography.*

class TypographyActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_typography

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TextViewCompat.setTextAppearance(typography_example_body_2, R.style.TextAppearance_UIFabric_Body2)
    }
}