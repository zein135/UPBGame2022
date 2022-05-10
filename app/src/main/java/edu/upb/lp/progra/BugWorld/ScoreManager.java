package edu.upb.lp.progra.BugWorld;

import edu.upb.lp.progra.adapterFiles.TextListener;

public class ScoreManager implements TextListener {
	private BugWorldUI ui;
	private int highScore;
	private String highScoreName;
	
	public ScoreManager (BugWorldUI bugWorldUI, int highScore, String highScoreName) {
		this.ui = bugWorldUI;
		this.highScore = highScore;
		this.highScoreName = highScoreName;
	}
	
	@Override
	public void receiveText(String name) {
		highScoreName = name;
		ui.storeHighScoreName(name);
		ui.endGame(highScore, highScoreName);
	}

	public void checkHighScore(int score) {
		if (score > highScore) {
			highScore = score;
			ui.storeHighScore(score);
		} else {
			ui.endGame(highScore, highScoreName);
		}
	}

}
