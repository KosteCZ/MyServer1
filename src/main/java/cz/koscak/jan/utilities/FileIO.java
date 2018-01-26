package cz.koscak.jan.utilities;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.util.Properties;
import java.util.Scanner;

/**
 * Abstraction layer for disk IO.
 */
public final class FileIO {

    /**
     * Empty constructor.
     */
    private FileIO() {
    }

    /**
     * Writes data to a file in a specified path, and debug it to a running instance of the Log Framework.
     *
     * @param path	Full path to write the file to
     * @param fileName	Name of the file to write the data to
     * @param data	String-encapsulated data to be written to the specified file
     * @throws IOException	An input and output error
     */
    public static void write(String path, String fileName, String data) throws IOException {
        write(path, fileName, data.getBytes("UTF-8"));
    }

    /**
     * Writes data to a file in a specified path, and debug it to a running instance of the Log Framework.
     *
     * @param path	Full path to write the file to
     * @param fileName	The file name
     * @param data	Byte array-encapsulated data to be written to the specified file
     * @throws IOException	An input and output error
     */
    public static void write(String path, String fileName, byte[] data) throws IOException {
        File file = returnFile(createPath(path, fileName));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        out.write(data);
        out.close();
    }

    /**
     * Persists an object to disk.
     *
     * @param filePath	File path to persist the object to
     * @param objectContent	Object to be persisted
     * @throws IOException	An input and output error
     *
     * @see ObjectOutputStream
     */
    public static void writeObject(String filePath, Object objectContent) throws IOException {
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(filePath, false));
        ObjectOutputStream objectOutput = new ObjectOutputStream(output);
        objectOutput.writeObject(objectContent);
        output.close();
    }

    /**
     * Writes a new file to disk or overwrite an existing file in disk.
     *
     * @param filePath	Path of the file to be written
     * @param content	Data to be written to the file
     * @throws IOException	Error while writing the file
     */
    public static void write(String filePath, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(returnFile(filePath), false);
        writeContentToFile(content, fileWriter);
        fileWriter.close();
    }

    /**
     * Append content to a file on disk.
     *
     * @param filePath	Path of the file to be written
     * @param content	Data to be written to the file
     * @throws IOException	Error while writing the file
     */
    public static void append(String filePath, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(returnFile(filePath), true);
        writeContentToFile(content, fileWriter);
        fileWriter.close();
    }

    private static void writeContentToFile(String content, FileWriter fileWriter) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(content);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    /**
     * Creates file if doesn't exist and returns it.
     *
     * @param filePath Path of the file to be written
     * @return file
     */
    public static File returnFile(String filePath) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        return file;
    }

    /**
     * Reads the contents of a given file into a String.
     *
     * @param filePath	The file path to read from
     * @return	The String-encapsulated file contents
     * @throws FileNotFoundException	An error indicating that the files does not exists
     *
     * @see File
     * @see Scanner
     */
    public static String read(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\\Z");
        String result = "";
        if (scanner.hasNext()) {
            result = scanner.next();
            scanner.close();
        }
        return result;
    }

    /**
     *
     * Reads the contents of a file into a String using a path.
     *
     * @param path	Path for the file to be read
     * @param fileName	File name to read the data from
     * @return	String containing the data from the given file
     * @throws FileNotFoundException	Error thrown if the file does not exists
     * @throws IOException	Error while reading the file
     */
    public static String read(String path, String fileName) throws IOException {
        return read(createPath(path, fileName));
    }

    /**
     * Reads an object from disk.
     *
     * @param filePath	File path to read the object from
     * @return	The object read from disk
     * @throws IOException	Error while reading the file
     * @throws ClassNotFoundException	Error while reading the object
     */
    public static Object readObject(String filePath) throws IOException, ClassNotFoundException {
        BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(filePath));
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        Object objectResult = objectInput.readObject();
        objectInput.close();
        return objectResult;
    }

    /**
     * This method loads certain file with properties and returns that as java object Properties.
     *
     * @param folderPath is path to property file folder
     * @param fileName is name of property file
     * @return content of property file in properties format
     * @throws IOException when file was not found or is not properties file
     */
    public static Properties readPropertiesFromDisk(String folderPath, String fileName) throws IOException {
        return readPropertiesFromDisk(createPath(folderPath, fileName));
    }

    /**
     * This method loads certain file with properties and returns that as java object Properties.
     *
     * @param filePath is path to property file
     * @return content of property file in properties format
     * @throws IOException when file was not found or is not properties file
     */
    public static Properties readPropertiesFromDisk(String filePath) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileReader(filePath));
        return prop;
    }

    /**
     * Returns a File object for a given path and file name.
     *
     * @param filePath	Path for the File object
     * @param fileName	Name of the file to be returned
     * @return	The File object
     */
    public static String createPath(String filePath, String fileName) {
        String rusultPath = fileName;
        if (filePath != null && filePath.length() > 0) {
            rusultPath = filePath;
            if (!filePath.contains(fileName)) {
                if (filePath.endsWith("/") || filePath.endsWith("\\")) {
                    rusultPath = filePath + fileName;
                } else {
                    rusultPath = filePath + File.separator + fileName;
                }
            }
        }
        return rusultPath;
    }

    /**
     * Returns file name.
     *
     * @param filePath	Path for the File object
     * @return file name
     */
    public static String getFileName(String filePath) {
        return new File(filePath).getName();
    }

    /**
     * Deletes a file in the file system.
     *
     * @param filePath	File path to be deleted
     * @return	Boolean indicating success or failure in the deletion
     */
    public static boolean delete(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }

    /**
     * Checks if a file exists in the file system.
     *
     * @param filePath	The path of the file to check
     * @return	a boolean indicating the existence of the given file
     *
     * @see File
     */
    public static boolean exists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Reads an file from disk as a string and removes it.
     *
     * @param filePath	File path to read the object from
     * @return	The object read from disk
     * @throws IOException	Error while reading the file
     * @throws ClassNotFoundException	Error while reading the object
     */
    public static Object readAndDelete(String filePath) throws IOException, ClassNotFoundException {
        Object objectResult = read(filePath);
        delete(filePath);
        return objectResult;
    }

    /**
     * Reads an object from disk and removes it.
     *
     * @param filePath	File path to read the object from
     * @return	The object read from disk
     * @throws IOException	Error while reading the file
     * @throws ClassNotFoundException	Error while reading the object
     */
    public static Object readAndDeleteObject(String filePath) throws IOException, ClassNotFoundException {
        Object objectResult = readObject(filePath);
        delete(filePath);
        return objectResult;
    }

    /**
     * Copies a given file to another given file.
     *
     * @param sourceFile	File to copy from
     * @param destinationFile	File to copy to
     * @return Boolean indicating the success or failure of the file copy
     */
    public static boolean copy(String sourceFile, String destinationFile) {
        FileChannel source = null;
        FileChannel destination = null;
        FileInputStream sourceStream = null;
        FileOutputStream destinationStream = null;
        try {
            sourceStream = new FileInputStream(sourceFile);
            source = sourceStream.getChannel();
            destinationStream = new FileOutputStream(destinationFile);
            destination = destinationStream.getChannel();
            destination.transferFrom(source, 0, source.size());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (source != null) {
                try {
                    source.close();
                    sourceStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (destination != null) {
                try {
                    destination.close();
                    destinationStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * Copies a content of input stream to given file.
     *
     * @param sourceInputStream input stream
     * @param destinationFile is path to destination file
     * @return Boolean indicating the success or failure of the file copy
     */
    public static boolean copy(InputStream sourceInputStream, String destinationFile) {
        FileOutputStream destinationStream = null;
        byte[] buffer = new byte[8192];
        int bytesRead;
        boolean success = true;
        try {
            destinationStream = new FileOutputStream(destinationFile);
            while ((bytesRead = sourceInputStream.read(buffer)) != -1) {
                destinationStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        } finally {
            if (destinationStream != null) {
                try {
                    destinationStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    /**
     * Gets the size of a file.
     *
     * @param filePath	Path to File from which to get the size
     * @return	File size
     */
    public static long size(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            long result = file.length();
            return result;
        } else {
            return 0;
        }
    }

}
