package pt.orgmir.budgetandroid.localstorage.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 * Created by Luis Ramos on 7/26/2015.
 */
@RealmClass
public open class BAExpenseModel(
  @PrimaryKey public open var id : String = UUID.randomUUID().toString(),
  public open var value : Double = 0.0,
  public open var category : String = "",
  public open var title : String = "",
  public open var description : String? = null
) : RealmObject()