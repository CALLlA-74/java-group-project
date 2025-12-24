package ru.project.Lib.List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.junit.Test;

public class LightListTest {
    @Test
    public void testIsEmpty() {
        List<Object> list = new LightList<Object>(1);
        assert(list.isEmpty() == true);
        assert(list.add(new Object()) == true);
        assert(list.isEmpty() == false);
        assert(list.remove(0) != null);
        assert(list.isEmpty() == true);
    }

    @Test 
    public void testSize() {
        List<Object> list = new LightList<Object>(1);
        int targetSize = 10;

        for (int i = 0; i < targetSize; i++) {
            assert(list.size() == i);
            assert(list.add(new Object()) == true);
        }
        assert(list.size() == targetSize);

        for (int i = targetSize - 1; i >= 0; i--) {
            assert(list.remove(i) != null);
            assert(list.size() == i);
        }
    }

    @Test 
    public void testContains() {
        List<Object> list = new LightList<>(1);
        int targetSize = 20;
        var objs = new Object[targetSize];

        for (int i = 0; i < targetSize; i++) {
            objs[i] = new Object();
            assert(list.add(objs[i]) == true);
        }

        for (var o : objs) {
            assert(list.contains(o) == true);
        }

        for (int i = 0; i < objs.length; i++) {
            assert(list.remove(objs[i]) == true);

            for (int j = i + 1; j < objs.length; j++) {
                assert(list.contains(objs[j]));
            }
        }
    }

    @Test 
    public void testIterator() {
        List<Object> list = new LightList<>(1);
        int targetSize = 2;
        var objs = new Object[targetSize];

        for (int i = 0; i < targetSize; i++) {
            objs[i] = new Object();
            assert(list.add(objs[i]) == true);
        }

        Iterator<Object> iter = list.iterator();
        assert(iter instanceof Iterator<Object>);
        boolean hasNx;
        int idx = 0;

        while (true) {
            hasNx = iter.hasNext();
            if (hasNx) {
                assert(idx < objs.length);
                assert(iter.next().equals(objs[idx++]));
            } else {
                assert(idx == objs.length);
                break;
            }
        }

        idx = 0;
        for (var v : list) {
            assert(list.get(idx++).equals(v));
        }
    }

    @Test public void testToArray() {
        List<Object> list = new LightList<>();
        var objs = new Object[110];

        for (int i = 0; i < objs.length; i++) {
            objs[i] = new Object();
            assert(list.add(objs[i]) == true);
        }

        var arr = list.toArray();
        assert(arr.length == objs.length);

        for (int i = 0; i < arr.length; i++) {
            assert(arr[i].equals(objs[i]));
        }

        var arr2 = list.toArray(new Object[1]);

        assert(arr2.length == objs.length);
        for (int i = 0; i < objs.length; i++) {
            assert(arr2[i].equals(objs[i]));
        }
    }

    @Test 
    public void testAdd() {
        List<Object> list = new LightList<>(0);
        Object objs[] = {new Object(), new Object(), new Object(), new Object()};
        
        assert(list.add(objs[2]));
        assert(list.add(objs[3]));
        list.add(0, objs[0]);
        list.add(1, objs[1]);
        assert(list.size() == objs.length);

        for (int i = 0; i < objs.length; i++) {
            assert(list.get(i).equals(objs[i]));
        }
    }

    @Test
    public void testAddAll() {
        int size = 1000;
        var notFrom = new LightList<Object>();
        List<Object> list = new LightList<>();
        var rd = new Random(System.nanoTime());

        for (int i = 0; i < size; i++) {
            assert(notFrom.add(new Object()));
            assert(list.add(new Object()));
        }

        assert(list.containsAll(notFrom) == false);
        assert(notFrom.containsAll(list) == false);

        assert(list.addAll(notFrom));
        assert(list.containsAll(notFrom));
        assert(notFrom.containsAll(list) == false);
        assert(list.indexOf(notFrom.get(0)) == size);
        assert(list.size() == 2*size);

        list.clear();
        assert(list.isEmpty());
        for (int i = 0; i < size; i++)
            list.add(new Object());

        int idx = rd.nextInt(0, list.size());
        assert(list.addAll(idx, notFrom));
        assert(list.containsAll(notFrom));
        assert(notFrom.containsAll(list) == false);
        assert(list.indexOf(notFrom.getFirst()) == idx);
        assert(list.size() == 2*size);
    }

    @Test
    public void testContainsAll() {
        int size = 1000;
        var notFrom = new LightList<Object>();
        List<Object> list = new LightList<>();

        for (int i = 0; i < 2*size; i++) {
            assert(list.add(new Object()));
        }

        for (int i = 0; i < size; i++) {
            assert(notFrom.add(new Object()));
        }
        assert(list.containsAll(notFrom) == false);

        var rd = new Random(System.nanoTime());
        int l, r;

        for (int i = 0; i < 10; i++) {
            l = rd.nextInt(0, list.size());
            r = rd.nextInt(l, list.size());
            
            var list2 = new LightList<Object>();
            for (int j = l; j < r; j++) {
                assert(list2.add(list.get(j)));
            }
            assert(list.containsAll(list2));

            list2.set(rd.nextInt(0, list2.size()), new Object());
            assert(list.containsAll(list2) == false);
        }
    }

