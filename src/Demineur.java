/**
 * @author Denis Aubin
 */

import javax.swing.*;


public class Demineur extends JFrame {

    public Champ getChamp() {
        return champ;
    }

    private Champ champ;


    public Demineur() { //Partie lancée par defaut au début en EASY
        champ = new Champ("EASY");
        champ.placeMines();
        champ.countMines();
        System.out.println(champ);

        Gui gui = new Gui(this);
        setContentPane(gui);
        setTitle("Démineur");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public Demineur(String level) { // Partie lancée a partir d'un autre endroit que le menu principal
        //Creation du champ
        champ = new Champ(level);
        champ.placeMines();
        champ.countMines();
        System.out.println(champ);

        //Creation du gui
        Gui gui = new Gui(this);
        setContentPane(gui);
        setTitle("Démineur");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


}


