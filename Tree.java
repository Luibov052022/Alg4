public class Tree<V extends Comparable<V>> {

  private Node root;

  private class Node {

    private V value;
    private Color color;
    private Node left;
    private Node right;
  }

  public boolean add(V value) {
    if (root != null) {
      boolean result = addNode(root, value);
      root = rebalance(root);
      root.color = Color.BLACK;
      return result;
    }
    root = new Node();
    root.color = Color.BLACK;
    root.value = value;
    return true;
  }

  private boolean addNode(Node node, V value) {
    if (node != null && node.value == value) {
      return false;
    }
    if (node != null && node.value.compareTo(value) > 0) {
      if (node.left != null) {
        boolean result = addNode(node.left, value);
        node.left = rebalance(node.left);
        return result;
      }
      node.left = new Node();
      node.left.color = Color.RED;
      node.left.value = value;
      return true;
    }
    if (node != null && node.right != null && node.right != value) {
      boolean result = addNode(node.right, value);
      node.right = rebalance(node.right);
      return result;
    }
    node.right = new Node();
    node.right.color = Color.RED;
    node.right.value = value;
    return true;
  }

  private Node rebalance(Node node) {
    Node result = node;
    boolean needRebalance;
    do {
      needRebalance = false;
      if (
        result.right != null &&
        result.right.color == Color.RED &&
        (result.left == null || result.left.color == Color.BLACK)
      ) {
        needRebalance = true;
        result = rightSwap(result);
      }
      if (
        result.left != null &&
        result.left.color == Color.RED &&
        result.left.left != null &&
        result.left.left.color == Color.RED
      ) {
        needRebalance = true;
        result = leftSwap(result);
      }
      if (
        result.left != null &&
        result.left.color == Color.RED &&
        result.right != null &&
        result.right.color == Color.RED
      ) {
        needRebalance = true;
        colorSwap(result);
      }
    } while (needRebalance);
    return result;
  }

  private Node rightSwap(Node node) {
    Node rightChild = node.right;
    Node betweenChild = rightChild.left;
    rightChild.left = node;
    node.right = betweenChild;
    rightChild.color = node.color;
    node.color = Color.RED;
    return rightChild;
  }

  private Node leftSwap(Node node) {
    Node leftChild = node.left;
    Node betweenChild = leftChild.right;
    leftChild.right = node;
    node.left = betweenChild;
    leftChild.color = node.color;
    node.color = Color.RED;
    return leftChild;
  }

  private void colorSwap(Node node) {
    node.right.color = Color.BLACK;
    node.left.color = Color.BLACK;
    node.color = Color.RED;
  }

  private enum Color {
    RED,
    BLACK,
  }

  void print() {
    Node current = root;
    int r = 0;
    int l = 0;
    Color cl = Color.RED;
    Color cr = Color.RED;
    while (current != null) {
      if (current.right != null) {
        r = (int) current.right.value;
        cr = current.right.color;
      } else r = 0;
      if (current.left != null) {
        l = (int) current.left.value;
        cl = current.left.color;
      } else l = 0;

      System.out.println(
        " root " + current.value +  " - " + current.color + " left " + l + " - " + cl + " right " + r + " - " + cr);
      current = current.left;
    }
    current = root.right;
    while (current != null) {
        if (current.right != null) {
          r = (int) current.right.value;
          cr = current.right.color;
        } else r = 0;
        if (current.left != null) {
          l = (int) current.left.value;
          cl = current.left.color;
        } else l = 0;
  
        System.out.println(
          " root " + current.value +  " - " + current.color + " left " + l + " - " + cl + " right " + r + " - " + cr);
        current = current.left;
      }
  }
}
