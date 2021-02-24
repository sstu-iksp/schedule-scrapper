package edu.put_the_machine.scrapper.services.impl.parsers.sstu;

import edu.put_the_machine.scrapper.model.dto.ScheduleDayDto;
import edu.put_the_machine.scrapper.services.interfaces.parser.GroupScheduleParser;
import edu.put_the_machine.scrapper.services.interfaces.parser.JsoupHelper;
import edu.put_the_machine.scrapper.services.interfaces.parser.ScheduleParser;
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

    @Autowired
    public SstuScheduleParser(JsoupHelper jsoupHelper, GroupScheduleParser sstuGroupScheduleParser,
                              @Value("${parser.university.url.sstu}") String pathToSchedulePage) {
        this.jsoupHelper = jsoupHelper;
        this.sstuGroupScheduleParser = sstuGroupScheduleParser;
        this.pathToSchedulePage = pathToSchedulePage;
    }

    @Override
    public List<ScheduleDayDto> parse() {
        Document document = jsoupHelper.getDocumentFromPath(pathToSchedulePage);
        Elements blocksWithLinks = document.select("div.panel-collapse.collapse");

        return parseScheduleDays(blocksWithLinks);
    }

    @NotNull
    private List<ScheduleDayDto> parseScheduleDays(Elements blocksWithLinks) {
        List<ScheduleDayDto> scheduleDays = new ArrayList<>();

        for (Element blockWithLink : blocksWithLinks) {
            scheduleDays.addAll(getBlockGroupsScheduleDays(blockWithLink));
        }

        return scheduleDays;
    }

    private List<ScheduleDayDto> getBlockGroupsScheduleDays(Element blockWithLink) {
        List<ScheduleDayDto> scheduleDays = new ArrayList<>();
        for (Element link : blockWithLink.getElementsByTag("a")) {
            if (link.attr("href").contains("group")) {
                scheduleDays.addAll(getGroupScheduleDays(link));
            }
        }
        return scheduleDays;
    }

    private List<ScheduleDayDto> getGroupScheduleDays(Element link) {
        String path = resolvePath(link.attr("href"));
        return sstuGroupScheduleParser.parse(path);
    }

    private String resolvePath(String href) {
        return pathToSchedulePage + "/" + href;
    }
}
