package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	
	public int life;
	
	public int worldX;
	public int worldY;
	public int speed;
	public int hight;
	
	public int Gravity = 2;
	
	public BufferedImage forward1, forward2,forward3,forward4,forward5,backward1,backward2,backward3,backward4,backward5,crouch1,crouch2,crouch3,crouch4,crouch5,backwardcrouch1,backwardcrouch2,backwardcrouch3,backwardcrouch4,backwardcrouch5,stand1,stand2,stand3;
	
	public static String action;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public int jumpSound,runSound;
	
	public Rectangle solidArea;
	public boolean collisionOn = false;
	public boolean falling = true;
	public boolean isLadder = false;
	
	

}
