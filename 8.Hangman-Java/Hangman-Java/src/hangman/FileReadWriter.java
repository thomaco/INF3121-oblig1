package hangman;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FileReadWriter {
	ArrayList<Players> myArr = new ArrayList<Players>();
	ObjectOutputStream output;
	ObjectInputStream input;

	/*
	* Opens ObjectOutputStream for writing, adds records to file with ArrayList<Players> as the object written.
	* Since ArrayList is dynamic but still serializable it is much more convenient than just wring one and one
	* Players object, because then we would also need a number for how many players.java were in the list in addition
	* to every object.
	* try with-resources closes streams automatically and in the contract they also flushes them before closing.
	*/
	public void openAddRecordsandCloseFiletoWrite (){
		try {
		 	output = new ObjectOutputStream(new FileOutputStream("players.ser")))
		} catch (IOException ioException) {
			System.err.println("Error opening file.");
			ioException.printStackTrace();
		}
	}

	//If game has been runned > 1 times before myArr will contain these players from the call on readRecords from last round.
	public void addRecords(int scores, String name) {
		Players players = new Players(name, scores);
		myArr.add(players);
		try { 
			output.writeObject(myArr);
		} catch (IOException ioException) {
			System.err.println("Error writing to file.");
		}finally{
			output.close();
		}
	}

	/*
	* Opens players.ser and and calls readObject on ObjectInputStream which then returns the
	* ArrayList<Players> with arbitrary Players.
	* As with openAddRecordsandCloseFiletoWrite() it closes automatically.
	*/
	public void openReadandWriteFileToRead() {
		try {
			input = new ObjectInputStream(new FileInputStream("players.ser"))
		} catch (IOException ioException) {
			System.err.println("Error opening file.");
		}
	}

	public void readRecords(ObjectInputStream input) {
		try{
			Object obj = null;
			obj = input.readObject();
			if(obj instanceof ArrayList){
				myArr = (ArrayList<Players>) obj;
			}
		}catch (Exception e) {
			System.err.println("No Players object reside in the file 'players.ser'");
			e.printStackTrace()
		}finally{
			input.close();
		}
	}

	public void printAndSortScoreBoard() {
		Collections.sort(myArr,new ScoreComparator());

		System.out.println("Scoreboard:");
		int position = 1;
		for(Players p: myArr){
			System.out.printf("%d. %s ----> %d\n", position, p.getName(), p.getScores());
			position++;
		}
	}
	
}


/*
Design method options:
1. Resources methods which opens and reads/writes
2. Resources which calls reads/read methods
3. Independent methods which also have a finally block in reading/writing for closing streams.
*/