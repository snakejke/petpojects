package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentAnswerServiceImpl extends ReadWriteServiceImpl<CommentAnswer, Long> implements CommentAnswerService {

    public CommentAnswerServiceImpl(ReadWriteDao<CommentAnswer, Long> readWriteDao) {
        super(readWriteDao);
    }
}