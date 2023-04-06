package com.example.look.views;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestThread {

    public static void main(String[] args) {

        // Callable+FutureTask
        CallableDemo demo = new CallableDemo();

        FutureTask<Integer> future = new FutureTask<>(demo);

        Thread a = new Thread(future);
        a.start();

        try {
            Integer sum = future.get();
            System.out.println(sum);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Thread使用
        ThreadDemo thread = new ThreadDemo();
        thread.start();

        // Runnable使用
        RunnableDemo runnableDemo = new RunnableDemo();
        Thread runnable = new Thread(runnableDemo);
        runnable.start();

    }
}

class CallableDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 5; i++) {
            sum += i;
        }
        return sum;
    }
}

class ThreadDemo extends Thread {

    @Override
    public void run() {
        super.run();
        System.out.println("Thread使用");
    }
}

class RunnableDemo implements Runnable {

    @Override
    public void run() {
        System.out.println("Runnable");
    }
}
