package edu.put_the_machine.scrapper.repository;

import edu.put_the_machine.scrapper.model.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
    List<Lesson> findByGroupIdAndDateBetween(Long groupId, LocalDate start, LocalDate end);
    List<Lesson> deleteByGroupIdAndDateBetween(Long groupId, LocalDate start, LocalDate end);
}
