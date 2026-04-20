package com.example.ibkr_xmlFileArchiver.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Profile("local")
public class LocalArchiveXmlFile implements ArchiveXmlFile {

    @Value("${flex.xml.current.file}")
    private String flexXmlCurrentfile;

    @Value("${flex.xml.archive.directory}")
    private String flexXmlArchiveDirectory;

    @PostConstruct
    public void archiveXmlFile() throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDate.now().format(formatter);

        Path source = Paths.get(flexXmlCurrentfile);

        String originalFileName = source.getFileName().toString();
        String fileNameWithoutFileType = originalFileName.replace(".xml", "");
        String finalArchivedFilename = fileNameWithoutFileType + "_" + date + ".xml";

        Path destination = Paths.get(flexXmlArchiveDirectory, finalArchivedFilename);

        Files.createDirectories(destination.getParent());

        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

}
