package org.njuse.cpp.dao;

import org.apache.ibatis.annotations.*;
import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.dao.po.QuestionPO;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO question (id, name, description, test_case, answer) " +
            "VALUES (#{id}, #{name}, #{description}, #{testCase}, #{answer})")
    void insertQuestion(QuestionPO question);

    @Results({
            @Result(property = "testCase", column = "test_case")
    })
    @Select("SELECT * FROM question WHERE id = #{id}")
    QuestionPO getQuestionById(Long id);

    @Select("SELECT * FROM question")
    List<QuestionPO> getAllQuestions();

    @Update("UPDATE question SET name = #{name}, description = #{description}, " +
            "test_case = #{testCase}, answer = #{answer} WHERE id = #{id}")
    void updateQuestion(QuestionPO question);

    @Delete("DELETE FROM question WHERE id = #{id}")
    void deleteQuestion(Long id);
}