package pt.orgmir.budgetandroid.core.expenses

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import pt.orgmir.budgetandroid.utils.minus
import pt.orgmir.budgetandroid.utils.v
import trikita.anvil.RenderableView
import trikita.anvil.BaseAttrs.*
import trikita.anvil.Nodes
import trikita.anvil.v10.Attrs.*
import pt.orgmir.budgetandroid.R
import pt.orgmir.budgetandroid.localstorage.wrapers.BAExpense
import trikita.anvil.RenderableArrayAdapter

/**
 * Created by Luis Ramos on 7/26/2015.
 */
public class BAExpensesListView(context: Context) : RenderableView(context) {

  val presenter = BAExpensesListPresenter(this)
  val adapter = BAExpensesAdapter()

  public override fun view() =
      v<LinearLayout>{
        -size(FILL, FILL)
        -orientation(LinearLayout.VERTICAL)
        -backgroundColor(0xff3f51b5.toInt())

        v<TextView>{
          -size(FILL, WRAP)
            .margin(dip(16), dip(16))
          -textColor(Color.WHITE)
          -textSize(24f)
          -typeface("RobotoCondensed-Light.ttf")
          -text(R.string.app_name)
        }

        v<FrameLayout>{
          -size(FILL, FILL).weight(1f)

          v<ListView> {
            -size(FILL, FILL)
            -adapter(adapter)
          }

          v<Button>{
            -size(dip(70), dip(70))
                .margin(0, 0, dip(16), dip(16))
                .gravity(BOTTOM or RIGHT)
            -backgroundResource(R.drawable.floating_circle)
            -text("+")
            -gravity(CENTER)
            -textColor(getResources().getColor(R.color.ba_white))
            -textSize(28f)
            -onClick { presenter.buttonClicked() }
          }
        }
      }

  private class BAExpensesAdapter() : RenderableArrayAdapter<BAExpense>(arrayListOf()) {
    override fun itemView(pos: Int, expense: BAExpense): Nodes.ViewNode? {
      return v<LinearLayout> {
        -size(MATCH, dip(50))
        -orientation(LinearLayout.HORIZONTAL)

        v<TextView> {
          -size(MATCH, MATCH)
          -text(expense.value.toString())
        }
      }
    }
  }
}