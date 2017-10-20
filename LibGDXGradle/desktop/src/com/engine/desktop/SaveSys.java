package com.engine.desktop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import Interface.EditorModel;
import Interface.ObjectModel;
import Tileset.Enemy;
import Tileset.Player;


/*
 * Save System
 * Used for editor model
 * Used for gameObjects
 */

public class SaveSys{
	private String dirPath;
	private String objPath;
	
	/*
	 * Construct directory path and directory for saving/loading
	 */
	public SaveSys() throws IOException {
		        
        Path currentRelativePath = Paths.get("");
        dirPath = currentRelativePath.toAbsolutePath().toString() + "/Library";
        objPath = currentRelativePath.toAbsolutePath().toString() + "/SpriteFamily";

        File dir = new File(dirPath);
		
        // If directory path already exists
		if(!dir.exists()) {	
			try {
				dir.mkdir();
			}
			catch(SecurityException se) {
				System.out.println(se);
			}
		}
	}
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SAVING MAP ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/*
	 * Object -> byte[]
	 */
    public void Save(EditorModel s, String fileName) throws IOException{
    	FileOutputStream fos = new FileOutputStream(new File(dirPath + "/" + fileName));
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(s);
        out.writeTo(fos);
    }
    
	/*
	 * byte[] -> Object
	 */
    public EditorModel Load(String fileName) throws IOException, ClassNotFoundException {
    	Path path = Paths.get(dirPath + "/" + fileName); 	
    	byte[] data = Files.readAllBytes(path);
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);        
        return (EditorModel)is.readObject();
    }
    
    
    public void Delete(String fileName) {
    	 File[] list = this.getLibrary();
    	 
         for (final File f : list) {
        	 final String file = f.getName().split("\\.", 2)[0];
        	 if(file.equals(fileName)) {
        		 f.delete();        	 }
         }
	}
    
    
    
    
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SAVING OBJECT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    

    /*
     * Player objects
     */
    public void SavePlayer(Player p, String fileName) throws IOException{
    	FileOutputStream fos = new FileOutputStream(new File(objPath + "/player_custom/" + fileName));
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(p);
        out.writeTo(fos);
    }
    
    public Player LoadPlayer(String fileName) throws IOException, ClassNotFoundException{
    	Path path = Paths.get(objPath + "/player_custom/" + fileName); 	
    	byte[] data = Files.readAllBytes(path);
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);        
        return (Player)is.readObject();
    }
    
    
    /*
     * Enemy objects
     */
    public void SaveEnemy(Enemy e, String fileName) throws IOException{
    	FileOutputStream fos = new FileOutputStream(new File(objPath + "/enemy_custom/" + fileName));
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(e);
        out.writeTo(fos);
    }
    
    public Enemy LoadEnemy(String fileName) throws IOException, ClassNotFoundException{
    	Path path = Paths.get(objPath + "/enemy_custom/" + fileName); 	
    	byte[] data = Files.readAllBytes(path);
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);        
        return (Enemy)is.readObject();
    }
    

    /*    
     * Returns list of files in the library directory
     */
    public File[] getLibrary(){
		File directory = new File(dirPath);
		File[] library = directory.listFiles();
		return library;
    }
}
