package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CustomFileWriter {
    public static void writeFile(String info, String path) {

        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(path);
            /*if (!file.exists()){
                file.createNewFile();
            }*/
            FileWriter fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(info);
            System.out.println("File written Successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bufferedWriter != null){
                    bufferedWriter.close();
                }
            }catch (Exception e){
                System.out.println("Error in closing the BufferedWriter"+e);
            }
        }

    }
}
