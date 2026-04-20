package com.example.ibkr_xmlFileArchiver.controller;


import com.example.ibkr_xmlFileArchiver.service.GcsArchiveXmlFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Profile("!local")
public class RunJob {

    private final GcsArchiveXmlFile gcsArchiveXmlFile;

    public RunJob(GcsArchiveXmlFile gcsArchiveXmlFile) {
        this.gcsArchiveXmlFile = gcsArchiveXmlFile;
    }

    private static final Logger log = LoggerFactory.getLogger(RunJob.class);

    @GetMapping("/run")
    public String runJob() throws IOException {

        log.info("Archive job started");

        gcsArchiveXmlFile.archiveXmlFile();

        return "Job completed";
    }
}
