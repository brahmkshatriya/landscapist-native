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
package com.skydoves.landscapist.core.event

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.windows.FILETIME
import platform.windows.GetSystemTimeAsFileTime

private const val WINDOWS_TO_UNIX_EPOCH_TICKS = 116_444_736_000_000_000L
private const val TICKS_PER_MILLISECOND = 10_000L

@OptIn(ExperimentalForeignApi::class)
internal actual fun currentTimeMillis(): Long = memScoped {
  val value = alloc<FILETIME>()
  GetSystemTimeAsFileTime(value.ptr)
  val ticks =
    (value.dwHighDateTime.toLong() shl 32) or
      (value.dwLowDateTime.toLong() and 0xffff_ffffL)
  (ticks - WINDOWS_TO_UNIX_EPOCH_TICKS) / TICKS_PER_MILLISECOND
}
