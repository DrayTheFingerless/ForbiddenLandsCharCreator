package com.robertferreira.forbiddenlandscharcreator.ui.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.robertferreira.forbiddenlandscharcreator.R
import kotlinx.android.synthetic.main.attribute_stepper.view.*

/**
 * TODO: document your custom view class.
 */
class StepperRow : LinearLayout {

    var minimum_value : Int = 0
    var max_value : Int = 6
    constructor(context: Context) : super(context) {
        init(context,null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs, defStyle)
    }

    private fun init(context : Context, attrs: AttributeSet?, defStyle: Int) {
        LayoutInflater.from(context).inflate(R.layout.attribute_stepper, this, true)
        attrs?.let {
            val styledAttributes = context.obtainStyledAttributes(
                attrs, R.styleable.StepperRow, defStyle, 0
            )

            //style stepper with styledAttributes
            minimum_value = styledAttributes.getInt(R.styleable.StepperRow_init_value, 0)
            styledAttributes.recycle()
        }

        stepper_value.text = minimum_value.toString()
        stepper_remove.isEnabled = false

    }
    fun isShowMinimumValue(): Int {
        return minimum_value
    }

    fun setShowText(minvalue: Int) {
        minimum_value = minvalue
        invalidate()
        requestLayout()
    }

    fun setText(value : Int?)
    {
        if (value != null)
            stepper_value.text = value.toString()
        else {
            stepper_value.text = "0"
        }
    }
}
