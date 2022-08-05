import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Jonathan Lin
 */
public class LineCounter {
    
    private String masterDirectory;
    private int numberOfLines;
    private int numberOfFiles;
    private int numberOfFolders;
    private long dataSize;
    private boolean isHighPerformance;
    
    
    /**
     * This is the constructor for the LineCounter object.
     * @param Nothing.
     * @return Nothing.
     */
    public LineCounter() {
        
        // set all the default values
        masterDirectory = "toBeCounted";
        numberOfFiles = 0;
        numberOfFolders = 0;
        dataSize = 0;
        isHighPerformance = true;
    }
    
    
    /**
     * This function runs all the available tests and prints the results in an
     * orderly fashion.
     * @param Nothing.
     * @return Nothing.
     */
    public void analyzeEverything() {
        
        // run all the available tests
        countNumberOfLines(masterDirectory);
        countNumberOfFilesAndFolders(masterDirectory);
        countDataSize(masterDirectory);
        
        // print everything
        System.out.println("-------------------------------------");
        System.out.println("Scanned Directory: " + masterDirectory);
        System.out.println("");
        System.out.println("Total Number of Lines: " + addCommasInt(numberOfLines) + " lines");
        System.out.println("Total Number of Files: " + addCommasInt(numberOfFiles) + " files");
        System.out.println("Total Number of Folders: " + addCommasInt(numberOfFolders) + " folders");
        System.out.println("Total Directory Size: " + addCommasLong(dataSize) + " bytes" + " (" + addCommasLong(dataSize / (1024 * 1024)) + " MB)");
        System.out.println("-------------------------------------");
    }
    
    
    /**
     * This function is called recursively to count the number of lines of code
     * in a given folder/file
     * @param fileName This is the path of the given folder or file.
     * @return The number of lines in the given folder or file.
     */
    public void countNumberOfLines(String path) {
        
        // this simply prints out what the code is currently doing
        if (isHighPerformance == false) {
            System.out.println("Counting lines in " + path);
        }
        
        int count = 0;
        
        // pathnames is the pathnames of the current file.
        // Will be null if it is a file.
        String[] pathnames;
        
        try {
            // retrieve the given filepaths of a file or folder
            File f = new File(path);
            pathnames = f.list();
            
            if (pathnames == null) {
                try{
                    
                    // count the number of lines in a file
                    File file = new File(path);

                    Scanner scanner = new Scanner(file);

                    while (scanner.hasNextLine()) {
                        scanner.nextLine();
                        count++;
                    }

                    scanner.close();
                }
                catch (Exception e) {
                    // this will run if there is an error counting the number
                    // of lines in a given file.
                    System.out.println("Error: There was an error counting the number of lines in the file found here " + path);
                    System.out.println(e);
                    System.exit(0);
                }
                numberOfLines += count;
            }
            else if (pathnames != null) {
                
                // add up the number of lines of code of everything in the
                // current directory
                for (String string : pathnames) {
                    //count += countNumberOfLines(path + "\\" + string);
                    countNumberOfLines(path + "\\" + string);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: There was an error accessing the given file or folder at " + path);
            System.out.println(e);
            System.exit(0);
        }
        
        //return count;
    }
    
    
    /**
     * This function is called recursively to go through the directory and 
     * appropriately updates the files and folders counter.
     * @param path This is the path the method is currently checking.
     * @return Nothing.
     */
    public void countNumberOfFilesAndFolders(String path) {
        
        // pathnames is the pathnames of the current file.
        // Will be null if it is a file.
        String[] pathnames;
        
        try {
            File f = new File(path);
            pathnames = f.list();
            
            if (pathnames == null) {
                
                // print out if the code recognized a new file
                if (isHighPerformance == false) {
                    System.out.println("Counted New File At: " + path);
                }
                
                numberOfFiles += 1;
            }
            if (pathnames != null) {
                
                // print out if the code recognized a new folder
                if (isHighPerformance == false) {
                    System.out.println("Counted New Folder At: " + path);
                }
                
                numberOfFolders += 1;
                for (String string : pathnames) {
                    countNumberOfFilesAndFolders(path + "\\" + string);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: There was an error accessing the given file or folder at " + path);
            System.out.println(e);
            System.exit(0);
        }
        
    }
    
    
    /**
     * This function is called recursively to go through the directory and
     * add up the size of all the files.
     * @param path This is the path of the given folder or file.
     * @return Nothing.
     */
    public void countDataSize(String path) {
        
        // pathnames is the pathnames of the current file.
        // Will be null if it is a file.
        String[] pathnames;
        
        try {
            File f = new File(path);
            pathnames = f.list();
            
            if (pathnames == null) {
                
                // print out the size of the new file
                if (isHighPerformance == false) {
                    System.out.println("New Data Recognized (" + String.valueOf(addCommasLong(f.length())) + " bytes) at " + path);
                }
                dataSize += f.length();
            }
            if (pathnames != null) {
                for (String string : pathnames) {
                    countDataSize(path + "\\" + string);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: There was an error accessing the given file or folder at " + path);
            System.out.println(e);
            System.exit(0);
        }
    }
    
    
    /**
     * This function will set the current directory that the program is sifting
     * through.
     * @param givenFolderName This is the path of the new directory the user
     * wants to perform tests and analysis on.
     * @return Nothing.
     */
    public void setFolderName(String givenFolderName) {
        masterDirectory = givenFolderName;
    }
    
    
    /**
     * This function will set whether the program is high performance or not.
     * @param status This will indicate whether isHighPerformance is true or
     * not. True means that the code will perform its actions as quickly as
     * possible.
     * @return Nothing.
     */
    public void setHighPerformance(boolean status) {
        isHighPerformance = status;
    }
    
    
    /**
     * This function sets all the variables to default or starting values.
     * @param Nothing.
     * @return Nothing.
     */
    public void reset() {
        masterDirectory = "toBeCounted";
        numberOfFiles = 0;
        numberOfFolders = 0;
        dataSize = 0;
        isHighPerformance = true;
    }
    
    
    /**
     * This returns the current directory the object is performing analysis on.
     * @param Nothing.
     * @return The folder name.
     */
    public String getFolderName() {
        return masterDirectory;
    }
    
    
    /**
     * This returns the current number of lines of code in the given directory.
     * countNumberOfLines() is required to run to update the numberOfLines.
     * @param Nothing.
     * @return The number of lines of code.
     */
    public int getNumberOfLines() {
        return numberOfLines;
    }
    
    
    /**
     * This returns the number of files in the given directory.
     * countNumberOfFilesAndFolders() is required to run to update 
     * numberOfFiles.
     * @param Nothing.
     * @return The number of files.
     */
    public int getNumberOfFiles() {
        return numberOfFiles;
    }
    
    
    /**
     * This returns the number of folders in the given directory.
     * countNumberOfFilesAndFolders() is required to run to update
     * numberOfFolders.
     * @param Nothing.
     * @return The number of folders.
     */
    public int getNumberOfFolders() {
        return numberOfFolders;
    }
    
    
    /**
     * This returns the number of files in the given directory.
     * countDataSize() is required to run to update numberOfFiles.
     * @param Nothing.
     * @return The total size, in bytes, of the directory.
     */
    public long getDataSize() {
        return dataSize;
    }
    
    /**
     * This returns whether the object is on high performance or not.
     * @param Nothing
     * @return if isHighPerformance is true or false.
     */
    public boolean getIsHighPerformance() {
        return isHighPerformance;
    }
    
    
    /**
     * This method adds commas in the appropriate places in an int.
     * @param givenInt The int to add commas to.
     * @return The int with commas in the appropriate places.
     */
    public String addCommasInt(int givenInt) {
        String givenIntStr = String.valueOf(givenInt);
        String out = "";
        
        for (int i = givenIntStr.length() - 1; i > -1; i--) {
            out = givenIntStr.substring(i, i+1) + out;
            if ((givenIntStr.length() - i) % 3 == 0 && i > 0) {
                out = "," + out;
            }
        }
        
        return out;
    }
    
    
    /**
     * This method adds commas in the appropriate places in a long.
     * @param givenInt The long to add commas to.
     * @return The long with commas in the appropriate places.
     */
    public String addCommasLong(long givenLong) {
        String givenLongStr = String.valueOf(givenLong);
        String out = "";
        
        for (int i = givenLongStr.length() - 1; i > -1; i--) {
            out = givenLongStr.substring(i, i+1) + out;
            if ((givenLongStr.length() - i) % 3 == 0 && i > 0) {
                out = "," + out;
            }
        }
        
        return out;
    }
    
    
    /**
     * This is the main method.
     * @param args The command-line arguments.
     * @return Nothing.
     */
    public static void main(String[] args) {
        
        LineCounter lineCounter = new LineCounter();
        lineCounter.reset(); // reset isn't necessary here since it's the first run. But it's best practice to reset before every new change in directory.
        lineCounter.setHighPerformance(false);
        lineCounter.analyzeEverything();
        
    }
}
