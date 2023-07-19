package com.ujisistemc.postgre;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderReader {
    public List<String> getFileName(String folderPath) {
        List<String> filenames = new ArrayList<>();

        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        filenames.add(file.getName());
                    }
                }
            }
        }

        return filenames;
    }
}
