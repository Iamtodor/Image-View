package com.todor.imageview;

public class Lab2 {
    static double x = 0.4, y = 0.7, resultNumenator, resultDenominator;

    public static void main(String[] args) throws InterruptedException {
        new Numerator().start();
        new Denominator().start();
        Thread.currentThread().sleep(300);

        System.out.println(resultNumenator / resultDenominator);
    }

    static class Numerator extends Thread{

        @Override
        public void run() {
            super.run();
            resultNumenator = (1 + Math.sin(Math.sqrt(x + 1)));
        }
    }

    static class Denominator extends Thread{

        @Override
        public void run() {
            super.run();
            resultDenominator = (Math.cos(12 * y - 4));
        }
    }
}
