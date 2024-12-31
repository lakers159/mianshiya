package com.eric.mianshiya.mapper;

import com.eric.mianshiya.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
* @author Eric
* @description 针对表【question(题目)】的数据库操作Mapper
* @createDate 2024-11-24 19:06:19
* @Entity com.eric.mianshiya.model.entity.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("select * from question where updateTime>= #{minUpdateTime}")
    List<Question> listQuestionWithDelete(Date fiveMinutesAgoDate);
}




