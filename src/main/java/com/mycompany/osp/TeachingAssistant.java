package com.mycompany.osp;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class TeachingAssistant implements Runnable {
    
    private Semaphore TALock;
    private Semaphore studentLock;
    public static int TASLeeping;
    public static int TAWalkingUp = 0;
    private int numTA; // Added variable for the number of TAs

    public TeachingAssistant(Semaphore TALock, Semaphore studentLock, int numTA) {
        this.TALock = TALock;
        this.studentLock = studentLock;
        this.numTA = numTA;
        this.TASLeeping = numTA;
    }

    public synchronized  static int getTASLeeping() {
        return TASLeeping;
    }

    public synchronized  static int getTAWalkingUp() {
        return TAWalkingUp;
    }

    
    @Override
    public void run() {
        while (true) {
            try {
                TALock.acquire();

                System.out.printf("%s waking up \n", Thread.currentThread().getName());
                TAWalkingUp++;
                TASLeeping--;
                System.out.printf("num of TAWalkingUp is %s and num of TASleeping is %s  \n", TAWalkingUp, TASLeeping);
                HelpStudents();

                studentLock.release();
                System.out.printf("%s sleeping \n", Thread.currentThread().getName());
                TASLeeping++;
                if (TAWalkingUp != 0)
                    TAWalkingUp--;
                System.out.printf("num of TAWalkingUp is %s and num of TASleeping is %s  \n", TAWalkingUp, TASLeeping);

                // Termination check: If the number of working TAs is 0 and the number of sleeping TAs is numTA, terminate
                if (TAWalkingUp == 0 && TASLeeping == numTA) {
                    System.out.println("All TAs finished working and all TAs are sleeping. Terminating.");
                    System.exit(0);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void HelpStudents() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
