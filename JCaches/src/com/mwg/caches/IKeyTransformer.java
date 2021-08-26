package com.mwg.caches;

public interface IKeyTransformer {
   public String transform(String srcKey) throws Exception;
}
