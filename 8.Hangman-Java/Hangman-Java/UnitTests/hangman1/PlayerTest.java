package hangman1;
import hangman.Players;

import org.junit.Test;
import static org.junit.Assert.*;


public class PlayerTest {

	@Test
	 public void testPlayerVariablesInitializationDeclarationValues() {
		Players p1 = new Players("Sebastian",Integer.MAX_VALUE);
		assertEquals("Sebastian",p1.getName());
		assertEquals(Integer.MAX_VALUE,p1.getScores());
		p1 = new Players("Sebastian",-100);
		assertEquals(-100,p1.getScores());
	}
}
