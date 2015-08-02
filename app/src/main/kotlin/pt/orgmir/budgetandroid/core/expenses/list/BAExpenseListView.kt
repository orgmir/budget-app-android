package pt.orgmir.budgetandroid.core.expenses.list

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
import pt.orgmir.budgetandroid.core.expenses.list.BAExpensesListPresenter
import pt.orgmir.budgetandroid.localstorage.wrapers.BAExpense
import trikita.anvil.RenderableArrayAdapter

/**
 * Created by Luis Ramos on 7/26/2015.
 */
public class BAExpenseListView(context: Context) : RenderableView(context) {

  val presenter = BAExpensesListPresenter(this)

  public override fun view() =
      v<ListView>{
        -id(11)
        -size(FILL, FILL)
        -backgroundColor(0xff3f51b5.toInt())
        -adapter(BAExpensesAdapter(presenter.getData()))
      }


  private class BAExpensesAdapter(items: List<BAExpense>) : RenderableArrayAdapter<BAExpense>(items) {

    constructor() : this(arrayListOf())

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