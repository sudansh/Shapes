package com.sudansh.shapes

import android.content.res.Resources
import java.util.*

fun ClosedRange<Int>.randomInt(): Int {
	return (Random().nextInt((endInclusive + 1) - start) + start)
}

val Int.px: Int
	get() = (this * Resources.getSystem().displayMetrics.density).toInt()