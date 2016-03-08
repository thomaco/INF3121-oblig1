package hangman1;
import hangman.FileReadWriter;
import hangman.Players;


import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.nio.file.Files;

/*
 * 1.This test only for filereadwriter
 * 2. In integration testing test this with players.java and Game.java(not this file
 */
public class FileReadWriterTest {
	
	@Test  
	public void FileReadWriterObjectOutputStreamException(){
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(outContent));
		File file = new File("players.ser");
		if(file.exists())
			file.delete();
		file.mkdir();
		FileReadWriter opener = new FileReadWriter();
		opener.openFileToWite();
		assertEquals("Error opening file.\n", outContent.toString());
	}

	@Test
	public void testFileReadWriter(){
		//to ensure that the file players.ser isn´t there by chance or in other format
		File temp = new File("players.ser");
		if(temp.exists())
			temp.delete();
		
		FileReadWriter opener = new FileReadWriter();
		//#1 - checks if the file players.ser is established as a file
		opener.openFileToWite();
		opener.closeFileFromWriting();
		File f = new File("players.ser");
		//System.out.println("Working Directory = " +System.getProperty("user.dir"));
		assertEquals(f.exists(),true);
		assertEquals(f.isFile(),true);
		assertEquals(f.getName(),"players.ser");
		
		//flashes the players.ser file
		try {
			FileWriter fw = new FileWriter(f,false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//#2 checks if a player is written to file with attributes
		//##### This can be classified as an integration test   ##### ref: http://istqbexamcertification.com/what-is-integration-testing/
		opener.openFileToWite();
		opener.addRecords(100, "Sebastian");
		opener.closeFileFromWriting();
		try {
			Players p;
			Object obj;
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("players.ser"));
			if (!(obj=ois.readObject()).equals(null)){
				if (obj instanceof Players){
					p = (Players) obj;
					assertEquals(p.getName(),"Sebastian");
					assertEquals(100,p.getScores());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//##### This can be classified as an integration test   #####
		opener.nop();
		
	}
	
}