    @Test
    public void testRemoveAll() {
        int size = 1000;
        var removable = new LightList<Object>();
        List<Object> list = new LightList<>();

        for (int i = 0; i < size; i++) {
            removable.add(new Object());
            assert(list.add(removable.getLast()));
            assert(list.add(new Object()));
        }

        assert(list.containsAll(removable));
        assert(list.removeAll(removable));
        assert(list.containsAll(removable) == false);
        assert(list.removeAll(removable) == false);
        assert(list.size() == size);

        var rd = new Random(System.nanoTime());
        list.set(
            rd.nextInt(0, list.size()), 
            removable.get(rd.nextInt(0, removable.size()))
        );
        assert(list.containsAll(removable) == false);

        assert(list.removeAll(removable));
        assert(list.containsAll(removable) == false);
        assert(list.removeAll(removable) == false);
        assert(list.size() + 1 == size);
    }

    @Test
    public void testRetainsAll() {
        int size = 1000;
        var retainable = new LightList<Object>();
        List<Object> list = new LightList<>();

        for (int i = 0; i < size; i++) {
            retainable.add(new Object());
            assert(list.add(retainable.getLast()));
            assert(list.add(new Object()));
            assert(retainable.add(new Object()));
        }
        assert(list.containsAll(retainable) == false);
        assert(retainable.containsAll(list) == false);

        assert(list.retainAll(retainable) == true);
        assert(retainable.containsAll(list));
        assert(list.containsAll(retainable) == false);

        assert(list.retainAll(retainable) == false);
    }


    @Test 
    public void testRemove() {
        List<Object> list = new LightList<>(10);
        var objs = new ArrayList<Object>(110);
        var rd = new Random(System.nanoTime());

        for (int i = 0; i < 110; i++) {
            assert(list.add(new Object()) == true);
            objs.add(list.getLast());
        }

        int idx;
        while (!list.isEmpty()) {
            idx = rd.nextInt(0, list.size());
            assert(list.remove(idx).equals(objs.remove(idx)));
        }

        for (int i = 0; i < 110; i++) {
            assert(list.add(new Object()) == true);
            objs.add(list.getLast());
        }

        Object o;
        while (!list.isEmpty()) {
            idx = rd.nextInt(0, list.size());
            o = list.get(idx);
            assert(list.remove(objs.get(idx)));
            assert(list.remove(o) == false);
            objs.remove(idx);
        }
    }

    @Test
    public void testClear() {
        List<Object> list = new LightList<>();
        assert(list.size() == 0);
        int len = 1000;

        for (int i = 0; i < len; i++) {
            assert(list.add(new Object()));
        }

        assert(list.size() == len);
        list.clear();
        assert(list.size() == 0);
    }

    @Test
    public void testSet() {
        int size = 1000;
        var from = new Object[size];
        var to = new Object[size];
        List<Object> list = new LightList<>();

        for (int i = 0; i < size; i++) {
            from[i] = new Object();
            assert(list.add(from[i]));
            to[i] = new Object();
        }
        assert(list.size() == size);

        for (int i = list.size() - 1; i >= 0; i--) {
            assert(list.set(i, to[i]).equals(from[i]));
            assert(list.get(i).equals(to[i]));
        }
    }

    @Test
    public void testIndexOfAndLastIndexOf() {
        int size = 1000;
        var from = new Object[size];
        List<Object> list = new LightList<>();
        Object o = new Object();

        for (int i = 0; i < size; i++) {
            from[i] = new Object();
            assert(list.add(from[i]));
            assert(list.add(o));
        }

        assert(list.indexOf(o) == 1);
        assert(list.lastIndexOf(o) == list.size() - 1);
    }

    @Test
    public void testListIterator() {
        int size = 10;
        List<Object> list = new LightList<>();
        for (int i = 0; i < size; i++) {
            assert(list.add(new Object()));
        }

        var iter = list.listIterator();
        assert(iter instanceof ListIterator<Object>);
        assert(iter.hasPrevious() == false);

        for (int i = 0; i < list.size(); i++) {
            assert(iter.hasNext());
            assert(iter.next().equals(list.get(i)));
        }
        assert(iter.hasNext() == false);

        for (int i = list.size() - 1; i >= 0; i--) {
            assert(iter.hasPrevious());
            assert(iter.previous().equals(list.get(i)));
        }
        assert(iter.hasPrevious() == false);

        var rd = new Random(System.nanoTime());
        int idx = rd.nextInt(0, list.size());
        iter = list.listIterator(idx);
        assert(list.get(idx).equals(iter.next()));

        // check iterator.set()
        while (iter.hasNext()) iter.next();
        var o = new Object();
        iter.set(o);
        assert(list.getLast().equals(o));
        assert(list.size() == size);

        // check iterator.add()
        o = new Object();
        iter = list.listIterator(list.size() - 1);
        assert(iter.hasNext());
        assert(list.getLast().equals(iter.next()));
        assert(iter.hasNext() == false);
        iter.add(o);
        assert(iter.hasNext() == false);
        assert(iter.previous().equals(o));
        assert(list.size() - 1 == size);

        // check iterator.remove()
        iter.remove();
        assert(iter.hasNext() == false);
        assert(iter.hasPrevious());
        assert(list.getLast().equals(iter.previous()));
        assert(iter.hasNext());
        assert(list.size() == size);
    }

    @Test
    public void testSubList() {
        int size = 1000, l, r;
        List<Object> list = new LightList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Object());
        }

        var rd = new Random(System.nanoTime());
        for (int i = 0; i < 100; i++) {
            l = rd.nextInt(0, list.size());
            r = rd.nextInt(l, list.size());

            assert(list.containsAll(list.subList(l, r)));
        }
    }
}
