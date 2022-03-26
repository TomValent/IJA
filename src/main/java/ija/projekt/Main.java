package ija.projekt;


public class Main{
    public static void main(String[] args)
    {
        if(args.length == 0)
        {
            System.out.println("File not found.");
            System.exit(1);
        }
        var fileName = args[0];
        FileReader file = new FileReader(fileName);
        file.readI();
        file.closeFile();
    }
}
