package pt.orgmir.budgetandroid.core.expenses.create

import android.os.Bundle
import pt.orgmir.budgetandroid.base.BABaseActivity
import pt.orgmir.budgetandroid.core.expenses.create.BAExpenseCreateView
import pt.orgmir.budgetandroid.core.expenses.list.BAExpenseListView

/**
 * Created by Luis Ramos on 8/1/2015.
 */
public class BAExpenseCreateActivity : BABaseActivity() {

  override fun onCreate(b: Bundle?) {
    super.onCreate(b);
    if (b != null) {
      backstack.load(b);
    } else {
      backstack.navigate(BAExpenseCreateView(this));
    }
  }
}