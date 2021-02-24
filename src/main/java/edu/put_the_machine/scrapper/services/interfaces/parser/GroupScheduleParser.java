package edu.put_the_machine.scrapper.services.interfaces.parser;

import edu.put_the_machine.scrapper.model.dto.ScheduleDayDto;

import java.util.List;

public interface GroupScheduleParser {
    List<ScheduleDayDto> parse(String path);
}
