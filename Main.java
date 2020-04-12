package com.stephen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class Main {

    static FileManager fileManager = new FileManager();
    static Scanner input = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
        // fileManager.inputFilePath = "data.txt";
        // fileManager.outputFilePath = "outputData.txt";
        // String data = fileManager.getData();
        // fileManager.writeData(data);
        //fileManager.formatFile();

        String answer = "";
        
        Company company = new Company();
        FileManager companyData = new FileManager();
        companyData.filePath = "companyData.txt";
        Scanner companyScanner = new Scanner(new File("companyData.txt"));
        String password = "";
        if(companyData.getData().compareTo("") == 0) {
            System.out.print("You have to create a new company account:\n");
            System.out.print("Name: ");
            company.name = input.next();
            System.out.print("Country: ");
            company.country = input.next();
            System.out.println("Processing data...");
            company.createCompany();
            System.out.println("Your password is " + company.pwd);
        } else {
            while(companyScanner.hasNextLine()) {
                password = companyScanner.nextLine();
            }
            System.out.print("Password: ");
            answer = input.nextLine();
            if(answer.compareTo(password) == 0) {
                System.out.println("Corect password");
            } else {
                System.out.println("Sorry, incorect password");
                System.exit(0);
            }
        }

        while(answer.compareTo("quit") != 0) {
            System.out.print("$ ");
            answer = input.next();
            switch(answer) {
                case "output": outputEmployees(); break;
                case "employ": createNewEmployee(); break;
                case "fire": deleteOldEmployee(); break; 
                case "quit": System.out.println("Exiting..."); break;
                case "format": Format(); break;
                default: System.out.println("Command not found"); break;
            }
        }

	}

    public static void outputEmployees() throws FileNotFoundException {
        fileManager.filePath = "data.txt";
        String data = fileManager.getData();
        System.out.println(data);
    }

    public static void Format() throws IOException {
        System.out.println("Are you sure? y/n");
        String ans = input.next();
        if(ans.compareTo("y") == 0) {
            fileManager.formatFile();
        }
    }

    public static void createNewEmployee() throws IOException {
        fileManager.filePath = "data.txt";
        String oldData = fileManager.getData();
        System.out.print("Name: ");
        String name = input.next();
        System.out.print("Age: ");
        String age = input.next();
        System.out.print("Country: ");
        String country = input.next();

        String employeeData = name + " - " + age + " - " + country + "\n";
        String newData = oldData.concat(employeeData);
        fileManager.writeData(newData);
    }

    public static void deleteOldEmployee() throws IOException {
        fileManager.filePath = "data.txt";
        String oldData = fileManager.getData();
        System.out.println("We need some information before.");
        System.out.print("Name: ");
        String name = input.next();
        System.out.print("Age: ");
        String age = input.next();
        System.out.print("Country: ");
        String country = input.next();

        String employeeData = name + " - " + age + " - " + country + "\n";
        File file = new File(fileManager.filePath);
        Scanner read = new Scanner(file);
        String newData = "";
        while(read.hasNextLine()) {
            String currentLine = read.nextLine();
            if(currentLine.length() == (employeeData.length()-1)) {
                currentLine = "";
            } 
            if(currentLine != "") {
                newData = newData.concat(currentLine + "\n");
            }
            
        }
        fileManager.writeData(newData);
    }
}

public class FileManager {

    String filePath;

    public String getData() throws FileNotFoundException {

        File file = new File(filePath);
        Scanner input = new Scanner(file);
        String fileContent = "";
        while (input.hasNextLine()) {
			fileContent = fileContent.concat(input.nextLine() + "\n");
		}
        return fileContent;
    }

    public void writeData(String content)  throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(content);
        writer.close();
    }

    public void formatFile() throws IOException {
        FileWriter formater = new FileWriter(filePath);
        formater.write("");
        formater.close();
    }
}

public class Company {
    String name;
    String country;
    String pwd = createRandomPassword();

    public void createCompany() throws IOException{
        FileManager companyData = new FileManager();
        companyData.filePath = "companyData.txt";
        // companyData.formatFile();
        String data = "";
        data = data.concat(name + "\n" + country + "\n" + pwd);
        companyData.writeData(data);
    }

    public String createRandomPassword() {
        String pwd = "";
        String characters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        int lenght = 5;

        Random rand = new Random();

        char[] text = new char[lenght];

        for(int i=0;i<lenght;i++) {
            text[i] = characters.charAt(rand.nextInt(characters.length()));
        }
        for(int i=0;i<text.length;i++) {
            pwd += text[i];
        }
        return pwd;
    }
}