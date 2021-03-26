package edu.put_the_machine.scrapper.aspect

import org.apache.logging.log4j.LogManager
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class ScrapperTimeLoggerAspect {

    private val log = LogManager.getLogger(ScrapperTimeLoggerAspect::class.java)

    @Around("execution(* edu.put_the_machine.scrapper.service.impl.parsers.sstu.SstuScheduleParser.parse(..))")
    fun logSstuScheduleParseTime(joinPoint: ProceedingJoinPoint): Any? {
        val start = System.currentTimeMillis()
        val result = joinPoint.proceed()
        log.info("SstuScheduleParser parse time: ${System.currentTimeMillis() - start} millis")
        return result
    }
}
