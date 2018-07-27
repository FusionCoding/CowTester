package de.fusion.cowtester.common;

import de.fusion.cowtester.CowTester;

public class CounterThread extends Thread {

  private int number;

  CounterThread(int number) {
    this.number = number;
  }

  @Override
  public void run() {
    super.run();
    long botsBefore = 0;
    while (CowTester.getInstance().isRunning()) {
      CowTester.getCountManager()
          .submit(number, CowTester.getCountManager().getTotalCows() - botsBefore);
      botsBefore = CowTester.getCountManager().getTotalCows();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
    }
  }
}
