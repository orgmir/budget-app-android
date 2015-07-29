package pt.orgmir.budgetandroid.core.expenses

import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import pt.orgmir.budgetandroid.R
import pt.orgmir.budgetandroid.Tasks
import trikita.anvil.*

import trikita.anvil.v10.Attrs.*

//
// A screen with some kind of an actionbar, a button to start/finish the task.
// Also shows a name and a countdown timer of the task.
//
public class CountDownView(c: Context) : RenderableView(c) {

  private var mCurrentTask: Tasks.Task? = null
  private var mTimeIsSet = false
  private val mBackstack: Backstack

  private val mTasks = Tasks.getInstance()

  // Infinite periodic timer to update UI every second
  private val mTimer = object : CountDownTimer(24 * 60 * 60 * 1000.toLong(), 1000) {
    override fun onTick(millis: Long) {
      Anvil.render(this@CountDownView)
    }

    override fun onFinish() {
      // restart timer once it's finished to make it infinite
      this.start()
    }
  }

  private val mStartClicked : (View)->Unit = { v ->
    val s = (findViewById(TASK_NAME_EDITTEXT_ID) as EditText).getText().toString()
    mCurrentTask?.setName(s);
    mCurrentTask?.start();
  }

  private val mRestartClicked : (View)->Unit = { v ->
    val task = mTasks?.create(mCurrentTask?.getName(), mCurrentTask?.getDuration() ?: 0L);
    mCurrentTask = task;
    mCurrentTask?.start();
  }

  private val mDoneClicked : (View)->Unit = { v ->
    mCurrentTask?.stop()
    finish()
  }

  private val mTimeClicked : (View)->Unit = { v ->
    val picker = TimePickerDialog(v.getContext(), object:TimePickerDialog.OnTimeSetListener {
      override fun onTimeSet(view: TimePicker, hour: Int, minute: Int){
        val duration = (hour * 60 + minute ) * 60 * 1000
        mCurrentTask?.setDuration(duration.toLong())
        mTimeIsSet = true
        Anvil.render(this@CountDownView)
      }
    },
        (( mCurrentTask?.getRemainder() ?: 1 )/60/60/1000).toInt(),
        ((( mCurrentTask?.getRemainder() ?: 1 )/60/1000)%60).toInt(),
        true)
    picker.setTitle(R.string.set_time_title);
    picker.show();
  }


  init {
    mBackstack = (c as BAExpensesListActivity).backstack
    mCurrentTask = mTasks.getCurrent()
    if (mCurrentTask == null) {
      mCurrentTask = mTasks.create("New task", 25 * 60 * 1000)
    } else if (mCurrentTask!!.isPaused()) {
      mCurrentTask!!.start()
    }
    mTimer.start()
  }

  public fun withTask(task: Tasks.Task): CountDownView {
    mCurrentTask = task
    return this
  }

  private fun finish() {
    mBackstack.back()
  }

  override fun onAttachedToWindow() {
    post {
      val editText = findViewById(TASK_NAME_EDITTEXT_ID) as EditText
      editText.requestFocus()
      editText.setSelection(editText.getText().length())
    }// Put cursor at the end of the edit text
  }

  override fun onDetachedFromWindow() {
    if (mCurrentTask!!.isRunning()) {
      mCurrentTask!!.pause()
    }
    mTimer.cancel()
  }

  private fun getCountDownText(): String {
    if (mCurrentTask!!.isNew() && !mTimeIsSet) {
      return getContext().getString(R.string.set_time)
    }
    val t = (if (mCurrentTask!!.isFinished()) mCurrentTask!!.getDuration() else mCurrentTask!!.getRemainder())
    if (t < 0) {
      return getContext().getString(R.string.failed)
    }
    return java.lang.String.format("%02d:%02d:%02d", t / 1000 / 60 / 60, (t / 1000 / 60) % 60, (t / 1000) % 60)
  }

  private fun materialIcon(s: String): Nodes.AttrNode {
    return BaseAttrs.attrs(BaseAttrs.size(BaseAttrs.dip(48), BaseAttrs.dip(48)).weight(0f), gravity(BaseAttrs.CENTER), textColor(Color.WHITE), clickable(true), BaseAttrs.typeface("Material-Design-Icons.ttf"), textSize(36f), backgroundResource(R.drawable.header_button), text(s))
  }

