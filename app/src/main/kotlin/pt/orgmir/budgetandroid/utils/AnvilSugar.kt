package pt.orgmir.budgetandroid.utils

import android.view.View
import trikita.anvil.Nodes
import java.util.*

/**
 * Created by Luis Ramos on 7/26/2015.
 */
val stack = ArrayDeque<Nodes.ViewNode>()

fun Nodes.AttrNode.minus() = stack.peek().addAttrs(this)
fun Nodes.AttrNode.plus() = stack.peek().addAttrs(this)

inline fun v<reified T: View>(f: () -> Unit): Nodes.ViewNode {
  val node = Nodes.ViewNode(javaClass<T>())
  if (stack.isNotEmpty()) {
    stack.peek().addViews(node)
  }
  stack.push(node)
  f()
  return stack.pop()
}