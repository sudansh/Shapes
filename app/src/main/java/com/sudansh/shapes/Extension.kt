package com.sudansh.shapes

import java.util.*

fun ClosedRange<Int>.random(): Float =
	(Random().nextInt((endInclusive + 1) - start) + start).toFloat()
