package ru.min.resaleplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.min.resaleplatform.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
