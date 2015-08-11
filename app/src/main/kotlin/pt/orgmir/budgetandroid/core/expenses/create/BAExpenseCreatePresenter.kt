package pt.orgmir.budgetandroid.core.expenses.create

import android.widget.EditText
import pt.orgmir.budgetandroid.base.BABaseActivity
import pt.orgmir.budgetandroid.localstorage.wrapers.BAExpense
import pt.orgmir.budgetandroid.localstorage.wrapers.saveData
import kotlin.properties.Delegates

/**
 * Created by Luis Ramos on 8/1/2015.
 */
public class BAExpenseCreatePresenter(val view : BAExpenseCreateView) {

  val backstack = (view.getContext() as BABaseActivity).backstack

  val editText by Delegates.lazy {
    view.findViewById(BAExpenseCreateView.VALUE_EDITTEXT_ID) as EditText
  }

  public fun doneButtonClicked() {
    addExpense()
    clearEditText()
  }

  public fun editTextDoneClicked() {
    addExpense()
    clearEditText()
  }

  private fun clearEditText() {
    editText.setText(null)
  }

  private fun addExpense() {
    val value = editText.getText().toString().toDouble()

    val expense = BAExpense()
    expense.value = value

    expense.create()
  }
}