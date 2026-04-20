package com.example.ibkr_xmlFileArchiver.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GcsArchiveXmlFile implements ArchiveXmlFile{

    @Value("${flex.query.bucket.current}")
    private String flexXmlCurrentfile;

    @Value("${flex.query.bucket.archive}")
    private String flexXmlArchiveDestination;

    @Value("${flex.query.bucket}")
    private String gcsBucketName;

    private Storage storage = StorageOptions.getDefaultInstance().getService();

    private static final Logger log = LoggerFactory.getLogger(GcsArchiveXmlFile.class);

    public void archiveXmlFile() throws IOException {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDate.now().format(formatter);

        String sourceObject = flexXmlCurrentfile;
        String targetObject = flexXmlArchiveDestination + date + ".xml";

        log.info("Moving {} to {}",sourceObject,targetObject);

        BlobId sourceBlobId = BlobId.of(gcsBucketName, sourceObject);

        BlobId targetBlobId = BlobId.of(gcsBucketName, targetObject);

        Storage.CopyRequest copyRequest = Storage.CopyRequest.newBuilder()
                .setSource(sourceBlobId)
                .setTarget(targetBlobId)
                .build();

        storage.copy(copyRequest);

        log.info("Archive Successful. Delete {}",sourceObject);

        storage.delete(sourceBlobId);


    }
}
