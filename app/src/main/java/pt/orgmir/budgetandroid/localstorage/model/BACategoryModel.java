package pt.orgmir.budgetandroid.localstorage.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Luis Ramos on 8/12/2015.
 */
public class BACategoryModel extends RealmObject {

	@PrimaryKey
	private String id = "";

	private String name = "";

	public BACategoryModel() { super(); }

	public String getId() {
		return id;
	} 

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

