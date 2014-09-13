package com.banisaeid.folderdiff.core;

import com.banisaeid.folderdiff.model.FileInfo;
import com.banisaeid.folderdiff.model.FileType;
import com.banisaeid.folderdiff.model.Snapshot;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;

public class SnapshotUtils implements Serializable {
    public static Snapshot make(String basePath) throws NoSuchAlgorithmException, IOException {
        File dir = new File(basePath);
        Collection<File> files = FileUtils.listFilesAndDirs(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

        Snapshot snapshot = new Snapshot();
        for (File file : files) {
            FileInfo fileInfo = new FileInfo();
            String relative = new File(basePath).toURI().relativize(file.toURI()).getPath();
            fileInfo.setRelativeName(relative);

            if (file.isFile()) {
                String md5 = calcMD5(file);
                fileInfo.setMd5(md5);
                fileInfo.setFileType(FileType.FILE);
            } else {
                fileInfo.setMd5("");
                fileInfo.setFileType(FileType.DIRECTOY);
            }

            snapshot.getFileInfos().add(fileInfo);
        }

        snapshot.setOriginalPath(basePath);
        snapshot.setDate(new Date());

        return snapshot;
    }

    static String calcMD5(File file) throws NoSuchAlgorithmException, IOException {
        FileInputStream inputStream = new FileInputStream(file);
        String md5 = DigestUtils.md5Hex(inputStream);
        inputStream.close();

        return md5;
    }

    public static void save(Snapshot snapshot, OutputStream outputStream) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(snapshot);
        objectOutputStream.close();
    }

    public static Snapshot load(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();

        return (Snapshot) object;
    }
}