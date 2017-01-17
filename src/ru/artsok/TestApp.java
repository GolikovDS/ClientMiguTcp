package ru.artsok;


import ru.artsok.util.Queue;

public class TestApp {
    public static void main(String[] args) {

        Queue<String> queue = new Queue<>(3);
        for (int i = 0; i <16; i++) {
            queue.append(String.valueOf(i));
            System.out.println(queue.toString());
        }
    }
}
