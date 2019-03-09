package com.learn;

public class SyncClass {
    public static synchronized void sleep1(int second) {
        try {
            System.out.println("enter sleep1");
            Thread.sleep(1000 * second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("exit sleep1");
    }

    public synchronized void sleep2(int second) {
        try {
            System.out.println("enter sleep2");
            Thread.sleep(1000 * second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("exit sleep2");
    }
}
