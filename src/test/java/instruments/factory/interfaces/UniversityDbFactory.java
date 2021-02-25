package instruments.factory.interfaces;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.University;

public interface UniversityDbFactory {
    University createUniversity(String name);

    Group createGroup(String name);

    Group createGroup(String name, University university);
}
