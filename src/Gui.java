/**
 * @author Denis Aubin
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JPanel implements ActionListener {
    private Demineur demineur;
    private JMenuItem mQuitter;
    private JMenuItem mPause;
    private JMenuItem mReset;
    private JMenuItem mEasy;
    private JMenuItem mMedium;
    private JMenuItem mHard;
    public int nbMinesRestantes;
    public JLabel labelMines;
    public JLabel timeLabel;
    public boolean counting;
    public int time;
    public Timer timer;


    Gui(Demineur demineur) {
        counting=false;
        time=0;
        timer = new Timer(1000,this);
        timer.start();
        this.demineur = demineur;
        Champ champ = demineur.getChamp();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Implémentation du Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        // Implémentation des menus
        // Menue Partie -> Reset et Quitter
        JMenuBar menuBar = new JMenuBar();
        JMenu menuPartie = new JMenu("Partie");
        menuBar.add(menuPartie);
        JMenuItem mPause = new JMenuItem("Pause");
        this.mPause = mPause;
        menuPartie.add(mPause);
        mPause.addActionListener(this);
        JMenuItem mReset = new JMenuItem("Reset");
        this.mReset = mReset;
        menuPartie.add(mReset);
        mReset.addActionListener(this);
        JMenuItem mQuitter = new JMenuItem("Quitter");
        this.mQuitter = mQuitter;
        menuPartie.add(mQuitter);
        mQuitter.addActionListener(this);
        // Menu Difficulté
        JMenu menuDiff = new JMenu("Difficulté");
        menuBar.add(menuDiff);
        JMenuItem mEasy = new JMenuItem("Easy");
        this.mEasy = mEasy;
        menuDiff.add(mEasy);
        mEasy.addActionListener(this);
        JMenuItem mMedium = new JMenuItem("Medium");
        this.mMedium = mMedium;
        menuDiff.add(mMedium);
        mMedium.addActionListener(this);
        JMenuItem mHard = new JMenuItem("Hard");
        this.mHard = mHard;
        menuDiff.add(mHard);
        mHard.addActionListener(this);
        topPanel.add(menuBar);


        mainPanel.add(topPanel, BorderLayout.NORTH);

        //Implémentation du Grid Panel
        JPanel mineGrid = new JPanel();
        mineGrid.setLayout(new GridLayout(champ.tab.length, champ.tab[0].length));
        for (int i = 0; i < champ.tab.length; i++) {
            for (int j = 0; j < champ.tab[0].length; j++) {
                mineGrid.add(new Case(champ.tab[i][j], this));
            }
        }
        mainPanel.add(mineGrid, BorderLayout.CENTER);

        // Implémentation du Panel du bas
        JPanel bottomPanel = new JPanel();
        nbMinesRestantes = champ.getNbMinesRestantes();
        JLabel labelMines = new JLabel("Mines restantes :" + nbMinesRestantes);
        this.labelMines = labelMines;
        bottomPanel.add(labelMines);
        bottomPanel.setLayout(new FlowLayout());
        JLabel timeLabel = new JLabel("Temps: "+time);
        this.timeLabel = timeLabel;
        bottomPanel.add(timeLabel);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public void resetNbMines() {
        labelMines.setText("Mines restantes :" + nbMinesRestantes);
    }

    public void resetTime(){
        timeLabel.setText("Temps: "+time);
    }

    public void lose() {
        Object options[] = {"Recommencer", "Quitter"};
        String msg = "Il restait " + nbMinesRestantes + " mines." +"\n" +"Voulez vous recommencer ou quitter ?";
        String ttl = "PERDU !";
        int retour = JOptionPane.showOptionDialog(null, msg, ttl, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (retour == JOptionPane.YES_OPTION) {
            demineur.dispose();
            new Demineur(demineur.getChamp().chosenLevel);
        }
        if (retour == JOptionPane.NO_OPTION) {
            demineur.dispose();
            }
        }

    public void win() {
        Object options[] = {"Recommencer", "Quitter"};
        String msg = "Bien joué !" +"\n" +"Voulez vous recommencer ou quitter ?";
        String ttl = "GAGNE !";
        int retour = JOptionPane.showOptionDialog(null, msg, ttl, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (retour == JOptionPane.YES_OPTION) {
            demineur.dispose();
            new Demineur(demineur.getChamp().chosenLevel);
        }
        if (retour == JOptionPane.NO_OPTION) {
            demineur.dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == timer ){
            if(counting){
                time+=1;
                resetTime();
            }
        }
        if (e.getSource() == mPause) {
            counting=false;
        }

        if (e.getSource() == mQuitter) {
            System.out.println("Il restait " + nbMinesRestantes + " mines.");
            demineur.dispose();
        }
        if (e.getSource() == mReset) {
            System.out.println("Il restait " + nbMinesRestantes + " mines.");
            String diff = demineur.getChamp().chosenLevel;
            demineur.dispose();
            new Demineur(diff);
        }
        if (e.getSource() == mEasy) {
            System.out.println("Il restait " + nbMinesRestantes + " mines.");
            demineur.dispose();
            new Demineur("EASY");
        }
        if (e.getSource() == mMedium) {
            System.out.println("Il restait " + nbMinesRestantes + " mines.");
            demineur.dispose();
            new Demineur("MEDIUM");
        }
        if (e.getSource() == mHard) {
            System.out.println("Il restait " + nbMinesRestantes + " mines.");
            demineur.dispose();
            new Demineur("HARD");
        }

    }


}
