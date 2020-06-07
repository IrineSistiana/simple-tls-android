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

import android.net.Uri
import android.os.ParcelFileDescriptor
import com.github.shadowsocks.plugin.NativePluginProvider
import com.github.shadowsocks.plugin.PathProvider
import java.io.File
import java.io.FileNotFoundException

abstract class GeneralBinaryProvider : NativePluginProvider() {
    abstract val libName:Lazy<String>

    override fun populateFiles(provider: PathProvider) {
        provider.addPath(libName.value, 755)
    }

    override fun getExecutable() =
        context!!.applicationInfo.nativeLibraryDir + "/${libName.value}"

    override fun openFile(uri: Uri): ParcelFileDescriptor = when (uri.path) {
        "/${libName.value}" -> ParcelFileDescriptor.open(
            File(getExecutable()),
            ParcelFileDescriptor.MODE_READ_ONLY
        )
        else -> throw FileNotFoundException(uri.path)
    }
}