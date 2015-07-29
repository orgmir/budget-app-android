package pt.orgmir.budgetandroid.core.expenses

import android.content.Context
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import pt.orgmir.budgetandroid
import pt.orgmir.budgetandroid.utils.minus
import pt.orgmir.budgetandroid.utils.v
import trikita.anvil.Backstack
import trikita.anvil.BaseAttrs
import trikita.anvil.Nodes
import trikita.anvil.RenderableView
import trikita.anvil.BaseAttrs.*
import trikita.anvil.v10.Attrs.*

/**
 * Created by Luis Ramos on 7/28/2015.
 */
public class BAExpenseCreateView(context: Context) : RenderableView(context) {

  val backstack: Backstack = (context as BAExpensesListActivity).backstack

  override fun view(): Nodes.ViewNode? {
    return v<LinearLayout>{
      -size(FILL, FILL)
      -orientation(LinearLayout.VERTICAL)
      -backgroundColor(0xff3f51b5.toInt())

      v<LinearLayout>{
        -size(FILL, WRAP).gravity(CENTER_HORIZONTAL or TOP)
        -orientation(LinearLayout.HORIZONTAL)

        v<TextView>{
          -materialIcon("\ue893")
          -onClick { backstack.back() }
        }
      }

    }
  }

  private fun materialIcon(s: String): Nodes.AttrNode {
    return BaseAttrs.attrs(
        BaseAttrs.size(BaseAttrs.dip(48), BaseAttrs.dip(48)),
        gravity(BaseAttrs.CENTER),
        textColor(Color.WHITE),
        clickable(true),
        BaseAttrs.typeface("Material-Design-Icons.ttf"),
        textSize(36f),
        backgroundResource(budgetandroid.R.drawable.header_button), text(s))
  }


}