/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvshowrecordkeeper;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Saketh
 */
public class TVShowRecordKeeper {

    static ArrayList<TVShow> tvShowList;
    static File showListFile;
    static ShowList sl;
    
    static int defaultLocX = 0;
    static int defaultLocY = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Constants.Initialize();
        ObjectInputStream ois = null;
        try {
            File cwd = new File(System.getProperty("user.dir"));
            System.out.println(cwd.getAbsolutePath());

            showListFile = new File(cwd.getAbsolutePath() + "\\ShowList");
            debugPrint(showListFile.length()+"length");
            if(showListFile.length() == 0){
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(showListFile));
                oos.writeObject(new ArrayList<>());
                oos.close();
            }
            
            ois = new ObjectInputStream(new FileInputStream(showListFile));

            tvShowList = (ArrayList) ois.readObject();
            if (tvShowList == null) {
                tvShowList = new ArrayList<>();
            }

            if (tvShowList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Show list is empty");
            }
            sl = new ShowList(tvShowList);
            sl.setDefaultCloseOperation(3);
            sl.setVisible(true);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(TVShowRecordKeeper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(TVShowRecordKeeper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static boolean playVid(String file) {
        try {
            new ProcessBuilder("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe", file).start();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(TVShowRecordKeeper.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static String rectifyFilePath(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        int prev = 0;
        while (sb.indexOf("\\", prev) != -1) {
            prev = sb.indexOf("\\", prev);
            sb.insert(prev, "\\");
            prev = prev + 2;
        }
        return sb.toString();
    }

    public static void storeEpisodes(File folder, File storeFile) {
        try {
            try (CSVWriter writer = new CSVWriter(new FileWriter(storeFile))) {
                File[] list = folder.listFiles();
                String[] fileList = new String[list.length];
                for (int i = 0; i < list.length; ++i) {
                    fileList[i] = rectifyFilePath(list[i].getAbsolutePath());
                }
                String[] line = new String[3];
                for (String x : fileList) {
                    line[0] = x;
                    line[1] = "false";
                    line[2] = "--/--/--";
                    if (line[0].endsWith(".mkv")) {
                        writer.writeNext(line);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TVShowRecordKeeper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addTVShow(String show, String filePath) {
        tvShowList.add(new TVShow(show, filePath));
        updateShowFile();
    }

    public static void updateShowFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(showListFile));
            if(oos != null){
                oos.writeObject(tvShowList);
                oos.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(TVShowRecordKeeper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void debugPrint(String str) {
//        System.out.println("DEBUG : " + str);
    }
}
