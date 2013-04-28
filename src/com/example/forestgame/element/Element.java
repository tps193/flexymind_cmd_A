package com.example.forestgame.element;

public class Element {
	
	private int type;
	private String texture;
	
	public Element(int type, String texture) {
		super();
		this.type = type;
		this.texture = texture;
	}
	
	
	public int getType() {
		return this.type;
	}
	
	public String getTexture() {
		return this.texture;
	}
	
	// �������������� ���������� ��������� � 1 ����� �������� ������.
	public void changeToNextLvl() {
		tableOfElements.getNextLvl(type);
	}
	
}
