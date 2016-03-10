package hangman;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FileReadWriter {
	ArrayList<Players> myArr = new ArrayList<Players>();


	//######################## writing to file start ##########################################
	/*
	* Opens ObjectOutputStream for writing, adds records to file with ArrayList<Players> as the object written.
	* Since ArrayList is dynamic but still serializable it is much more convenient than just wring one and one
	* Players object, because then we would also need a number for how many players.java were in the list in addition
	* to every object.
	* try with-resources closes streams automatically and in the contract they also flushes them before closing.
	*/
	public void openAddRecordsandCloseFiletoWrite (int score, String name){
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("players.ser"))){
			addRecords(output, score, name);
		} catch (IOException ioException) {
			System.err.println("Error opening file.");
			ioException.printStackTrace();
		}
	}

	//If game has been runned > 1 times before myArr will contain these players from the call on readRecords from last round.
	public void addRecords(ObjectOutputStream output, int scores, String name) {
		Players players = new Players(name, scores);
		myArr.add(players);
		try { 
			output.writeObject(myArr);
		} catch (IOException ioException) {
			System.err.println("Error writing to file.");
		}
	}
	//######################## writing to file end ########################



	/*
	* Opens players.ser and and calls readObject on ObjectInputStream which then returns the
	* ArrayList<Players> with arbitrary Players.
	* As with openAddRecordsandCloseFiletoWrite() it closes automatically.
	*/
	//######################## reading from file start ########################
	public void openReadandWriteFileToRead() {
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("players.ser"))){
			readRecords(input);
		} catch (IOException ioException) {
			System.err.println("Error opening file.");
		}
	}

	public void readRecords(ObjectInputStream input) {
		try{
			int count = 0;
			Object obj = null;
			obj = input.readObject();

			if(obj instanceof ArrayList){
				myArr = (ArrayList<Players>) obj;
				//System.out.println("\nactually reads streams from file\n");
			}else{
				System.err.println("players.ser corrupted, deleting it...");
				File f = new File("players.ser");
				try{
					if(f.exists())
						f.delete();
				}catch (SecurityException secE){
					System.err.println("File is restricted, please overwrite it with admin-rights");
					secE.printStackTrace();
					System.exit(1);
				}
			}
		}catch (EOFException ex) {
			System.err.println("No Players object reside in the file 'players.ser'");
			ex.printStackTrace();
		} catch (ClassNotFoundException classNotFoundException) {
			System.err.println("Cannot find class Players.java");
		} catch (InvalidClassException invalidClass){
			System.err.println("Serialization failed for class Players.java");
		} catch (StreamCorruptedException streamCorrupt) {
			System.err.println("Stream corrupted - Control information in the stream is inconsistent."); 
		}catch (OptionalDataException optionalData){
			System.err.println("Primitive data was found in the stream instead of objects.");
		}catch (IOException ioException) {
			ioException.printStackTrace();
			System.err.println("Error during reading from file.");
		}
	} 
	//######################## reading from file end ########################


	public void printAndSortScoreBoard() {
		Collections.sort(myArr,new ScoreComparator());

		System.out.println("Scoreboard:");
		int position = 1;
		for(Players p: myArr){
			System.out.printf("%d. %s ----> %d\n", position, p.getName(), p.getScores());
			position++;
		}
	}
	
	public static class ScoreComparator implements Comparator<Players> {
    	@Override
	    public int compare(Players p1, Players p2) {
	    	return p1.getScores()-p2.getScores();
	    }
	}

}


	//TODO:
	/*
	* 1. Check if any Exceptions should make the program close.
	*/