package pt.orgmir.budgetandroid.core.expenses

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

  override fun view(): Nodes.ViewNode? {
    return v<LinearLayout>{
      -size(FILL, FILL)
      -orientation(LinearLayout.VERTICAL)
//      -backgroundResource(R.color.ba_dark_white)


      v<BAExpenseCreateView>{
        -size(MATCH, WRAP)
      }
      v<LinearLayout>{
        -size(FILL, FILL)

        v<BAExpenseListView>{

        }
      }


    }
  }
}