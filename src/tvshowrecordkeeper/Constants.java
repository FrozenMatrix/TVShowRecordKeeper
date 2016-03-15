/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvshowrecordkeeper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Saketh
 */
public class Constants {
    static ArrayList<String> vidExtensions = new ArrayList();
    public static void Initialize(){
        vidExtensions.addAll(Arrays.asList(new String[]{"mkv", "mp4", "rmbv", "avi", "m4v"}));
    }
}
