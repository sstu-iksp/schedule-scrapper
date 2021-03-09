package edu.put_the_machine.scrapper.repo;

import edu.put_the_machine.scrapper.model.Lesson;
import org.springframework.data.repository.CrudRepository;

public interface LessonRepo extends CrudRepository<Lesson, Long> {}
