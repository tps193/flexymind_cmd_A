package com.example.forestgame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.content.Context;

public class ScoresTable {

    private static final float HIGHSCORES_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 17 / 120;
    private static final float HIGHSCORES_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 8 / 64;
    private static final float HIGHSCORES_WIDTH = MainActivity.TEXTURE_WIDTH * 286 / 400;
    private static final float HIGHSCORES_HEIGHT = MainActivity.TEXTURE_HEIGHT * 10 / 128;
    private static final float SCORES_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 1 / 5;
    private static final float SCORES_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 19 / 64;

    private final int NUMBER_OF_SCORES = 6;
    private long[] scores = new long[NUMBER_OF_SCORES];
    
    private Text scoresText;
    private Sprite caption;
    

//    public void createFile() {
    
//		try {
//		    FileOutputStream fout = MainActivity.mainActivity.getApplicationContext().openFileOutput("scores",0);
//		    fout.close();
//		} 
//		catch (FileNotFoundException e) {
//	    	    e.printStackTrace();
//		}
//		catch (IOException e) {
//		    e.printStackTrace();
//		}
		
//	}

    public void init() {
	try {
	    FileInputStream fin = MainActivity.mainActivity.getApplicationContext().openFileInput("scores");
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
	MainScene.getScoresScene().detachChild(caption);
	MainScene.getScoresScene().detachChild(scoresText);
	for (int i = 0; i < scores.length - 1; i++) {
	    str += Integer.toString(i + 1) + ".  " + Long.toString(scores[i]) + "\n\n";
	}
	caption = new Sprite(HIGHSCORES_POSITION_LEFT,
		HIGHSCORES_POSITION_UP,
		HIGHSCORES_WIDTH,
		HIGHSCORES_HEIGHT,
		MainActivity.mainActivity.textureHighScoresL,
		MainActivity.mainActivity.getVertexBufferObjectManager());

	scoresText = new Text(SCORES_POSITION_LEFT, SCORES_POSITION_UP,
		MainActivity.mainActivity.tScoresSceneScores, str,
		MainActivity.mainActivity.getVertexBufferObjectManager());

	MainScene.getScoresScene().attachChild(caption);
	MainScene.getScoresScene().attachChild(scoresText);
    }

    public void save() {
	try {
	    FileOutputStream fout = MainActivity.mainActivity.getApplicationContext().openFileOutput("scores",Context.MODE_PRIVATE);
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
    
    public void saveResult(long result){
//	createFile();
	init();
	scores[NUMBER_OF_SCORES - 1] = result;
	sort();
	save();
    }
}
