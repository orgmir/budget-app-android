package pt.orgmir.budgetandroid.localstorage.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Luis Ramos on 8/2/2015.
 */
public class BAExpenseModel extends RealmObject {

  @PrimaryKey
  private String id = UUID.randomUUID().toString();

  private double value = 0.0;

  private String category = "";

  private String title = "";

  private String description = "";

  public BAExpenseModel() { super(); }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
