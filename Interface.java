//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Interface extends JFrame { 
  
  JFrame thisFrame;
  BinaryTree inventory;
  DefaultListModel<String> listModel;
  JList<String> list;
  InformationWindow infoWindow;
  SignOutsWindow signOutsWindow;
  int nextNumber;
  
  //Constructor
  Interface(String userType) { 
    super("Music Dept.Software");
    
    this.nextNumber = 1;
    this.inventory = new BinaryTree();
    this.listModel = new DefaultListModel<String>();
    
    try {
      BufferedReader in = new BufferedReader(new FileReader("data/inventory.txt"));
      String itemType;
      
      //Read through everything in the file and add it to the inventory
      while((itemType = in.readLine()) != null) {
        
        String name = in.readLine();
        
        //If the item is an instrument, create an instrument
        if(itemType.equals("Instrument")) {
          Instrument temp =  new Instrument(name, Integer.parseInt(in.readLine()), in.readLine(), in.readLine(), in.readLine(), in.readLine(), in.readLine(), in.readLine());
          
          if (inventory.add(temp)) { //Add the instrument to the inventory
            listModel.add(inventory.indexOf(name), name); //If add was successful, add to listModel
            nextNumber++;
          }
        } else if(itemType.equals("SheetMusic")) { //Similar to above but for sheet music
          SheetMusic temp =  new SheetMusic(name, Integer.parseInt(in.readLine()), in.readLine(), in.readLine(), in.readLine(), in.readLine(), in.readLine());
          
          if (inventory.add(temp)) {
            listModel.add(inventory.indexOf(name), name);
            nextNumber++;
          }
        } if(itemType.equals("Equipment")) { //Similar to above but for equipment
          Equipment temp =  new Equipment(name, Integer.parseInt(in.readLine()), in.readLine(), in.readLine(), in.readLine(), in.readLine(), in.readLine());
          
          if (inventory.add(temp)) {
            listModel.add(inventory.indexOf(name), name);
            nextNumber++;
          }
        }
      }
      in.close(); //Close the reader
      
    } catch (IOException e) {
      System.out.println("No saved data found");
    } catch (Exception e) {
      System.out.println("Failed to load data");
      e.printStackTrace();
    }
    
    this.thisFrame = this; 
    
    //configure the window
    this.setSize(700,400);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen 
    this.setResizable (false);
    this.thisFrame.addWindowListener((WindowAdapter)(new WindowHandler())); //Listener for window closing
    
    //Panel that contains the list
    JPanel listPanel = new JPanel();
    listPanel.setLayout(new BorderLayout()); //Uses BorderLayout
    
    list = new JList<String>(listModel); //Create a list from the listModel
    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    listPanel.add(list, BorderLayout.CENTER); //Put it at the center
    
    //Panel for actions
    JPanel actionPanel = new JPanel();
    actionPanel.setLayout(new GridLayout(0,3)); //Uses GridLayout
    
    //Creating JButtons for the actionPanel and adding the appropirate action listeners
    JButton addInstrumentButton = new JButton("Add instrument");
    addInstrumentButton.addActionListener(new AddInstrumentButtonListener());
    
    JButton addSheetMusicButton = new JButton("Add Sheet Music");
    addSheetMusicButton.addActionListener(new AddSheetMusicButtonListener());
    
    JButton addEquipmentButton = new JButton("Add Equipment");
    addEquipmentButton.addActionListener(new AddEquipmentButtonListener());
    
    JButton removeItemButton = new JButton("Remove selected item");
    removeItemButton.addActionListener(new RemoveItemButtonListener());
    
    JButton moreInformationButton = new JButton("View selected item");
    moreInformationButton.addActionListener(new MoreInformationButtonListener());
    
    JButton borrowButton = new JButton("Borrow selected item");
    borrowButton.addActionListener(new BorrowButtonListener());
    
    JButton returnButton = new JButton("Return selected item");
    returnButton.addActionListener(new ReturnButtonListener());
    
    JButton repairButton = new JButton("Mark as repairing");
    repairButton.addActionListener(new RepairButtonListener());
    
    JButton doneRepairingButton = new JButton("Mark as repaired");
    doneRepairingButton.addActionListener(new DoneRepairingButtonListener());
    
    JButton viewSignOutsButton = new JButton("Browse all sign-outs");
    viewSignOutsButton.addActionListener(new ViewSignOutsButtonListener());
    
    JButton setConditionButton = new JButton("Set item condition");
    setConditionButton.addActionListener(new SetConditionButtonListener());
    
    JButton setDescriptionButton = new JButton("Set item description");
    setDescriptionButton.addActionListener(new SetDescriptionButtonListener());
    
    //Add all panels to the actionPanel based on the user type
    
    actionPanel.add(moreInformationButton); //Both users have this button
    
    if (userType.equals("Student")) {
      actionPanel.add(borrowButton);
      actionPanel.add(returnButton);
    } else if (userType.equals("Teacher")) {
      actionPanel.add(repairButton);
      actionPanel.add(doneRepairingButton);
      actionPanel.add(addInstrumentButton);
      actionPanel.add(addSheetMusicButton);
      actionPanel.add(addEquipmentButton);
      actionPanel.add(setConditionButton);
      actionPanel.add(setDescriptionButton);
      actionPanel.add(viewSignOutsButton);
      actionPanel.add(removeItemButton);
    }
    
    //Uper panel that simply says "Inventory"
    JPanel upperPanel = new JPanel();
    
    JLabel label = new JLabel("Inventory"); //Create JLabel
    upperPanel.add(label); //Add JLabel to the panel
    
    //add the panels to the frame
    this.add(upperPanel, BorderLayout.NORTH);
    this.add(listPanel, BorderLayout.CENTER);
    this.add(actionPanel, BorderLayout.SOUTH);
    
    //Start the app
    this.setVisible(true);
  }
  
  //Innner classes for handling events
  
  class AddInstrumentButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event)  {  
      
      //Get user information about the instrument
      String type = JOptionPane.showInputDialog("What type of instrument are you adding?");
      while (type.equals("")) { //Loops while the input is empty
        type = JOptionPane.showInputDialog("This field cannot be blank.\nWhat type of instrument are you adding?");
      }
      
      String name = JOptionPane.showInputDialog("Please name your " + type.toLowerCase() +".");
      while (name.equals("")) {
        name = JOptionPane.showInputDialog("This field cannot be blank.\nPlease name your " + type.toLowerCase() +".");
      }
      
      String description = JOptionPane.showInputDialog("Please enter a description.");
      while (description.equals("")) {
        description = JOptionPane.showInputDialog("This field cannot be blank.\nPlease enter a description.");
      }
      
      String condition = JOptionPane.showInputDialog("Enter the instrument's condition.");
      while (condition.equals("")) {
        condition = JOptionPane.showInputDialog("This field cannot be blank\nEnter the intrument's condition.");
      }
      
      
      if (inventory.add(new Instrument(name, nextNumber, description, condition, type)) ) { //Attempt to add it to the inventory
        listModel.add(inventory.indexOf(name), name); //If successful, add it to the list at the appropriate index
        nextNumber++; //Update the number for the next item
      } else {
        JOptionPane.showMessageDialog(null, "An tem of the same name already exists."); //If an item of the same name exists, report it.
      }
      
    }
    
  }
  
  class AddSheetMusicButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event) {
      
       //Get user information about the sheet music      
      String name = JOptionPane.showInputDialog("Please name your sheet music.");
      while (name.equals("")) {
        name = JOptionPane.showInputDialog("This field cannot be blank.\nPlease name your sheet music.");
      }
      
      String description = JOptionPane.showInputDialog("Please enter a description.");
      while (description.equals("")) {
        description = JOptionPane.showInputDialog("This field cannot be blank.\nPlease enter a description.");
      }
      
      String condition = JOptionPane.showInputDialog("Enter the sheet's condition.");
      while (condition.equals("")) {
        condition = JOptionPane.showInputDialog("This field cannot be blank\nEnter the sheet's condition.");
      }
      
      if (inventory.add(new SheetMusic(name, nextNumber, description, condition)) ) { //Attempt to add it to the inventory
        listModel.add(inventory.indexOf(name), name); //If successful, add it to the list at the appropriate index
        nextNumber++; //Update the number for the next item
      } else {
        JOptionPane.showMessageDialog(null, "An item of the same name already exists."); //If an item of the same name exists, report it.
      }
    }
  }
  
  class AddEquipmentButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event) {
      
      //Get user information about the equipment
      String name = JOptionPane.showInputDialog("Please name your equipment.");
      while (name.equals("")) {
        name = JOptionPane.showInputDialog("This field cannot be blank.\nPlease name your sheet music.");
      }
      
      String description = JOptionPane.showInputDialog("Please enter a description.");
      while (description.equals("")) {
        description = JOptionPane.showInputDialog("This field cannot be blank.\nPlease enter a description.");
      }
      
      String condition = JOptionPane.showInputDialog("Enter the equipment's condition.");
      while (condition.equals("")) {
        condition = JOptionPane.showInputDialog("This field cannot be blank\nEnter the sheet's condition.");
      }
      
      if (inventory.add(new SheetMusic(name, nextNumber, description, condition)) ) { //Attempt to add it to the inventory
        listModel.add(inventory.indexOf(name), name); //If successful, add it to the list at the appropriate index
        nextNumber++; //Update the number for the next item
      } else {
        JOptionPane.showMessageDialog(null, "An item of the same name already exists."); //If an item of the same name exists, report it.
      }
    }
  }
  
  class RemoveItemButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      inventory.remove(new Instrument(list.getSelectedValue())); //Remove item from the inventory
      listModel.remove(list.getSelectedIndex()); //Remove it from the list
      
    }
  }
  
  class MoreInformationButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      if(list.getSelectedValue() == null) { //If no item has been selected, report it.
        JOptionPane.showMessageDialog(null, "Please select an item.");
      } else {
        if(infoWindow != null) { //If an infoWindow is already open, close it first
          infoWindow.dispose();
        }
        infoWindow = new InformationWindow(inventory.getItem(list.getSelectedValue()), list.getSelectedIndex(), list.getModel(), inventory); //Creates a new InformationWindow to display the item
      }
      
    }
  }
  
  class BorrowButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      if(list.getSelectedValue() == null) { //Ensure that an item is selected
        JOptionPane.showMessageDialog(null, "Please select an item.");
      } else {
        Item item = inventory.getItem(list.getSelectedValue()); //Get the item
        
        if(item.getStatus().equals("Available")) {
          String currentDate = JOptionPane.showInputDialog("Please enter the date checked out in the following format: YYYY/MM/DD"); //Asks for date signed out
          String dueDate = JOptionPane.showInputDialog("Please enter the due date in the following format: YYYY/MM/DD"); //Asks for due date
          item. checkOut(currentDate, dueDate);
        } else {
          JOptionPane.showMessageDialog(null, "Sorry, the item is unavailable at this time."); //If item is unavailable, report it
        }
      }
      
    }
  }
  
  class ReturnButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      if(list.getSelectedValue() == null) { //Ensure an item is selected
        JOptionPane.showMessageDialog(null, "Please select an item.");
      } else {
        Item item = inventory.getItem(list.getSelectedValue()); //Get the item
        
        if(item.getStatus().equals("Signed out")) {
          String currentDate = JOptionPane.showInputDialog("Please enter the current date in the following format: YYYY/MM/DD"); //Asks for date returned
          if( inventory.getItem(list.getSelectedValue()).giveBack(currentDate) ) { //Return item and check if it was returned on time
            JOptionPane.showMessageDialog(null, "Item returned on time.");
          } else {
            JOptionPane.showMessageDialog(null, "Item is late.");
          }
        } else {
          JOptionPane.showMessageDialog(null, "The item is not currently signed out."); //If the item is not currently signed out, report it.
        }
      }
      
    }
  }
  
  class SetConditionButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      if(list.getSelectedValue() == null) { //Ensure an item is selected
        JOptionPane.showMessageDialog(null, "Please select an item.");
      } else {
        Item item = inventory.getItem(list.getSelectedValue()); //Get item
        
        String newCondition = JOptionPane.showInputDialog("Please enter the item's new condition."); //Asks for new condition
        item.setCondition(newCondition); //Updates the item
      }
      
    }
  }
  
  class SetDescriptionButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      if(list.getSelectedValue() == null) { //Ensure an item is selected
        JOptionPane.showMessageDialog(null, "Please select an item.");
      } else {
        Item item = inventory.getItem(list.getSelectedValue()); //Get item
        
        String newCondition = JOptionPane.showInputDialog("Please enter the item's new description."); //Asks for new condition
        item.setDescription(newCondition); //Updates the item
      }
      
    }
  }
  
  class RepairButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      if(list.getSelectedValue() == null) { //Ensures an item has been selected
        JOptionPane.showMessageDialog(null, "Please select an item.");
      } else {
        Item item = inventory.getItem(list.getSelectedValue()); //Get the item
        
        if(item.getStatus().equals("Available")) { //If the item is available, update the item status
          item.setStatus("Repairing");
          JOptionPane.showMessageDialog(null, "The item's status has been updated.");
        } else if (item.getStatus().equals("Signed out")) { //If it is signed out, report it
          JOptionPane.showMessageDialog(null, "The item is currently unavailable.");
        } else if (item.getStatus().equals("Repairing")) { //If it is already repairing, report it
          JOptionPane.showMessageDialog(null, "The item is already under repair.");
        }
      }
      
    }
  }
  
  class DoneRepairingButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      if(list.getSelectedValue() == null) { //Ensures an item has been selected
        JOptionPane.showMessageDialog(null, "Please select an item.");
      } else {
        Item item = inventory.getItem(list.getSelectedValue()); //Gets the item
        
        if(item.getStatus().equals("Repairing")) { //If item is under repair, update the stuatus
          item.setStatus("Available");
          JOptionPane.showMessageDialog(null, "The item's status has been updated.");
        } else {
          JOptionPane.showMessageDialog(null, "The item is not currently under repair."); //If item is not under repair, report it
        }
      }
      
    }
  }
  
  class ViewSignOutsButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      LinkedList<Item> signedOut = inventory.getSignedOut(); //Get the signed-out items sorted by date signed out
      
      if (signOutsWindow != null) { //If a window showing the signouts already exists, dispose of it first
        signOutsWindow.dispose();
      }
      signOutsWindow = new SignOutsWindow(signedOut, inventory); //Create a new window to browse the sign outs
      
    }
  }
  
  class WindowHandler extends WindowAdapter {
    public void windowClosing(WindowEvent w) {
      inventory.save("data/inventory.txt"); //Save the inventory
      thisFrame.dispose(); // Release the window resources
      System.exit(0); // End the application
    }
  }
  
}