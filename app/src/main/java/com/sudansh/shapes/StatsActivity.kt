package com.sudansh.shapes

import android.app.Activity
import android.os.Bundle

class StatsActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_stats)

	}

	companion object {
		const val KEY_STATS_CIRCLE = "KEY_STATS_CIRCLE"
		const val KEY_STATS_TRIANGLE = "KEY_STATS_TRIANGLE"
		const val KEY_STATS_SQUARE = "KEY_STATS_SQUARE"
	}
}
