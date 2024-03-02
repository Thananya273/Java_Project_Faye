package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
public class KeyHandler implements KeyListener {
	GamePanel gp;
	public boolean jumpPressed, crouchPressed, forwardPressed, backwardPressed, climbPressed;
	public int x,y;
	public int width,heigth;
	int clickSound = 1;
	int openSound = 1;
	public boolean isKeyPressed = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		isKeyPressed = true;
		if(code == KeyEvent.VK_W) { 
			jumpPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			crouchPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			forwardPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			backwardPressed = true;
		}
		if (code == KeyEvent.VK_SPACE) {
			climbPressed = true;
		}
		
		//Menu
		if (code == KeyEvent.VK_M) {
			gp.gameState = gp.menuState;
			if (openSound==1) {
				gp.playSE(4);
				openSound--;
				Timer timer = new Timer(100,new ActionListener() {
				     @Override
				     public void actionPerformed(ActionEvent e) {
				    	 openSound=1;
				     }
				    });
				    timer.setRepeats(false);
				    timer.start();
			}
		}
		
		if (gp.gameState == gp.menuState) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum<0) {
					gp.ui.commandNum = 2;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum>2) {
					gp.ui.commandNum = 0;
				}
			}
			if (code != KeyEvent.VK_ENTER&&code != KeyEvent.VK_M&&clickSound==1) {
				gp.playSE(3);
				clickSound--;
				Timer timer = new Timer(100,new ActionListener() {
				     @Override
				     public void actionPerformed(ActionEvent e) {
				    	 clickSound=1;
				     }
				    });
				    timer.setRepeats(false);
				    timer.start();
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
				}
				if (gp.ui.commandNum == 1) {
					gp.player.setDefaultValues();
					gp.gameState = gp.playState;
				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
				
				if (openSound==1) {
					gp.playSE(4);
					openSound--;
					Timer timer = new Timer(100,new ActionListener() {
					     @Override
					     public void actionPerformed(ActionEvent e) {
					    	 openSound=1;
					     }
					    });
					    timer.setRepeats(false);
					    timer.start();
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();	
		isKeyPressed = false;
		if(code == KeyEvent.VK_W) {
			jumpPressed = false;	
		}
		if(code == KeyEvent.VK_S) {
			crouchPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			forwardPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			backwardPressed = false;
		}
		if (code == KeyEvent.VK_SPACE) {
			climbPressed = false;
		}
		
	}

}
