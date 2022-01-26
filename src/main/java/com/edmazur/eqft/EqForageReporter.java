package com.edmazur.eqft;

import java.io.IOException;
import java.time.Duration;

public class EqForageReporter implements Runnable {

  private static final String NOTIFICATION_SOUND =
      "/home/mazur/git/everquest-forage-timer/src/main/resources/alert.wav";

  private static final Duration INTERVAL_ON_COOLDOWN = Duration.ofSeconds(1L);
  private static final Duration INTERVAL_OFF_COOLDOWN = Duration.ofSeconds(5L);

  private EqForageState eqForageState;

  public EqForageReporter(EqForageState eqForageState) {
    this.eqForageState = eqForageState;
  }

  @Override
  public void run() {
    while (true) {
      // TODO: Hold off on reporting until initial file scan is complete.
      Duration interval = report();

      try {
        Thread.sleep(interval.toMillis());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private Duration report() {
    if (eqForageState.isForageOnCooldown()) {
      // TODO: Print something more interesting, e.g. a big ASCII art rendering
      // of the seconds remaining that clears itself on each refresh.
      System.out.println(eqForageState.getSecondsUntilForageAvailable()
          + " second(s) until off cooldown");
      return INTERVAL_ON_COOLDOWN;
    } else {
      try {
        // TODO: Do this in a native way.
        // TODO: Reference file relatively.
        Runtime.getRuntime().exec(new String[] {"mplayer", NOTIFICATION_SOUND});
      } catch (IOException e) {
        e.printStackTrace();
      }
      return INTERVAL_OFF_COOLDOWN;
    }
  }

}