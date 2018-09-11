package com.sudansh.shapes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.sudansh.shapes.MainActivity.Companion.KEY_DELETE
import kotlinx.android.synthetic.main.activity_stats.*

class StatsActivity : Activity() {

	private val resultIntent by lazy { Intent(this, MainActivity::class.java) }
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_stats)
		intent?.let {
			triangle.text = String.format("%s", it.getIntExtra(KEY_STATS_TRIANGLE, 0))
			square.text = String.format("%s", it.getIntExtra(KEY_STATS_SQUARE, 0))
			circle.text = String.format("%s", it.getIntExtra(KEY_STATS_CIRCLE, 0))
		}
		deleteCircle.setOnClickListener {
			circle.text = "0"
			resultIntent.putExtra(KEY_DELETE, Shape.CIRCLE)
			setResult(RESULT_OK, resultIntent)
			finish()
		}
		deleteSquare.setOnClickListener {
			square.text = "0"
			resultIntent.putExtra(KEY_DELETE, Shape.SQUARE)
			setResult(RESULT_OK, resultIntent)
			finish()
		}
		deleteTriangle.setOnClickListener {
			triangle.text = "0"
			resultIntent.putExtra(KEY_DELETE, Shape.TRIANGLE)
			setResult(RESULT_OK, resultIntent)
			finish()
		}
	}

	companion object {
		const val KEY_STATS_CIRCLE = "KEY_STATS_CIRCLE"
		const val KEY_STATS_TRIANGLE = "KEY_STATS_TRIANGLE"
		const val KEY_STATS_SQUARE = "KEY_STATS_SQUARE"
	}
}
