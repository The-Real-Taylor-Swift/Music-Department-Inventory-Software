public abstract class Item{
  
  private String name;
  private int number;
  private String description;
  private String condition;
  private String status;
  private String dateSignedOut;
  private String dueDate;
  
  public Item(String name, int number, String description, String condition) {
    this.name = name;
    this.number = number;
    this.description = description;
    this.condition = condition;
    this.status = "Available";
    this.dateSignedOut = "N/A";
    this.dueDate = "N/A";
  }
  
  public Item(String name, int number, String description, String condition, String status, String dateSignedOut, String dueDate) {
    this.name = name;
    this.number = number;
    this.description = description;
    this.condition = condition;
    this.status = status;
    this.dateSignedOut = dateSignedOut;
    this.dueDate = dueDate;
  }
  
  public Item(String name) {
    this.name = name;
  }
  
  public void checkOut(String currentDate, String dueDate) {
    
    this.status = "Signed out";
    this.dateSignedOut = currentDate;
    this.dueDate = dueDate;
  }
  
  public boolean giveBack(String date) {
    
    this.status = "Available";
    this.dateSignedOut = "N/A";
    this.dueDate = "N/A";
    
    if(date.compareTo(this.dueDate) <= 0) { 
      return true;
    } else {
      return false;
    }
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getNumber() {
    return this.number;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String input) {
    this.description = input;
  }
  
  public String getCondition() {
    return this.condition;
  }
  
  public void setCondition(String input) {
    this.condition = input;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String input) {
    this.status = input;
  }
  
  public String getDateSignedOut() {
    return this.dateSignedOut;
  }
  
  public String getDueDate() {
    return this.dueDate;
  }
  
}