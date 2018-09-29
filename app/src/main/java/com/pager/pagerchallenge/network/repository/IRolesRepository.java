package com.pager.pagerchallenge.network.repository;

import java.io.IOException;
import java.util.Map;

public interface IRolesRepository {

  Map<Integer, String> all() throws IOException;
}