package org.njuse.cpp.controller;

import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("{/id}")
    public QuestionBO getQuestionById(@PathVariable ("id") Integer id)
    {
        return questionService.getQuestionById(id);
    }
}
