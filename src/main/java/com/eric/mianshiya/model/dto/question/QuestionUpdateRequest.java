package com.eric.mianshiya.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新题目请求
 *
 * @author <a href="https://github.com/lieric">程序员eric</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
//一般情况下，Edit是给用户使用的，能更改的字段相对较少一些，而Update是给用户使用的，能更改的字段就相对多一些
@Data
public class QuestionUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 推荐答案
     */
    private String answer;

    private static final long serialVersionUID = 1L;
}