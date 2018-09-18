import javax.swing.JOptionPane;

public class Main {
  
  /* *
   * Main method to start the application
   * 
   * @param Array of strings containing runtime options
   */
  public static void main(String[] args) {
    
    String[] options = new String[] {"Teacher", "Student"};
    int response = JOptionPane.showOptionDialog(null, "Welcome! Are you a teacher or a student?", "Initializing",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);
    
    new Interface(options[response]);
    
  }
  
}