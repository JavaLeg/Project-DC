package com.engine.desktop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * Save System (Can work for game state + game object models)
 */

public class SaveSys {

	/*
	 * Object -> byte[]
	 */
    public byte[] Save(DCGame g) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(g);
        return out.toByteArray();    
    }
    
	/*
	 * byte[] -> Object
	 */
    public DCGame Load(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (DCGame)is.readObject();
    }
}
