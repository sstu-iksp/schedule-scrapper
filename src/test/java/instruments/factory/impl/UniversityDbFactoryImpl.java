package instruments.factory.impl;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repo.GroupRepo;
import edu.put_the_machine.scrapper.repo.UniversityRepo;
import instruments.factory.interfaces.UniversityDbFactory;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class UniversityDbFactoryImpl implements UniversityDbFactory {
    private final UniversityRepo universityRepo;
    private final GroupRepo groupRepo;
    private final University defaultUniversity;

    public UniversityDbFactoryImpl(UniversityRepo universityRepo, GroupRepo groupRepo) {
        this.universityRepo = universityRepo;
        this.groupRepo = groupRepo;
        defaultUniversity = createUniversity("defaultUniversity");
    }

    @Override
    public University createUniversity(String name) {
        University university = new University(name);
        return universityRepo.save(university);
    }

    @Override
    public Group createGroup(String name) {
        return createGroup(name, defaultUniversity);
    }

    @Override
    public Group createGroup(String name, University university) {
        Group group = new Group(name, university);
        return groupRepo.save(group);
    }
}
