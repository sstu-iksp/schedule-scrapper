package instruments.factory.interfaces;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.Teacher;
import edu.put_the_machine.scrapper.model.University;

public interface EntitiesDbFactory {
    University createUniversity(String name);

    Group createGroup(String name);

    Group createGroup(String name, University university);

    Teacher createTeacher(String name);

    Teacher createTeacher(String name, University university);
}
