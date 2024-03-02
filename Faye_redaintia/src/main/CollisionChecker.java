package main;

import entity.Entity;
import entity.Player;

public class CollisionChecker {
	
	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	public boolean out = false;
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1,tileNum2;
		
		if (entityBottomRow >= 8) {
			out = true;
		}
		else {
			out = false;
		}
		switch (entity.action) {
		 
		  case "jump":
			   entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			   tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			   tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			   if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
			    entity.collisionOn = true;
			   }
			   break;
		  case "forward":
			  entityRightCol= (entityRightWorldX + entity.speed)/gp.tileSize;
			  tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			  tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			  if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				  entity.collisionOn = true;
			  }
			  break;
		  case "backward":
			  entityLeftCol= (entityLeftWorldX - entity.speed)/gp.tileSize;
			  tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			  tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			  if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				  entity.collisionOn = true;
			  }
			  break;
		   
		  case "crouch":
			   entityRightCol= (entityRightWorldX + entity.speed)/gp.tileSize;
			   entityLeftCol= (entityLeftWorldX - entity.speed)/gp.tileSize;
			   tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			   tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			   if(tileNum1 == 2 || tileNum2 == 2) {
				   entity.collisionOn = false;
			   }
			   if (tileNum1 != 2 && tileNum2 != 2) {
				   entity.collisionOn = true;
			   }
			   break;
		  case "climb":
			   entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			   entityLeftCol= (entityLeftWorldX - entity.speed)/gp.tileSize;
			   tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			   tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			   if(tileNum1 != 3 && tileNum2 != 3) {
					entity.isLadder = false;
				}
				if (tileNum1 == 3 || tileNum2 == 3) {
					entity.isLadder = true;
				}
			   break;
		}
		if (!entity.falling){
			 entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			 tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			 tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			 if(gp.tileM.tile[tileNum1].collision == false || gp.tileM.tile[tileNum2].collision == false) {
				   entity.falling = true;
			   }
		}
		if (entity.falling) {
			 entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			 tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			 tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			 if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				   entity.falling = false;
			   }
		}
		if (!entity.isLadder) {
			entityRightCol= (entityRightWorldX + entity.speed)/gp.tileSize;
			entityLeftCol= (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
		}
	}
}
