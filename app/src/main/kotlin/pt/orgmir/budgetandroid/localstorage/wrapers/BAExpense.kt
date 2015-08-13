package pt.orgmir.budgetandroid.localstorage.wrapers

import io.realm.Realm
import pt.orgmir.budgetandroid.BAApplication
import pt.orgmir.budgetandroid.localstorage.model.BAExpenseModel
import pt.orgmir.budgetandroid.localstorage.wrapers.BACategory

/**
 * Created by Luis Ramos on 7/26/2015.
 */
public class BAExpense(public var model: BAExpenseModel) {

  public constructor() : this(BAExpenseModel())

  companion object{

    public fun findAll() : List<BAExpense> {
      val findAll = BAApplication.realm.where(javaClass<BAExpenseModel>()).findAll()
      return findAll.map { BAExpense(it) }
    }

    public fun findAllOrderedByDate() : List<BAExpense> {
      val findAll = BAApplication.realm.where(javaClass<BAExpenseModel>()).findAllSorted("createdAt", false)
      return findAll.map { BAExpense(it) }
    }
  }

  public var id : String
    get() = model.getId()
    set(value) { model.setId(value) }

  public var value : Double
    get() = model.getValue()
    set(value) { model.setValue(value) }

  public var category: BACategory
    get() = BACategory(model.getCategory())
    set(value) { model.setCategory(value.model) }

  public var title : String
    get() = model.getTitle()
    set(value) { model.setTitle(value)}

  public var description : String?
    get() = model.getDescription()
    set(value) { model.setDescription(description) }

  /**
   * QUERIES
   */

  public fun create() {
    saveData {
      model = it.copyToRealm(model)
    }
  }
}