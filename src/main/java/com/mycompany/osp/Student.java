package com.mycompany.osp;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Student implements Runnable {

    private Office office;

    public Student(Office office){
        this.office = office;
    }

    @Override
    public void run(){
        waitForSomeTime();
        walkIntoOffice();
    }

    private void walkIntoOffice(){
        office.acceptStudent();
    }

    private void waitForSomeTime(){
        int duration = new Random().nextInt(10);

        try {
            Thread.sleep(2000);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.printf("%s arriving after %d seconds \n", Thread.currentThread().getName(), duration);
    }

}
