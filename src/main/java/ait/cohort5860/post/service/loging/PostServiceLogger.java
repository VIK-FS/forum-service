package ait.cohort5860.post.service.loging;

import ait.cohort5860.post.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "Post Service")
@Aspect

public class PostServiceLogger {

    @Pointcut("execution(public * ait.cohort5860.post.service.PostServiceImpl.*(Long)) && args(id)")
    public void findById(Long id) {
    }

    @Pointcut("@annotation(ait.cohort5860.post.service.loging.PostLogger)")
    public void annotatePostLogger() {
    }

    @Pointcut("execution(public Iterable<ait.cohort5860.post.dto.PostDto> ait.cohort5860.post.service.PostServiceImpl.findPosts*(..))")
    public void bulkFindPostsLogger() {
    }

    @Before("findById(id)")
    public void longFindById(Long id) {
        log.info("Find post by id: {}", id);
    }

    @AfterReturning("annotatePostLogger()")
    public void longAnnotatePostLogger(JoinPoint joinPoint) {
        log.info("Annotated by PostLogger method: {}, done", joinPoint.getSignature().getName());
    }

    @Around("bulkFindPostsLogger()")
    public Object logBulkFindPostsLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String str) {
                args[i] = str.toLowerCase();
            }
        }
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed(args);
        long end = System.currentTimeMillis();
        log.info("method: {}, time: {} ms", joinPoint.getSignature().getName(), end - start);
        return result;
    }
}
