package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao) {
        super(readWriteDao);
    }
}
