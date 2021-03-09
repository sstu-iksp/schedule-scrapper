package edu.put_the_machine.scrapper.service.impl.parsers.sstu;

import edu.put_the_machine.scrapper.model.parser_dto.GroupLessons;
import edu.put_the_machine.scrapper.model.parser_dto.UniversityLessons;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class SstuScheduleParser implements ScheduleParser {
    private final JsoupHelper jsoupHelper;
    private final GroupScheduleParser sstuGroupScheduleParser;
    private final String pathToSchedulePage;
    private final String universityName;

    @Autowired
    public SstuScheduleParser(JsoupHelper jsoupHelper, GroupScheduleParser sstuGroupScheduleParser,
                              @Value("${parser.university.url.sstu}") String pathToSchedulePage,
                              @Value("${parser.university.name.sstu}") String universityName) {
        this.jsoupHelper = jsoupHelper;
        this.sstuGroupScheduleParser = sstuGroupScheduleParser;
        this.pathToSchedulePage = pathToSchedulePage;
        this.universityName = universityName;
    }

    @Override
    public UniversityLessons parse() {
        Document document = jsoupHelper.getDocumentFromPath(pathToSchedulePage);
        Elements blocksWithLinks = document.select("div.panel-collapse.collapse");

        return new UniversityLessons(universityName, parseLessons(blocksWithLinks));
    }

    @NotNull
    private List<GroupLessons> parseLessons(Elements blocksWithLinks) {
        List<GroupLessons> lessons = new ArrayList<>();
        for (Element blockWithLink : blocksWithLinks) {
            lessons.addAll(getBlockGroupsLessons(blockWithLink));
        }

        return lessons;
    }

    private List<GroupLessons> getBlockGroupsLessons(Element blockWithLink) {
        List<GroupLessons> lessons = new ArrayList<>();
        for (Element link : blockWithLink.getElementsByTag("a")) {
            if (link.attr("href").contains("group")) {
                lessons.add(getGroupLessons(link));
            }
        }
        return lessons;
    }

    private GroupLessons getGroupLessons(Element link) {
        String path = resolvePath(link.attr("href"));
        return sstuGroupScheduleParser.parse(path);
    }

    private String resolvePath(String href) {
        return pathToSchedulePage + "/" + href;
    }
}
