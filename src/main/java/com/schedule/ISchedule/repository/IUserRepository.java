package com.schedule.ISchedule.repository;


import com.schedule.ISchedule.model.RoleEnum;
import com.schedule.ISchedule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(RoleEnum role);
}
