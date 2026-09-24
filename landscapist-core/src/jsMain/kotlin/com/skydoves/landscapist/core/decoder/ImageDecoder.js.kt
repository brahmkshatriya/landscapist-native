package com.skydoves.landscapist.core.decoder

import com.skydoves.landscapist.core.LandscapistConfig

public actual fun createPlatformDecoder(): ImageDecoder = JsImageDecoder()

internal class JsImageDecoder : ImageDecoder {
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
