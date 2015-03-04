// Josh Lindoo
// Databases Assignment 3
// This program is a simple data retrieval/storage system

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class Client {
	
	//global declarations
	static HashMap<String, int[]> hashmap = new HashMap<String, int[]>();

	public static void main(String[] args) {
		
		loadHashmap();// attempt to load previous hashmap
		menu(); // display menu, process user input
		
	}
	
	public static void menu() {
		Scanner in = new Scanner(System.in);
		
		System.out.print("Welcome to the Data Retrieval System!\n"
				+ "Try one of the following commands to get started:\n\n"
				+ "I record - Insert record into the file (no duplicates)\n"
				+ "R name - Retrieve the record having key name, if it is there\n"
				+ "D name - Delete record using key name\n"
				+ "U name field-name new-value - Update the given record\n\n$ ");
		
		//menu loops until the process is killed
		while(true)
		{
			String inputLn = in.nextLine();
			Scanner commandScan = new Scanner(inputLn);
			
			String commName = null;
			if(commandScan.hasNext()) commName = commandScan.next();
			
			if(commName != null) {
				switch(commName.toLowerCase())
				{
				case "i":
					execInsert(commandScan);
					break;
				case "r":
					execRetrieve(commandScan);
					break;
				case "d":
					execDelete(commandScan);
					break;
				case "u":
					execUpdate(commandScan);
					break;
				case "q":
					execQuitRoutine();
					break;
				default:
					System.out.println("*** Command not recognized ***\n");
					break;
				}
			}
			
			//print new prompt
			System.out.print("$ ");
		}
		
	}
	
	public static void execInsert(Scanner commandScan) {
		String key = commandScan.next();
		int[] intArr = new int[4];
		for(int i = 0; i<4; i++)
		{
			intArr[i] = Integer.parseInt(commandScan.next());
		}
		hashmap.putIfAbsent(key, intArr);
	}
	
	public static void execRetrieve(Scanner commandScan) {
		String key = commandScan.next();
		int[] record = hashmap.get(key);
		
		if(record != null)
		{
			System.out.print(key + " ");
			for(int i=0; i<4; i++)
			{
				System.out.print(record[i] + " ");
			}
			System.out.println();
		}
		else
		{
			System.out.println("*** Record not found ***");
		}
	}
	
	public static void execDelete(Scanner commandScan) {
		String key = commandScan.next();
		hashmap.remove(key);
	}
	
	public static void execUpdate(Scanner commandScan) {
		String key = commandScan.next();
		int[] record = hashmap.get(key);
		
		int index = 5;
		switch(commandScan.next()){
		case "a1":
			index = 0;
			break;
		case "a2":
			index = 1;
			break;
		case "a3":
			index = 2;
			break;
		case "a4":
			index = 3;
			break;
		default:
			break;
		}
		
		int newVal = Integer.parseInt(commandScan.next());
		record[index] = newVal;
		hashmap.replace(key, record);
	}
	
	public static void execQuitRoutine() {
		try
		{
			FileOutputStream fileOut = new FileOutputStream("hashmap.ser");
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(hashmap);
			objOut.close();
			fileOut.close();
		}
		catch (Exception e)
		{
			//do nothing
			System.out.println("Save failed..");
		}
		System.exit(0);
	}
	
	public static void loadHashmap() {
		try
		{
			FileInputStream inputFileStream = new FileInputStream("hashmap.ser");
			ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
			hashmap = (HashMap<String, int[]>)objectInputStream.readObject();
			objectInputStream.close();
			inputFileStream.close();
		}
		catch (Exception e)
		{
			//do nothing
			//System.out.println("Nothing loaded..");
		}
	}
	
}