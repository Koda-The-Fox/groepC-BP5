package com.greengenie.green_genie.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonMethods {


    public static void setJSONToFile(JSONObject objct, String file) {

        FileWriter writer = null;
        try
        {
            writer = new FileWriter(file);
            writer.write(objct.toString().replace(",", ",\n   ").replace("{", "{\n   ").replace("}", "\n}"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.flush();
                writer.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("Done.");
    }

    public static JSONObject getJSONFromFile(String file) {
        JSONObject result = null;
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        FileReader reader = null;
        try
        {
            reader = new FileReader(file);

            //Read JSON file
            result = (JSONObject) jsonParser.parse(reader);

        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

}
