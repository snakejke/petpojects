package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class IgnoredTagServiceImpl extends ReadWriteServiceImpl<IgnoredTag, Long> implements IgnoredTagService {

    public IgnoredTagServiceImpl(ReadWriteDao<IgnoredTag, Long> readWriteDao) {
        super(readWriteDao);
    }
}
