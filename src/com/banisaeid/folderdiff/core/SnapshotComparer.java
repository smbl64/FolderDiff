package com.banisaeid.folderdiff.core;

import com.banisaeid.folderdiff.model.FileInfo;
import com.banisaeid.folderdiff.model.Snapshot;

import java.util.ArrayList;
import java.util.List;

public class SnapshotComparer {
    private Snapshot left;
    private Snapshot right;

    public SnapshotComparer(Snapshot left, Snapshot right) {
        this.left = left;
        this.right = right;
    }

    public SnapshotDifference compare(){
        List<FileInfo> onlyInLeft = new ArrayList<FileInfo>();
        List<FileInfo> onlyInRight = new ArrayList<FileInfo>();
        List<DifferenceEntry> different = new ArrayList<DifferenceEntry>();

        for (FileInfo leftEntry : left.getFileInfos()) {
            FileInfo rightEntry = right.find(leftEntry);

            if (rightEntry == null){
                onlyInLeft.add(leftEntry);
                continue;
            }

            if (leftEntry.equals(rightEntry) && !leftEntry.getMd5().equals(rightEntry.getMd5())){
                DifferenceEntry differenceEntry = new DifferenceEntry(leftEntry, rightEntry);
                different.add(differenceEntry);
            }
        }

        for (FileInfo rightEntry : right.getFileInfos()) {
            FileInfo leftEntry = left.find(rightEntry);

            if (leftEntry == null){
                onlyInRight.add(rightEntry);
            }
        }

        SnapshotDifference difference = new SnapshotDifference(left, right);
        difference.setOnlyInLeft(onlyInLeft);
        difference.setOnlyInRight(onlyInRight);
        difference.setDifferences(different);

        return difference;
    }
}
