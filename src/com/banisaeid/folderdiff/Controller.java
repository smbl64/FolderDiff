package com.banisaeid.folderdiff;

import com.banisaeid.folderdiff.core.DifferenceEntry;
import com.banisaeid.folderdiff.core.SnapshotComparer;
import com.banisaeid.folderdiff.core.SnapshotDifference;
import com.banisaeid.folderdiff.core.SnapshotUtils;
import com.banisaeid.folderdiff.model.FileInfo;
import com.banisaeid.folderdiff.model.FileType;
import com.banisaeid.folderdiff.model.Snapshot;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private Logger logger;

    public Controller(Logger logger) {
        this.logger = logger;
    }

    public void takeSnapshot(String folder, String snapshotFilePath){
        logger.log("Creating snapshot...");
        Snapshot snapshot;
        try {
            snapshot = SnapshotUtils.make(folder);
        } catch (Exception e1) {
            logger.log("Error creating snapshot: %s", e1.getMessage());
            return;
        }

        logger.log("Snapshot created. Saving...");

        try {
            File file = new File(snapshotFilePath);
            SnapshotUtils.save(snapshot, FileUtils.openOutputStream(file));
        } catch (IOException e1) {
            logger.log("Error saving snapshot: %s", e1.getMessage());
            return;
        }

        logger.log("Snapshot saved successfully.");
    }

    public void generatePatch(String inputSnapshotFilePath, String sourceFolder, String patchFolder) {
        try {
            logger.log("Loading snapshot file...");
            Snapshot destSnapshot = SnapshotUtils.load(new FileInputStream(inputSnapshotFilePath));
            logger.log("Loaded.");

            logger.log("Creating snapshot for source folder...");
            Snapshot srcSnapshot = SnapshotUtils.make(sourceFolder);
            logger.log("Done.");

            logger.log("Creating patch...");
            SnapshotComparer comparer = new SnapshotComparer(srcSnapshot, destSnapshot);
            SnapshotDifference difference = comparer.compare();

            String patchName = "patch_" + simpleDateFormat.format(new Date());
            patchFolder = patchFolder + "/" + patchName;

            makePatch(difference, patchFolder, true, true);
            logger.log("Done.");
        } catch (Exception e) {
            logger.log("Error in generating patch: %s", e.getMessage());
        }
    }

    private static void makePatch(SnapshotDifference result, String patchDirectory, boolean addLeftFiles, boolean addDifferences) throws IOException {
        String leftDir = result.getLeft().getOriginalPath();

        //FileUtils.cleanDirectory(new File(patchDirectory));

        if (addLeftFiles) {
            for (FileInfo fileInfo : result.getOnlyInLeft()) {
                if (fileInfo.getFileType() == FileType.FILE) {
                    String relativeName = fileInfo.getRelativeName();
                    File source = new File(normalizePath(leftDir, relativeName));
                    File dest = new File(normalizePath(patchDirectory, relativeName));
                    FileUtils.copyFile(source, dest);
                }
            }
        }

        if (addDifferences) {
            for (DifferenceEntry differenceEntry : result.getDifferences()) {
                if (differenceEntry.getLeftEntry().getFileType() == FileType.FILE) {
                    String relativeName = differenceEntry.getLeftEntry().getRelativeName();
                    File source = new File(normalizePath(leftDir, relativeName));
                    File dest = new File(normalizePath(patchDirectory, relativeName));
                    FileUtils.copyFile(source, dest);
                }
            }
        }
    }

    private static String normalizePath(String base, String relativePath) {
        return FilenameUtils.normalize(base + "/" + relativePath);
    }

    public void showSnapshotInfo(String snapshotFile) {
        SimpleDateFormat snapshotDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Snapshot snapshot = SnapshotUtils.load(new FileInputStream(snapshotFile));
            logger.log("Original Path: %s", snapshot.getOriginalPath());
            logger.log("Taken at: %s", snapshotDateFormat.format(snapshot.getDate()));

            for (FileInfo fileInfo : snapshot.getFileInfos()) {
                if (fileInfo.getFileType() == FileType.DIRECTOY) {
                    logger.log("%s", fileInfo.getRelativeName());
                } else {
                    logger.log("%s md5:%s", fileInfo.getRelativeName(), fileInfo.getMd5());
                }
            }

        } catch (Exception e) {
            logger.log("Error showing snapshot information: %s", e.getMessage());
        }

    }
}
