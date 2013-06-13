package com.example.forestgame;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationByModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseQuartIn;
import org.andengine.util.modifier.ease.IEaseFunction;

import android.graphics.Color;
import android.util.Log;

import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;

public class SlotMatrix {
    
    private Slot[][] matrix;
    private static final int ROWS = 6;
    private static final int COLUMNS = 6;
    private int lastEditedSlotRow;
    private int lastEditedSlotColumn;
    private static int NUMBER_OF_ElEMENTS_ON_START = 24;
    private int score;
    private int filledSlots;
    private GameScene gameScene;
    
    private final static float SLOT_WIDTH = MainActivity.TEXTURE_WIDTH/8;
    private final static float SLOT_HEIGHT = MainActivity.TEXTURE_HEIGHT/13;
    private final static int FIRST_SLOT_POSITION_LEFT = 96;
    private final static int FIRST_SLOT_POSITION_UP = 218;
    private final static float BORDER_WIDTH = 24;
    private final static float BORDER_HEIGHT = 26;
    
    private LinkedList<SlotWithFlyingSquirrel> slotsWithFlyingSquirrels = new LinkedList<SlotWithFlyingSquirrel>();
    private LinkedList<SlotWithForester> slotsWithForesters = new LinkedList<SlotWithForester>();
    private LinkedList<SlotPosition> lastEditedSlots = new LinkedList<SlotPosition>();
    
    
    ParallelEntityModifier entityModifier;
    float animationDuration = 0.4f;
    float animationDurationText = 0.8f;
    float fromAlpha = 1.0f;
    float toAlpha = 0.3f;
    float toAlphaScores = 0.0f;
    IEaseFunction easeFunction = EaseLinear.getInstance();
    IEaseFunction easeFunctionAlpha = EaseQuartIn.getInstance();
    TimerHandler spriteTimerHandler;
    float disappearAlpha = 0.0f;
    float appearAlpha = 1.0f;
    
    private int currentScore;
    
    private static final Random randomGenerator = new Random();
    
    
    public SlotMatrix(GameScene scene) {
	
	gameScene = scene;
	matrix = new Slot[ROWS][COLUMNS];
	init();
	viewSlots();
    }
    
    public void putToSlot( Element element
	    		 , int row
	    		 , int col) {
	
	if (isSlotEmpty(row, col)) {
	    
	    if (element.getName().equals("FORESTER")) {
		
		addForesterToSlot(row, col);
		MainActivity.mainActivity.mStep.play();
	    } else if (element.getName().equals("DROP")) {
		
		addDropToSlot(row, col);
		lastEditedSlotRow = row;
		lastEditedSlotColumn = col;
		MainActivity.mainActivity.mDrop.play(); 
	    } else if (element.getName().equals("FLYING_SQUIRREL")) {
		    
		addFlyingSquirrelToSLot(row, col);
	    } else {
		
		addToSlot(element, row, col);
		MainActivity.mainActivity.mStep.play();
		lastEditedSlotRow = row;
		lastEditedSlotColumn = col;
	    }
	    
	} else if (element.getName().equals("MAGIC_STICK")) {
	    
	    addMagicStickToSlot(row, col);
	    MainActivity.mainActivity.mMagic.play();
	}
	
	update();
    }
    
    // has to be used always before using addToSLot(..)
    public boolean isSlotEmpty( int row
	    		      , int col) {
	
	return matrix[row][col].isEmpty();
    }
    

    private void update() {
	
	disappearFlyingSquirrels();
	viewSlots();
	currentScore = 0;
	checkSimilarElements();
	score = score + currentScore;
	gameScene.setScores(getScore());
	showScoreToast(lastEditedSlotRow, lastEditedSlotColumn, currentScore);
	moveForesters();
	appearFlyingSquirrels();
	
				
	MainActivity.mainActivity.getEngine().registerUpdateHandler(new TimerHandler(animationDuration
		, false
		, new ITimerCallback() {

		    @Override
		    public void onTimePassed(TimerHandler pTimerHandler) {
			// TODO Auto-generated method stub
			while (!lastEditedSlots.isEmpty()) {
			    
			    SlotPosition s = lastEditedSlots.getFirst();
			    lastEditedSlots.removeFirst();
			    lastEditedSlotRow = s.getRow();
			    lastEditedSlotColumn = s.getColumn();
			    currentScore = 0;
			    checkSimilarElements();
			    
			    score = score + currentScore;
			    gameScene.setScores(getScore());
			    showScoreToast(lastEditedSlotRow, lastEditedSlotColumn, currentScore);
			}
			viewSlots();
			filledSlots = 0;
			    for (int i = 0; i < ROWS; i++) {
				    
				for (int j = 0; j < COLUMNS; j++) {
					
				    if (!isSlotEmpty(i, j)) {
					    
					filledSlots++;
				    }
				}
			    }
			    if (filledSlots == ROWS*COLUMNS) {
				    
				Log.d("GAME", "OVER");
				MainScene.showGameOverScene();
			    } else {
				   
				TableOfElements.renewAvaliableRandomElements(score);
			    }
			
		    }
		}));
    }
    
