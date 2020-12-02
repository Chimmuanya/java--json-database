package client;

import java.io.*;

public class FileReader {
    public static String readFile(String filePath) throws IOException {
        StringBuilder readString = new StringBuilder();
        File file = new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        int singleCharInt;
        char singleChar;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            while ((singleCharInt = bufferedInputStream.read()) != -1){
                singleChar = (char)singleCharInt;
                readString.append(singleChar);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        return readString.toString();
    }
}
