package com.sudansh.shapes

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_shape.view.*

class ShapeView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

	private lateinit var listener: ClickListener
	lateinit var currentShape: Shape

	init {
		LayoutInflater.from(context).inflate(R.layout.view_shape, this, true)
		image.setOnClickListener { nextShape() }
		image.setOnLongClickListener {
			listener.onClick(this, Action.DELETE)
			return@setOnLongClickListener true
		}
	}

	private fun nextShape() {
		listener.onClick(this, Action.CHANGE)
		when (currentShape) {
			Shape.CIRCLE -> setShape(Shape.TRIANGLE)
			Shape.TRIANGLE -> setShape(Shape.SQUARE)
			Shape.SQUARE -> setShape(Shape.CIRCLE)
		}
	}

	fun setShape(shape: Shape) {
		currentShape = shape
		when (currentShape) {
			Shape.CIRCLE -> image.setImageResource(R.drawable.ic_circle)
			Shape.TRIANGLE -> image.setImageResource(R.drawable.ic_triangle)
			Shape.SQUARE -> image.setImageResource(R.drawable.ic_square)
		}
	}

	fun setListener(listener: ClickListener) {
		this.listener = listener
	}

	fun undoShape() {
		when (currentShape) {
			Shape.CIRCLE -> setShape(Shape.SQUARE)
			Shape.TRIANGLE -> setShape(Shape.CIRCLE)
			Shape.SQUARE -> setShape(Shape.TRIANGLE)
		}
	}
}

interface ClickListener {
	fun onClick(shapeView: ShapeView, action: Action)
}
