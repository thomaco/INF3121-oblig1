package hangman;

import java.io.Serializable;

public class Players implements Serializable, Comparable<Players>{
	
	private String name;
	private int scores;

	public Players(String name, int scores) {
		this.name = name;
		this.scores = scores;
	}
	
	public String getName() {
		return name;
	}

	public int getScores() {
		return scores;
	}

	@Override
	public int compareTo(Players p1) {
		return this.getScores()-p1.getScores();
	}
}
