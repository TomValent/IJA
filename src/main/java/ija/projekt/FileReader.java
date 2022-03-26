package ija.projekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader implements ReadInput {
    File filename;
    private Scanner myReader;

    public FileReader(java.lang.String fileName) {
        try {
            this.filename = new File(fileName);
            myReader = new Scanner(this.filename);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }
    }

    @Override
    public void readI() {
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
        }
    }

    public void closeFile() {
        myReader.close();
    }

}
