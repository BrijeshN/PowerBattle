/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Brijesh
 */
public class Utils {

    public static String loadFileAsString(String path) {

        //allows you add string character easily
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getResourceAsStream(path)));

            String line;

            while ((line = br.readLine()) != null) {
                builder.append(line + "\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();

    }

    public static int parseInt(String number) {
        try {

            return Integer.parseInt(number);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
