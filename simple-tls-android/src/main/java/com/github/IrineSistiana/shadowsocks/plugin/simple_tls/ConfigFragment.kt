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

package com.github.IrineSistiana.shadowsocks.plugin.simple_tls

import android.os.Bundle
import android.text.InputType
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import com.github.IrineSistiana.shadowsocks.plugin.R
import com.github.IrineSistiana.shadowsocks.plugin.general.GeneralConfigFragment
import com.github.shadowsocks.plugin.PluginOptions
import kotlin.collections.set

class ConfigFragment : GeneralConfigFragment() {
    //required
    private val n by lazy { findPreference<EditTextPreference>("n")!! }
    private val auth by lazy { findPreference<EditTextPreference>("auth")!! }
    private val mux by lazy { findPreference<EditTextPreference>("mux")!! }
    private val cca by lazy { findPreference<EditTextPreference>("cca")!! }

    //geek's
    private val noVerify by lazy { findPreference<CheckBoxPreference>("noVerify")!! }
    private val timeout by lazy { findPreference<EditTextPreference>("timeout")!! }

    //debug
    private val receivedStr by lazy { findPreference<Preference>("receivedStr")!! }

    override val options
        get() = PluginOptions().apply {
            this.id = requireContext().getString(R.string.plugin_id)
            if (n.text.isNotBlank()) this["n"] = n.text
            if (auth.text.isNotBlank()) this["auth"] = auth.text
            if (mux.text.isNotBlank()) this["mux"] = mux.text
            if (cca.text.isNotBlank()) this["cca"] = cca.text.trimEnd('=')

            if (noVerify.isChecked) this["no-verify"] = null
            if (timeout.text.isNotBlank()) this["timeout"] = timeout.text
        }

    override fun onInitializePluginOptions(options: PluginOptions) {
        n.text = options["n"] ?: ""
        auth.text = options["auth"] ?: ""
        mux.text = options["mux"] ?: ""
        cca.text = options["cca"] ?: ""

        noVerify.isChecked = options.contains("no-verify")
        timeout.text = options["timeout"] ?: ""

        receivedStr.summary = options.toString()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.config_settings)
        n.setOnBindEditTextListener { it.inputType = InputType.TYPE_TEXT_VARIATION_URI }
        auth.setOnBindEditTextListener { it.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS }
        cca.setOnBindEditTextListener { it.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS }
        mux.setOnBindEditTextListener { it.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL }
        timeout.setOnBindEditTextListener { it.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL }
    }
}
