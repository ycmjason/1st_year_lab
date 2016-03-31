package dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.lang.UnsupportedOperationException;

import java.util.Stack;

/*
 * Binary search tree based implementation of the Dictionary
 * interface. The nodes of the tree are ordered by an associated key-attribute
 * key of type K, such that every node's left subtree contains only nodes 
 * whose key-attributes are less than key, and every node's right subtree
 * contains only nodes whose key-attributes are greater than key. A
 * linear order is defined on keys through the Comparable interface.
 * Duplicate keys are not permitted.
 */
public class BinarySearchTree<K extends Comparable<? super K>, V> implements
        Dictionary<K, V> {
    private int size = 0;
    private BinarySearchTreeEntry<K, V> head;
    private int state = 0;

    @Override
    public int size(){
      return size;
    }

    @Override
    public boolean isEmpty(){
      return (size == 0);
    }

    @Override
    public V get(K key) throws NoSuchElementException{
      BinarySearchTreeEntry<K, V> current = head;

      while (current != null){
        if (current.getKey().equals(key)){
          return current.getValue();
        }

        if (current.getKey().compareTo(key) > 0){
          current = current.left;
        }else {
          current = current.right;
        }
      }

      throw new NoSuchElementException();
    };

    @Override
    public void put(K key, V value){
      state++;
      if (head == null){
        size++;
        head = new BinarySearchTreeEntry<K, V>(key, value);
        return;
      }

      BinarySearchTreeEntry<K, V> current = head;

      while (current != null){
        if (current.getKey().equals(key)){
          current.val = value;
          return;
        }

        if (current.getKey().compareTo(key) > 0){
          if (current.left != null){
            current = current.left;
          } else{
            size++;
            current.left = new BinarySearchTreeEntry<K, V>(key, value);
            return;
          }
        }else {
          if (current.right != null){
            current = current.right;
          } else{
            size++;
            current.right = new BinarySearchTreeEntry<K, V>(key, value);
            return;
          }
        }
      }
    };
    
    private BinarySearchTreeEntry<K, V> takeLeast(BinarySearchTreeEntry<K, V> parent, BinarySearchTreeEntry<K, V> current){
      while (current.left != null){
        parent = current;
        current = current.left;
      }
      parent.left = null;
      return current;
    }

    private BinarySearchTreeEntry<K, V> takeGreatest(BinarySearchTreeEntry<K, V> parent, BinarySearchTreeEntry<K, V> current){
      while (current.right != null){
        parent = current;
        current = current.right;
      }
      parent.right = null;
      return current;
    }

    @Override
    public void remove(K key) throws NoSuchElementException{
      state++;
      if (head == null){
        throw new NoSuchElementException();
      }

      if (head.getKey().equals(key)){
        size--;
        head = null;
        return;
      }

      BinarySearchTreeEntry<K, V> parent = null;
      BinarySearchTreeEntry<K, V> current = head;

      while (current != null){
        if (current.getKey().equals(key)){
          //case 1, current is a leaf, i.e. no more element to search
          if (current.left == null && current.right == null){
            parent.left = null;
            parent.right = null;
          //case 2, current has 1 left child
          }else if (current.right == null){
            if (parent.left == current){
              parent.left = current.left;
            }else{
              parent.right = current.left;
            }
          //case 3, current has 1 right child
          }else if (current.left == null){
            if (parent.left == current){
              parent.left = current.right;
            }else{
              parent.right = current.right;
            }
          //case 4, current has 2 children
          }else{
            if (parent.left == current){
              parent.left = takeLeast(current, current.right);
            }else{
              parent.right = takeLeast(current, current.right);
            }
          }
          size--;
          return;
        }
        parent = current;
        
        if(current.getKey().compareTo(key) > 0){
          current = current.left;
        }else{
          current = current.right;
        }
      }

      throw new NoSuchElementException();
    };

    @Override
    public void clear(){
      state++;
      size = 0;
      head = null;
    };

    @Override
    public Iterator<DictionaryEntry<K, V>> iterator(){
      return new Iterator<DictionaryEntry<K, V>>(){
        BinarySearchTreeEntry<K, V> current = head;
        Stack<BinarySearchTreeEntry<K, V>> workList = new Stack<BinarySearchTreeEntry<K, V>>();
        int recordedState = state;

        private void pushAllLeftNodes(){
          while (current != null){
            workList.push(current);
            current = current.left;
          }
        }

        private void throwConcurrentExc() throws ConcurrentModificationException{
          if(recordedState != state){
            throw new ConcurrentModificationException();
          }
        }

        public boolean hasNext() throws ConcurrentModificationException{
          throwConcurrentExc();
          pushAllLeftNodes();

          return (!workList.empty());
        }

        public DictionaryEntry<K, V> next() throws NoSuchElementException, ConcurrentModificationException{
          throwConcurrentExc();
          BinarySearchTreeEntry<K, V> result;

          pushAllLeftNodes();

          if (!workList.empty()){
            result = workList.pop();
            current = result.right;
            return result;
          }
          throw new NoSuchElementException();
        }

        public void remove() throws UnsupportedOperationException{
          throw new UnsupportedOperationException();
        }

      };
    }
}
