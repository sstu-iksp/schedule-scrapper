package edu.put_the_machine.scrapper.repository;

import edu.put_the_machine.scrapper.model.Teacher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends CrudRepository<Teacher, Long>, PagingAndSortingRepository<Teacher, Long> {
    List<Teacher> findByUniversityId(Long universityId, Pageable pageable);
    List<Teacher> findByUniversityIdAndNameIn(Long universityId, List<String> name);
    @Modifying
    @Query("UPDATE Teacher t SET t.url = :url WHERE t.id = :id")
    void setUrlById(@Param("id") Long id, @Param("url") String url);
}
