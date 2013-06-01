package com.example.forestgame;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;

import android.util.Log;

public class HelpScene extends Scene {
    
    private static final float ARROW_LEFT_POSITION_LEFT = MainActivity.DISPLAY_WIDTH * 10 / 2000;
    private static final float ARROW_LEFT_POSITION_UP = MainActivity.DISPLAY_HEIGHT * 900 / 2000;
    private static final float ARROW_LEFT_WIDTH = MainActivity.DISPLAY_WIDTH * 100 / 2000;
    private static final float ARROW_LEFT_HEIGHT = MainActivity.DISPLAY_HEIGHT * 200 / 2000;
    
    private static final float ARROW_RIGHT_POSITION_LEFT = MainActivity.DISPLAY_WIDTH * 1890 / 2000;
    private static final float ARROW_RIGHT_POSITION_UP = MainActivity.DISPLAY_HEIGHT * 900 / 2000;
    private static final float ARROW_RIGHT_WIDTH = MainActivity.DISPLAY_WIDTH * 100 / 2000;
    private static final float ARROW_RIGHT_HEIGHT = MainActivity.DISPLAY_HEIGHT * 200 / 2000;
    
    private static final float ARROW_UP_POSITION_LEFT = MainActivity.DISPLAY_WIDTH * 900 / 2000;
    private static final float ARROW_UP_POSITION_UP = MainActivity.DISPLAY_HEIGHT * 10 / 2000;
    private static final float ARROW_UP_WIDTH = MainActivity.DISPLAY_WIDTH * 400 / 2000;
    private static final float ARROW_UP_HEIGHT = MainActivity.DISPLAY_HEIGHT * 50 / 2000;
    
    private static final float ARROW_DOWN_POSITION_LEFT = MainActivity.DISPLAY_WIDTH * 900 / 2000;
    private static final float ARROW_DOWN_POSITION_UP = MainActivity.DISPLAY_HEIGHT * 1940 / 2000;
    private static final float ARROW_DOWN_WIDTH = MainActivity.DISPLAY_WIDTH * 400 / 2000;
    private static final float ARROW_DOWN_HEIGHT = MainActivity.DISPLAY_HEIGHT * 50 / 2000;

    private static final float RECTANGLE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 200 / 2000;
    private static final float RECTANGLE_POSITION_UP = MainActivity.TEXTURE_WIDTH * 200 / 2000;
    private static final float RECTANGLE_WIDTH = MainActivity.TEXTURE_WIDTH * 1600 / 2000;
    private static final float RECTANGLE_HEIGHT = MainActivity.TEXTURE_WIDTH * 500 / 2000;
    
    private static final float RECTANGLES_INTERVAL = RECTANGLE_HEIGHT + MainActivity.TEXTURE_WIDTH * 20 / 2000;
    
    private static final float ARROW_POSITION_LEFT = RECTANGLE_POSITION_LEFT + MainActivity.TEXTURE_HEIGHT * 526 /2000;
    private static final float ARROW_POSITION_UP = RECTANGLE_POSITION_UP + MainActivity.TEXTURE_HEIGHT * 100 /2000;
    private static final float ARROW_WIDTH = MainActivity.TEXTURE_WIDTH * 256 / 2000;
    private static final float ARROW_HEIGHT = MainActivity.TEXTURE_HEIGHT * 100 / 2000;
    
    private static final float ELEMENT_MARGIN_LEFT = MainActivity.TEXTURE_WIDTH * 80 / 2000;
    private static final float ELEMENT_MARGIN_TOP = MainActivity.TEXTURE_HEIGHT * 50 / 2000;
    
    private static final float ELEMENT_WIDTH = MainActivity.TEXTURE_WIDTH * 325 / 2000;
    private static final float ELEMENT_HEIGHT = MainActivity.TEXTURE_HEIGHT * 200 / 2000;    
    
