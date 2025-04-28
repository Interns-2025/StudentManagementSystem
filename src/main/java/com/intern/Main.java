package com.intern;

import com.intern.sms.util.PrintDB;
import com.khan.fazal.intern.PrintDash;

import java.util.Scanner;

import static com.intern.TableLister.printTableNames;
import static com.intern.TableLister.tableNames;

public class Main {
    public static void main(String[] args) {

//        //Print from CSV
//        String filePath = "src/main/resources/";
//        CustomDelimiter.readAndPrintTable(filePath, ",");
//
//        // Print with no User Input
//        List<String[]> rows = new ArrayList<>();
//        String arr[] = {"Name", "Class", "Age"};
//        rows.add(arr);
//        rows.add(new String[]{"Alexa", "5-G", "12"});
//        PrintDash.printTable(rows);

//        // Print using input
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter more rows (Name Class Age), or type 'exit' to finish:");
//        while (true) {
//            System.out.print("> ");
//            String line = sc.nextLine();
//            if (line.equalsIgnoreCase("exit")) break;
//
//            String[] values = line.split(",");
//            if (values.length == 3) {
//                rows.add(values);
//            } else {
//                System.out.println("Please enter exactly 3 values.");
//            }
//        }
//        System.out.println("\nFinal Table:");
//        PrintDash.printTable(rows);

        // Print from DB
        printTableNames();
        Scanner sc = new Scanner(System.in);
        String tableName="";
        while (true) {
            System.out.print("Enter the Table Name or type 'exit' to finish: ");
            tableName = sc.nextLine();
            if (tableName.equalsIgnoreCase("exit")) break;

            if (tableNames.contains(tableName)) {
                PrintDash.printTable(PrintDB.fetchTable(tableName));
            }
            else{
                System.out.println("Incorrect Table Name");
            }

        }


    }
}