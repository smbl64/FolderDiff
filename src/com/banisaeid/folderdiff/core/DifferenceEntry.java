package com.banisaeid.folderdiff.core;

import com.banisaeid.folderdiff.model.FileInfo;

public class DifferenceEntry {
    private FileInfo leftEntry;
    private FileInfo rightEntry;

    public DifferenceEntry(FileInfo leftEntry, FileInfo rightEntry) {
        this.leftEntry = leftEntry;
        this.rightEntry = rightEntry;
    }

    public FileInfo getLeftEntry() {
        return leftEntry;
    }

    public FileInfo getRightEntry() {
        return rightEntry;
    }
}
