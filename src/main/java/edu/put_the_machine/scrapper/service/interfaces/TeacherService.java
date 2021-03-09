package edu.put_the_machine.scrapper.service.interfaces;

import edu.put_the_machine.scrapper.model.Teacher;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {
    List<Teacher> getTeachersByUniversityId(Long universityId, Pageable pageable);
}
