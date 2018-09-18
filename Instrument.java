public class Instrument extends Item {
  
  private String instrumentType;
  
  public Instrument(String name, int number, String description, String condition, String type) {
    super(name, number, description, condition);
    this.instrumentType = type;
  }
  
  public Instrument(String name, int number, String description, String condition, String status, String dateSignedOut, String dueDate, String type) {
    super(name, number, description, condition, status, dateSignedOut, dueDate);
    this.instrumentType = type;
  }
  
  public Instrument(String name) {
    super(name);
  }
  
  public String getInstrumentType() {
    return this.instrumentType;
  }
  
}