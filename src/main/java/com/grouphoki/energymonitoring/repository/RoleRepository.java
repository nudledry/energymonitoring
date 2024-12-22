package com.grouphoki.energymonitoring.repository;

import com.grouphoki.energymonitoring.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Long>{
    Role findByName(String name);
}
