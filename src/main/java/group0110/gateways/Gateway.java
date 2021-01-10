package group0110.gateways;

import java.io.*;

/**
 * The Gateway class. This handles serializing and deserializing Objects from the filePath.
 * @author fairchi5
 */
class Gateway<T> {
    private final String filePath;

    /**
     * Creates a Gateway to save Objects to files.
     *
     * @param filePath  the file path in which to store the files
     */
    Gateway(String filePath) {
        this.filePath = filePath;
    }

    @SuppressWarnings("unchecked")
    T deserializeObject() {
        T obj;
        try {
            // Creates input streams from filePath
            FileInputStream fileInput = new FileInputStream(filePath);
            ObjectInputStream objInput = new ObjectInputStream(fileInput);
            // Reads object from file
            obj = (T) objInput.readObject();
            // Close streams
            objInput.close();
            fileInput.close();
            return obj;
        } catch (IOException i) {
            // Likely file doesn't exist
            System.out.println("No save file found");
            return null;
        } catch (ClassNotFoundException c) {
            // Class doesn't match. Something wrong with save data
            System.out.println( "Object not found");
            return null;
        }
    }

    boolean serializeObject(T obj) {
        try {
            // Creates output streams to filePath
            FileOutputStream fileOutput =
                    new FileOutputStream(filePath);
            ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput);
            // Writes object to file
            objOutput.writeObject(obj);
            // Closes streams
            objOutput.close();
            fileOutput.close();
            System.out.println("Save data was saved to " + filePath);
            return true;
        } catch (IOException i) {
            // Something wrong with saving to file
            i.printStackTrace();
            return false;
        }
    }
}