    public void init() {
	
	score=0;
	TableOfElements.renewAvaliableRandomElements(score);
	for (int i = 0; i < ROWS; i++) {
	    
	    for (int j = 0; j < COLUMNS; j++) {
		
		matrix[i][j] = new Slot();
	    }
	}
	Random random = new Random();
	for (int i = 0; i < NUMBER_OF_ElEMENTS_ON_START; i++) {
	    
	    int row = (int) (random.nextDouble() * ROWS);
	    int col = (int) (random.nextDouble() * COLUMNS);
	    if (isSlotEmpty(row, col)) {
		
		Element element = TableOfElements.getRandomElement();
		if (element.getName().equals("FORESTER")) {
		    
		    addForesterToSlot(row, col);
		} else {
		    
		    addToSlot(element, row, col);
		}
								     
	    } else {
		
		i--;
	    }
	}
	gameScene.setScores(score);
    }
    
    public int getScore() {
	
	return score;
    }
    
    public static float getSlotPositionLeft(int col) {
	return FIRST_SLOT_POSITION_LEFT + col * (SLOT_WIDTH + BORDER_WIDTH);
    }
    
    public static float getSlotPositionUp(int row) {
	return FIRST_SLOT_POSITION_UP + row * (SLOT_HEIGHT + BORDER_HEIGHT);
    }
    
    public static float getSlotWidth() {
	return SLOT_WIDTH;
    }
    
    public static float getSlotHeight() {
	return SLOT_HEIGHT;
    }
    
    
    public void reInit() {
        
	for (int i = 0; i < ROWS; i++) {
	    
            for (int j = 0; j < COLUMNS; j++) {
        	
        	clearSlot(i, j);
            }
        }
	
	slotsWithFlyingSquirrels.clear();
	slotsWithForesters.clear();
	init();
	viewSlots();
    }
    
    public String[][] getNamesForSave() {
	
	String[][] namesMatrix = new String[ROWS][COLUMNS];
	
	for(int i = 0; i < ROWS; i++) {
	    for(int j = 0; j < COLUMNS; j++) {
		if(matrix[i][j].isEmpty()) namesMatrix[i][j] = null;
		else namesMatrix[i][j] = matrix[i][j].getElement().getName();
	    }
	}
	
	return namesMatrix;
    }
    
    
    public void loadInit(Object obj, int scores) throws IOException {
	
	for (int i = 0; i < ROWS; i++) {
	    
            for (int j = 0; j < COLUMNS; j++) {
        	
        	clearSlot(i, j);
            }
        }
	slotsWithFlyingSquirrels.clear();
	slotsWithForesters.clear();
	setMatrix((String[][]) obj);
	this.score = scores;
	TableOfElements.renewAvaliableRandomElements(score);
	viewSlots();
	
    }
    
    
    private void setMatrix(String[][] namesMatrix) {
	
        for(int i = 0; i < ROWS; i++) {
            
            for(int j = 0; j < COLUMNS; j++) {
        	
        	if(namesMatrix[i][j] == null) {
        	    
        	    matrix[i][j] = new Slot();
        	} else { 
        	    
        	    matrix[i][j] = new Slot();
        	    addToSlot(new Element(namesMatrix[i][j]), i, j);
        	    if (namesMatrix[i][j].equals("FORESTER")) {
        		
        		slotsWithForesters.add(new SlotWithForester(i, j));
        	    } else if (namesMatrix[i][j].equals("FLYING_SQUIRREL")) {
        		
        		slotsWithFlyingSquirrels.add(new SlotWithFlyingSquirrel(i, j));
        	    }
        	}
            }
        } 
    }

