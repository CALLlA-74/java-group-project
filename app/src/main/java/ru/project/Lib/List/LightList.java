package ru.project.Lib.List;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LightList<T> implements List<T> {
    public static final int defaultCapacity = 100;

    private Object[] vals;
    private int capacity = defaultCapacity;
    private int size = 0;

    public LightList() {
        initVals();
    }

    public LightList(int capacity) {
        this.capacity = Math.max(capacity, 1);
        initVals();
    }

    public LightList(T vals[]) {
        this.capacity = Math.max(defaultCapacity, vals.length << 1);
        initVals();
        System.arraycopy(vals, 0, this.vals, 0, vals.length);
    }

    private void initVals() {
        vals = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public boolean contains(Object o) {        
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorEntry();
    }

    @Override
    public Object[] toArray() {
        return (Object[]) toArray(new Object[0]);
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null || a.length < size) {
            a = (T[]) new Object[size];
        }

        System.arraycopy(vals, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(T e) {
        grows();
        vals[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (vals[i].equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var v : c) {
            if (!contains(v)) return false;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        grows(size + c.size());
        
        for (var v : c) {
            if (!add(v)) return false;
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);
        grows(size + c.size());
        System.arraycopy(vals, index, vals, index + c.size(), size - index);

        for (var v : c) {
            vals[index++] = v;
        }
        size += c.size();

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean ans = false;
        for (int i = size - 1; i >= 0; i--) {
            if (c.contains(vals[i])) {
                ans = true;
                remove(i);
            }
        }

        return ans;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean ans = false;
        for (int i = size - 1; i >= 0; i--) {
            if (!c.contains(vals[i])) {
                ans = true;
                remove(i);
            }
        }

        return ans;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return castT(vals[index]);
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);

        T v = castT(vals[index]);
        vals[index] = element;
        return v;
    }

    @Override
    public void add(int index, T element) {
        grows();
        if (index == size) {
            add(element);
            return;
        }
        
        checkIndex(index);
        System.arraycopy(vals, index, vals, index + 1, size - index);
        vals[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        T v = castT(vals[index]);
        System.arraycopy(vals, index + 1, vals, index, --size - index);
        return v;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (vals[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (vals[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        checkIndex(index);
        return new IteratorEntry(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        var sub = new LightList<T>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++)
            sub.add(castT(vals[i]));
        return sub;
    }

    private void grows() {
        if (size < capacity) return;

        grows(capacity << 1);
    }

    public void grows(int targetCapacity) {
        if (targetCapacity <= capacity) return;
        capacity = targetCapacity;
        copyVals();
    }

    private void copyVals() {
        var newVals = new Object[capacity];
        System.arraycopy(vals, 0, newVals, 0, vals.length);
        vals = newVals;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException(
            String.format("index %d is out of bounds", index)
        );
    } 

    
    @SuppressWarnings("unchecked")
    private T castT(Object o) {
        return (T) o;
    }

    private class IteratorEntry implements ListIterator<T> {
        private int lastRet = -1;
        private int index;

        public IteratorEntry() {
            index = 0;
        }

        public IteratorEntry(int startIndex) {
            index = startIndex;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            return castT(vals[lastRet = index++]);
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public T previous() {
            return castT(vals[lastRet = --index]);
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            LightList.this.remove(index = lastRet);
            lastRet = -1;
        }

        @Override
        public void set(T e) {
            if (lastRet < 0)
                throw new IllegalStateException();

            LightList.this.set(lastRet, e);
        }

        @Override
        public void add(T e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            LightList.this.add(index++, e);
            lastRet = -1;
        }

    }

}