package com.edmazur.eqft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.edmazur.eqlp.EqLogEvent;
import com.edmazur.eqlp.EqLogListener;

public class EqForageListener implements EqLogListener {

  private static final Pattern FORAGE_PATTERN = Pattern.compile(
      "You have scrounged up some.+");

  private final EqForageState eqForageState;

  public EqForageListener(EqForageState eqForageState) {
    this.eqForageState = eqForageState;
  }

  @Override
  public void onEvent(EqLogEvent eqLogEvent) {
    Matcher matcher = FORAGE_PATTERN.matcher(eqLogEvent.getPayload());
    if (matcher.matches()) {
      eqForageState.registerForageEvent(eqLogEvent.getTimestamp());
    }
  }

}