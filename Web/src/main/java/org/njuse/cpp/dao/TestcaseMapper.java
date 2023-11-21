package org.njuse.cpp.dao;
import org.apache.ibatis.annotations.*;
import org.njuse.cpp.dao.DTO.TestcaseRequestDTO;
import org.njuse.cpp.dao.po.TestcasePO;

import java.util.List;
@Mapper
public interface TestcaseMapper {

    @Insert("INSERT INTO testcase (test_id, question_id, input, output, tip) " +
            "VALUES (#{testId}, #{questionId}, #{input}, #{output}, #{tip})")
    void insertTestcase(TestcasePO testcase);

    // 根据ID查询测试用例
    @Select("SELECT * FROM testcase WHERE test_id = #{testId}")
    TestcasePO getTestcaseById(Long testId);

    //根据testid获取questionid
    @Select("SELECT question_id FROM testcase WHERE test_id = #{testId}")
    Long getQuestionIdByTestcaseId(Long testId);

    // 获取所有测试用例
    @Select("SELECT * FROM testcase")
    List<TestcasePO> getAllTestcases();

    // 更新测试用例
    @Update("UPDATE testcase SET question_id = #{questionId}, input = #{input}, output = #{output}, tip = #{tip} " +
            "WHERE test_id = #{testId}")
    void updateTestcase(TestcasePO testcase);

    // 删除测试用例
    @Delete("DELETE FROM testcase WHERE test_id = #{testId}")
    void deleteTestcase(Long testId);

    //分页查询所有用例
    @Select({
            "SELECT test_id AS testid, input, output, tip FROM testcase ",
            "WHERE #{questionId} IS NULL OR question_id = #{questionId}",
            "LIMIT #{pageSize} OFFSET #{offset}"
    })
    List<TestcasePO> getTestcasesByQuestionIdAndPage(
            @Param("pageSize") Integer pageSize,
            @Param("offset") Integer offset,
            @Param("questionId") Long questionId
    );

    // 获取符合条件的总记录数
    @Select({
            "SELECT COUNT(*) FROM testcase",
            "WHERE #{questionId} IS NULL OR question_id = #{questionId}"
    })
    int getTotalCountByQuestionId(@Param("questionId") Long questionId);

    @Insert({//根据questionid批量添加
            "<script>",
            "INSERT INTO testcase (question_id, input, output, tip) VALUES ",
            "<foreach collection='testcases' item='testcase' separator=','>",
            "(#{questionId}, #{testcase.input}, #{testcase.output}, #{testcase.tip})",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "testcases.testId",keyColumn = "test_id")
    void batchInsertTestcases(@Param("questionId") Long questionId, @Param("testcases") List<TestcasePO> testcases);

    @Delete({//根据用例id删除用例
            "<script>",
            "DELETE FROM testcase WHERE test_id IN",
            "<foreach collection='testcaseIds' item='testId' open='(' separator=',' close=')'>",
            "#{testId}",
            "</foreach>",
            "</script>"
    })
    void batchDeleteTestcases(@Param("testcaseIds") Integer[] testcaseIds);
    // 删除指定题目的所有测试用例
    @Delete("DELETE FROM testcase WHERE question_id = #{questionId}")
    void deleteTestcasesByQuestionId(@Param("questionId") Long questionId);
}
