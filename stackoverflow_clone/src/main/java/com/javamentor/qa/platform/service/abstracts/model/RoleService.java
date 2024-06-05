package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface RoleService extends ReadWriteService<Role, Long> {
    Optional<Role> getByName(String name);
}
