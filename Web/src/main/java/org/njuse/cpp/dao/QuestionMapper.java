package org.njuse.cpp.dao;

import org.apache.ibatis.annotations.*;
import org.njuse.cpp.bo.QuestionBO;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO question (id, name, description, test_case, answer) " +
            "VALUES (#{id}, #{name}, #{description}, #{testCase}, #{answer})")
    void insertQuestion(QuestionBO question);

    @Select("SELECT * FROM question WHERE id = #{id}")
    QuestionBO getQuestionById(Long id);

    @Select("SELECT * FROM question")
    List<QuestionBO> getAllQuestions();

    @Update("UPDATE question SET name = #{name}, description = #{description}, " +
            "test_case = #{testCase}, answer = #{answer} WHERE id = #{id}")
    void updateQuestion(QuestionBO question);

    @Delete("DELETE FROM question WHERE id = #{id}")
    void deleteQuestion(Long id);
}