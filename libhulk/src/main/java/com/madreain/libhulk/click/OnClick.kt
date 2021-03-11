package com.madreain.libhulk.click


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class OnClick(val ids: IntArray, val debounce: Boolean = true)