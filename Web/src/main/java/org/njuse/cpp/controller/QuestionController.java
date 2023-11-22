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
    public QuestionBO getQuestionById(@PathVariable("id") Long id) {
        return questionService.getQuestionById(id);
    }

    @GetMapping("/getAll")//分页查询所有题目
    public QuestionResponseDTO getQuestions(@RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("keyword") String keyword) {
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

    @PostMapping("/update")
    public QuestionBO updateQuestion(
            @RequestBody Map<String, String> requestBody) {
        String id = requestBody.get("questionId");
        String name = requestBody.get("name");
        String description = requestBody.get("description");
        return questionService.updateQuestion(id, name, description);
    }

    @GetMapping("/testcase/getAll")//分页查询所有用例
    public TestcaseResponseDTO getAllTestcases(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("questionId") Long questionId) {

        // 调用 service 方法获取数据
        List<TestcasePO> testcaseList = testcaseService.getAllTestcases(pageSize, pageNo, questionId);
        int total = testcaseService.countTestcases(questionId);

        // 构造 TestcaseResponseDTO 并返回
        return TestcaseResponseDTO.fromListAndTotal(testcaseList, total);
    }

    @PostMapping("/testcase/batchInsert")
    public QuestionBO batchAddTestcases(
            @RequestBody BatchInsertRequestDTO requestDTO) {
        Long questionId = requestDTO.getQuestionId();
        List<TestcaseRequestDTO> testcases = requestDTO.getTestcases();
        QuestionBO questionBO = testcaseService.batchAddTestcases(questionId, testcases);
        return questionBO;
    }

    @PostMapping("/testcase/batchDelete")
    public QuestionBO batchDeleteTestcases(@RequestBody Map<String, Integer[]> requestBody) {
        // 调用 service 方法批量删除测试用例，并返回 questionId
        Integer[] testcaseIds = requestBody.get("testcaseIds");

        // 调用 service 方法批量删除测试用例，并返回 questionId
        Long questionId = testcaseService.batchDeleteTestcases(testcaseIds);

        // 根据 questionId 获取对应的 QuestionBO
        QuestionBO questionBO = getQuestionById(questionId);

        // 返回 QuestionBO
        return questionBO;
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteQuestionAndTestcases(@RequestBody Map<String, Long> payload) {
        // 调用 service 方法删除题目及其所有用例
        Long questionId = payload.get("questionId");
        questionService.deleteQuestionAndTestcases(questionId);

        // 返回成功的响应
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
