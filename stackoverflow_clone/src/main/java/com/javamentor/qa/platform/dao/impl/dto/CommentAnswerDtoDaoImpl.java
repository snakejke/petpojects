package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentAnswerDtoDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentAnswerDtoDaoImpl implements CommentAnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

}