  override fun view(): Nodes.ViewNode {
    return Nodes.v(javaClass<LinearLayout>(), orientation(LinearLayout.VERTICAL), backgroundColor(BACKGROUND_COLOR),
        Nodes.v(javaClass<LinearLayout>(), orientation(LinearLayout.HORIZONTAL), BaseAttrs.size(BaseAttrs.FILL, BaseAttrs.WRAP).gravity(BaseAttrs.CENTER_HORIZONTAL or BaseAttrs.TOP), BaseAttrs.padding(BaseAttrs.dip(4)),
            Nodes.v(javaClass<TextView>(), materialIcon("\ue893"), //Back
                onClick { finish() }),
            Nodes.v(javaClass<View>(), BaseAttrs.size(BaseAttrs.WRAP, BaseAttrs.FILL).weight(1f), gravity(BaseAttrs.CENTER_VERTICAL)),
            Nodes.v(javaClass<TextView>(), materialIcon("\ue796"), // Edit
                visibility(if (mCurrentTask!!.isRunning()) View.VISIBLE else View.GONE), onClick{
              mCurrentTask?.pause();
              post {
                val editText = findViewById(TASK_NAME_EDITTEXT_ID) as EditText
                editText.requestFocus()
              }
            }),
            Nodes.v(javaClass<TextView>(), materialIcon("\ue620"), // Remove
                visibility(if (mCurrentTask!!.isFinished()) View.VISIBLE else View.GONE), onClick{
              mCurrentTask?.remove()
              finish()
            })),
        Nodes.v(javaClass<FrameLayout>(), BaseAttrs.size(BaseAttrs.FILL, BaseAttrs.WRAP).weight(1f),
            Nodes.v(javaClass<LinearLayout>(), orientation(LinearLayout.VERTICAL), BaseAttrs.size(BaseAttrs.FILL, BaseAttrs.WRAP).gravity(BaseAttrs.CENTER_HORIZONTAL or BaseAttrs.BOTTOM),
                Nodes.v(javaClass<EditText>(), id(TASK_NAME_EDITTEXT_ID), BaseAttrs.size(BaseAttrs.FILL, BaseAttrs.WRAP), gravity(BaseAttrs.CENTER), textColor(Color.WHITE), textSize((if (BaseAttrs.isPortrait()) 42 else 36).toFloat()), singleLine(true), focusable(mCurrentTask!!.isNew() || mCurrentTask!!.isPaused()), focusableInTouchMode(mCurrentTask!!.isNew() || mCurrentTask!!.isPaused()), clickable(mCurrentTask!!.isNew() || mCurrentTask!!.isPaused()), cursorVisible(mCurrentTask!!.isNew() || mCurrentTask!!.isPaused()), backgroundDrawable(null), BaseAttrs.typeface("RobotoCondensed-Light.ttf"), hint(R.string.task_name_hint), text(mCurrentTask!!.getName())),
                Nodes.v(javaClass<TextView>(), BaseAttrs.size(BaseAttrs.FILL, BaseAttrs.WRAP), gravity(BaseAttrs.CENTER), textColor(Color.WHITE), textSize((if (BaseAttrs.isPortrait()) 67 else 42).toFloat()), clickable(mCurrentTask!!.isNew() || mCurrentTask!!.isPaused()), onClick(mTimeClicked), BaseAttrs.typeface("RobotoCondensed-Bold.ttf"), text(getCountDownText())))),

        Nodes.v(javaClass<FrameLayout>(), BaseAttrs.size(BaseAttrs.FILL, BaseAttrs.WRAP).gravity(BaseAttrs.CENTER).weight(1f),
            Nodes.v(javaClass<TextView>(), BaseAttrs.size(BaseAttrs.dip(StartView.BUTTON_SIZE), BaseAttrs.dip(StartView.BUTTON_SIZE)).gravity(BaseAttrs.CENTER).weight(1f), gravity(BaseAttrs.CENTER), clickable(true), backgroundResource(R.drawable.black_button), textColor(Color.WHITE), textSize(24f), enabled(!mCurrentTask!!.isNew() || mTimeIsSet), BaseAttrs.typeface("RobotoCondensed-Light.ttf"), onClick(if (mCurrentTask!!.isRunning())
              mDoneClicked
            else
              (if (mCurrentTask!!.isFinished()) mRestartClicked else mStartClicked)), text(if (mCurrentTask!!.isNew() || mCurrentTask!!.isPaused())
              R.string.start
            else
              (if (mCurrentTask!!.isFinished()) R.string.again else R.string.done)))))
  }

  override fun onLoad(b: Bundle?) {
    if (b!!.getBoolean("new")) {
      mCurrentTask = mTasks.create(b.getString("newName"), b.getLong("newTime"))
      mTimeIsSet = b.getBoolean("newTimeSet")
      return
    }
    if (b.getBoolean("current")) {
      mCurrentTask = mTasks.getCurrent()
      return
    }
    val completed = b.getBoolean("completed")
    val index = b.getInt("index")
    mCurrentTask = (if (completed)
      mTasks.getCompleted()
    else
      mTasks.getFailed()).get(index)
  }

  override fun onSave(b: Bundle?) {
    if (mCurrentTask!!.isNew()) {
      b!!.putBoolean("new", true)
      b.putBoolean("newTimeSet", mTimeIsSet)
      b.putString("newName", mCurrentTask!!.getName())
      b.putLong("newTime", mCurrentTask!!.getDuration())
    }
    if (mCurrentTask !== mTasks.getCurrent()) {
      val completed = mTasks.getCompleted().contains(mCurrentTask)
      b!!.putBoolean("current", false)
      b.putBoolean("completed", completed)
      b.putInt("index", (if (completed)
        mTasks.getCompleted()
      else
        mTasks.getFailed()).indexOf(mCurrentTask))
    } else {
      b!!.putBoolean("current", true)
    }
  }

  companion object {

    public val TASK_NAME_EDITTEXT_ID: Int = 1

    private val BACKGROUND_COLOR = -3285959
  }
}