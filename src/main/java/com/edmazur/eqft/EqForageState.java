package com.edmazur.eqft;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EqForageState {

  private static final Duration FORAGE_COOLDOWN = Duration.ofSeconds(100L);
  // Avoid notifying early by adjusting for potential log delay.
  private static final Duration BUFFER = Duration.ofSeconds(1L);

  private LocalDateTime lastForageTime = LocalDateTime.MIN;

  public void registerForageEvent(LocalDateTime forageTime) {
    lastForageTime = forageTime;
  }

  public long getSecondsUntilForageAvailable() {
    return ChronoUnit.SECONDS.between(
        LocalDateTime.now(), lastForageTime.plus(FORAGE_COOLDOWN).plus(BUFFER));
  }

  public boolean isForageOnCooldown() {
    return getSecondsUntilForageAvailable() > 0;
  }

}