package entity;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp,KeyHandler keyH) {
		
		this.gp=gp;
		this.keyH=keyH;
		
		solidArea = new Rectangle();
		solidArea.x = 36;
		solidArea.y = 16;
		solidArea.width = 60;
		solidArea.height = 90;
		
		
		screenX = gp.screenWidth-1100;
		screenY = gp.screenHight-364;
		
		life = 3;
		jumpSound = 1;
		runSound = 1;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 3;
		worldY = 64;
		speed = 10;
		hight = 35;
		action = "";
		
	}
	
	public void getPlayerImage() {
		
		try {
			//move forward
			forward1 = ImageIO.read(getClass().getResourceAsStream("/player/Forward_1.png"));
			forward2 = ImageIO.read(getClass().getResourceAsStream("/player/Forward_2.png"));
			forward3 = ImageIO.read(getClass().getResourceAsStream("/player/Forward_3.png"));
			forward4 = ImageIO.read(getClass().getResourceAsStream("/player/Forward_4.png"));
			forward5 = ImageIO.read(getClass().getResourceAsStream("/player/Forward_5.png"));
			//move backward
			backward1 = ImageIO.read(getClass().getResourceAsStream("/player/Backward_1.png"));
			backward2 = ImageIO.read(getClass().getResourceAsStream("/player/Backward_2.png"));
			backward3 = ImageIO.read(getClass().getResourceAsStream("/player/Backward_3.png"));
			backward4 = ImageIO.read(getClass().getResourceAsStream("/player/Backward_4.png"));
			backward5 = ImageIO.read(getClass().getResourceAsStream("/player/Backward_5.png"));
			// crouch
			crouch1 =  ImageIO.read(getClass().getResourceAsStream("/player/Crouch_1.png"));
			crouch2 =  ImageIO.read(getClass().getResourceAsStream("/player/Crouch_2.png"));
			crouch3 =  ImageIO.read(getClass().getResourceAsStream("/player/Crouch_3.png"));
			crouch4 =  ImageIO.read(getClass().getResourceAsStream("/player/Crouch_4.png"));
			crouch5 =  ImageIO.read(getClass().getResourceAsStream("/player/Crouch_5.png"));
			// backward crouch
			backwardcrouch1 = ImageIO.read(getClass().getResourceAsStream("/player/Crouchbackward_1.png"));
			backwardcrouch2 = ImageIO.read(getClass().getResourceAsStream("/player/Crouchbackward_2.png"));
			backwardcrouch3 = ImageIO.read(getClass().getResourceAsStream("/player/Crouchbackward_3.png"));
			backwardcrouch4 = ImageIO.read(getClass().getResourceAsStream("/player/Crouchbackward_4.png"));
			backwardcrouch5 = ImageIO.read(getClass().getResourceAsStream("/player/Crouchbackward_5.png"));
			//stand
			stand1 = ImageIO.read(getClass().getResourceAsStream("/player/Standing_4.png"));
			stand2 = ImageIO.read(getClass().getResourceAsStream("/player/Standing_5.png"));
			stand3 = ImageIO.read(getClass().getResourceAsStream("/player/Standing_6.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		if(keyH.jumpPressed == true||keyH.crouchPressed == true||keyH.forwardPressed == true||keyH.backwardPressed == true) {
			
			if((keyH.crouchPressed == true&&keyH.forwardPressed == true)||(keyH.crouchPressed == true&&keyH.backwardPressed == true)) {
				action = "crouch";
			}
			else if(keyH.forwardPressed == true) {
				action = "forward";
			}
			else if(keyH.backwardPressed == true) {
				action = "backward";
			}
			if(keyH.jumpPressed == true&&keyH.backwardPressed == true) {
			    action = "jumpbackward";
			    Timer timer = new Timer(100,new ActionListener() {
				     @Override
				     public void actionPerformed(ActionEvent e) {
				    	 keyH.jumpPressed = false;
				     }
				    });
				    timer.setRepeats(false);
				    timer.start();
			}
			else if (keyH.climbPressed== true) {
				action = "climb";
			}
			else if(keyH.jumpPressed == true) {
				action = "jump";
				Timer timer = new Timer(100,new ActionListener() {
				     @Override
				     public void actionPerformed(ActionEvent e) {
				    	 keyH.jumpPressed = false;
				     }
				    });
				    timer.setRepeats(false);
				    timer.start();
			}
			//check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			if (!collisionOn) {
				if (action == "crouch") {
					if (keyH.backwardPressed==true){
						worldX-=speed-5;
					}
					if (keyH.forwardPressed==true) {
						worldX+=speed-5;
					}
				}
				if (action == "jumpbackward") {
					worldY -= hight;
				}
				if (action == "forward") {
					worldX += speed;
					if (runSound==1) {
						gp.playSE(2);
						runSound--;
						Timer timer = new Timer(1800,new ActionListener() {
						     @Override
						     public void actionPerformed(ActionEvent e) {
						    	 runSound=1;
						     }
						    });
						    timer.setRepeats(false);
						    timer.start();
					}
					
				}
				if (action == "backward") {
					worldX -= speed;
				}
				if (action == "jump") {
					worldY -= hight;
					if (jumpSound==1) {
						gp.playSE(1);
						jumpSound--;
						Timer timer = new Timer(300,new ActionListener() {
						     @Override
						     public void actionPerformed(ActionEvent e) {
						    	 jumpSound=1;
						     }
						    });
						    timer.setRepeats(false);
						    timer.start();
					}
				}
			}
			spriteCounter++;
			
			if(spriteCounter>10) {
				if(spriteNum>=5) {
					spriteNum=1;
				}
				spriteNum++;
				spriteCounter=0;
			}
		}
		
		if (gp.cChecker.out) {
			worldX-= gp.tileSize*2;
			worldY = 300;
			life --;
			if (life == 0) {
				setDefaultValues();
				life = 3;
			}
		}
		//check falling
		gp.cChecker.checkTile(this);
		if(falling) {
			worldY+=speed;
		}
		if (isLadder) {
			falling = false;
		}
		if (isLadder&&action == "climb"){
			worldY -= speed-5;
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = forward1;
	
		if (falling) {
			image = forward1;
		}
		switch(action) {
		case "jumpbackward":
			image = backward2;
			break;
		case "jump":
			image = forward2;
			break;
		case "climb":{
			if(spriteNum==1) {
				image = stand1;
			}
			if(spriteNum==2) {
				image = stand2;
			}
			if(spriteNum==3) {
				image = stand3;
			}
			if(spriteNum==4) {
				image = stand1;
			}
			if(spriteNum==5) {
				image = stand2;
			}
			break;
		}
		case "crouch":
			if (keyH.forwardPressed) {
				if(spriteNum==1) {
					image = crouch1;
				}
				if(spriteNum==2) {
					image = crouch2;
				}
				if(spriteNum==3) {
					image = crouch3;
				}
				if(spriteNum==4) {
					image = crouch4;
				}
				if(spriteNum==5) {
					image = crouch5;
				}
			}
			else if (keyH.backwardPressed) {
				if(spriteNum==1) {
					image = backwardcrouch1;
				}
				if(spriteNum==2) {
					image = backwardcrouch2;
				}
				if(spriteNum==3) {
					image = backwardcrouch3;
				}
				if(spriteNum==4) {
					image = backwardcrouch4;
				}
				if(spriteNum==5) {
					image = backwardcrouch5;
				}
			}
			break;
		case "forward":
			if(spriteNum==1) {
				image = forward1;
			}
			if(spriteNum==2) {
				image = forward2;
			}
			if(spriteNum==3) {
				image = forward3;
			}
			if(spriteNum==4) {
				image = forward4;
			}
			if(spriteNum==5) {
				image = forward5;
			}
			break;
		case "backward":
			if(spriteNum==1) {
				image = backward1;
			}
			if(spriteNum==2) {
				image = backward2;
			}
			if(spriteNum==3) {
				image = backward3;
			}
			if(spriteNum==4) {
				image = backward4;
			}
			if(spriteNum==5) {
				image = backward5;
			}
			break;
		}
		g2.drawImage(image, screenX,screenY, gp.tileSize,gp.tileSize,null);
	}
}
