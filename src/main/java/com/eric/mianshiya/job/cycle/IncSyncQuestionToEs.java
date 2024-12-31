package com.eric.mianshiya.job.cycle;

import cn.hutool.core.collection.CollUtil;
import com.eric.mianshiya.esdao.QuestionEsDao;
import com.eric.mianshiya.mapper.QuestionMapper;
import com.eric.mianshiya.model.dto.question.QuestionEsDTO;
import com.eric.mianshiya.model.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 增量同步帖子到 es
 *
 * @author <a href="https://github.com/lieric">程序员eric</a>
 * @from <a href="https://eric.icu">编程导航知识星球</a>
 */
// todo 取消注释开启任务
@Component
@Slf4j
public class IncSyncQuestionToEs{
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionEsDao questionEsDao;

    //@Scheduled(fixedRate = 60*1000)表示每一分钟执行一次
    @Scheduled(fixedRate = 60*1000)
    public void run() throws Exception {
        // 查询近 5 分钟内的数据
        Date fiveMinutesAgoDate = new Date(new Date().getTime() - 5 * 60 * 1000L);
        List<Question> questionList = questionMapper.listQuestionWithDelete(fiveMinutesAgoDate);
        if (CollUtil.isEmpty(questionList)) {
            log.info("no inc question");
            return;
        }
        List<QuestionEsDTO> questionEsDTOList = questionList.stream()
                .map(QuestionEsDTO::objToDto)
                .collect(Collectors.toList());
        final int pageSize = 500;
        int total = questionEsDTOList.size();
        log.info("IncSyncquestionToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            questionEsDao.saveAll(questionEsDTOList.subList(i,end));
        }
        log.info("IncSyncquestionToEs end, total {}", total);
    }
}