    // method for visualizing textures on GameScene
    private void viewSlots() {
	
	for (int i = 0; i < ROWS; i++) {
	    
	    for (int j = 0; j < COLUMNS; j++) {

		final Slot slot = matrix[i][j];
		gameScene.detachChild(slot.getSprite());
		gameScene.unregisterTouchArea(slot.getSprite());
                if (!slot.isEmpty()) {
                    
                   TextureRegion slotTexture = MainActivity.mainActivity.storage.getTexture(
                	   TableOfElements. getTextureName(slot.getElement()));

                   Sprite slotSprite = new Sprite (getSlotPositionLeft(j)
                                           , getSlotPositionUp(i)
                                           , SLOT_WIDTH
                                           , SLOT_HEIGHT
                                           , slotTexture
                                           , MainActivity.mainActivity.getVertexBufferObjectManager()) {
                       
                       	@Override
       			public boolean onAreaTouched( TouchEvent pSceneTouchEvent
       			    			, float pTouchAreaLocalX
       			    			, float pTouchAreaLocalY) {
                       	    if (pSceneTouchEvent.isActionDown()) {
       			
                       		slotIsActionDown(slot.getElement());
       			
                       	    } else if (pSceneTouchEvent.isActionUp()) {
       			    
                       		slotIsActionUp();
       			    
                       	    } else if (pSceneTouchEvent.isActionMove()) {
       			    
                       		
       			
                       	    }
                       	    return true;
                       	}
                   };
                   
                   slot.setSprite(slotSprite);

                   gameScene.attachChild(slot.getSprite());
                   gameScene.registerTouchArea(slotSprite);
		   slot.getSprite().setZIndex(300);
		   slot.getSprite().getParent().sortChildren();
                } 
	    }
	}
    }
    
      
    private void clearSlot(int row, int col) {
	
	 Slot s = matrix[row][col];
	 gameScene.unregisterTouchArea(s.getSprite());
	 gameScene.detachChild(s.getSprite());
	 matrix[row][col] = new Slot();
    }
    
    // putting Element into Slot and changing flags if needed
    private void addToSlot(Element element, int row, int col) {
	
	
	matrix[row][col].addElement(element);
	if (row > 0) {
	    
	    analyzeNeighbor(row, col, row-1, col);
	}
	if (row < ROWS-1) {
	    
	    analyzeNeighbor(row, col, row+1, col);
	}
	if (col > 0) {
	    
	    analyzeNeighbor(row, col, row, col-1);
	}
	if (col < COLUMNS-1) {
	    
	    analyzeNeighbor(row, col, row, col+1);
	}
    }
    
    private void addMagicStickToSlot(int row, int column) {
	
	Element element = matrix[row][column].getElement();
	currentScore = TableOfElements.getScores(element);
	score = score + currentScore;
	gameScene.setScores(getScore());
	showScoreToast(row, column, currentScore);
	if (element.getName().equals("FORESTER")) {
	    
	    catchForester(row, column);
	} else if (element.getName().equals("FLYING_SQUIRREL")) {
	    
	    for (SlotWithFlyingSquirrel slotWFS : slotsWithFlyingSquirrels) {
		if (matrix[slotWFS.getRow()][slotWFS.getColumn()].equals(matrix[row][column])) {
		    slotsWithFlyingSquirrels.remove(slotWFS);
		    break;
		}
	    }
	    element.changeToNextLvl();
	    clearSlot(row, column);
	    addToSlot(element, row, column);
	    lastEditedSlotRow = row;
	    lastEditedSlotColumn = column;
	} else {
	    
	    if (row > 0) {
		    
		if (matrix[row-1][column].isSimilarTo(element)) {
		    matrix[row-1][column].reduceNeighbor();
		}
	    }
	    if (row < ROWS-1) {
			    
		if (matrix[row+1][column].isSimilarTo(element)) {
		    matrix[row+1][column].reduceNeighbor();
		}
	    }
	    if (column > 0) {
			    
		if (matrix[row][column-1].isSimilarTo(element)) {
		    matrix[row][column-1].reduceNeighbor();
		}
	    }
	    if (column < COLUMNS-1) {
			    
		if (matrix[row][column+1].isSimilarTo(element)) {
		    matrix[row][column+1].reduceNeighbor();
		}
	    }
	    clearSlot(row, column);
	}
	
	
    }
    
