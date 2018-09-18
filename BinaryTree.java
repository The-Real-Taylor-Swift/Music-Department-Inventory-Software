//Imports
import java.util.LinkedList;
import java.util.Queue;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;

public class BinaryTree {
  
  private Node head;
  
  /**
   * Add a new node into the tree. Calls on helper method addHelper
   *
   * @param The current node
   * @param The node to be added to tree
   * @return True if a new node has been added, false if a node of the same name exists
   */
  public boolean add(Item item) {
    Node input = new Node(item);
    
    if (this.head == null) { //If the tree is empty, add input to the head
      this.head = new Node(input.getItem());
      return true;
    } else {
      return addHelper(this.head, input); //Calls on a recursive helper method
    }
  }
  
  /**
   * Recursive helper method to add a new node into the tree.
   *
   * @param The current node
   * @param The node to be added to tree
   * @return True if a new node has been added, false if a node of the same name exists
   */
  private boolean addHelper(Node current, Node input) {
    
    if (input.compareTo(current) < 0) { //If input should be placed on the left of current
      if (current.getLeft() == null) { //If the left spot is available, place input there and return true
        current.setLeft(input);
        return true;
      } else {
        return addHelper(current.getLeft(), input); //Otherwise, move to the left node
      }
    } else if (input.compareTo(current) > 0) { //Similar to above. If input should be placed on the right, place input on the right (if possible) or move to the right
      if (current.getRight() == null) {
        current.setRight(input);
        return true;
      } else {
        return addHelper(current.getRight(), input);
      }
    } else {
      return false; //If an item with the same name is input already exists, do nothing and return false.
    }
  }
  
  /**
   * Method to check if the tree contains an item
   * 
   * @param Item to look for
   * @return true if the tree contains the item, false otherwise
   */
  public boolean contains(Item input) {
    
    Node lookingFor = new Node(input);
    Node current = this.head;
    
    //Traverses the tree until item is found or a leaf has been reached.
    while(true) {
      if (current.compareTo(lookingFor) == 0) { //If item has been found, return true
        return true;
      } else if (current.getRight() != null && current.compareTo(lookingFor) < 0) {
        current = current.getRight(); //Move to the right
      } else if (current.getLeft() != null && current.compareTo(lookingFor) > 0) {
        current = current.getLeft(); //Move to the left
      } else {
        return false; //If a dead end has been reached without finding the item, return false
      }
    }
  }
  
