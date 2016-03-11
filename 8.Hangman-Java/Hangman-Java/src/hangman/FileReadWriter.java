package hangman;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileReadWriter {
	ArrayList<Players> myArr = new ArrayList<Players>();


	/*
	* Opens ObjectOutputStream for writing, adds records to file with ArrayList<Players> as the object written.
	* Since ArrayList is dynamic but still serializable it is much more convenient than just wring one and one
	* Players object, because then we would also need a number for how many players.java were in the list in addition
	* to every object.
	* try with-resources closes streams automatically and in the contract they also flushes them before closing.
	*/
	public void openAndWritetoFile(int score, String name){
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("players.ser"))){
			Players players = new Players(name, score);
			myArr.add(players);
			try { 
				output.writeObject(myArr);
			} catch (IOException ioException) {
				System.err.println("Error writing to file.");
			}
		} catch (IOException ioException) {
			System.err.println("Error opening file.");
			ioException.printStackTrace();
		}
	}

	/*
	* Opens players.ser and and calls readObject on ObjectInputStream which then returns the
	* ArrayList<Players> with arbitrary Players.
	* As with openAddRecordsandCloseFiletoWrite() it closes automatically.
	*/
	@SuppressWarnings("unchecked")
	public void openAndReadfromFile() {
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("players.ser"))){
			try{
				Object obj = null;
				obj = input.readObject();
				if(obj instanceof ArrayList){
					myArr = (ArrayList<Players>) obj;
				}
			}catch (Exception e) {
				System.err.println("No Players object reside in the file 'players.ser'");
				e.printStackTrace();
			}
		} catch (IOException ioException) {
			System.err.println("Error opening file.");
		}
	}
	
	//Sorts and prints Scoreboard from ArrayList<Players>
	public void printAndSortScoreBoard() {
		Collections.sort(myArr);

		System.out.println("Scoreboard:");
		int position = 1;
		for(Players p: myArr){
			System.out.printf("%d. %s ----> %d\n", position, p.getName(), p.getScores());
			position++;
		}
	}
	
}
