package pt.orgmir.budgetandroid.core.expenses

import android.animation.LayoutTransition
import android.content.Context
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import pt.orgmir.budgetandroid.R
import pt.orgmir.budgetandroid.core.expenses.create.BAExpenseCreateView
import pt.orgmir.budgetandroid.core.expenses.list.BAExpenseListView
import pt.orgmir.budgetandroid.utils.minus
import pt.orgmir.budgetandroid.utils.v
import trikita.anvil.BaseAttrs.*
import trikita.anvil.Nodes
import trikita.anvil.RenderableView
import trikita.anvil.v10.Attrs.*

/**
 * Created by Luis Ramos on 8/2/2015.
 */
public class BAMainView(context: Context) : RenderableView(context) {

  val CONTENT_ID = 100

  override fun onAttachedToWindow() {
    post {
      val content = findViewById(CONTENT_ID) as LinearLayout
      content.setLayoutTransition(LayoutTransition())
    }
  }

  override fun view(): Nodes.ViewNode? {
    return v<LinearLayout>{
      -id(CONTENT_ID)
      -size(MATCH, MATCH)
      -orientation(LinearLayout.VERTICAL)
      -backgroundResource(R.color.ba_white)

      v<LinearLayout>{
        -size(MATCH, 0).weight(1f)
        v<BAExpenseListView>{
          -size(MATCH, MATCH)
        }
      }
      v<BAExpenseCreateView>{
        -size(MATCH, WRAP)
      }
    }
  }
}