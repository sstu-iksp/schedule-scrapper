package edu.put_the_machine.scrapper.repo;

import edu.put_the_machine.scrapper.model.Teacher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TeacherRepo extends CrudRepository<Teacher, Long>, PagingAndSortingRepository<Teacher, Long> {
    List<Teacher> findByUniversityId(Long universityId, Pageable pageable);
}
