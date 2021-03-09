package edu.put_the_machine.scrapper.service.interfaces.parser;

import edu.put_the_machine.scrapper.model.parser_dto.GroupLessons;

import java.util.List;

public interface GroupScheduleParser {
    GroupLessons parse(String path);
}
