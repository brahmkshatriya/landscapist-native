/*
 * Designed and developed by 2020-2023 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.skydoves.landscapist.core

import com.skydoves.landscapist.core.cache.DiskCache
import com.skydoves.landscapist.core.cache.DiskLruCache
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import okio.FileSystem
import okio.Path.Companion.toPath
import platform.posix.getenv

@OptIn(ExperimentalForeignApi::class)
private fun environmentVariable(name: String): String? = getenv(name)?.toKString()

/** Creates a disk cache under the Linux XDG cache directory. */
internal actual fun createDefaultDiskCache(maxSize: Long): DiskCache? {
  val cacheRoot = environmentVariable("XDG_CACHE_HOME")
    ?: environmentVariable("HOME")?.let { "$it/.cache" }
    ?: return null

  return DiskLruCache.create(
    directory = "$cacheRoot/landscapist".toPath(),
    maxSize = maxSize,
    fileSystem = FileSystem.SYSTEM,
  )
}
