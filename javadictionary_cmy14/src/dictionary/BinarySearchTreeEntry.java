package dictionary;

import dictionary.DictionaryEntry;

// Implementation class representing nodes of the binary search tree.
public class BinarySearchTreeEntry<K, V> implements DictionaryEntry<K, V> {
  K key;
  V val;
  BinarySearchTreeEntry<K, V> left;
  BinarySearchTreeEntry<K, V> right;

  public BinarySearchTreeEntry(K key, V val){
    this.key = key;
    this.val = val;
  }
  
  @Override
  public K getKey(){
    return key;
  }

  @Override
  public V getValue(){
    return val;
  }
}
