package pt.orgmir.budgetandroid.core.expenses.create

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.view.Gravity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import pt.orgmir.budgetandroid
import pt.orgmir.budgetandroid.base.BABaseActivity
import pt.orgmir.budgetandroid.core.expenses.list.BAExpensesListActivity
import pt.orgmir.budgetandroid.utils.minus
import pt.orgmir.budgetandroid.utils.v
import trikita.anvil.Backstack
import trikita.anvil.BaseAttrs
import trikita.anvil.Nodes
import trikita.anvil.RenderableView
import trikita.anvil.BaseAttrs.*
import trikita.anvil.v10.Attrs.*
import pt.orgmir.budgetandroid.R

/**
 * Created by Luis Ramos on 7/28/2015.
 */
public class BAExpenseCreateView(context: Context) : RenderableView(context), TextView.OnEditorActionListener {

  companion object {
    public val VALUE_EDITTEXT_ID : Int = 1
  }

  val presenter = BAExpenseCreatePresenter(this)

  override fun onAttachedToWindow() {
    post {
      val editText = findViewById(VALUE_EDITTEXT_ID) as EditText
      editText.requestFocus()
      editText.setSelection(editText.getText().length())
    }// Put cursor at the end of the edit text
  }

  override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
    if(actionId == EditorInfo.IME_ACTION_DONE){
      presenter.editTextDoneClicked()
      return true
    }
    return false
  }

  override fun view(): Nodes.ViewNode? {
    return v<RelativeLayout>{
      -size(MATCH, WRAP)
      -padding(dip(16), dip(5))
      //-orientation(LinearLayout.VERTICAL)
      -backgroundResource(R.drawable.background_top_shadow)

      v<EditText>{
        -size(MATCH, dip(44))
                .weight(1f)
                .margin(0, 0, dip(8), 0)
        -gravity(Gravity.RIGHT or Gravity.CENTER_VERTICAL)
        -id(VALUE_EDITTEXT_ID)
        -hint(R.string.value_input_hint)
        -inputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
        -imeOptions(EditorInfo.IME_ACTION_DONE)
        -onEditorAction(this)
      }

//     v<Button>{
//       -size(WRAP, MATCH)
//       -text(R.string.add_expense)
//        -onClick { presenter.doneButtonClicked() }
//      }

      for(category in presenter.getCategories()){
        v<Button>{
          -size(WRAP, WRAP)
          -text(category.name)
          -onClick { presenter.doneButtonClicked(category) }
        }
      }

    }
  }

}