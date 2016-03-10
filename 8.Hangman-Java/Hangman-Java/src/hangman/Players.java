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
	public int compare(Players p1, Players p2) {
		return p1.getScores()-p2.getScores();
	}
}
