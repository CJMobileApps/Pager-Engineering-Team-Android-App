package com.pager.pagerchallenge;

import io.reactivex.Flowable;
import java.util.Map;

public interface RolesRepository {

  Flowable<Map<String, String>> get();
}
