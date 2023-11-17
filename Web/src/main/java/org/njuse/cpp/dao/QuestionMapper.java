package org.njuse.cpp.dao;

import org.apache.ibatis.annotations.*;
import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.dao.po.QuestionPO;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO question (name, description) VALUES (#{name}, #{description})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="questionId", before=false, resultType=Long.class)
    void insertQuestion(QuestionPO question);

    // 根据ID查询问题
    @Select("SELECT questionid, name, description FROM question WHERE questionid = #{questionId}")
    QuestionPO getQuestionById(Long questionId);

    // 获取所有问题
    @Select("SELECT questionid, name, description FROM question")
    List<QuestionPO> getAllQuestions();

    // 更新问题
    @Update("UPDATE question SET name = #{name}, description = #{description} WHERE questionid = #{questionId}")
    void updateQuestion(QuestionPO question);

    // 删除问题
    @Delete("DELETE FROM question WHERE questionid = #{questionId}")
    void deleteQuestion(Long questionId);

    // 分页查询题目，支持关键字搜索
    @Select("<script>" +
            "SELECT questionid, name, description " +
            "FROM question " +
            "<where>" +
            "   <if test='keyword != null and keyword != \"\"'>" +
            "       AND (name LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%'))" +
            "   </if>" +
            "</where>" +
            "ORDER BY questionid " +
            "LIMIT #{pageSize} OFFSET ${(pageNo - 1) * pageSize}" +
            "</script>")
    List<QuestionPO> getQuestionsByPage(
            @Param("pageSize") Integer pageSize,
            @Param("pageNo") Integer pageNo,
            @Param("keyword") String keyword);


    //查询总数
    @Select("<script>" +
            "SELECT COUNT(*) " +
            "FROM question " +
            "<where>" +
            "   <if test='keyword != null and keyword != \"\"'>" +
            "       AND (name LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%'))" +
            "   </if>" +
            "</where>" +
            "</script>")
    Integer countQuestions(
            @Param("keyword") String keyword);
}