    private static final float SECOND_ELEMENT_DELTA_X = MainActivity.TEXTURE_WIDTH * 200 / 2000;
    private static final float SECOND_ELEMENT_DELTA_Y = MainActivity.TEXTURE_WIDTH * 100 / 2000;
    
    private static final float THIRD_ELEMENT_DELTA_X = MainActivity.TEXTURE_WIDTH * 200 / 2000;
    private static final float THIRD_ELEMENT_DELTA_Y = MainActivity.TEXTURE_WIDTH * (-200) / 2000;
    
    private static final float FIRST_ELEMENT_LEFT = RECTANGLE_POSITION_LEFT + ELEMENT_MARGIN_LEFT;
    private static final float FIRST_ELEMENT_UP = RECTANGLE_POSITION_UP + ELEMENT_MARGIN_TOP;
    
    private static final float SECOND_ELEMENT_LEFT = FIRST_ELEMENT_LEFT + SECOND_ELEMENT_DELTA_X;
    private static final float SECOND_ELEMENT_UP = FIRST_ELEMENT_UP + SECOND_ELEMENT_DELTA_Y;
    
    private static final float THIRD_ELEMENT_LEFT = SECOND_ELEMENT_LEFT + THIRD_ELEMENT_DELTA_X;
    private static final float THIRD_ELEMENT_UP = SECOND_ELEMENT_UP + THIRD_ELEMENT_DELTA_Y;
    
    private static final float NEXT_ELEMENT_LEFT = RECTANGLE_POSITION_LEFT + MainActivity.TEXTURE_HEIGHT * 716 /2000;
    private static final float NEXT_ELEMENT_UP = RECTANGLE_POSITION_UP + MainActivity.TEXTURE_HEIGHT * 50 /2000;
    
    
    /////////////
    private static final float ARROW_DELTA_LEFT = MainActivity.TEXTURE_HEIGHT * 526 /2000;
    private static final float ARROW_DELTA_UP = MainActivity.TEXTURE_HEIGHT * 100 /2000;
    
    private static final float SECOND_DELTA_LEFT = ELEMENT_MARGIN_LEFT + SECOND_ELEMENT_DELTA_X;
    private static final float SECOND_DELTA_UP = ELEMENT_MARGIN_TOP + SECOND_ELEMENT_DELTA_Y;
    
    private static final float THIRD_DELTA_LEFT = SECOND_DELTA_LEFT + THIRD_ELEMENT_DELTA_X;
    private static final float THIRD_DELTA_UP = SECOND_DELTA_UP + THIRD_ELEMENT_DELTA_Y;
    
    private static final float NEXT_DELTA_LEFT = MainActivity.TEXTURE_HEIGHT * 716 /2000;
    private static final float NEXT_DELTA_UP = MainActivity.TEXTURE_HEIGHT * 50 /2000;
    ////////////
    private static final int CARD_ON_LIST = 5;
    private static final int LIST_COUNT[] = {1, 0};
    private static final int ROOT_COUNT = 1;
    private int listNumber = 0;
    private int rootNumber = 0;
    private Element el;
    private static final String FIRST_ELEMENT_OF_ROOT[] = { "GRASS"
    							  , "HUT"
    							  , "POND"
    };
    
    private List<HelpCard> arrayHelpCard = new ArrayList<HelpCard>();
    ////////////
    
    public HUD hud = new HUD();
    
