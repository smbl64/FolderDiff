package com.banisaeid.folderdiff.core;

import com.banisaeid.folderdiff.model.FileInfo;
import com.banisaeid.folderdiff.model.Snapshot;

import java.util.List;

public class SnapshotDifference {
    private Snapshot left;
    private Snapshot right;

    private List<FileInfo> onlyInLeft;
    private List<FileInfo> onlyInRight;
    private List<DifferenceEntry> differences;

    public SnapshotDifference(Snapshot left, Snapshot right) {
        this.left = left;
        this.right = right;
    }

    public Snapshot getLeft() {
        return left;
    }

    public void setLeft(Snapshot left) {
        this.left = left;
    }

    public Snapshot getRight() {
        return right;
    }

    public void setRight(Snapshot right) {
        this.right = right;
    }

    public List<FileInfo> getOnlyInLeft() {
        return onlyInLeft;
    }

    public void setOnlyInLeft(List<FileInfo> onlyInLeft) {
        this.onlyInLeft = onlyInLeft;
    }

    public List<FileInfo> getOnlyInRight() {
        return onlyInRight;
    }

    public void setOnlyInRight(List<FileInfo> onlyInRight) {
        this.onlyInRight = onlyInRight;
    }

    public List<DifferenceEntry> getDifferences() {
        return differences;
    }

    public void setDifferences(List<DifferenceEntry> differences) {
        this.differences = differences;
    }
}
