package instruments.factory.impl;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.Teacher;
import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repository.GroupRepository;
import edu.put_the_machine.scrapper.repository.TeacherRepository;
import edu.put_the_machine.scrapper.repository.UniversityRepository;
import instruments.factory.interfaces.EntitiesDbFactory;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class EntitiesDbFactoryImpl implements EntitiesDbFactory {
    private final UniversityRepository universityRepository;
    private final GroupRepository groupRepo;
    private final TeacherRepository teacherRepository;
    private University defaultUniversity;

    public EntitiesDbFactoryImpl(UniversityRepository universityRepository, GroupRepository groupRepo, TeacherRepository teacherRepository) {
        this.universityRepository = universityRepository;
        this.groupRepo = groupRepo;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Group createGroup(String name) {
        return createGroup(name, getDefaultUniversity());
    }

    @Override
    public Group createGroup(String name, University university) {
        Group group = new Group(name, university);
        return groupRepo.save(group);
    }

    @Override
    public Teacher createTeacher(String name) {
        Teacher teacher = new Teacher(name, null, getDefaultUniversity());
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher createTeacher(String name, University university) {
        Teacher teacher = new Teacher(name, null, university);
        return teacherRepository.save(teacher);
    }

    private University getDefaultUniversity() {
        if (defaultUniversity == null)
            defaultUniversity = createUniversity("defaultUniversity");
        return defaultUniversity;
    }

    @Override
    public University createUniversity(String name) {
        University university = new University(name);
        return universityRepository.save(university);
    }
}
