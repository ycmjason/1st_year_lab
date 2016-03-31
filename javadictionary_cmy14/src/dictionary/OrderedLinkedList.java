package dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.lang.UnsupportedOperationException;

/*
 * Ordered linked list based implementation of the Dictionary
 * interface. The nodes of the list are ordered in ascending order by
 * the key-attribute of type K. Duplicate keys are not permitted.
 */
public class OrderedLinkedList<K extends Comparable<? super K>, V> implements
        Dictionary<K, V> {
    int size;
    OrderedLinkedListEntry<K, V> head;
    //track concurrent
    int state = 0;

    @Override
    public int size(){
      return size;
    }

    @Override
    public boolean isEmpty(){
      return (size==0);
    }

    @Override
    public V get(K key) throws NoSuchElementException{
      OrderedLinkedListEntry<K, V> current = head;
      while (current != null){
        if(current.getKey().equals(key)){
          return current.getValue();
        }
        current = current.next;
      }
      throw new NoSuchElementException();
    }

    @Override
    public void put(K key, V value){
      state++;
      OrderedLinkedListEntry<K, V> newEntry = new OrderedLinkedListEntry<K,V>(key, value, null);
      //deal with empty list
      if(size <= 0){
        head = newEntry;
        size++;
        return;
      }

      //deal with first node
      if(head.getKey().compareTo(key) >= 0){
        newEntry.pointTo(head);
        head = newEntry;
        size++;
        return;
      }

      OrderedLinkedListEntry<K, V> current = head;
      while (current != null){
        // shortcircuit on {next == null}, i.e. end of list, to avoid null pointer exception
        if (current.next == null || current.next.getKey().compareTo(key) > 0){ 
          newEntry.pointTo(current.next);
          current.pointTo(newEntry);
          size++;
          return;
        }
        if (current.next.getKey().equals(key)){
          current.next.val = value;
          return;
        }
        current = current.next;
      }

    }

    @Override
    public void remove(K key) throws NoSuchElementException{
      state++;
      if (head == null){
        throw new NoSuchElementException();
      }

      if (head.getKey().equals(key)){
        head = head.next;
        size--;
        return;
      }

      OrderedLinkedListEntry<K, V> current = head;
      while (current.next != null){
        if (current.next.getKey().equals(key)){
          current.pointTo(current.next.next);
          size--;
          return;
        }
        current = current.next;
      }
      throw new NoSuchElementException();
    }

    @Override
    public void clear(){
      state++;
      head = null;
      size = 0;
    }

    @Override
    public Iterator<DictionaryEntry<K, V>> iterator(){
      return new Iterator<DictionaryEntry<K, V>>(){
        OrderedLinkedListEntry<K, V> current = head;
        int recordedState = state;

        private void throwConcurrentExc() throws ConcurrentModificationException{
          if(recordedState != state){
            throw new ConcurrentModificationException();
          }
        }

        public boolean hasNext() throws ConcurrentModificationException{
          throwConcurrentExc();
          return (current != null);
        }

        public DictionaryEntry<K, V> next() throws NoSuchElementException, ConcurrentModificationException{
          throwConcurrentExc();
          if (hasNext()){
            DictionaryEntry<K,V> result = current;
            current = current.next;
            return result;
          }else{
            throw new NoSuchElementException();
          }
        }

        public void remove() throws UnsupportedOperationException{
          throw new UnsupportedOperationException();
        }

      };
    }
}
