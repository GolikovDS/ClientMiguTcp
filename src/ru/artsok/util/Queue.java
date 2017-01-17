package ru.artsok.util;


import java.util.ArrayList;
import java.util.List;

public class Queue<T> {
    private List<T> queue;
    private int maxSize;

    public Queue(int maxSize) {
        this.maxSize = maxSize;
        queue = new ArrayList<>();

    }

    public void append(T t) {
        if (queue.size() < maxSize) {
            queue.add(0, t);
        } else {
            queue.add(0, t);
            queue.remove(maxSize);
        }
    }

    public String forTextArray(){

        return "";
    }
    @Override
    public String toString() {
        return "Queue{" +
                "queue=" + queue +
                '}';
    }
}
