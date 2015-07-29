package pt.orgmir.budgetandroid.core.expenses

import android.support.v7.app.ActionBarActivity
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
import pt.orgmir.budgetandroid.core.expenses.BAExpensesListView
import trikita.anvil.Backstack
import trikita.anvil.BaseAttrs
import trikita.anvil.Nodes
import trikita.anvil.v10.Attrs


public class BAExpensesListActivity : BABaseActivity() {

  public val backstack : Backstack = Backstack(this, { v ->
    setContentView(v);
  })

//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(StartView(this))
//  }

  override fun onCreate(b: Bundle?) {
    super.onCreate(b);
    if (b != null) {
      backstack.load(b);
    } else {
      backstack.navigate(BAExpensesListView(this));
    }
  }

  override fun onSaveInstanceState(b: Bundle?) {
    backstack.save(b);
    super.onSaveInstanceState(b);
  }

  override fun onBackPressed() {
    if (!backstack.back()) {
      finish();
    }
  }
}
