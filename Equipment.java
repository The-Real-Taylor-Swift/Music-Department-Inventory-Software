public class Equipment extends Item {
  
  public Equipment(String name, int number, String description, String condition) {
    super(name, number, description, condition);
  }
  
  public Equipment(String name, int number, String description, String condition, String status, String dateSignedOut, String dueDate) {
    super(name, number, description, condition, status, dateSignedOut, dueDate);
  }
  
  public Equipment(String name) {
    super(name);
  }
  
}