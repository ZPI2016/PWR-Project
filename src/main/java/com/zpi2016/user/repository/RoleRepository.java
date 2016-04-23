package com.zpi2016.user.repository;

import com.zpi2016.user.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aman on 23.04.16.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("select a.role from Role a, User b where b.username=?1 and a.userId=b.id")
    public List<String> findRoleByUsername(String username);

}
