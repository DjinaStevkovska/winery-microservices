package io.pivotal.microservices.util;
import java.io.File;

/**
 * mvn exec:java -Dexec.mainClass="io.pivotal.microservices.util.CacheCleaner" -Dexec.args="/{echo $HOME}/.m2/repository"
 * chmod +rwx /{echo $HOME}/.m2/repository
 *
 * # Set the correct permissions for the repository directory
 * chmod 775 ~/.m2/repository
 *
 * # Recursively set the correct permissions for all files and subdirectories under ~/.m2/repository
 * chmod -R 664 ~/.m2/repository
 */
public class CacheCleaner {

    /**
     * @param args
     *        command-line argument, which should be the path to the cache directory.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java CacheCleaner <directory_path>");
            System.exit(1);
        }

        String cacheDirectoryPath = args[0];

        cleanCache(cacheDirectoryPath);
    }

    private static void cleanCache(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            System.out.println("Directory does not exist: " + directoryPath);
            System.exit(1);
        }

        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files to delete in the directory: " + directoryPath);
            System.exit(0);
        }

        for (File file : files) {
            if (file.delete()) {
                System.out.println("Deleted file: " + file.getName());
            } else {
                System.out.println("Failed to delete file: " + file.getName());
            }
        }

        System.out.println("Cache cleaning finished successfully.");
    }
}

