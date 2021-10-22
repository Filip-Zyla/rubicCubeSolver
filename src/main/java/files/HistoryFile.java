package files;

import java.io.*;
import java.nio.file.Paths;

public class HistoryFile {

    private final static String fileName = "history.txt";

    public static void createFile(){
        File file = new File(Paths.get(fileName).toString());
        if (file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToHistory(String s) {
        File file = new File(fileName);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(s);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String printHistory() {
        File file = new File(fileName);
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            bufferedReader.lines().forEach(s -> stringBuffer.append(s).append("\n"));
            bufferedReader.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error in file";
    }
}