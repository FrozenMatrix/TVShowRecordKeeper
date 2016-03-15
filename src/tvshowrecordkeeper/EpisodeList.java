package tvshowrecordkeeper;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import static tvshowrecordkeeper.TVShowRecordKeeper.debugPrint;
import static tvshowrecordkeeper.TVShowRecordKeeper.rectifyFilePath;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Saketh
 */
public class EpisodeList extends javax.swing.JFrame {

    Episode selected;
    TVShow currentShow;
    ArrayList<Episode> episodeList;

    /**
     * Creates new form TVShowViewer
     *
     * @param curShow
     */
    public EpisodeList(TVShow curShow) {
        initComponents();
        currentShow = curShow;

        this.getRootPane().setDefaultButton(btnPlayVid);
        jListEpisodes.setFocusable(true);
        jListEpisodes.requestFocus();
        updateLastWatchedLabel();
        ListSelectionListener ll = (ListSelectionEvent e) -> {
            int index = ((JList) e.getSource()).getSelectedIndex();
//            System.out.println(((JList) e.getSource()).getModel().getElementAt(index));
            selected = (Episode) ((JList) e.getSource()).getModel().getElementAt(index);
            jListEpisodes.ensureIndexIsVisible(index);
        };

        MouseAdapter ma = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    setupPlayVid();
                }
            }
        };

        KeyListener keyList = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                debugPrint(e.getKeyCode() + " " + KeyEvent.VK_BACK_SPACE);
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    btnBack.doClick(0);
                }
            }
        };

        jListEpisodes.addKeyListener(keyList);

        episodeList = new ArrayList<>();
        Episode[] ep = getEpisodeList(currentShow.filePath);
        for (int i = 0; i < ep.length; i++) {
            if (ep[i] == null) {
                debugPrint("NULL " + i);
            } else {
                episodeList.add(ep[i]);
            }
        }

        if (!episodeList.isEmpty()) {
            jListEpisodes.setModel(new javax.swing.AbstractListModel() {
                @Override
                public int getSize() {
                    return episodeList.size();
                }

                @Override
                public Object getElementAt(int i) {
                    return episodeList.get(i);
                }
            });
        }

        jListEpisodes.addListSelectionListener(ll);
        jListEpisodes.addMouseListener(ma);

        int lastEpisode = 0;
        if (!currentShow.getLastWatchedEpisode().toLowerCase().equals("none")) {
            for (; lastEpisode < episodeList.size(); lastEpisode++) {
                if (episodeList.get(lastEpisode).name.equals(currentShow.getLastWatchedEpisode())) {
                    break;
                }
            }
        }

        debugPrint("Episode " + lastEpisode);

        jListEpisodes.setSelectedIndex(lastEpisode);
        jListEpisodes.setSelectedValue(jListEpisodes.getModel().getElementAt(jListEpisodes.getSelectedIndex()), true);
        jListEpisodes.ensureIndexIsVisible((lastEpisode < episodeList.size() - 3) ? lastEpisode + 2 : lastEpisode);

        jListEpisodes.setSize(this.getSize());

        this.setDefaultCloseOperation(3);
        
        
        if (jScrollPane1.getPreferredSize().width > this.getPreferredSize().width - 100) {
            this.setSize(jScrollPane1.getPreferredSize().width + 100, this.getPreferredSize().height);
        }
        
        this.setLocation(TVShowRecordKeeper.defaultLocX, TVShowRecordKeeper.defaultLocY);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        jScrollPane1.setSize(this.getWidth(), jScrollPane1.getHeight());
    }
    
    

    private Episode[] getEpisodeList(String filePath) {
        File file = new File(filePath);
//        debugPrint(file.getAbsolutePath());
        File[] list = file.listFiles();
        if (list == null) {
            debugPrint("NULL List");
        }
        int count = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i].getAbsolutePath().endsWith(".mkv"));
            ++count;
        }
        String[] fileList = new String[list.length];
        for (int i = 0; i < list.length; ++i) {
            fileList[i] = rectifyFilePath(list[i].getAbsolutePath());
        }
        LinkedList<Episode> episodeAL = new LinkedList<>();

        for (int i = 0; i < fileList.length; ++i) {
            String p = fileList[i].substring(fileList[i].lastIndexOf('.') + 1, fileList[i].length());
//            debugPrint(p);
            if (Constants.vidExtensions.contains(p)) {
                episodeAL.add(new Episode(fileList[i], false));
            }
        }
        Episode[] episodes = new Episode[episodeAL.size()];
        for (int i = 0; i < episodeAL.size(); ++i) {
            episodes[i] = episodeAL.get(i);
        }
        return episodes;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLastWatched = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListEpisodes = new javax.swing.JList();
        btnPlayVid = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblLastWatched.setText("Last Watched : ");

        jListEpisodes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jListEpisodes);

        btnPlayVid.setText("Watch Selected Episode");
        btnPlayVid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayVidActionPerformed(evt);
            }
        });

        btnBack.setText("<= Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addGap(66, 66, 66)
                        .addComponent(lblLastWatched)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 141, Short.MAX_VALUE)
                        .addComponent(btnPlayVid)
                        .addGap(0, 138, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLastWatched)
                    .addComponent(btnBack))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(btnPlayVid)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPlayVidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayVidActionPerformed
        setupPlayVid();
    }//GEN-LAST:event_btnPlayVidActionPerformed

    private void setupPlayVid() {
        currentShow.updateLastWatched(selected.toString());
        updateLastWatchedLabel();
        TVShowRecordKeeper.updateShowFile();
        TVShowRecordKeeper.playVid(selected.filePath);
    }
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        TVShowRecordKeeper.defaultLocX = this.getX();
        TVShowRecordKeeper.defaultLocY = this.getY();
        TVShowRecordKeeper.sl.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    private void updateLastWatchedLabel() {
        lblLastWatched.setText("Last Watched : " + " " + currentShow.getLastWatchedEpisode());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnPlayVid;
    private javax.swing.JList jListEpisodes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLastWatched;
    // End of variables declaration//GEN-END:variables
}
