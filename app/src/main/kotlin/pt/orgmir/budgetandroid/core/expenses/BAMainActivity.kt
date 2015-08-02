package pt.orgmir.budgetandroid.core.expenses

import android.os.Bundle
import pt.orgmir.budgetandroid.base.BABaseActivity
import pt.orgmir.budgetandroid.core.expenses.list.BAExpenseListView

/**
 * Created by Luis Ramos on 8/2/2015.
 */
public class BAMainActivity : BABaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (savedInstanceState != null) {
      backstack.load(savedInstanceState);
    } else {
      backstack.navigate(BAMainView(this));
    }
  }
}