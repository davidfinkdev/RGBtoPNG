/*
 * The MIT License
 *
 * Copyright 2016 David Fink.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package rgbtopng;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author David Fink
 */
public class RGBHistoryManager {

    //declare a LinkedList of RGB values to store the last five colors, and an 
    //integer to store the number of actual values in the queue
    LinkedList<RGB> rgbHistory;
    int historyCount;

    public RGBHistoryManager() {
        rgbHistory = new LinkedList<>();
        historyCount = 0;
    }

    //write the history of RGB colors generated by the user to a file
    public void writeRGBHistory() throws FileNotFoundException, IOException {
        //declare and initialize variables to be used throughout the operation
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        
        //open an output stream, write the history LinkedList to file, and close 
        //the output stream
        fileOutputStream = new FileOutputStream("rgbHistory.rgb");
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(rgbHistory);
        objectOutputStream.writeObject(historyCount);
        objectOutputStream.close();
    }

    //reads the history of RGB colors from a previously generated history file
    public void readRGBHistory() throws FileNotFoundException, IOException, ClassNotFoundException {
        //declare and initialize variables relating to reading in a history LinkedList
        FileInputStream fileInputStream = new FileInputStream("rgbHistory.rgb");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        //read in the serialized ArrayList and int to rgbHistory and historyCount
        rgbHistory = (LinkedList<RGB>) objectInputStream.readObject();
        historyCount = (Integer) objectInputStream.readObject();
        
        //close the input stream
        objectInputStream.close();
    }

    //add a color to the history and return true or false, depending on whether
    //or not the add operation completed successfully
    public boolean addRGBHistory(RGB rgbVal) {
        if (historyCount <= 5) {
            //removes the oldest RGB value to make room for the new value and 
            //decrements the historyCount variable in case the later addition 
            //operation is unsuccessful
            rgbHistory.remove();
            historyCount--;
        }
        //adds the new RGB value to the foot of the history LinkedList and 
        //increments the historyCount variable if the operation is successful
        boolean addSuccessful = rgbHistory.add(rgbVal);
        if (addSuccessful) {
            historyCount++;
        }
        
        //returns whether or not the add operation was successful
        return addSuccessful;
    }
    
    //clears all RGB history and resets the historyCount variable to 0
    public void clearRGBHistory()   {
        rgbHistory = new LinkedList<>();
        historyCount = 0;
    }

    public LinkedList<RGB> getRGBHistory() {
        return rgbHistory;
    }

    public int getHistoryCount() {
        return historyCount;
    }
}
