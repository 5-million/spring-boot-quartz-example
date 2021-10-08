package com.example.quartz.job;

import com.example.quartz.service.SimpleService;
import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

public class JobWithBean extends CustomJob {

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ApplicationContext ctx = getApplicationContext(context);
        SimpleService service = ctx.getBean(SimpleService.class);

        String jobName = getData(context, "jobName", String.class);
        String group = getData(context, "group", String.class);
        service.run(jobName, group);
    }
}