    private void addDropToSlot(int row, int column) {
	
	LinkedList<Slot> slots = new LinkedList<Slot>();
	if (row != 0) {
	    if ( !matrix[row-1][column].isEmpty() ) {
		if (!matrix[row-1][column].getElement().getName().equals("FORESTER")  
		&& (!matrix[row-1][column].getElement().getName().equals("FLYING_SQUIRREL"))) {
		    
		    slots.add(matrix[row-1][column]); 
		}
	    }
	}
	if (row != ROWS-1) {
	    if ( !matrix[row+1][column].isEmpty() ) {
		if (!matrix[row+1][column].getElement().getName().equals("FORESTER")  
		&& (!matrix[row+1][column].getElement().getName().equals("FLYING_SQUIRREL"))) {
			    
		    slots.add(matrix[row+1][column]); 
		}
	    }
	}
	if (column != 0) {
	    if ( !matrix[row][column-1].isEmpty() ) {
		if (!matrix[row][column-1].getElement().getName().equals("FORESTER")  
		&& (!matrix[row][column-1].getElement().getName().equals("FLYING_SQUIRREL"))) {
			    
		    slots.add(matrix[row][column-1]); 
		}
	    }
	}
	if (column != COLUMNS-1) {
	    if ( !matrix[row][column+1].isEmpty() ) {
		if (!matrix[row][column+1].getElement().getName().equals("FORESTER")  
		&& (!matrix[row][column+1].getElement().getName().equals("FLYING_SQUIRREL"))) {
			    
		    slots.add(matrix[row][column+1]); 
		}
	    }
	}
	
	filterSlotsLinkedList(slots);
	if ( !slots.isEmpty() ) {
	    
	    Element element = bestElementToAdd(slots.getFirst().getElement(), slots);
	    addToSlot(element, row, column);
	    
	} else {
	    
	    addToSlot( new Element("POND"), row, column);
	}
	//addToSlot(gameScene.getBestElementForDropAdd(), row, column);
    }
    
    public void filterSlotsLinkedList(LinkedList<Slot> slots) {
	
	if (slots.isEmpty()){
	    
	    return;
	}
	
	for (int i = 0; i < slots.size(); i++) {
	    for (int j = i + 1; j < slots.size(); j++) {
		if (slots.get(i).getScore() < slots.get(j).getScore()) {
		    Slot s = slots.get(i);
		    slots.set(i, slots.get(j));
		    slots.set(j, s);
		}
	    }
	}
	
	for (int i = 0; i < slots.size()-1; i++) {
	    Slot s = slots.get(i);
	    if ( !(s.getHasSimilarNeighbor() 
		    || s.isSimilarTo(slots.get(i+1).getElement()))) {
		
		slots.remove(i);
		i--;
	    }
	}
	if (!slots.getLast().getHasSimilarNeighbor()) {
	    slots.removeLast();
	}
    }
    
    public Element bestElementToAdd(Element currentBestElement, LinkedList<Slot> slots) {
	
	for (Slot s : slots) {
		
	    Element e = s.getElement();
	    if (TableOfElements.getNextLvl(e).equals(currentBestElement.getName())) {
	
		return bestElementToAdd(e, slots);
	    } 
	}
	return currentBestElement;
    }
    
    private void addFlyingSquirrelToSLot(int row, int column) {
	
	Slot slot = matrix[row][column];
	slot.addElement(new Element("FLYING_SQUIRREL"));
	slotsWithFlyingSquirrels.add(new SlotWithFlyingSquirrel(row, column));
    }
    
    private void disappearFlyingSquirrels() {
	
	for (SlotWithFlyingSquirrel slotWFS : slotsWithFlyingSquirrels) {
	    
	    disappearFlyingSquirrel(slotWFS);
	}
    }
    
    private void appearFlyingSquirrels() {
	
	int numberFS = slotsWithFlyingSquirrels.size();
	slotsWithFlyingSquirrels.clear();
	for (int i = 0; i < numberFS; i++) {
	    
	    appearFlyingSquirrel();
	}
    }
    