    private Sprite background = new Sprite( 0
            				, 0
            				, MainActivity.TEXTURE_WIDTH
            				, MainActivity.TEXTURE_HEIGHT
            				, MainActivity.mainActivity.textureBackground
            				, MainActivity.mainActivity.getVertexBufferObjectManager());
    
    
    private Sprite arrowLeft = new Sprite( ARROW_LEFT_POSITION_LEFT
					 , ARROW_LEFT_POSITION_UP
					 , ARROW_LEFT_WIDTH
					 , ARROW_LEFT_HEIGHT
					 , MainActivity.mainActivity.textureArrowLeft
					 , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
					, float pTouchAreaLocalX
					, float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.isActionDown()) {
		    
		    Log.d("arrowLeft", "touch");
		    arrowLeft.setAlpha(0.6f);
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("arrowLeft", "no touch");
		    arrowLeft.setAlpha(1.0f);
		    arrowLeftClick();
		}
		return true;
	    }
	};
	
	private void arrowLeftClick() {
	    
	    MainActivity.mainActivity.mClick.play();
	    
	    rootNumber--;
	    
	    arrowRight.setVisible(true);
	    hud.registerTouchArea(arrowRight);
	    
	    if (LIST_COUNT[rootNumber] == 0) {
		
		arrowDown.setVisible(false);
		hud.unregisterTouchArea(arrowDown);
	    } 
	    else {
		
		arrowDown.setVisible(true);
		hud.registerTouchArea(arrowDown);
	    }

	    if (rootNumber == 0) {
		
		arrowLeft.setVisible(false);
		hud.unregisterTouchArea(arrowLeft);
	    }
	    
	    listNumber = 0;
	    el = new Element(FIRST_ELEMENT_OF_ROOT[rootNumber]);
	    detachAllElementHint();
	    attachAllElementHint(this);
	}
	
	
    
	private Sprite arrowRight = new Sprite( ARROW_RIGHT_POSITION_LEFT
	    				  , ARROW_RIGHT_POSITION_UP
	    				  , ARROW_RIGHT_WIDTH
	    				  , ARROW_RIGHT_HEIGHT
	    				  , MainActivity.mainActivity.textureArrowRight
	    				  , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
					, float pTouchAreaLocalX
					, float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.isActionDown()) {
		    
		    Log.d("arrowRight", "touch");
		    arrowRight.setAlpha(0.6f);
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("arrowRight", "no touch");
		    arrowRight.setAlpha(1.0f);
		    arrowRightClick();
		}
		return true;
	    }
	};
	
	private void arrowRightClick() {
	    
	    MainActivity.mainActivity.mClick.play();
	    
	    rootNumber++;
	    
	    arrowLeft.setVisible(true);
	    hud.registerTouchArea(arrowLeft);
	    
	    if (LIST_COUNT[rootNumber] == 0) {
		
		arrowDown.setVisible(false);
		hud.unregisterTouchArea(arrowDown);
	    }
	    else {
		
		arrowDown.setVisible(true);
		hud.registerTouchArea(arrowDown);
	    }
	    
	    if (rootNumber == ROOT_COUNT) {
		
		arrowRight.setVisible(false);
		hud.unregisterTouchArea(arrowRight);
	    }
	    
	    listNumber = 0;
	    el = new Element(FIRST_ELEMENT_OF_ROOT[rootNumber]);
	    detachAllElementHint();
	    attachAllElementHint(this);
	}
    
	
	
    private Sprite arrowUp = new Sprite( ARROW_UP_POSITION_LEFT
	    			       , ARROW_UP_POSITION_UP
	    			       , ARROW_UP_WIDTH
	    			       , ARROW_UP_HEIGHT
	    			       , MainActivity.mainActivity.textureArrowUp
	    			       , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
					, float pTouchAreaLocalX
					, float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.isActionDown()) {
		    
		    Log.d("arrowUp", "touch");
		    arrowUp.setAlpha(0.6f);
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("arrowUp", "no touch");
		    arrowUp.setAlpha(1.0f);
		    arrowUpClick();
		}
		return true;
	    }
	};
	
	private void arrowUpClick() {
	    
	    MainActivity.mainActivity.mClick.play();
	    
	    listNumber--;
	    
	    arrowDown.setVisible(true);
	    hud.registerTouchArea(arrowDown);
	    
	    if (listNumber == 0) {
		
		if (rootNumber < ROOT_COUNT) {
		    
		    arrowRight.setVisible(true);
		    hud.registerTouchArea(arrowRight);
		}
		
		if (rootNumber > 0) {  
		    
		    arrowLeft.setVisible(true);
		    hud.registerTouchArea(arrowLeft);
		}
		
		arrowUp.setVisible(false);
		hud.unregisterTouchArea(arrowUp);
	    }
	    
	    el.changeToLvl(listNumber * CARD_ON_LIST);
	    
	    detachAllElementHint();
	    attachAllElementHint(this);
	}
	
	
	
    
    private Sprite arrowDown = new Sprite( ARROW_DOWN_POSITION_LEFT
	    				 , ARROW_DOWN_POSITION_UP
	    				 , ARROW_DOWN_WIDTH
	    				 , ARROW_DOWN_HEIGHT
	    				 , MainActivity.mainActivity.textureArrowDown
	    				 , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
					, float pTouchAreaLocalX
					, float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.isActionDown()) {
		    
		    Log.d("arrowDown", "touch");
		    arrowDown.setAlpha(0.6f);
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("arrowDown", "no touch");
		    arrowDown.setAlpha(1.0f);
		    arrowDownClick();
		}
		return true;
	    }
	};
	
	private void arrowDownClick() {
	    
	    MainActivity.mainActivity.mClick.play();
	    
	    listNumber++;
	    
	    arrowUp.setVisible(true);
	    hud.registerTouchArea(arrowUp);
	    
	    arrowRight.setVisible(false);
	    hud.unregisterTouchArea(arrowRight);
	    
	    arrowLeft.setVisible(false);
	    hud.unregisterTouchArea(arrowLeft);
	    
	    if (listNumber == LIST_COUNT[rootNumber]) {
		
		arrowDown.setVisible(false);
		hud.unregisterTouchArea(arrowDown);
	    }
	    
	    detachAllElementHint();
	    attachAllElementHint(this);
	}
	
    
    public HelpScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	attachChild(background);
	background.registerEntityModifier(MainActivity.SHOW_ALPHA_MODIFIER.deepCopy());
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	
	hud.attachChild(arrowLeft);
	hud.attachChild(arrowRight);
	hud.attachChild(arrowUp);
	hud.attachChild(arrowDown);
	hud.registerTouchArea(arrowLeft);
	hud.registerTouchArea(arrowRight);
	hud.registerTouchArea(arrowUp);
	hud.registerTouchArea(arrowDown);
	
	// Неактивные стрелки
	arrowLeft.setVisible(false);
	hud.unregisterTouchArea(arrowLeft);
	arrowUp.setVisible(false);
	hud.unregisterTouchArea(arrowUp);
	/////////
	
	el = new Element(FIRST_ELEMENT_OF_ROOT[rootNumber]);
	
	attachAllElementHint(this);
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
	hud.setVisible(true);
	hud.setIgnoreUpdate(false);	
	background.registerEntityModifier(MainActivity.SHOW_ALPHA_MODIFIER.deepCopy());
    }
    
    public void hide() {
	
	hud.setVisible(false);
	hud.setIgnoreUpdate(true);
   	setVisible(false);
   	setIgnoreUpdate(true);
   	background.setAlpha(1.0f);
    }
    
    private void attachAllElementHint(HelpScene helpScene) {
	
	for(int i = 0; i < CARD_ON_LIST; i++) {
	    
	    attachElementHint(helpScene, RECTANGLE_POSITION_LEFT, RECTANGLE_POSITION_UP + i * RECTANGLES_INTERVAL, el, i);
	    String temp = el.getName();
	    el.changeToNextLvl();
	    if (temp.equals(el.getName())) return;
	}
    }
    
    private void detachAllElementHint() {
	
	while(!arrayHelpCard.isEmpty()) {
	    
	    detachElementHint(0);
	}	
    }

    private void attachElementHint(HelpScene helpScene, float x, float y, Element el, int number) {	
	
	Element element = new Element(el.getName());
	
	arrayHelpCard.add(new HelpCard(helpScene
				     , new Sprite(x + ARROW_DELTA_LEFT
						, y + ARROW_DELTA_UP
						, ARROW_WIDTH
						, ARROW_HEIGHT
						, MainActivity.mainActivity.storage.getTexture("gfx_hint_arrow.png")
						, MainActivity.mainActivity.getVertexBufferObjectManager())
	
				     , new Sprite(x + ELEMENT_MARGIN_LEFT
						, y + ELEMENT_MARGIN_TOP
						, ELEMENT_WIDTH
						, ELEMENT_HEIGHT
						, MainActivity.mainActivity.storage.getTexture(TableOfElements
                                                        				     . getTextureName
                                                        				     ( element))
						, MainActivity.mainActivity.getVertexBufferObjectManager())
	
				     , new Sprite(x + SECOND_DELTA_LEFT
						, y + SECOND_DELTA_UP
						, ELEMENT_WIDTH
						, ELEMENT_HEIGHT
						, MainActivity.mainActivity.storage.getTexture(TableOfElements
                   				     					     . getTextureName
                   				     					     ( element))
						, MainActivity.mainActivity.getVertexBufferObjectManager())
	
				     , new Sprite(x + THIRD_DELTA_LEFT
						, y + THIRD_DELTA_UP
						, ELEMENT_WIDTH
						, ELEMENT_HEIGHT
						, MainActivity.mainActivity.storage.getTexture(TableOfElements
                   				     					     . getTextureName
                   				     					     ( element))
						, MainActivity.mainActivity.getVertexBufferObjectManager())
	
				     , new Sprite(x + NEXT_DELTA_LEFT
						, y + NEXT_DELTA_UP
						, ELEMENT_WIDTH
						, ELEMENT_HEIGHT
						, MainActivity.mainActivity.storage.getTexture(TableOfElements
                   				     					     . getTextureName
                   				     					     ( element.changeToNextLvl()))
						, MainActivity.mainActivity.getVertexBufferObjectManager())
	
				     , new Rectangle(x
						   , y
						   , RECTANGLE_WIDTH
						   , RECTANGLE_HEIGHT
						   , MainActivity.mainActivity.getVertexBufferObjectManager())));
	
	arrayHelpCard.get(number).attachHelpCard();
    }
    
    private void detachElementHint(int number) {
	
	arrayHelpCard.get(number).detachHelpCard();
	arrayHelpCard.remove(number);
    }
    
    private static class HelpCard {
	
	private Sprite arrow;
	private Sprite firstElement;
	private Sprite secondElement;
	private Sprite thirdElement;
	private Sprite nextElement;
	private Rectangle rect;
	private HelpScene helpScene;
	
	private HelpCard(HelpScene helpScene, Sprite arrow, Sprite firstElement, Sprite secondElement, Sprite thirdElement, Sprite nextElement, Rectangle rect) {
	    
	    this.helpScene = helpScene;
	    this.arrow = arrow;
	    this.firstElement = firstElement;
	    this.secondElement = secondElement;
	    this.thirdElement = thirdElement;
	    this.nextElement = nextElement;
	    this.rect = rect;
	}
	
	private void attachHelpCard() {
	    
	    helpScene.attachChild(rect);
	    rect.setColor(1.0f, 0.82f, 0.43f);
	    helpScene.attachChild(arrow);
	    helpScene.attachChild(firstElement);
	    helpScene.attachChild(secondElement);
	    helpScene.attachChild(thirdElement);
	    helpScene.attachChild(nextElement);
	}
	
	private void detachHelpCard() {
	    
	    helpScene.detachChild(rect);
	    helpScene.detachChild(arrow);
	    helpScene.detachChild(firstElement);
	    helpScene.detachChild(secondElement);
	    helpScene.detachChild(thirdElement);
	    helpScene.detachChild(nextElement);
	}
    }
}
