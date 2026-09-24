package com.skydoves.landscapist.core.cache

public actual class WeakRef<T : Any> actual constructor(referent: T) {
  private var ref: T? = referent

  public actual fun get(): T? = ref

  public actual fun clear() {
    ref = null
  }
}
