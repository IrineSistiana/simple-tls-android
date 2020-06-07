/*******************************************************************************
 *                                                                             *
 *  Copyright (C) 2019-2020 by IrineSistiana                                        *
 *                                                                             *
 *  This program is free software: you can redistribute it and/or modify       *
 *  it under the terms of the GNU General Public License as published by       *
 *  the Free Software Foundation, either version 3 of the License, or          *
 *  (at your option) any later version.                                        *
 *                                                                             *
 *  This program is distributed in the hope that it will be useful,            *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 *  GNU General Public License for more details.                               *
 *                                                                             *
 *  You should have received a copy of the GNU General Public License          *
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *                                                                             *
 *******************************************************************************/

package com.github.IrineSistiana.shadowsocks.plugin.general

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.github.IrineSistiana.shadowsocks.plugin.R
import com.github.shadowsocks.plugin.ConfigurationActivity
import com.github.shadowsocks.plugin.PluginOptions
import kotlinx.android.synthetic.main.activity_config.*

abstract class GeneralConfigActivity : ConfigurationActivity() {
    abstract val settingsFrame: GeneralConfigFragment

    private lateinit var oldOptions: PluginOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_frame, settingsFrame)
            .commit()

        save_button.setOnClickListener {
            saveChanges(settingsFrame.options)
            finish()
        }
    }


    override fun onInitializePluginOptions(options: PluginOptions) {
        oldOptions = options
        settingsFrame.onInitializePluginOptions(options)
    }

    override fun onBackPressed() {
        if (settingsFrame.options.toString() != oldOptions.toString()) {
            AlertDialog.Builder(this).run {
                setTitle(R.string.unsaved_changes_prompt)
                setMessage("old: ${oldOptions}\nnew: ${settingsFrame.options}")
                setPositiveButton(R.string.yes) { _, _ ->
                    saveChanges(settingsFrame.options)
                    finish()
                }
                setNegativeButton(R.string.no) { _, _ -> finish() }
                setNeutralButton(android.R.string.cancel, null)
                create()
            }.show()
        } else {
            finish()
        }
    }
}