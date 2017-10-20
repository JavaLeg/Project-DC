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

import Interface.EditorModel;
import Interface.ObjectModel;
import Tileset.DynamicObject;
import Tileset.Enemy;
import Tileset.GameObject.ObjectType;
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
    
    
    
    
    
    
    
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SAVING OBJECT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    

    /*
     * Player objects
     */
    public void Save(DynamicObject obj, String fileName) throws IOException{
    	FileOutputStream fos = new FileOutputStream(new File(objPath + "/" + obj.getType().toString().toLowerCase() + "_custom/" + fileName));
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        out.writeTo(fos);
    }
    
    public DynamicObject Load(String fileName, ObjectType type) throws IOException, ClassNotFoundException{
    	Path path = Paths.get(objPath + "/" + type.toString().toLowerCase() + "_custom/" + fileName); 	
    	byte[] data = Files.readAllBytes(path);
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);        
        return (DynamicObject)is.readObject();
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
