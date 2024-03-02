package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.Timer;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_80B;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public int commandNum = 0;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
        arial_40 = new Font("SansSerif", Font.PLAIN, 40);
        arial_80B = new Font("SansSerif", Font.BOLD, 80);
    }
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		if (gp.gameState == gp.playState) {
			
		}
		if (gp.gameState == gp.menuState) {
			drawMenuScreen();
			
		}
	}
	public void drawMenuScreen(){
		//background
		int x = gp.tileSize*4;
		int y = gp.tileSize*1;
		int width = gp.tileSize*4;
		int hight = gp.tileSize*4;
		drawSubWindow(x, y, width, hight);
		
		g2.setColor(Color.white);
		String text = "MENU";
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		int textX = getXforCenterdText(text);
		int textY = gp.screenHight/2-150;
		g2.drawString(text, textX, textY);
		
		text = "CONTINUE";
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
		textX = getXforCenterdText(text);
		textY = gp.screenHight/2-25;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX-50,textY);
		}
		
		text = "RESTART";
		textX = getXforCenterdText(text);
		textY = gp.screenHight/2+50;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX-50,textY);
		}
		
		text = "QUIT";
		textX = getXforCenterdText(text);
		textY = gp.screenHight/2+125;
		g2.drawString(text, textX, textY);
		if (commandNum == 2) {
			g2.drawString(">", textX-50,textY);
		}
		
	}
	public void drawSubWindow(int x, int y, int width, int hight) {
		
		Color c = new Color(255,255,255,80);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, hight, 250, 250);
		
	}
	public int getXforCenterdText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
		
	}
}
