package pt.orgmir.budgetandroid.core.expenses

import trikita.anvil.Backstack

/**
 * Created by Luis Ramos on 7/27/2015.
 */
public class BAExpensesListPresenter(val view: BAExpensesListView) {

  val backstack : Backstack = (view.getContext() as BAExpensesListActivity).backstack

  public fun showCreateExpenseView() {
    backstack.navigate(BAExpenseCreateView(view.getContext()))
  }

  public fun buttonClicked() {
    showCreateExpenseView()
  }
}