package com.neuswp.services;

import java.util.List;
import java.util.Map;


public interface AiQuestionCenterService {

    String simpleAskQuestion(String question);

    String askQuestion(Integer userId, String question);

    List<Map<String, Object>> getHistoryByUserId(Integer id, Integer page, Integer limit);
}
