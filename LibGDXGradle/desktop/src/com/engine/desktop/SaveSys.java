package com.engine.desktop;

import State.State;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import Interface.EditorModel;


/*
 * Save System (Can work for game state + game object models)
 */

public class SaveSys{
	private String dirPath;
	
	/*
	 * Construct directory path and directory for saving/loading
	 */
	public SaveSys() throws IOException {
		        
        Path currentRelativePath = Paths.get("");
        dirPath = currentRelativePath.toAbsolutePath().toString() + "/Library";

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
    
    public File[] getLibrary(){
		File directory = new File(dirPath);
		File[] library = directory.listFiles();
		return library;
    }
}
