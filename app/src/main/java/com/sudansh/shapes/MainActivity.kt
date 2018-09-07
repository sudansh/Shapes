package com.sudansh.shapes

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		circle.setOnClickListener { drawNext(Shape.TRIANGLE) }
		triangle.setOnClickListener { drawNext(Shape.SQUARE) }
		square.setOnClickListener { drawNext(Shape.CIRCLE) }
		stats.setOnClickListener { openStats() }
		undo.setOnClickListener { undoShape() }
	}

	private fun drawNext(nextShape: Shape) {
		triangle.text = String.format("%d", shape.shapeCounts[Shape.TRIANGLE] ?: 0)
		circle.text = String.format("%d", shape.shapeCounts[Shape.CIRCLE] ?: 0)
		square.text = String.format("%d", shape.shapeCounts[Shape.SQUARE] ?: 0)
		shape.drawNextShape(nextShape)
	}

	private fun undoShape() {
		shape.undoShape()
	}

	private fun openStats() {
		val options = ActivityOptions.makeSceneTransitionAnimation(this,
																   stats,
																   getString(R.string.transition_stats))

		val revealX = (stats.x + stats.width) / 2
		val revealY = (stats.y + stats.height) / 2
		val intent = Intent(this, StatsActivity::class.java).apply {
			putExtra(StatsActivity.KEY_STATS_CIRCLE, shape.shapeCounts[Shape.TRIANGLE])
			putExtra(StatsActivity.KEY_STATS_TRIANGLE, shape.shapeCounts[Shape.CIRCLE])
			putExtra(StatsActivity.KEY_STATS_SQUARE, shape.shapeCounts[Shape.SQUARE])
		}
		startActivity(intent, options.toBundle())
	}

}