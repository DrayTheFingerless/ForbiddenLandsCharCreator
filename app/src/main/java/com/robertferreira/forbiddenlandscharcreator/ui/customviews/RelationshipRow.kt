package com.robertferreira.forbiddenlandscharcreator.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.robertferreira.forbiddenlandscharcreator.R
import kotlinx.android.synthetic.main.attribute_stepper.view.*
import kotlinx.android.synthetic.main.relationship_row.view.*

class RelationshipRow : LinearLayout {

    var relationshipId : Int = 0


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
        LayoutInflater.from(context).inflate(R.layout.relationship_row, this, true)
        attrs?.let {
            val styledAttributes = context.obtainStyledAttributes(
                attrs, R.styleable.RelationshipRow, defStyle, 0
            )

            //style stepper with styledAttributes
            relationshipId = styledAttributes.getInt(R.styleable.RelationshipRow_id, 0)

            styledAttributes.recycle()
        }
    }

    fun setValues(title : String, desc : String){
        title_field.text = title
        description_field.text = desc
    }
}