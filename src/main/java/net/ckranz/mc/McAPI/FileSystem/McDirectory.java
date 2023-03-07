package net.ckranz.mc.McAPI.FileSystem;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;


public class McDirectory {
    private final File file;
    private final static HashMap<File, McDirectory> directories = new HashMap<>();


    private McDirectory(File file) {
        this.file = file;
        directories.put(file, this);
    }

    public static McDirectory create(String path) {
        File tempFile = new File(path);
        if(!tempFile.exists()) {
            tempFile.mkdirs();
        }
        if(directories.containsKey(tempFile)) {
            return directories.get(tempFile);
        } else {
            return new McDirectory(tempFile);
        }
    }

    public static McDirectory create(String name, McDirectory parent) {
        return create(parent.getPath() + "/" + name);
    }


    public String getName() {
        return file.getName();
    }

    public String getPath() {
        return file.getPath();
    }

    public McDirectory getParent() {
        return create(file.getParent());
    }

    public File getFile() {
        return file;
    }

    public void delete() {
        for(McFile f : getChildFiles().values()) {
            f.delete();
        }
        for(McDirectory dir : getChildDirectories().values()) {
            dir.delete();
        }
        file.delete();
        directories.remove(file);
    }


    public HashMap<String, McFile> getChildFiles() {
        HashMap<String, McFile> childFiles = new HashMap<>();
        for(String name : file.list()) {
            File tempFile = new File(name);
            if(tempFile.isFile()) {
                childFiles.put(name, McFile.create(tempFile.getPath()));
            }
        }
        return childFiles;
    }

    public HashMap<String, McDirectory> getChildDirectories() {
        HashMap<String, McDirectory> childDirectories = new HashMap<>();
        for(String name : file.list()) {
            File tempFile = new File(name);
            if(tempFile.isDirectory()) {
                childDirectories.put(name, create(tempFile.getPath()));
            }
        }
        return childDirectories;
    }


    public McFile addFile(String name) {
        return McFile.create(name, this);
    }

    public McDirectory addDirectory(String name) {
        return create(name, this);
    }

    public void removeFile(String name) {
        getChildFiles().get(name).delete();
    }

    public void removeDirectory(String name) {
        getChildDirectories().get(name).delete();
    }

    public static McDirectory getPluginDirectory(Plugin plugin) {
        return create(plugin.getDataFolder().getAbsolutePath());
    }
}