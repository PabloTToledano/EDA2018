import graphsDSESIUCLM.*;

public class DecoratedElement<T> implements Element {

  private String ID;                 //Vertex ID
  private T element;                 //Data Element
  private boolean visited;          //Attribute to label the node as visited
  private DecoratedElement<T> parent; // Vertex from which
                                                // the current node is accessed
  private int distance;    // Distance (in vertices) from the original node

  public DecoratedElement(String key, T element) {
    this.element = element;
    ID = key;
    visited = false;
    parent = null;
    distance = 0;
  }

  public T getElement() {
    return element;
  }
  public boolean getVisited() {
    return visited;
  }
  public void setVisited(boolean t) {
    visited = t;
  }
  public DecoratedElement<T> getParent() {
    return parent;
  }
  public void setParent(DecoratedElement<T> u) {
    parent = u;
  }
  public int getDistance() {
    return distance;
  }
  public void setDistance(int d) {
    distance = d;
  }

  /* In this case, to check if two Vertices are identical, both the key and the
   * element must be equal.
   * Notice the cast to convert n (class Object) to class
   * DecoratedElementShortestPath<T>
   * IMPORTANT: element needs to override equals()
  */
  public boolean equals (Object n) {
    return (ID.equals(((DecoratedElement) n).getID())
       && element.equals(((DecoratedElement<T>) n).getElement()));
  }
  public String toString() {
    return element.toString();   //element needs to override toString()
  }
  public String getID() {
    return ID;
  }
}
