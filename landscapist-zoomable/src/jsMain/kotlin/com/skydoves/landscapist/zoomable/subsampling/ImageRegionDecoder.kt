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
package com.skydoves.landscapist.zoomable.subsampling

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize

/** JS browsers do not expose an efficient region decoder, so subsampling falls back gracefully. */
public actual class ImageRegionDecoder private constructor(
  private val _imageSize: IntSize,
) {
  public actual val imageSize: IntSize
    get() = _imageSize

  public actual suspend fun decodeRegion(
    region: IntRect,
    sampleSize: Int,
  ): ImageBitmap? = null

  public actual fun close(): Unit = Unit

  public actual companion object {
    public actual fun create(path: String): ImageRegionDecoder? = null

    public actual fun create(data: ByteArray): ImageRegionDecoder? = null
  }
}
