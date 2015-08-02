package pt.orgmir.budgetandroid.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import trikita.anvil.Backstack
import kotlin.properties.Delegates

/**
 * Created by Luis Ramos on 7/26/2015.
 */
public open class BABaseActivity : AppCompatActivity() {

  public val backstack : Backstack = Backstack(this, { v ->
    setContentView(v);
  })
  
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