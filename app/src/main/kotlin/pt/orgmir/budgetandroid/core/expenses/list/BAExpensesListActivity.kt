package pt.orgmir.budgetandroid.core.expenses.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import pt.orgmir.budgetandroid.R
import pt.orgmir.budgetandroid.Tasks
import pt.orgmir.budgetandroid.base.BABaseActivity
import pt.orgmir.budgetandroid.utils.minus
import pt.orgmir.budgetandroid.utils.v
import pt.orgmir.budgetandroid.core.expenses.list.BAExpenseListView
import trikita.anvil.Backstack
import trikita.anvil.BaseAttrs
import trikita.anvil.Nodes
import trikita.anvil.v10.Attrs


public class BAExpensesListActivity : pt.orgmir.budgetandroid.base.BABaseActivity() {

  override fun onCreate(b: android.os.Bundle?) {
    super.onCreate(b);
    if (b != null) {
      backstack.load(b);
    } else {
      backstack.navigate(BAExpenseListView(this));
    }
  }

}
