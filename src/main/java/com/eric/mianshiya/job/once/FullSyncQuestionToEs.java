package com.eric.mianshiya.job.once;

import cn.hutool.core.collection.CollUtil;
import com.eric.mianshiya.esdao.PostEsDao;
import com.eric.mianshiya.esdao.QuestionEsDao;
import com.eric.mianshiya.model.dto.post.PostEsDTO;
import com.eric.mianshiya.model.dto.question.QuestionEsDTO;
import com.eric.mianshiya.model.entity.Post;
import com.eric.mianshiya.model.entity.Question;
import com.eric.mianshiya.service.PostService;
import com.eric.mianshiya.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全量同步题目到 es
 *
 * @author <a href="https://github.com/lieric">程序员eric</a>
 * @from <a href="https://eric.icu">编程导航知识星球</a>
 */
// todo 取消注释开启任务
@Component
@Slf4j
public class FullSyncQuestionToEs implements CommandLineRunner {
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionEsDao questionEsDao;

    @Override
    public void run(String... args) throws Exception {
        //全量获取题目（题目量不大的情况下使用）
        List<Question> questionList = questionService.list();
        if(CollUtil.isEmpty(questionList)){
            return;
        }
        //首先调用stream()，会生成一个Stream<Question>对象
        //接着调用map(),对Stream中的每个元素进行映射操作，将Question对象转换为QuestionEsDTO对象
        //最后调用collect()，将前面生成的QuestionEsDTO对象放到一个集合中进行收集
        List<QuestionEsDTO> questionEsDTOList = questionList.stream()
                .map(QuestionEsDTO::objToDto)
                .collect(Collectors.toList());
        //分页批量的将数据从questionEsDTOList插入到ES中，由于写操作是较重的操作，为了减轻ES服务器的压力，选择分页批量进行操作
        final int pageSize=500;
        int total=questionEsDTOList.size();
        log.info("FullSyncQuestionToEs start,total {}",total);
        for (int i = 0; i <total ; i=i+pageSize) {
            //注意同步的数据下标不能超过总数据量，只有最后一次同步可能超出
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}",i,end);
            questionEsDao.saveAll(questionEsDTOList.subList(i,end));
        }
        log.info("FullSyncQuestionToEs end,total {}",total);
    }
}
