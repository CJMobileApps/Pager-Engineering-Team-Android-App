package com.pager.pagerchallenge;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public class RxUtils {

  private static final int UNCHECKED_ERROR_TYPE_CODE = -100;

  private static final int NUM_RETRIES = 3;

  private static final double INITIAL_DELAY = 2;

  public static Function<? super Flowable<Throwable>, ? extends Publisher<?>> incremental() {
    return errors -> errors.zipWith(Flowable.range(1, NUM_RETRIES + 1),
      (error, integer) -> new RetryResult(error, UNCHECKED_ERROR_TYPE_CODE))
      .flatMap(RxUtils::delayed);
  }

  private static Flowable<Long> delayed(RetryResult result) {
    int retryAttempt = result.code();
    if (retryAttempt == UNCHECKED_ERROR_TYPE_CODE) {
      return Flowable.error(result.error());
    }
    long currentDelay = (long) Math.pow(INITIAL_DELAY, retryAttempt);
    return Flowable.timer(currentDelay, TimeUnit.SECONDS);
  }

  private static class RetryResult {

    private final Throwable error;

    private final int code;

    RetryResult(Throwable error, int code) {
      this.error = error;
      this.code = code;
    }

    Throwable error() {
      return error;
    }

    int code() {
      return code;
    }
  }
}
