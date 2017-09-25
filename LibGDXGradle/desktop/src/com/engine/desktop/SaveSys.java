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

import org.omg.PortableServer.POAManagerPackage.State;

/*
 * Save System (Can work for game state + game object models)
 */

public class SaveSys {
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
    public void Save(State s, String fileName) throws IOException{
    	FileOutputStream fos = new FileOutputStream(new File(dirPath + "/" + fileName));
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(s);
        out.writeTo(fos);
    }
    
	/*
	 * byte[] -> Object
	 */
    public State Load(String fileName) throws IOException, ClassNotFoundException {
    	Path path = Paths.get(dirPath); 	
    	byte[] data = Files.readAllBytes(path);
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (State)is.readObject();
    }
    
    /*
     * 		SaveSys s = null;
		try {
			s = new SaveSys();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		State state = new State();
		try {
			s.Save(state,"testFile.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     */
}
