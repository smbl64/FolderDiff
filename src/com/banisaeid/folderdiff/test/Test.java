package com.banisaeid.folderdiff.test;

import com.banisaeid.folderdiff.DifferenceEntry;
import com.banisaeid.folderdiff.SnapshotComparer;
import com.banisaeid.folderdiff.SnapshotDifference;
import com.banisaeid.folderdiff.SnapshotUtils;
import com.banisaeid.folderdiff.model.FileInfo;
import com.banisaeid.folderdiff.model.FileType;
import com.banisaeid.folderdiff.model.Snapshot;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        String leftDir = "D:\\Projects Data\\Left";
        String rightDir = "D:\\Projects Data\\Right";

        System.out.println("making snapshot 1...");
        Snapshot left = SnapshotUtils.make(leftDir);

        System.out.println("making snapshot 2...");
        Snapshot right = SnapshotUtils.make(rightDir);

        System.out.println("making comparison...");
        SnapshotComparer comparer = new SnapshotComparer(left, right);
        SnapshotDifference result = comparer.compare();

        System.out.println("Left");
        for (FileInfo entry : result.getOnlyInLeft()) {
            String normalize = normalizePath(left.getOriginalPath(), entry.getRelativeName());
            System.out.println("   > " + normalize);
        }

        System.out.println("Right");
        for (FileInfo entry : result.getOnlyInRight()) {
            String normalize = normalizePath(right.getOriginalPath(), entry.getRelativeName());
            System.out.println("   > " + normalize);
        }


        System.out.println("Different");
        for (DifferenceEntry entry : result.getDifferences()) {
            String normalize = normalizePath(left.getOriginalPath(), entry.getLeftEntry().getRelativeName());
            System.out.println("   > " + normalize);
        }

        System.out.println("Saving snapshots...");
        SnapshotUtils.save(left, new FileOutputStream("d:\\left.snapshot"));
        SnapshotUtils.save(right, new FileOutputStream("d:\\right.snapshot"));

        System.out.println("making patch directory...");
        makePatch(result, "D:\\ZZ", true, true);
    }

    private static void makePatch(SnapshotDifference result, String patchDirectory, boolean addLeftFiles, boolean addDifferences) throws IOException {
        String leftDir = result.getLeft().getOriginalPath();

        FileUtils.cleanDirectory(new File(patchDirectory));

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
}
