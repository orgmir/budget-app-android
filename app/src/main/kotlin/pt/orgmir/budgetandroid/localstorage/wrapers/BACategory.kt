package pt.orgmir.budgetandroid.localstorage.wrapers

import io.realm.Realm
import pt.orgmir.budgetandroid.BAApplication
import pt.orgmir.budgetandroid.localstorage.model.BACategoryModel

/**
 + Created by Luis Ramos on 8/12/2015.
 */
 public class BACategory(public var model: BACategoryModel) {

 	public constructor() : this(BACategoryModel())

 	companion object {

 		private val defaultCategories = listOf(
			"Supermercado",
			"Vestuário",
			"Despesas casa",
			"Móveis e eletrodomésticos",
			"Saúde",
			"Transportes",
			"Comunicações",
			"Lazer",
			"Restaurantes e bares",
			"Diversos"
 		)

 		public fun findAll() : List<BACategory> {
 			val findAll = BAApplication.realm.where(javaClass<BACategoryModel>()).findAll()
 			return findAll.map { BACategory(it) }
 		}

 		public fun hasCategories() : Boolean {
 			return BAApplication.realm.where(javaClass<BACategoryModel>()).count() > 0
 		}

 		public fun createCategories() {
 			saveData {
 				for(name in defaultCategories){
 					_create(name, it)
 				}
 			}
 		}

 		private fun _create(name: String, realm: Realm) {
			val category = BACategory()
			category.id = name.toLowerCase()
			category.name = name
			realm.copyToRealm(category.model)
		}
 	}

	public var id : String
    get() = model.getId()
    set(value) { model.setId(value) }

  public var name : String
    get() = model.getName()
    set(value) { model.setName(value) }

 }