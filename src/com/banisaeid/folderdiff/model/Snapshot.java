package com.banisaeid.folderdiff.model;

import java.io.*;
import java.util.*;

public class Snapshot implements Serializable {
    private String originalPath;
    private List<FileInfo> fileInfos;

    public Snapshot() {
        fileInfos = new ArrayList<FileInfo>();
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public List<FileInfo> getFileInfos() {
        return fileInfos;
    }

    public void setFileInfos(List<FileInfo> fileInfos) {
        this.fileInfos = fileInfos;
    }

    public FileInfo find(FileInfo item){
        int index = fileInfos.indexOf(item);
        if (index >= 0){
            return fileInfos.get(index);
        }

        return null;
    }

}
