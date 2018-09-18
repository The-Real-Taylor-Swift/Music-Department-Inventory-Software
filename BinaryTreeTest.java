public class BinaryTreeTest {
  
  public static void main(String[] args) {
    
    BinaryTree tree = new BinaryTree();
    
    tree.add(new Instrument("a", 2, "ad", "adsf", "dlkfs"));
    tree.add(new Instrument("c"));
    tree.add(new Instrument("b"));
    tree.add(new Instrument("e"));
    tree.add(new Instrument("d"));
    tree.add(new Instrument("f"));
    tree.remove(new Instrument("c"));
    System.out.println(tree.size());
  }
  
}