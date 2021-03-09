package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.model.Teacher;
import edu.put_the_machine.scrapper.repo.TeacherRepo;
import edu.put_the_machine.scrapper.service.interfaces.TeacherService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepo teacherRepo;

    public TeacherServiceImpl(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    @Override
    public List<Teacher> getTeachersByUniversityId(Long universityId, Pageable pageable) {
        return teacherRepo.findByUniversityId(universityId, pageable);
    }
}
