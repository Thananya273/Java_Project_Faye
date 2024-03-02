package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	public BufferedImage background;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum =new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap();
	}
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt.png"));
			tile[1].collision = true; //cannot pass
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Flower_Up.png"));
			tile[2].collision = true; //cannot pass
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Vines_.png"));
			tile[3].collision = true; //cannot pass
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt_Grass.png"));
			tile[4].collision = true; //cannot pass
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Flower_Floor.png"));
			tile[5].collision = true; //cannot pass
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void loadMap() {
		try {
			//input files
			InputStream is = getClass().getResourceAsStream("/maps/testMap.txt");
			//read the map
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col<gp.maxWorldCol && row<gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while(col<gp.maxWorldCol) {
					String numbers[] = line.split(" "); //split string at space
					int num = Integer.parseInt(numbers[col]); //change String to integer
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
				
			}
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			
			if(		worldX + gp.tileSize>gp.player.worldX - gp.player.screenX &&
					worldX - gp.tileSize-550<gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize>gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize-550<gp.player.worldY + gp.player.screenY) {
			
				g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
				
			}
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		
			
		}
		
	}

}