  /**
   * Method to remove an element
   * 
   * @param Item to remove
   * @return true if the item has been removed, false if the tree does not contain the item
   */
  public boolean remove(Item input) {
    
    Node lookingFor = new Node(input);
    Node current = this.head;
    Node previous = null;
    char orientation = 'L'; //The position of current relative to previous
    
    if(current == null) { //If tree is empty, return false
      return false;
    } else if (current.compareTo(lookingFor) == 0) { //If the head is being removed
      
      if (current.getRight() == null && current.getLeft() == null) { //If tree is empty, remove the head
        this.head = null;
      } else if (current.getRight() == null && current.getLeft() != null) { //If the head has one child, set the other child as the head
        this.head = this.head.getLeft();
      } else if(current.getRight() != null && current.getLeft() == null) {
        this.head = this.head.getRight();
      } else {
        
        //If the head has two children, find the leftmost on the right side of the head and replace the head with it. Then, if the that node has any children, find the rightmost of that node's children and set its right child to the original head's right node.
        Node temp = this.head.getRight();
        Node previousTemp = this.head;
        
        boolean movedLeft = false; //Boolean to store if current's right child has a left child
        
        //If possible, move left once and set movedLeft to true
        if( temp.getLeft() != null) {
          temp = temp.getLeft();
          previousTemp = previousTemp.getRight();
          movedLeft = true;
        }
        
        //Move as far left as possible while keeping track of the previous node
        while (temp.getLeft() != null) {
          temp = temp.getLeft();
          previousTemp = previousTemp.getLeft();
        }
        
        //Find the rightmost of temp's children
        Node temp2 = temp;
        while(temp2.getRight() != null) {
          temp2 = temp2.getRight();
        }
        
        //Get temp's rightmost child and set its right node the head's right child
        if (movedLeft) {
          temp2.setRight(current.getRight());
          previousTemp.setLeft(null);
        }
        
        Node newNode= new Node(temp.getItem(), current.getLeft(), temp.getRight()); //Replace head with temp without changing the head's left child
        this.head = newNode;
      }
      
      return true;
      
    } else if (current.compareTo(lookingFor) < 0) { //If it should move to the right
      if (current.getRight() == null) { //If it's a dead end, return false
        return false;
      } else {
        previous = this.head; //Set previous to head
        current = current.getRight(); //Move to the right
        orientation = 'R'; //Set orientation
      }
    } else if (current.compareTo(lookingFor) > 0) { //Same us above, but moving left
      if (current.getLeft() == null) {
        return false;
      } else {
        previous = this.head;
        current = current.getLeft();
      }
    }
    
    while(true) {
      if (current.compareTo(lookingFor) == 0) {  //If item to remove has been found
        
        if (current.isLeaf()) { //If the item has no children
          if (orientation == 'R') { //Set the previous node's right or left to null (depending on the orientation)
            previous.setRight(null);
          } else {
            previous.setLeft(null);
          }
          
        } else if (current.getRight() != null && current.getLeft() == null) { //If the item has only a left child
          
          if(orientation == 'R') { //Set the previous node's left or right child (depending on the orientation) to the node's right child.
            previous.setRight(current.getRight());
          } else {
            previous.setLeft(current.getRight());
          }
          
        } else if (current.getRight() == null && current.getLeft() != null) { //Similar to above, but for when the item only has a right child
          
          if (orientation == 'R') {
            previous.setRight(current.getLeft());
          } else {
            previous.setLeft(current.getLeft());
          }
          
        } else { //If the item has two children. Similar to removing the head when it has two children
          
          //If the node has two children, find the leftmost on the right side of the node and replace the node with it. Then, if the that node has any children, find the rightmost of that node's children and set its right child to the original node's right child.
          
          Node temp = current.getRight();
          Node previousTemp = current;
          
          boolean movedLeft = false; //Boolean to store if current's right child has a left child
          
          //If possible, move left once and set movedLeft to true
          if( temp.getLeft() != null) {
            temp = temp.getLeft();
            previousTemp = previousTemp.getRight();
            movedLeft = true;
          }
          
          //Move as far left as possible while keeping track of the previous node
          while (temp.getLeft() != null) {
            temp = temp.getLeft();
            previousTemp = previousTemp.getLeft();
          }
          
          //Find the rightmost of temp's children
          Node temp2 = temp;
          while(temp2.getRight() != null) {
            temp2 = temp2.getRight();
          }
          
          //Get temp's rightmost child and set its right node the head's right child
          if(movedLeft) {
            temp2.setRight(current.getRight());
            previousTemp.setLeft(null);
          }
          
          Node newNode= new Node(temp.getItem(), current.getLeft(), temp.getRight()); //Replace current with temp without changing the current's left child
          
          //Replace the node by setting the previous node to newNode
          if(orientation == 'R') {
            previous.setRight(newNode);
          } else {
            previous.setLeft(newNode);
          }
        }
        return true;
        
      } else if (current.getRight() != null && current.compareTo(lookingFor) < 0) { //If the item has not been found and it should be on the right of current (and it's possible to move right)
        current = current.getRight(); //Move right
        
        //Move previous based on its position relative to current
        if (orientation == 'R') {
          previous = previous.getRight();
        } else {
          previous = previous.getLeft();
        }
        
      } else if (current.getLeft() != null && current.compareTo(lookingFor) > 0) { //Similar to above, but moving left
        current = current.getLeft();
        
        if (orientation == 'R') {
          previous = previous.getRight();
        } else {
          previous = previous.getLeft();
        }
        
      } else { //If a dead end has been reached
        return false;
      }
    }
  }
  
  /**
   * Traverses the tree to find an item
   * 
   * @param The name of the item to find
   * @return The item
   */
  public Item getItem(String name) {
    
    Node lookingFor = new Node(new Instrument(name)); //Node to find
    Node current = this.head; //Node to start with
    
    while(true) {
      if (current.compareTo(lookingFor) == 0) { //If item has been found
        return current.getItem();
      } else if (current.getRight() != null && current.compareTo(lookingFor) < 0) { //Traverses the tree. Moves right if possible and necessary
        current = current.getRight();
      } else if (current.getLeft() != null && current.compareTo(lookingFor) > 0) { //Moving left
        current = current.getLeft();
      } else {
        return null; //If a dead end has been reached, return null
      }
    }
  }
  
  /**
   * Finds the number of items that have a lesser value than a given item
   * 
   * @param Name of the item
   * @return Number of items that have a lesser value than the item
   */
  public int indexOf(String name) {
    Node lookingFor = new Node(new Instrument(name));
    Node current = this.head;
    int counter = 0;
    
    if(this.head == null) { //If tree is empty, return 0
      return 0;
    }
    
    //Traverse the tree until item has been found
    while(true) {
      if (current.compareTo(lookingFor) == 0) { //If the item has been found
        
        if(current.getLeft() == null) { //If the item has no left child, simply return counter
          return counter;
        } else {
          return counter + totalChildren(current.getLeft()) + 1; //If the item has more children on its left, return counter plus the total number of children on its left
        }
        
      } else if (current.getRight() != null && current.compareTo(lookingFor) < 0) { //Traverses the tree to find the item. Moves right if possible and necessary
        current = current.getRight();
        if(current.getLeft() != null) { //If the current node has more children on the left
          counter += totalChildren(current.getLeft()) + 1; //When moving right, add the number of children on its left to the counter (as everything to the left of the node is less than it)
        } else {
          counter++; //Increment counter as current is less than the node its looking for
        }
      } else if (current.getLeft() != null && current.compareTo(lookingFor) > 0) { //Move left if possible and necessary
        current = current.getLeft();
      } else {
        return -1; //If a dead end has been reached, the return -1 as the tree does not contain the element
      }
    }
  }
  