    private void disappearFlyingSquirrel(SlotWithFlyingSquirrel slotWFS) {
	
	int row = slotWFS.getRow();
	int col = slotWFS.getColumn();
	clearSlot(slotWFS.getRow(), slotWFS.getColumn());
	entityModifier = new ParallelEntityModifier(new AlphaModifier(animationDuration
			, appearAlpha
			, disappearAlpha
			, easeFunction));


	TextureRegion slotTexture = MainActivity.mainActivity.storage.getTexture(
					TableOfElements.getTextureName(new Element("FLYING_SQUIRREL")));

	final Sprite animationSprite = new Sprite ( getSlotPositionLeft(col)
							, getSlotPositionUp(row)
							, SLOT_WIDTH
							, SLOT_HEIGHT
							, slotTexture
							, MainActivity.mainActivity.getVertexBufferObjectManager());

	gameScene.attachChild(animationSprite);

	animationSprite.registerEntityModifier(entityModifier);

	MainActivity.mainActivity.getEngine().registerUpdateHandler(
		spriteTimerHandler = new TimerHandler(animationDuration
							, false
							, new ITimerCallback() {

		    @Override
		    public void onTimePassed(TimerHandler pTimerHandler) {
			// TODO Auto-generated method stub
			gameScene.detachChild(animationSprite);
		    }
		}));
    }
    
    private void appearFlyingSquirrel(){
	
	int r = randomGenerator.nextInt(ROWS*COLUMNS - filledSlots);
	for (int row = 0; row < ROWS; row++) {
	    for (int col = 0; col < COLUMNS; col++) {
		if (matrix[row][col].isEmpty()) {
		    if (r == 0) {
			addFlyingSquirrelToSLot(row, col);
			entityModifier = new ParallelEntityModifier(new AlphaModifier(animationDuration
				    						, disappearAlpha
				    						, appearAlpha
				    						, easeFunction));

			TextureRegion slotTexture = MainActivity.mainActivity.storage.getTexture(
							TableOfElements.getTextureName(new Element("FLYING_SQUIRREL")));

			final Sprite animationSprite = new Sprite ( getSlotPositionLeft(col)
								, getSlotPositionUp(row)
								, SLOT_WIDTH
								, SLOT_HEIGHT
								, slotTexture
								, MainActivity.mainActivity.getVertexBufferObjectManager());

			gameScene.attachChild(animationSprite);

			animationSprite.registerEntityModifier(entityModifier);

			MainActivity.mainActivity.getEngine().registerUpdateHandler(
				spriteTimerHandler = new TimerHandler(animationDuration
									, false
									, new ITimerCallback() {

				    @Override
				    public void onTimePassed(TimerHandler pTimerHandler) {
					// TODO Auto-generated method stub
					gameScene.detachChild(animationSprite);
				    }
				}));
			
			return;
		    } else {
			r--;
		    }
		}
	    }
	}
    }
    
    private void addForesterToSlot(int row, int column) {
	
	Slot slot = matrix[row][column];
	slot.addElement(new Element("FORESTER"));
	slotsWithForesters.add(new SlotWithForester(row, column));
    }
    
    private void catchForester(int row, int column) {
	
	Slot slot = matrix[row][column];
	for (SlotWithForester slotWF : slotsWithForesters) {
	    if (matrix[slotWF.getRow()][slotWF.getColumn()].equals(slot)) {
		slotsWithForesters.remove(slotWF);
		break;
	    }
	}
	transformForesterIntoNextLevel(row, column);
    }
    
   
    private void moveForesters() {
	
	if (slotsWithForesters.isEmpty()) {
	    
	    return;
	}
	for (SlotWithForester slotWF : slotsWithForesters) {
	    
	    slotWF.setHasAlreadyMoved(false);
	}
	LinkedList<SlotWithForester> newList = new LinkedList<SlotWithForester>(); 
		
	boolean oneHasMoved = true;
	while (oneHasMoved) {
	    
	    oneHasMoved = false;
	    for (SlotWithForester slotWF : slotsWithForesters) {
		
		if (!slotWF.getHasAlreadyMoved()) {
		    
		    boolean hasMoved = foresterTriesToMove(slotWF);
		    if (hasMoved) {
			    
			newList.add(slotWF);
			oneHasMoved = true;
		    }
		}
	    }
	}
	
	for (SlotWithForester slotWF : slotsWithForesters) {
	    
	    if (!slotWF.getHasAlreadyMoved()) {
		
		transformForesterIntoNextLevel(slotWF.getRow(), slotWF.getColumn());
		currentScore = TableOfElements.getScores(new Element("FORESTER"));
		score = score + currentScore;
		gameScene.setScores(getScore());
		showScoreToast(slotWF.getRow(), slotWF.getColumn(), currentScore);
	    }
	}	
	
	slotsWithForesters.clear();
	for (SlotWithForester slotWF : newList) {
	    
	    slotsWithForesters.add(slotWF);
	}	
	
    }
   
    
    
    
    private void transformForesterIntoNextLevel(int row, int column) {
	
	Element element = matrix[row][column].getElement();
	element.changeToNextLvl();
	clearSlot(row, column);
	addToSlot(element, row, column);
	graphicalMoving(row, column, row, column);
	lastEditedSlots.add(new SlotPosition(row, column));
	MainActivity.mainActivity.mSound.play();
    }
    
