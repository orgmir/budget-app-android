package pt.orgmir.budgetandroid

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlin.properties.Delegates

/**
 * Created by Luis Ramos on 8/1/2015.
 */
public class BAApplication : Application() {

  companion object {
    public var context : Context by Delegates.notNull()

    public var realm : Realm by Delegates.notNull()
  }

  override fun onCreate() {
    super.onCreate()

    context = getApplicationContext()

    val realmConfig = RealmConfiguration.Builder(context)
        .build()
    Realm.setDefaultConfiguration(realmConfig)

    realm = Realm.getDefaultInstance()
  }
}