package pt.orgmir.budgetandroid.core.expenses

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.*
import pt.orgmir.budgetandroid.utils.minus
import pt.orgmir.budgetandroid.utils.v
import trikita.anvil.Backstack
import trikita.anvil.Nodes
import trikita.anvil.RenderableView
import trikita.anvil.BaseAttrs.*
import trikita.anvil.v10.Attrs.*
import pt.orgmir.budgetandroid.R
import pt.orgmir.budgetandroid.Tasks
import trikita.anvil.RenderableAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Luis Ramos on 7/27/2015.
 */
public class StartView(c: Context) : RenderableView(c) {

  companion object {
    val MIN_TOP_HEIGHT = 120
    val MAX_TOP_HEIGHT = 260
    val BUTTON_SIZE = (MIN_TOP_HEIGHT * 0.8).toInt()
    val BACKGROUND_COLOR = 0xff3f51b5.toInt()
  }

  private val mBackstack: Backstack

  private var mTopViewHeight = MAX_TOP_HEIGHT

  private val mTasksAdapter = TasksAdapter()

  private val mScrollListener = object : AbsListView.OnScrollListener {
    override fun onScroll(v: AbsListView, first: Int, count: Int, total: Int) {
      var top = 0
      if (v.getChildCount() > 0) {
        top = -v.getChildAt(0).getTop() + first * v.getChildAt(0).getHeight()
        if (top >= 0) {
          mTopViewHeight = Math.max((MAX_TOP_HEIGHT - top / (getResources().getDisplayMetrics().density) / 1.5f).toInt() , MIN_TOP_HEIGHT)
        }
      }
    }

    override fun onScrollStateChanged(v: AbsListView, state: Int) {
    }
  }

  init {
    mBackstack = (c as BAExpensesListActivity).backstack
  }

  override fun view(): Nodes.ViewNode {
    return if (isPortrait()) portraitView() else landscapeView()
  }

  private fun landscapeView(): Nodes.ViewNode {
    return v<LinearLayout>{
      -size(FILL, FILL)
      -backgroundColor(BACKGROUND_COLOR)

      v<FrameLayout>{
        -size(FILL, FILL).weight(1f)
        blackButton()
      }

      listView(false)
    }

  }

  private fun portraitView(): Nodes.ViewNode {
    return v<FrameLayout>{
      -size(FILL, FILL)
      -backgroundColor(BACKGROUND_COLOR)

      listView(true)

      v<FrameLayout>{
        -size(FILL, dip(mTopViewHeight))
        blackButton()
      }

      v<View>{
        -size(FILL, dip(2)).margin(0, dip(mTopViewHeight), 0, 0)
        -visibility(if (mTopViewHeight < MAX_TOP_HEIGHT) View.VISIBLE else View.GONE)
        -backgroundColor(0x20000000)
      }
    }
  }

  private fun blackButton(): Nodes.ViewNode {
    return v<TextView>{
      -size(dip(BUTTON_SIZE), dip(BUTTON_SIZE)).gravity(CENTER)
      -clickable(true)
      -gravity(CENTER)
      -backgroundResource(R.drawable.black_button)
      -textColor(Color.WHITE)
      -textSize(24f)
      -typeface("RobotoCondensed-Light.ttf")
      -onClick { mBackstack.navigate(CountDownView(getContext()))}
      -text(if (Tasks.getInstance().getCurrent() == null) R.string.create else R.string.resume)
    }
  }

  private fun listView(withMargin: Boolean): Nodes.ViewNode {
    return v<ListView>{
      -size(FILL, FILL).margin(0, (if (withMargin) dip(mTopViewHeight) else 0), 0, 0).weight(1f)
      -adapter(mTasksAdapter as ListAdapter)
      -divider(null)
      -overScrollMode(View.OVER_SCROLL_NEVER)
      onItemClick { adapterView, v, pos, id ->
        val task = mTasksAdapter.getItem(pos)
        task?.let{
          mBackstack.navigate(CountDownView(getContext()).withTask(it))
        }
      }
      -onScroll(mScrollListener)
    }
  }

  //
  // A list adapter that shows merged list of completed and failed tasks
  //
  private inner class TasksAdapter : RenderableAdapter() {
    private var completedSize: Int = 0
    private var failedSize: Int = 0

    override fun getCount(): Int {
      completedSize = Tasks.getInstance().getCompleted().size()
      failedSize = Tasks.getInstance().getFailed().size()
      return completedSize + failedSize + (if (completedSize > 0) 1 else 0) + (if (failedSize > 0) 1 else 0)
    }

    override fun getItem(pos: Int): Tasks.Task? {
      val completedOffset = (if (completedSize > 0) 1 else 0)
      val failedOffset = completedOffset + (if (failedSize > 0) 1 else 0)
      if (pos == 0 && completedSize > 0) {
        return null
      } else if (pos - completedOffset < completedSize) {
        return Tasks.getInstance().getCompleted().get(pos - 1)
      } else if (pos - completedOffset == completedSize) {
        return null
      } else {
        return Tasks.getInstance().getFailed().get(pos - completedOffset - completedSize - 1)
      }
    }

    override fun isEnabled(pos: Int): Boolean {
      return getItem(pos) != null
    }

    override fun itemView(pos: Int): Nodes.ViewNode {
      val task = getItem(pos)
      val isHeader = (task == null)
      val itemText: String
      var itemDate = ""
      if (task == null) {
        if (pos == 0) {
          itemText = getContext().getString(R.string.list_group_completed)
        } else {
          itemText = getContext().getString(R.string.list_group_failed)
        }
      } else {
        itemText = task!!.getName()
        val now = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(task!!.getCompletionTime())
        if (calendar.get(Calendar.YEAR) !== now.get(Calendar.YEAR)) {
          // If something happened in the previous years - show year, month and date
          itemDate = SimpleDateFormat("MMM d, ''yy").format(calendar.getTime())
        } else if (calendar.get(Calendar.DAY_OF_YEAR) !== now.get(Calendar.DAY_OF_YEAR)) {
          // For current year show only month and date
          itemDate = SimpleDateFormat("MMM d").format(calendar.getTime())
        } else {
          // For today's tasks show only hours and minutes
          itemDate = SimpleDateFormat("HH:mm").format(calendar.getTime())
        }
      }

      return v<LinearLayout>{
        -size(FILL, WRAP)

        v<TextView>{
          -size(WRAP, dip(48)).weight(1f)
          -gravity(CENTER_VERTICAL)
          -typeface("RobotoCondensed-Bold.ttf")
          -padding(dip(8))
          -textSize(24f)
          -textColor(if (task == null) 0xFFFFFFFF.toInt() else 0x90FFFFFF.toInt())
          -text(itemText)
        }

        v<TextView>{
          -size(WRAP, dip(48)).weight(0f)
          -gravity(CENTER_VERTICAL)
          -typeface("RobotoCondensed-Bold.ttf")
          -padding(dip(8))
          -textSize(24f)
          -textColor(Color.WHITE)
          -text(itemDate)
        }

      }
    }
  }
}