    private boolean foresterTriesToMove(SlotWithForester slotWF) {
	
	int row = slotWF.getRow();
	int column = slotWF.getColumn();
	LinkedList<SlotWithForester> possibleSlots = new LinkedList<SlotWithForester>();
	if (row != 0) {
	    if (matrix[row-1][column].isEmpty()) {
		
		possibleSlots.add(new SlotWithForester(row-1, column));
	    }
	}
	if (row != ROWS-1) {
	    if (matrix[row+1][column].isEmpty()) {
		
		possibleSlots.add(new SlotWithForester(row+1, column));
	    }
	}
	if (column != 0) {
	    if (matrix[row][column-1].isEmpty()) {
		
		possibleSlots.add(new SlotWithForester(row, column-1));
	    }
	}
	if (column != COLUMNS-1) {
	    if (matrix[row][column+1].isEmpty()) {
		
		possibleSlots.add(new SlotWithForester(row, column+1));
	    }
	}
	
	if (possibleSlots.isEmpty()) {
	    
	    return false;
	} else {
	    
	    SlotWithForester newSlotWF = possibleSlots.get(
		    randomGenerator.nextInt(possibleSlots.size()));
	    clearSlot(row, column);
	    foresterGraphicalMoving(row, column, newSlotWF.getRow(), newSlotWF.getColumn());
	    
	    slotWF.foresterMoveTo(newSlotWF.getRow(), newSlotWF.getColumn());
	    
	    
	    
	    matrix[newSlotWF.getRow()][newSlotWF.getColumn()].addElement(new Element("FORESTER"));
	    return true;
	}
    }
    
    
    
