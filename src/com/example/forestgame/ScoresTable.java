package com.example.forestgame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.andengine.entity.text.Text;

import android.os.Environment;

public class ScoresTable {

    private static final float HIGHSCORES_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 1 / 4;
    private static final float HIGHSCORES_POSITION_UP = MainActivity.TEXTURE_HEIGHT / 6;
    private static final float SCORES_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 1 / 4;
    private static final float SCORES_POSITION_UP = MainActivity.TEXTURE_HEIGHT / 3;

    private final int NUMBER_OF_SCORES = 6;
    private final String FILEPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TheNutsKing";
    private final String FILENAME = "/scores.txt";
    private long[] scores = new long[NUMBER_OF_SCORES];

    public void createFile() {
	File f = new File(FILEPATH);
	if (!f.exists())
	    try {
		f.mkdirs();
		f = new File(FILEPATH + FILENAME);
		f.createNewFile();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
    }

    public void init() {
	try {
	    FileInputStream fin = new FileInputStream(FILEPATH + FILENAME);
	    DataInputStream din = new DataInputStream(fin);
	    for (int i = 0; i < scores.length - 1; i++) {
		scores[i] = din.readLong();
	    }
	    din.close();
	    fin.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void show() {
	String str = "";

	for (int i = 0; i < scores.length - 1; i++) {
	    str += Integer.toString(i + 1) + ".  " + Long.toString(scores[i]) + "\n";
	}

	Text caption = new Text(HIGHSCORES_POSITION_LEFT,
		HIGHSCORES_POSITION_UP, MainActivity.mainActivity.tCaptions,
		"High Scores \n\n\n",
		MainActivity.mainActivity.getVertexBufferObjectManager());

	Text scoresText = new Text(SCORES_POSITION_LEFT, SCORES_POSITION_UP,
		MainActivity.mainActivity.tDevNames, str,
		MainActivity.mainActivity.getVertexBufferObjectManager());

	MainScene.getScoresScene().attachChild(caption);
	MainScene.getScoresScene().attachChild(scoresText);

    }

    public void save() {
	try {
	    FileOutputStream fout = new FileOutputStream(FILEPATH + FILENAME);
	    DataOutputStream dos = new DataOutputStream(fout);
	    for (int i = 0; i < scores.length - 1; i++) {
		dos.writeLong(scores[i]);
	    }
	    dos.flush();
	    dos.close();
	    fout.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void addScores(long result) {
	scores[NUMBER_OF_SCORES - 1] = result;
    }

    public void sort() {
	for (int i = 0; i < scores.length - 1; i++) {
	    for (int j = i + 1; j < scores.length; j++) {
		if (scores[i] < scores[j]) {
		    long tmp = scores[i];
		    scores[i] = scores[j];
		    scores[j] = tmp;
		}
	    }
	}
    }
}
