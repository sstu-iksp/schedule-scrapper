package edu.put_the_machine.scrapper.service.impl.parsers.sgu;

import edu.put_the_machine.scrapper.model.dto.parser.GroupLessons;
import edu.put_the_machine.scrapper.model.dto.parser.UniversityLessons;
import edu.put_the_machine.scrapper.service.interfaces.parser.GroupScheduleParser;
import edu.put_the_machine.scrapper.service.interfaces.parser.JsoupHelper;
import edu.put_the_machine.scrapper.service.interfaces.parser.ScheduleParser;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SguScheduleParser implements ScheduleParser {
    private final JsoupHelper jsoupHelper;
    private final GroupScheduleParser sguGroupScheduleParser;
    private final String pathToSchedulePage;
    private final String universityName;

    @Autowired
    public SguScheduleParser(JsoupHelper jsoupHelper, GroupScheduleParser sguGroupScheduleParser,
                             @Value("${parser.university.url.sgu}") String pathToSchedulePage,
                             @Value("${parser.university.name.sgu}") String universityName) {
        this.jsoupHelper = jsoupHelper;
        this.sguGroupScheduleParser = sguGroupScheduleParser;
        this.pathToSchedulePage = pathToSchedulePage;
        this.universityName = universityName;
    }

    @Override
    public UniversityLessons parse() {
        Document document = jsoupHelper.getDocumentFromPath(pathToSchedulePage);
        Elements instituteLinks = document.select("div#schedule_page a");
        return new UniversityLessons(universityName, parseLessons(instituteLinks));
    }

    @NotNull
    private List<GroupLessons> parseLessons(Elements instituteLinks) {
        return instituteLinks.parallelStream()
                .flatMap(link -> getInstituteLessons(link).stream())
                .collect(Collectors.toList());
    }

    private List<GroupLessons> getInstituteLessons(Element instituteLink) {
        String path = resolvePath(instituteLink.attr("href"));
        Document document = jsoupHelper.getDocumentFromPath(path);
        Elements groupLinks = document.select("div#schedule_page a");
        return groupLinks.stream()
                .map(this::getGroupLessons)
                .collect(Collectors.toList());
    }

    private GroupLessons getGroupLessons(Element link) {
        String path = resolvePath(link.attr("href"));
        return sguGroupScheduleParser.parse(path);
    }

    private String resolvePath(String href) {
        return pathToSchedulePage + "/" + href;
    }
}
