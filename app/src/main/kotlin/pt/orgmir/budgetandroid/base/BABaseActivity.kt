package pt.orgmir.budgetandroid.base

import android.support.v7.app.AppCompatActivity
import kotlin.properties.Delegates

/**
 * Created by Luis Ramos on 7/26/2015.
 */
public open class BABaseActivity : AppCompatActivity() {

  protected var container : BAContainer by Delegates.notNull()

  override fun onBackPressed() {
    val handled = container.onBackPressed()
    if(!handled){
      finish()
    }
  }
}