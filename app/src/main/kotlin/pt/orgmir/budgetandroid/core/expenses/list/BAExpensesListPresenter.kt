package pt.orgmir.budgetandroid.core.expenses.list

import pt.orgmir.budgetandroid.base.BABaseActivity
import pt.orgmir.budgetandroid.core.expenses.create.BAExpenseCreateView
import pt.orgmir.budgetandroid.core.expenses.list.BAExpenseListView
import pt.orgmir.budgetandroid.localstorage.wrapers.BAExpense
import trikita.anvil.Backstack

/**
 * Created by Luis Ramos on 7/27/2015.
 */
public class BAExpensesListPresenter(val view: BAExpenseListView) {

  val backstack : trikita.anvil.Backstack = (view.getContext() as BABaseActivity).backstack

  public fun getData() : List<BAExpense> = BAExpense.findAll()

  public fun showCreateExpenseView() {
    backstack.navigate(BAExpenseCreateView(view.getContext()))
  }

  public fun buttonClicked() {
    showCreateExpenseView()
  }
}