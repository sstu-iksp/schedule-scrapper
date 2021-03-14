package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.model.Teacher;
import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.model.dto.TeacherDto;
import edu.put_the_machine.scrapper.repository.TeacherRepository;
import edu.put_the_machine.scrapper.service.interfaces.TeacherService;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> getTeachersByUniversityId(Long universityId, Pageable pageable) {
        return teacherRepository.findByUniversityId(universityId, pageable);
    }

    @Override
    public List<Teacher> findTeachersByUniversityIdAndNames(Long universityId, List<String> names) {
        return teacherRepository.findByUniversityIdAndNameIn(universityId, names);
    }

    @Override
    public Teacher createTeacher(University university, String name, String url) {
        return teacherRepository.save(new Teacher(name, url, university));
    }

    @Override
    public Iterable<Teacher> createTeachers(University university, List<TeacherDto> teacherDtos) {
        val teachers = teacherDtos.stream()
                .filter(teacherDto -> teacherDto.getUniversityId() == university.getId())
                .map(teacherDto -> new Teacher(teacherDto.getName(), teacherDto.getUrl(), university))
                .collect(Collectors.toList());
        return teacherRepository.saveAll(teachers);
    }

    @Override
    public void updateUrl(Long teacherId, String newUrl) {
        teacherRepository.setUrlById(teacherId, newUrl);
    }
}
