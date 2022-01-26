package com.edmazur.eqft;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.edmazur.eqlp.EqLog;

public class EqForageTimerMain {

  public static void main(String[] args) {
    // TODO: Make these command-line inputs.
    final Path eqInstallDirectory =
        Paths.get("/opt/everquest/EverQuest Project 1999");
    final ZoneId timezone = ZoneId.of("America/New_York");
    final String server = "green";
    final String character = "Stanvern";

    EqLog eqLog = new EqLog(
        eqInstallDirectory,
        timezone,
        server,
        character,
        Instant.now().minus(Duration.ofMinutes(2L)),
        Instant.MAX);
    EqForageState eqForageState = new EqForageState();
    EqForageListener eqForageListener = new EqForageListener(eqForageState);
    EqForageReporter eqForageReporter = new EqForageReporter(eqForageState);
    eqLog.addListener(eqForageListener);

    Executor executor = Executors.newCachedThreadPool();
    executor.execute(eqLog);
    executor.execute(eqForageReporter);
  }
}