/**
 * Created by Grigoryan on 20.11.2016.
 */

import Abstractions.IInputReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleInputReader implements IInputReader {
    private BufferedReader _reader;

    public String GetString() {
        try {
            return _reader.readLine();
        }
        catch(IOException exc) {
            return "";
        }
    }

    public int GetInteger() {
        try {
            String data = _reader.readLine();
            int number = Integer.parseInt(data);
            return number;
        }
        catch(IOException exc) {
            return 0;
        }
    }

    public boolean GetBoolean() {
        try {
            char decision = _reader.readLine().charAt(0);
            return decision == 'y';
        }
        catch(IOException exc)
        {
            return false;
        }
    }

    public ConsoleInputReader()
    {
        _reader = new BufferedReader(new InputStreamReader(System.in));
    }
}
