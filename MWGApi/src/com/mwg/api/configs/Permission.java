package com.mwg.api.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Permission {
  private final List<String> funcPrefixs = new ArrayList<>();

  private final Map<String, Boolean> caches = new HashMap<>();

  public Permission(String prefixs) {
    if (prefixs != null) {
      for (String prefix : prefixs.split(",")) {
        if (!prefix.trim().isEmpty())
          funcPrefixs.add(prefix.trim());
      }
    }
  }

  public boolean hasPermission(String func) {
    Boolean match = caches.get(func);
    if (match == null) {
      match = false;
      for (String funcPrefix : funcPrefixs) {
        if (func.startsWith(funcPrefix)) {
          match = true;
          break;
        }
      }
      caches.put(func, match);
    }
    return match;
  }
}
