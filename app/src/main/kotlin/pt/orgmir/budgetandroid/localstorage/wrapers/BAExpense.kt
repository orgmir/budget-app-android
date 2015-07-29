package pt.orgmir.budgetandroid.localstorage.wrapers

import pt.orgmir.budgetandroid.localstorage.model.BAExpenseModel

/**
 * Created by Luis Ramos on 7/26/2015.
 */
public class BAExpense(public val model: BAExpenseModel) {

  companion object{

  }

  public var id : String
    get() = model.id
    set(value) { model.id = value}

  public var value : Double
    get() = model.value
    set(value) { model.value = value }

  public var category: String
    get() = model.category
    set(value) { model.category = value }

  public var title : String
    get() = model.title
    set(value) { model.title = value}

  public var description : String?
    get() = model.description
    set(value) { model.description = description }

  /**
   * QUERIES
   */

  
}