package ru.min.resaleplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.min.resaleplatform.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
