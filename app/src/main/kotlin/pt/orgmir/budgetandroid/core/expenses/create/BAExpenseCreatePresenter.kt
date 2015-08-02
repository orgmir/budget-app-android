package pt.orgmir.budgetandroid.core.expenses.create

import android.widget.EditText
import pt.orgmir.budgetandroid.base.BABaseActivity
import pt.orgmir.budgetandroid.localstorage.wrapers.BAExpense
import pt.orgmir.budgetandroid.localstorage.wrapers.saveData

/**
 * Created by Luis Ramos on 8/1/2015.
 */
public class BAExpenseCreatePresenter(val view : BAExpenseCreateView) {

  val backstack = (view.getContext() as BABaseActivity).backstack

  public fun doneButtonClicked() {
    addExpense()
  }

  public fun editTextDoneClicked() {
    addExpense()
  }

  private fun addExpense() {
    val editText = view.findViewById(BAExpenseCreateView.VALUE_EDITTEXT_ID) as EditText

    val value = editText.getText().toString().toDouble()

    val expense = BAExpense()
    expense.value = value

    expense.create()
  }
}