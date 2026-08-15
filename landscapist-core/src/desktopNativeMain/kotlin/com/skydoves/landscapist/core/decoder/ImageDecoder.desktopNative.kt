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
package com.skydoves.landscapist.core.decoder

import com.skydoves.landscapist.core.LandscapistConfig

/** Creates a native desktop decoder; Compose/Skia performs the final encoded-image decode. */
public actual fun createPlatformDecoder(): ImageDecoder = DesktopNativeImageDecoder()

internal class DesktopNativeImageDecoder : ImageDecoder {
  override suspend fun decode(
    data: ByteArray,
    mimeType: String?,
    targetWidth: Int?,
    targetHeight: Int?,
    config: LandscapistConfig,
  ): DecodeResult {
    val size = readImageDimensions(data)
    return DecodeResult.Success(
      bitmap = RawImageData(data, mimeType),
      width = size?.width ?: targetWidth ?: 0,
      height = size?.height ?: targetHeight ?: 0,
    )
  }
}
