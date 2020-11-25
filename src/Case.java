/**
 * @author Denis Aubin
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Case extends JPanel implements MouseListener {

    private final static int DIM = 20;
    private int colorR = 128;
    private int colorG = 128;
    private int colorB = 128;
    private String s = " ";
    private int inventory;
    private boolean flagged = false;
    private boolean turned = false;
    private Gui gui;

    public Case(int inv, Gui gui) {
        this.gui = gui;
        setPreferredSize(new Dimension(DIM, DIM));
        addMouseListener(this);
        inventory = inv;
    }


    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        gc.setColor(new Color(colorR, colorG, colorB));
        gc.fillRect(1, 1, 18, 18);
        gc.setColor(new Color(0, 0, 0));
        gc.drawString(s, 5, 15);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if(!gui.counting){
            gui.counting = true;
        }
        if (e.getModifiers() == MouseEvent.BUTTON3_MASK && e.getClickCount() == 1 && turned==false) {
            // Si clic droit
            if (flagged) { // Si la case est deja flag -> déflag
                flagged = false;
                colorR = 128;
                colorG = 128;
                colorB = 128;
                if (inventory == 9) { //Si mine augmenter le nb de mines restantes
                    gui.nbMinesRestantes++;
                    gui.resetNbMines();
                }
            } else { // Si la case est pas flag -> flag
                flagged = true;
                colorR = 255;
                colorG = 0;
                colorB = 0;
                if (inventory == 9) { // Si mine diminuer le nb de mines restantes, lancer win si dernière.
                    gui.nbMinesRestantes--;
                    gui.resetNbMines();
                    if (gui.nbMinesRestantes == 0){
                        gui.win();
                    }
                }
            }
        } else { // Si clic gauche
            turned = true; // On ne peut plus la retourner
            flagged = false; // Elle n'est plus flag
            colorR = 255;
            colorG = 255;
            colorB = 255;
            if (inventory == 9) {
                s = "X";
                gui.lose(); // Defaite si mine
            } else {
                s = "" + inventory; // On affiche le nb de voisins
            }

        }
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
