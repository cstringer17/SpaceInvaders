
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cstring
 */
public class getScores {

    public static String getString() {
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get("src/main/resources/Scores.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

}
