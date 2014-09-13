package com.banisaeid.folderdiff.model;

import java.io.Serializable;

public class FileInfo implements Serializable{
    private String md5;
    private String relativeName;
    private FileType fileType;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getRelativeName() {
        return relativeName;
    }

    public void setRelativeName(String relativeName) {
        this.relativeName = relativeName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    /**
     * Checks to see if two items have the same relative name and file type.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileInfo fileInfo = (FileInfo) o;

        if (fileType != fileInfo.fileType) {
            return false;
        }
        if (!relativeName.equals(fileInfo.relativeName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + relativeName.hashCode();
        result = 31 * result + fileType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return relativeName;
    }
}