    private void foresterGraphicalMoving( int fromRow
	    				, int fromColumn
	    				, int toRow
	    				, int toColumn) {
	
	entityModifier = new ParallelEntityModifier(new MoveModifier(animationDuration
							, getSlotPositionLeft(fromColumn)
							, getSlotPositionLeft(toColumn)
							, getSlotPositionUp(fromRow)
							, getSlotPositionUp(toRow)
							, easeFunction)
						, new SequenceEntityModifier(new RotationByModifier(animationDuration/4, 10)
									   , new RotationByModifier(animationDuration/2, -20)
									   , new RotationByModifier(animationDuration/4, 10)));

	TextureRegion slotTexture = MainActivity.mainActivity.storage.getTexture(
					TableOfElements.getTextureName(new Element("FORESTER")));

	final Sprite animationSprite = new Sprite ( getSlotPositionLeft(fromColumn)
						, getSlotPositionUp(fromRow)
						, SLOT_WIDTH
						, SLOT_HEIGHT
						, slotTexture
						, MainActivity.mainActivity.getVertexBufferObjectManager());

	gameScene.attachChild(animationSprite);

	animationSprite.registerEntityModifier(entityModifier);

	MainActivity.mainActivity.getEngine().registerUpdateHandler(
					spriteTimerHandler = new TimerHandler(animationDuration
					, false
					, new ITimerCallback() {

					    @Override
					    public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						gameScene.detachChild(animationSprite);
					    }
					}));
    }
    
    // setting hasSimilarNeighbor and readyForNextLevel flags
    // flag readyForNextLevel doesn't have to be set for every Slot in chain, only for the last edited
    private void analyzeNeighbor( int thisRow
	    			, int thisCol
	    			, int neighborRow
	    			, int neightborCol) {
	
	Slot s = matrix[thisRow][thisCol];
	Slot s1 = matrix[neighborRow][neightborCol];
	if (s1.isSimilarTo(s.getElement())) {
	    
	    if (s.getHasSimilarNeighbor()) {
		
		  s1.setReadyForNextLevel(true);
		  s.setReadyForNextLevel(true);
	    }
	    s.setHasSimilarNeighbor(true);
	    
	    if (s1.getHasSimilarNeighbor()) {
		
		s1.setReadyForNextLevel(true);
		s.setReadyForNextLevel(true);
	    } else {
		
		s1.setHasSimilarNeighbor(true);
	    }
	}
    }
    
    // checking if the last added element is the third (or more) and has to get next level 
    private void checkSimilarElements() {
	
	int currentRow = lastEditedSlotRow;
	int currentCol = lastEditedSlotColumn;
	if (matrix[currentRow][currentCol].getReadyForNextLevel()) {
	    
	    Slot slot = matrix[currentRow][currentCol];
	    Element element = slot.getElement();
	    currentScore = currentScore + matrix[currentRow][currentCol].getScore();
	    clearSlot(currentRow, currentCol);
	    gameScene.detachChild(matrix[currentRow][currentCol].getSprite());
	    
	    if (currentRow > 0) {
		
		if (matrix[currentRow-1][currentCol].isSimilarTo(element)) {
		    
		    collectSimilarElements(currentRow, currentCol, currentRow-1, currentCol, element);
		}
	    }
	    if (currentRow < ROWS-1) {
		
		if (matrix[currentRow+1][currentCol].isSimilarTo(element)) {
		    
		    collectSimilarElements(currentRow, currentCol, currentRow+1, currentCol, element);
		}
	    }
	    if (currentCol > 0) {
		
		if (matrix[currentRow][currentCol-1].isSimilarTo(element)) {
		    
		    collectSimilarElements(currentRow, currentCol, currentRow, currentCol-1, element);
		}
	    }
	    if (currentCol < COLUMNS-1) {
		
		if (matrix[currentRow][currentCol+1].isSimilarTo(element)) {
		    
		    collectSimilarElements(currentRow, currentCol, currentRow, currentCol+1, element);
		}
	    }
	    element.changeToNextLvl();
	    MainActivity.mainActivity.mSound.play();
	    addToSlot(element, currentRow, currentCol);
	    lastEditedSlotRow = currentRow;
	    lastEditedSlotColumn = currentCol;
	    checkSimilarElements();
	}
    }
    
    //recoursively collecting the chain of similar elements, removing them from field
    private void collectSimilarElements( int toRow
	    			       , int toCol
	    			       , int fromRow
	    			       , int fromCol
	    			       , Element element) {
	
	graphicalMoving( toRow
		       , toCol
		       , fromRow
		       , fromCol);
	
	currentScore = currentScore + matrix[fromRow][fromCol].getScore();
 	clearSlot(fromRow, fromCol);
 	
	if (fromRow > 0) {
	    
	    if (matrix[fromRow-1][fromCol].isSimilarTo(element)) {
		
		collectSimilarElements( toRow
				      , toCol
				      , fromRow-1
				      , fromCol
				      , element);
	    }
	}
	if (fromRow < ROWS-1) {
	    
	    if (matrix[fromRow+1][fromCol].isSimilarTo(element)) {
		
		collectSimilarElements( toRow
				      , toCol
				      , fromRow+1
				      , fromCol
				      , element);
	    }
	}
	if (fromCol > 0) {
	    
	    if (matrix[fromRow][fromCol-1].isSimilarTo(element)) {
		
		collectSimilarElements( toRow
			              , toCol
			              , fromRow
			              , fromCol-1
			              , element);
	    }
	}
	if (fromCol < COLUMNS-1) {
	    
	    if (matrix[fromRow][fromCol+1].isSimilarTo(element)) {
		
		collectSimilarElements( toRow
				      , toCol
				      , fromRow
				      , fromCol+1
				      , element);
	    }
	}
    }
    
    private void graphicalMoving(final int toRow, final int toCol, final int fromRow, final int fromCol) {
	
	entityModifier = new ParallelEntityModifier(new AlphaModifier(animationDuration
			    					    , fromAlpha
			    					    , toAlpha
			    					    , easeFunction)

						  , new MoveModifier(animationDuration
							  	   , getSlotPositionLeft(fromCol)
							  	   , getSlotPositionLeft(toCol)
							  	   , getSlotPositionUp(fromRow)
							  	   , getSlotPositionUp(toRow)
							  	   , easeFunction));
						  				
	
	Slot s = matrix[fromRow][fromCol];
	
	TextureRegion slotTexture = MainActivity.mainActivity.storage.getTexture(TableOfElements
                							       . getTextureName
                							       ( s.getElement()));

	final Sprite animationSprite = new Sprite ( getSlotPositionLeft(fromCol)
					   , getSlotPositionUp(fromRow)
					   , SLOT_WIDTH
					   , SLOT_HEIGHT
					   , slotTexture
					   , MainActivity.mainActivity.getVertexBufferObjectManager());
	
	gameScene.attachChild(animationSprite);
	
	animationSprite.registerEntityModifier(entityModifier);

	MainActivity.mainActivity.getEngine().registerUpdateHandler(
		spriteTimerHandler = new TimerHandler(animationDuration
						    , false
						    , new ITimerCallback() {
		    
		    					@Override
							public void onTimePassed(TimerHandler pTimerHandler) {
							// TODO Auto-generated method stub
		    					    gameScene.detachChild(animationSprite);
							}
						    }));
    }
    
    public String getElement(int row, int column){
	
	return matrix[row][column].getElement().getName();
    }
    
    public static int getROWS() {
	
	return ROWS;
    }
    
    public static int getCOLUMNS() {
	
	return COLUMNS;
    }
   
    public static int getPrisonPlaceRow() {
	
	return ROWS + 1;
    }
    
    
    public static int getPrisonPlaceColumn() {
	
	return COLUMNS + 1;
    }
    
    public static int getRespawnPlaceRow() {
	
	return ROWS + 2;
    }
    
    
    public static int getRespawnPlaceColumn() {
	
	return COLUMNS + 2;
    }
    
    public static int getMilkPointRow() {
	
	return ROWS + 20;
    }

    public static int getMilkPointColumn() {
	
	return COLUMNS + 20;
    }
    
    private void slotIsActionDown(Element element) {
	
	gameScene.attachHelpForElement(element);
    }
    
    private void slotIsActionUp() {
	
	gameScene.detachHelpForElement();
    }
    
    private class SlotPosition {
	
	private int row;
	private int column;
	
	public SlotPosition(int row, int column) {
	    
	    this.row = row;
	    this.column = column;
	}

	int getRow() {
	    
	    return row;
	}
	
	int getColumn() {
	    
	    return column;
	}
    }
    
    public Slot getSlot(int row, int col) {
	
	return matrix[row][col];
    }

    private void showScoreToast(final int row, final int col, final int showScore) {

	float offset = 0;
	if (showScore != 0){
	    if (showScore < 100)
		offset += SLOT_WIDTH/4;
	/*Font font = FontFactory.create( MainActivity.mainActivity.getFontManager(), 
					MainActivity.mainActivity.getTextureManager(), 
					256, 
					256, 
					Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD), 
					SLOT_WIDTH / 2,
					true, 
					Color.rgb(255, 110, 0));*/
	StrokeFont font = FontFactory.createStrokeFromAsset(MainActivity.mainActivity.getFontManager()
		,MainActivity.scoresToastAtlas 
		, MainActivity.mainActivity.getAssets()
		, "showg.ttf"
		, SLOT_WIDTH / 2
		, true
		, Color.rgb(255, 255, 0)
		, 4
		, Color.rgb(255, 143, 0));
	
	final Text text = new Text(0,0,font,Integer.toString(showScore), 10, MainActivity.mainActivity.getVertexBufferObjectManager());
	entityModifier = new ParallelEntityModifier(new AlphaModifier(animationDurationText
		    , fromAlpha
		    , toAlphaScores
		    , easeFunctionAlpha)

			, new MoveModifier(animationDurationText
	  	   , getSlotPositionLeft(col) + offset
	  	   , getSlotPositionLeft(col) + offset
	  	   , getSlotPositionUp(row)
	  	   , getSlotPositionUp(row-1) //- SLOT_HEIGHT/2
	  	   , easeFunction));
				
	font.load();
	text.setZIndex(1000);
	text.sortChildren();
	gameScene.attachChild(text);

	text.registerEntityModifier(entityModifier);

	MainActivity.mainActivity.getEngine().registerUpdateHandler(
		spriteTimerHandler = new TimerHandler(animationDurationText
							, false
							, new ITimerCallback() {

	@Override
	public void onTimePassed(TimerHandler pTimerHandler) {
	    gameScene.detachChild(text);
		}
	}));
	}
    }
    
}