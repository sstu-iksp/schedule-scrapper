package edu.put_the_machine.scrapper.service.interfaces;

import edu.put_the_machine.scrapper.model.Teacher;
import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.model.dto.TeacherDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {
    List<Teacher> getTeachersByUniversityId(Long universityId, Pageable pageable);
    List<Teacher> findTeachersByUniversityIdAndNames(Long universityId, List<String> names);
    Teacher createTeacher(University university, String name, String url);
    Iterable<Teacher> createTeachers(University university, List<TeacherDto> teacherDtos);
    void updateUrl(Long teacherId, String newUrl);

}
