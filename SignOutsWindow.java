//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import java.util.LinkedList;

public class SignOutsWindow extends JFrame {
  
  JFrame thisFrame;
  DefaultListModel<String> listModel;
  JList<String> list;
  InformationWindow infoWindow;
  BinaryTree inventory;
  
  public SignOutsWindow(LinkedList<Item> input, BinaryTree inventory) {
    super("Signed out items");
    
    this.thisFrame = this;  
    this.setSize(500,200);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setResizable(false);
    this.setLayout(new BorderLayout());
    this.inventory = inventory;
    
    JPanel listPanel = new JPanel();
    listPanel.setLayout(new BorderLayout());
    
    listModel = new DefaultListModel<String>();
    list = new JList<String>(listModel);
    listPanel.add(list, BorderLayout.CENTER);
    
    for(int i = 0; i<input.size(); i++) {
      listModel.addElement(input.get(i).getName());
    }
    
    JPanel actionPanel = new JPanel();
    actionPanel.setLayout(new BorderLayout());
    
    JButton viewItemButton = new JButton("View selected item");
    viewItemButton.addActionListener(new ViewItemButtonListener());
    actionPanel.add(viewItemButton, BorderLayout.CENTER);
    
    this.add(listPanel, BorderLayout.CENTER);
    this.add(actionPanel, BorderLayout.SOUTH);
    
    this.setVisible(true);
    
  }
    
  class ViewItemButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
       if(list.getSelectedValue() == null) {
        JOptionPane.showMessageDialog(null, "Please select an item.");
      } else {
        if(infoWindow != null) {
          infoWindow.dispose();
        }
        infoWindow = new InformationWindow(inventory.getItem(list.getSelectedValue()), list.getSelectedIndex(), list.getModel(), inventory);
      }
      
    }
  }
  
}