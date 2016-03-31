package dictionary;

import dictionary.DictionaryEntry;


public class OrderedLinkedListEntry<K, V> implements DictionaryEntry<K, V> {
  K key;
  V val;
  OrderedLinkedListEntry<K, V> next;

  OrderedLinkedListEntry(K key, V val, OrderedLinkedListEntry<K, V> next){
    this.key = key;
    this.val = val;
    this.next = next;
  }
  
  void pointTo(OrderedLinkedListEntry<K, V> newNext){
    next = newNext;
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
