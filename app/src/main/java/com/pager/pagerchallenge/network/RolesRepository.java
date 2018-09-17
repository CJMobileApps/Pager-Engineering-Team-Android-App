package com.pager.pagerchallenge.network;

import java.io.IOException;
import java.util.Map;

interface RolesRepository {

  Map<String, String> all() throws IOException;
}