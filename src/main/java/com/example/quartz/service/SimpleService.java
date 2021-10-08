package com.example.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimpleService {

    public void run(String jobName, String group) {
        log.info(String.format("| %s | %s | SimpleService's run() method execute !!!", jobName, group));
    }

}
