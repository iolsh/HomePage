package me.iolsh.homepage.repositories;

import me.iolsh.homepage.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Set<Role> findByUserId(Long userId);
}
