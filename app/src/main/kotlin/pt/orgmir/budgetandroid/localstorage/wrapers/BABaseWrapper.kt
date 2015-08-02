package pt.orgmir.budgetandroid.localstorage.wrapers

import io.realm.Realm

/**
 * Created by Luis Ramos on 8/1/2015.
 */

fun saveData(block: (Realm) -> Unit) {
  val realm = Realm.getDefaultInstance()
  realm.executeTransaction(block)
}