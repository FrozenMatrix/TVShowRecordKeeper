/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvshowrecordkeeper;

import java.io.Serializable;

/**
 *
 * @author Saketh
 */
public class TVShow implements Serializable{
    final String name;
    final String filePath;
    private String lastWatchedEpisode = "None";

    public TVShow(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return name;
    }

    void updateLastWatched(String episode) {
        this.lastWatchedEpisode = episode;
    }

    public String getLastWatchedEpisode() {
        return lastWatchedEpisode;
    }
}
