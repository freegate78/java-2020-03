import generics.bounds.entries.Animal;
import generics.bounds.entries.Cat;
import generics.bounds.entries.HomeCat;
import generics.bounds.entries.WildCat;
import org.w3c.dom.Node;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.function.UnaryOperator;


public class DIYArrayList <T> implements List<T>
{
//    public List<T> subList(int fromIndex, int toIndex) {        throw new UnsupportedOperationException();    }
//    void addAll(Collection<? super T> c, T... elements)
//    static <T> void copy(List<? super T> dest, List<? extends T> src)
//    static <T> void sort(List<T> list, Comparator<? super T> c)

     public static void main(String[] args) {

         var list = new DIYArrayList<Integer>(30);
         //Collections.addAll(Collection<? super T> c, T... elements)
         Collections.addAll(list, 111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132); // <-- !!! НЕ list.addAll !!!
         //for (int i=0; i < list.size(); i++){System.out.println(list.get(i));}
         //System.out.println(list.for);//ссылка на объект ,фу
         //for (list : listitr) { System.out.println(listitr);}; //ошибка, чето недокрутил
         list.forEach(c -> System.out.println(c));//заработало после реализации итератора

         var list2 = new DIYArrayList<Integer>(30);
         // не работает без добавления элементов в массив, он создается пустым
         //добавили 22 нуля чтобы проверить что будет после копирования через метод коллекций
         Collections.addAll(list2, 0, 0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0); // <-- !!! НЕ list.addAll !!!
         System.out.println("size of list is "+list.size());
         System.out.println("size of list2 is "+list2.size());
         //эта штука зависит от public ListIterator listIterator()
         //взял его код из  Iterator, и пришлось все методы остальные переопредялть, чето сделал...
         //Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
         Collections.copy(list2,list);
         System.out.println("size of list2 after copy is "+list2.size());
         //элементы list скопировались в list2
         list2.forEach(c -> System.out.println(c));//заработало после реализации итератора
//         for (int i=0; i < list2.size(); i++){System.out.println(list2.get(i));}

         var list3 = new DIYArrayList<Integer>(30);
         //добавили 22 цифры в произвольном порядке чтобы проверить что будет после сортировки через метод коллекций
         Collections.addAll(list3, 112, 122,107,114,104,117,113,102,111,106,103,116,101,121,115,109,105,120,110,119,108,118); // <-- !!! НЕ list.addAll !!!
         //Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
         Collections.sort(list3, null);
         System.out.println("elements of list3 after copy");
         list3.forEach(c -> System.out.println(c));//заработало после реализации итератора
    }


    private int size = 0;
    private int capacity = 0;
    private final int CAPACITY = 30;
    private Object[] array;

    public DIYArrayList () {
        capacity = CAPACITY;
        array = new Object[capacity];
    }

    public DIYArrayList(int capacity) {
        this.capacity = capacity;
        array = new Object[capacity];
    }

    private void increaseCapacity() {
        capacity = capacity * 2;
        Object[] newArray = new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
            array[i] = null;
        }
        array = newArray;
    }

    private void trimToSizeArray() {
        capacity = size + 1;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator <T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size && array[currentIndex] != null;
            }

            @Override
            public T next() {
                return (T)array[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
          };
        };

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    @Override
    public boolean add(Object o) {
        if (size >= capacity) {
            increaseCapacity();
        }
        array[size++] = o;
        return true;
    }

    private void shiftToLeft(int start) {
        size--;
        if (size <= 0) {
            return;
        }
        if (size != start) {
            System.arraycopy(array, start + 1, array, start, size - start);
        }
        array[size] = null;
    }

    @Override
    public boolean remove(Object o) {
        if ((size == 0)) {
            return false;
        }
        int i;
        for (i = 0; i < size; i++) {
            if (array[i] == null && o == null) {
                break;
            }
            if ((array[i] != null) && (array[i].equals(o))) {
                break;
            }
        }
        if (i < size) {
            shiftToLeft(i);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        if (c == null) {
            return false;
        }
        if (c.isEmpty()) {
            return false;
        }
        for (Object item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        if (c == null) {
            return false;
        }
        if (c.isEmpty() || (index < 0)) {
            return false;
        }
        if (index > size) {
            index = size;
        }
        for (Object item : c) {
            add(index++, item);
        }
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        if ((index < size) && (index >= 0)) {
            return (T) array[index];
        }
        return null;
    }

    @Override
    public Object set(int index, Object element) {
        if ((index < size) && (index >= 0)) {
            Object o = array[index];
            array[index] = element;
            return o;
        }
        return null;
    }

    @Override
    public void add(int index, Object element) {
        if (index < 0) {
            return;
        }
        if (size + 1 >= capacity) {
            increaseCapacity();
        }
        if (index > size) {
            index = size;
        }
        for (int i = size; i >= index; i--) {
            array[i + 1] = array[i];
        }
        array[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        Object o = null;
        if ((index < size) && (index >= 0)) {
            o = get(index);
            shiftToLeft(index);
        }
        return (T) o;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    lastIndex = i;
                }
            }
            return lastIndex;
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(array[i])) {
                    lastIndex = i;
                }
            }
        }
        return lastIndex;
    }

    @Override
    public ListIterator listIterator() {
        return new ListIterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size && array[currentIndex] != null;
            }

            @Override
            //возвращает значение для установки
            public T next() {
                return (T)array[currentIndex++];
            }

            @Override
            public T previous() {
                return (T)array[currentIndex--];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add (T t)
            {
                throw new UnsupportedOperationException();
            }

            @Override
            //устанавлирает значение в класс, надо передвинуть итератор на 1 назад, т.к. next уже сдвинул
            public void set (T t)
            {
                array[currentIndex-1] = t;

            }

            @Override
            public int previousIndex() { return currentIndex-1;}

            @Override
            public int nextIndex() { return currentIndex+1;}
            @Override
            public boolean hasPrevious() {return currentIndex >0 ? true :false;}

        };
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            int temp = fromIndex;
            fromIndex = toIndex;
            toIndex = temp;
        }
        if ((fromIndex < 0) || (toIndex > size)) {
            return null;
        }
        List myArrayList = new DIYArrayList(toIndex-fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            myArrayList.add(array[i]);
        }
        return myArrayList;
    }

    @Override
    public boolean retainAll(Collection c) {
        if (c == null) {
            return true;
        }
        if (c.size() == 0) {
            clear();
            return true;
        }
        int i = 0;
        boolean modyfied = false;
        while (i < size) {
            if (c.contains(array[i])) {
                i++;
            } else {
                shiftToLeft(i);
                modyfied = true;
            }
        }
        return modyfied;
    }

    @Override
    public boolean removeAll(Collection c) {
        if (c == null) {
            return false;
        }
        if ((c.size() == 0) || (size == 0)) {
            return false;
        }
        boolean modyfied = false;
        int i = 0;
        while (i < size) {
            if (c.contains(array[i])) {
                shiftToLeft(i);
                modyfied = true;
            } else {
                i++;
            }
        }
        return modyfied;
    }

    @Override
    public boolean containsAll(Collection c) {
        if (c == null) {
            return false;
        }
        if (c.size() == 0) {
            return true;
        }
        for (Object e : c) {
            if (contains(e)) {
                ;
            } else {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

}

