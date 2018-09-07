package com.sudansh.shapes

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.sudansh.shapes.Shape.CIRCLE
import com.sudansh.shapes.Shape.SQUARE
import com.sudansh.shapes.Shape.TRIANGLE
import java.util.*

class Circle @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
	val animator: ValueAnimator by lazy {
		ValueAnimator.ofInt(0, 100)
	}
	private var cx: Float = 100f
	private var cy: Float = 100f
	private var r: Float = 80f
	private val random = Random()
	private var currentShape = SQUARE
	private var nextShape = SQUARE
	val shapeCounts = hashMapOf(
		TRIANGLE to 0,
		CIRCLE to 0,
		SQUARE to 0
	)
	private val paint by lazy {
		Paint(Paint.ANTI_ALIAS_FLAG).apply {
			style = Paint.Style.FILL
			color = Color.RED
		}
	}

	private fun generateRandom() {
		this.cx = (100 until 500).random()
		this.cy = (100 until 1000).random()
	}

	override fun onDraw(canvas: Canvas) {
		generateRandom()
		when (nextShape) {
			CIRCLE -> drawCircle(canvas)
			TRIANGLE -> drawTriangle(canvas)
			SQUARE -> drawSquare(canvas)
		}
		currentShape = nextShape
	}

	fun undoShape() {

	}

	fun drawCircle(canvas: Canvas) {
		canvas.drawCircle(cx, cy, r, paint)
		shapeCounts[CIRCLE] = shapeCounts[CIRCLE]?.plus(1) ?: 1
	}

	fun drawSquare(canvas: Canvas) {
		canvas.drawRect(RectF(cx, cy, cx + r, cy + r), paint)
		shapeCounts[SQUARE] = shapeCounts[SQUARE]?.plus(1) ?: 1
	}

	fun drawTriangle(canvas: Canvas) {
		val halfWidth = r / 2
		val path = Path()
		path.moveTo(cx, cy - halfWidth) // Top
		path.lineTo(cx - halfWidth, cy + halfWidth) // Bottom left
		path.lineTo(cx + halfWidth, cy + halfWidth) // Bottom right
		path.lineTo(cx, cy - halfWidth) // Back to Top
		path.close()

		canvas.drawPath(path, paint)
		shapeCounts[TRIANGLE] = shapeCounts[TRIANGLE]?.plus(1) ?: 1
	}

	fun drawNextShape(nextShape: Shape) {
		this.nextShape = nextShape
		invalidate()
	}

	fun drawNextShape() {
		nextShape = when (currentShape) {
			CIRCLE -> TRIANGLE
			TRIANGLE -> SQUARE
			SQUARE -> CIRCLE
		}
		invalidate()
	}

	override fun onTouchEvent(event: MotionEvent?): Boolean {
		drawNextShape()
		return super.onTouchEvent(event)
	}

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		// Try for a width based on our minimum
		val minw: Int = r.toInt()
		val w: Int = View.resolveSizeAndState(minw, widthMeasureSpec, 1)

		// Whatever the width ends up being, ask for a height that would let the pie
		// get as big as it can
		val minh: Int =
			View.MeasureSpec.getSize(w) - r.toInt() + paddingBottom + paddingTop
		val h: Int = View.resolveSizeAndState(
			View.MeasureSpec.getSize(w) - r.toInt(),
			heightMeasureSpec,
			0
		)

		setMeasuredDimension(w, h)
		super.onMeasure(w, h)

	}
}