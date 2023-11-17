package org.njuse.cpp.controller;

import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.dao.DTO.BatchInsertRequestDTO;
import org.njuse.cpp.dao.DTO.QuestionResponseDTO;
import org.njuse.cpp.dao.DTO.TestcaseRequestDTO;
import org.njuse.cpp.dao.DTO.TestcaseResponseDTO;
import org.njuse.cpp.dao.po.QuestionPO;
import org.njuse.cpp.dao.po.TestcasePO;
import org.njuse.cpp.service.QuestionService;
import org.njuse.cpp.service.TestcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    TestcaseService testcaseService;

    @GetMapping("{/id}")
    public QuestionBO getQuestionById(@PathVariable("id") Integer id) {
        return questionService.getQuestionById(id);
    }

    @GetMapping("/getAll")//分页查询所有题目
    public QuestionResponseDTO getQuestions(
            @RequestBody Map<String, String> requestBody) {
        Integer pageSize = Integer.parseInt(requestBody.get("pageSize"));
        Integer pageNo = Integer.parseInt(requestBody.get("pageNo"));
        String keyword = requestBody.get("keyword");
        List<QuestionBO> questions = questionService.getQuestionsByPage(pageSize, pageNo, keyword);
        int total = questionService.countQuestions(keyword);

        return new QuestionResponseDTO(questions, total);
    }

    @PostMapping("/insert")//添加题目
    public QuestionBO addQuestion(
            @RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        String description = requestBody.get("description");
        return questionService.addQuestion(name, description);
    }

    @PutMapping("/update")
    public QuestionBO updateQuestion(
            @RequestBody Map<String, String> requestBody) {
        String id = requestBody.get("id");
        String name = requestBody.get("name");
        String description = requestBody.get("description");
        return questionService.updateQuestion(id, name, description);
    }

    @GetMapping("/testcase/getAll")//分页查询所有用例
    public TestcaseResponseDTO getAllTestcases(
            @RequestBody Map<String, Integer> requestParams) {
        Integer pageSize = requestParams.get("pageSize");
        Integer pageNo = requestParams.get("pageNo");
        Integer questionId = requestParams.get("questionId");

        // 调用 service 方法获取数据
        List<TestcasePO> testcaseList = testcaseService.getAllTestcases(pageSize, pageNo, questionId);

        // 构造 TestcaseResponseDTO 并返回
        return TestcaseResponseDTO.fromListAndTotal(testcaseList, testcaseList.size());
    }

    @PostMapping("/testcase/batchInsert")
    public QuestionBO batchAddTestcases(
            @RequestBody BatchInsertRequestDTO requestDTO) {
        Integer questionId = requestDTO.getQuestionId();
        List<TestcaseRequestDTO> testcases = requestDTO.getTestcases();
        QuestionBO questionBO = testcaseService.batchAddTestcases(Long.valueOf(questionId), testcases);
        return questionBO;
    }

    @DeleteMapping("/testcase/batchDelete")
    public QuestionBO batchDeleteTestcases(@RequestBody Map<String, Integer[]> requestBody) {
        // 调用 service 方法批量删除测试用例，并返回 questionId
        Integer[] testcaseIds = requestBody.get("testcaseIds");

        // 调用 service 方法批量删除测试用例，并返回 questionId
        Long questionId = testcaseService.batchDeleteTestcases(testcaseIds);

        // 根据 questionId 获取对应的 QuestionBO
        QuestionBO questionBO = getQuestionById(questionId.intValue());

        // 返回 QuestionBO
        return questionBO;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteQuestionAndTestcases(@RequestBody Map<String, Integer> payload) {
        // 调用 service 方法删除题目及其所有用例
        Integer questionId = payload.get("questionId");
        questionService.deleteQuestionAndTestcases(questionId.longValue());

        // 返回成功的响应
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
