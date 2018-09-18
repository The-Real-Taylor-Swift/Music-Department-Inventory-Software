//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

public class InformationWindow extends JFrame {
  
  JFrame thisFrame;
  Item item;
  ListModel<String> list;
  BinaryTree inventory;
  JLabel info;
  int currentIndex;
  
  public InformationWindow(Item input, int currentIndex, ListModel<String> list, BinaryTree inventory) {
    super("Information");
    
    this.item = input;
    this.thisFrame = this;  
    this.setSize(500,200);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setResizable(false);
    this.setLayout(new BorderLayout());
    
    this.info = new JLabel("<html>Item name: " + item.getName() + "<br>Item type: " + item.getClass().getName() + "<br>Item number: " + item.getNumber() + "<br>Item description: " + item.getDescription() + "<br>Item condition: " + item.getCondition() + "<br>Item status: " + item.getStatus() + "<br>Date Signed Out: " + item.getDateSignedOut() + "<br>Due date: " + item.getDueDate() + "<html>");
    this.list = list;
    this.currentIndex = currentIndex;
    this.inventory = inventory;
    
    JPanel mainPanel = new JPanel();
    mainPanel.add(info);
    
    JButton next = new JButton ("Next item");
    next.addActionListener(new NextListener());
    
    JButton previous = new JButton("Previous item");
    previous.addActionListener(new PreviousListener());
    
    JPanel right = new JPanel();
    right.add(next);
    
    JPanel left = new JPanel();
    left.add(previous);
    
    this.add(mainPanel, BorderLayout.CENTER);
    this.add(right, BorderLayout.EAST);
    this.add(left, BorderLayout.WEST);
    
    this.setVisible(true);
    
  }
  
  class NextListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      if(currentIndex == list.getSize() - 1) {
        JOptionPane.showMessageDialog(null, "You have reached the end of the list.");
      } else {
        
        String name = list.getElementAt(currentIndex+1);
        Item item = inventory.getItem(name);
        currentIndex++;
        
        info.setText("<html>Item name: " + item.getName() + "<br>Item type: " + item.getClass().getName() + "<br>Item number: " + item.getNumber() + "<br>Item description: " + item.getDescription() + "<br>Item condition: " + item.getCondition() + "<br>Item status: " + item.getStatus() + "<br>Date signed out: " + item.getDateSignedOut() + "<br>Due date: " + item.getDueDate() + "<html>");
      }
      
    }
  }
  
  class PreviousListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      if(currentIndex == 0) {
        JOptionPane.showMessageDialog(null, "You have reached the top of the list.");
      } else {
        
        String name = list.getElementAt(currentIndex-1);
        Item item = inventory.getItem(name);
        currentIndex --;
        
        info.setText("<html>Item name: " + item.getName() + "<br>Item type: " + item.getClass().getName() + "<br>Item number: " + item.getNumber() + "<br>Item description: " + item.getDescription() + "<br>Item condition: " + item.getCondition() + "<br>Item status: " + item.getStatus() + "<br>Date Signed Out: " + item.getDateSignedOut() + "<br>Due date: " + item.getDueDate() + "<html>");
      }
      
    }
  }
  
}