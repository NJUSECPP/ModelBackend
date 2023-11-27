package org.njuse.cpp.llm;

import java.util.List;
import java.util.Map;

public abstract class BaseLlm {
    public abstract Map<String,Object> call(String prompt, Map<String,Object> extendedParams);


}
