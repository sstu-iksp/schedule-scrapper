package edu.put_the_machine.scrapper.service.interfaces.parser;

import edu.put_the_machine.scrapper.model.dto.parser.dto.GroupLessons;

public interface GroupScheduleParser {
    GroupLessons parse(String path);
}
