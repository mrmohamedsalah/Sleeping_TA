package com.mycompany.osp;

import java.util.concurrent.Semaphore;
import java.util.concurrent.Semaphore;
import javax.swing.SwingWorker;
public class OSP {
    
    private static int noOfStudents=20;
    private static Thread[] Studentthreads = new Thread[noOfStudents];
    private static int nofTAS=5;
    private static Thread[] TASthreads = new Thread[nofTAS];
    private static int nofChairs=5;
    
    public OSP(int n , int m , int y ){
        this.noOfStudents=n;
        this.nofTAS=m;
        this.nofChairs=y;
    }
    public static void run() throws InterruptedException {

        Semaphore TALock = new Semaphore(0);
        Semaphore StudentLock = new Semaphore(0);
        
        Office office = new Office(TALock, StudentLock, nofChairs, nofTAS);

        TeachingAssistant ta = new TeachingAssistant(TALock, StudentLock, nofTAS);
        String x=Integer.toString(TeachingAssistant.getTASLeeping()); 
        String y=Integer.toString(TeachingAssistant.getTAWalkingUp()); 
        // Thread TAthread = new Thread(ta);
        for (int i = 0; i < nofTAS; i++) {
            Thread TAthread = new Thread(ta);
            TAthread.setName("TA-" + i);
            TASthreads[i] = TAthread;
        }

//            TAthread.start();
        for (int i = 0; i < nofTAS; i++) {
            TASthreads[i].start();
        }

        for (int i = 0; i < noOfStudents; i++) {
            Thread thread = new Thread(new Student(office));
            thread.setName("Student-S" + i);
            Studentthreads[i] = thread;
        }

        for (int i = 0; i < noOfStudents; i++) {
            Studentthreads[i].start();
        }

        for (int i = 0; i < noOfStudents; i++) {
            Studentthreads[i].join();
        }
//            TAthread.interrupt();

        for (int i = 0; i < nofTAS; i++) {
            TASthreads[i].join();
        }
    }
}
