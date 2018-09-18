public class Node implements Comparable<Node> {
  
 private Item item;
 private Node left;
 private Node right;
 
 public Node(Item item, Node left, Node right) {
  this.item = item;
  this.left = left;
  this.right = right;
 }
 
 public Node(Item item) {
   this.item = item;
 }
 
 public int compareTo(Node input) {
   return this.item.getName().compareTo(input.getItem().getName());
 }
 
 public Node getRight() {
  return this.right;
 }
 
 public Node getLeft(){
  return this.left;
 }

 public void setRight(Node input) {
  this.right = input;
 }

 public void setLeft(Node input){
  this.left = input;
 }
 
 public void setItem(Item input){
  this.item = input;
 }
 
 public Item getItem(){
  return this.item;
 }
 
 public boolean isLeaf(){
  return (this.getLeft()==null && this.getRight()==null);
 }
 
}