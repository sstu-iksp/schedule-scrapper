package edu.put_the_machine.scrapper.service.impl.parsers.sstu;

import edu.put_the_machine.scrapper.model.dto.parser.dto.GroupLessons;
import edu.put_the_machine.scrapper.model.dto.parser.dto.UniversityLessons;
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
        return blocksWithLinks.stream()
                .flatMap(block -> getBlockGroupsLessons(block).stream())
                .collect(Collectors.toList());
    }

    private List<GroupLessons> getBlockGroupsLessons(Element blockWithLink) {
        return blockWithLink.getElementsByTag("a").stream()
                .filter(link -> link.attr("href").contains("group"))
                .map(this::getGroupLessons)
                .collect(Collectors.toList());
    }

    private GroupLessons getGroupLessons(Element link) {
        String path = resolvePath(link.attr("href"));
        return sstuGroupScheduleParser.parse(path);
    }

    private String resolvePath(String href) {
        return pathToSchedulePage + "/" + href;
    }
}
