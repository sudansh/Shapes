package com.sudansh.shapes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), ClickListener {
	private val listActions = mutableListOf<Pair<ShapeView, Action>>()
	private var cx: Int = 0
	private var cy: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		circle.setOnClickListener { draw(Shape.CIRCLE) }
		triangle.setOnClickListener { draw(Shape.TRIANGLE) }
		square.setOnClickListener { draw(Shape.SQUARE) }
		stats.setOnClickListener { openStats() }
		undo.setOnClickListener { undoShape() }
	}

	private fun draw(nextShape: Shape) {
		generateRandomPoint()
		ShapeView(this).apply {
			setShape(nextShape)
			layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
				marginStart = cx
				topMargin = cy
			}
			setListener(this@MainActivity)
		}.also {
			frame.addView(it)
			listActions.add(Pair(it, Action.NEW))
		}
	}

	/**
	 * Undo last action performed
	 */
	private fun undoShape() {
		if (listActions.isEmpty()) return
		listActions.last().let {
			when (it.second) {
				Action.NEW -> frame.removeView(it.first)
				Action.CHANGE -> it.first.undoShape()
				Action.DELETE -> frame.addView(it.first)
			}
			listActions.remove(it)
		}

	}

	private fun openStats() {
		var circle = 0
		var square = 0
		var triangle = 0
		listActions.filter { it.second == Action.NEW }.forEach {
			when (it.first.currentShape) {
				Shape.CIRCLE -> circle++
				Shape.TRIANGLE -> triangle++
				Shape.SQUARE -> square++
			}
		}
		Intent(this, StatsActivity::class.java).apply {
			putExtra(StatsActivity.KEY_STATS_CIRCLE, circle)
			putExtra(StatsActivity.KEY_STATS_TRIANGLE, triangle)
			putExtra(StatsActivity.KEY_STATS_SQUARE, square)
		}.also {
			startActivityForResult(it, REQUEST_DELETE)
		}
	}

	override fun onClick(shapeView: ShapeView, action: Action) {
		when (action) {
			Action.DELETE -> {
				frame.removeView(shapeView)
//				listActions.remove(listActions.firstOrNull { it.first == shapeView })
				listActions.add(Pair(shapeView, Action.DELETE))
			}
			Action.CHANGE -> listActions.add(Pair(shapeView, Action.CHANGE))
		}
	}

	/**
	 * Generates random points in the fram eto draw a shape
	 */
	private fun generateRandomPoint() {
		this.cx = (0 until (frame.width - 64.px)).randomInt()
		this.cy = (0 until (frame.height - 64.px)).randomInt()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == REQUEST_DELETE && resultCode == RESULT_OK) {
			val delete = data?.extras?.getSerializable(KEY_DELETE) as Shape?
			delete?.let {
				deleteShapes(it)
			}
		}
	}

	private fun deleteShapes(shape: Shape) {
		listActions.filter { it.first.currentShape == shape }
			.forEach {
				frame.removeView(it.first)
				listActions.remove(it)
			}
	}

	companion object {
		const val KEY_DELETE = "KEY_DELETE"
		const val REQUEST_DELETE = 101
	}

}