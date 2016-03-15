/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvshowrecordkeeper;

/**
 *
 * @author Saketh
 */
public class Episode {

    final String filePath;
    final String name;
    final boolean watched;

    public Episode(String filePath, boolean watched) {
        this.filePath = filePath;
        this.name = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf('.'));
        this.watched = watched;
    }

    @Override
    public String toString() {
        return name;
    }
}
