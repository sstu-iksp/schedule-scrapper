package edu.put_the_machine.scrapper.controller

import edu.put_the_machine.scrapper.model.dto.LessonDto
import edu.put_the_machine.scrapper.model.dto.toDto
import edu.put_the_machine.scrapper.service.LessonService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.stream.Collectors

@RestController
@RequestMapping("/api")
class LessonController(private val lessonService: LessonService) {
    @GetMapping("/lessons")
    fun getLessons(
        @RequestParam(value = "universityId", required = false) universityId: Long?,
        @RequestParam(value = "teacherId", required = false) teacherId: Long?,
        @RequestParam(value = "groupId", required = false) groupId: Long?,
        @RequestParam(value = "startDate", required = false) startDate: LocalDate?,
        @RequestParam(value = "endDate", required = false) endDate: LocalDate?,
        @RequestParam(value = "subject", required = false) subject: String?,
        @RequestParam(value = "location", required = false) location: String?,
        @RequestParam(value = "type", required = false) type: String?,
        pageable: Pageable
    ): List<LessonDto> {
        return lessonService.findLessons(
            universityId = universityId,
            teacherId = teacherId,
            groupId = groupId,
            startDate = startDate,
            endDate = endDate,
            subject = subject,
            location = location,
            type = type,
            pageable = pageable
        ).map { it.toDto() }
    }
}