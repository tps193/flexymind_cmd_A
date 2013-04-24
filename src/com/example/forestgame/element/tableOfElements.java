package com.example.forestgame.element;

public class tableOfElements {

	//Список констант - названий элементов
	public final static int ELEMENT_NUT = 0;
	public final static int ELEMENT_MEGA_NUT = 1;
	
	//Таблица, содержащая информацию об элементах(0 колонка - название, 1 - элемент следующего уровня, 3 - количество очков
	private static final int[][] TABLE_OF_ELEMENTS = {
		{ELEMENT_NUT, ELEMENT_MEGA_NUT, 100}
	};
	
	
	public static int getNextLvl(int name) {
		return TABLE_OF_ELEMENTS[name][1];
	}
	
	public static int getPoints(int name) {
		return TABLE_OF_ELEMENTS[name][2];
	}
	
}
