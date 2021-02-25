package instruments.factory.impl;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.Teacher;
import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repo.GroupRepo;
import edu.put_the_machine.scrapper.repo.TeacherRepo;
import edu.put_the_machine.scrapper.repo.UniversityRepo;
import instruments.factory.interfaces.EntitiesDbFactory;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class EntitiesDbFactoryImpl implements EntitiesDbFactory {
    private final UniversityRepo universityRepo;
    private final GroupRepo groupRepo;
    private final TeacherRepo teacherRepo;
    private University defaultUniversity;

    public EntitiesDbFactoryImpl(UniversityRepo universityRepo, GroupRepo groupRepo, TeacherRepo teacherRepo) {
        this.universityRepo = universityRepo;
        this.groupRepo = groupRepo;
        this.teacherRepo = teacherRepo;
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
        return teacherRepo.save(teacher);
    }

    @Override
    public Teacher createTeacher(String name, University university) {
        Teacher teacher = new Teacher(name, null, university);
        return teacherRepo.save(teacher);
    }

    private University getDefaultUniversity() {
        if (defaultUniversity == null)
            defaultUniversity = createUniversity("defaultUniversity");
        return defaultUniversity;
    }

    @Override
    public University createUniversity(String name) {
        University university = new University(name);
        return universityRepo.save(university);
    }
}