  /**
   * Calls on the totalChildren method to determine the number of elements in the tree
   * 
   * @return Number of nodese in the tree
   */
  public int size() {
    if(this.head == null) { //If the tree is empty, return 0
      return 0;
    }
    return 1 + totalChildren(this.head);
  }
  
  /**
   * Uses breadth-frist search to find the number of children of a node
   * 
   * @return Number of children
   */
  private int totalChildren(Node node) {
    Queue<Node> queue = new LinkedList<Node>(); //Keeps track of the nodes to count
    queue.add(node); //Add the node to start from
    int counter = 0;
    
    while (!queue.isEmpty()) { //Loops while there are still more nodes to count           
      Node temp = queue.remove(); //Gets current node and removes it
      
      if(temp.getLeft() != null) { //If the node has a left child, add it to the queue
        queue.add(temp.getLeft());
        counter++;
      }
      if(temp.getRight() != null) { //If the node has a right child, add it to the queue
        queue.add(temp.getRight());
        counter++;
      }
    }
    return counter;
  }
  
  /**
   * Compiles all the items that have been signed out in a linked list in order of the date signed out.
   * Insertion sort is used (it is most efficient in this cae as it is an online algorithm).
   * 
   * @return Sorted linked list of items in ascending order based on the date it was signed out
   */
  public LinkedList<Item> getSignedOut() {
    LinkedList<Item> output = new LinkedList<Item>();
    Queue<Node> queue = new LinkedList<Node>();
    
    if (this.head == null) { //If tree is empty, return an empty linked list
      return output;
    }
    
    queue.add(this.head); //Add the head to the queue
    
    while(!queue.isEmpty()) { //While there are more nodes to consider
      Node temp = queue.remove(); //Get and remove a node from the bottom of the top of the queue
      
      if (temp.getItem().getStatus().equals("Signed out")) { //If item has been signed out, add it to the linked list
        
        int counter = 0; //Index of the linked list the item should be added to
        
        //Search for the right location to add the node to
        while(counter < output.size() && temp.getItem().getDateSignedOut().compareTo(output.get(counter).getDateSignedOut()) > 0) {
          counter ++;
        }
        output.add(counter, temp.getItem()); //Add the item to the appropriate index
      }
      
      if(temp.getLeft() != null) { //If the node has a left child, add it to the queue
        queue.add(temp.getLeft());
      }
      
      if(temp.getRight() != null) { //If the node has a right child, add it to the queue
        queue.add(temp.getRight());
      }
    }
    
    return output;
  }
  
  /**
   * Uses breadth-first-search to write all necessary information in the tree to a file.
   * The items are written in order of depth.
   * 
   * @param Path of the file to be written
   */
  public void save(String fileName) {
    
    try {
      File file = new File(fileName); //Create a new file
      file.getParentFile().mkdirs(); //Create directories if necessary
      PrintWriter writer = new PrintWriter(file); //Write to file
      
      if(this.head == null) { //If the tree is empty, create an empty file
        writer.print("");
      } else {
        
        Queue<Node> queue = new LinkedList<Node>(); //A queue of nodes to consider
        queue.add(this.head); //Add the head to the queue
        
        while(!queue.isEmpty()) { //While there are more nodes to consider
          Node temp = queue.remove(); //Get and remove the item at the top of the queue
          Item item = temp.getItem();
          String itemType = item.getClass().getName();
          
          //Write all the necessary information in this item to the file
          writer.println(item.getClass().getName() + "\r\n" + item.getName() + "\r\n" + item.getNumber() +  "\r\n" + item.getDescription() + "\r\n" + item.getCondition() + "\r\n" + item.getStatus() + "\r\n" + item.getDateSignedOut() + "\r\n" + item.getDueDate());
          
          //Instruments must also include the instrument type
          if(itemType.equals("Instrument")) {
            writer.println(((Instrument)item).getInstrumentType());
          }
          
          if(temp.getLeft() != null) { //If the node has a left child, add it to the queue
            queue.add(temp.getLeft());
          }
          
          if(temp.getRight() != null) { //If the node has a right child, add it to the queue
            queue.add(temp.getRight());
          }
        }
        
      }
      writer.close(); //Close the writer
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}//End of class BinaryTree