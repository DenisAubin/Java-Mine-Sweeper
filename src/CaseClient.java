/**
 * @author Denis Aubin
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class CaseClient extends JPanel implements MouseListener { //Case utilisée par le client en multijoueur

    private final static int DIM = 20;
    private int colorR = 128;
    private int colorG = 128;
    private int colorB = 128;
    private boolean flagged = false;
    private String s = "";
    private int posX;
    private int posY;
    private Client client;

    public CaseClient(int posX, int posY, Client client) {
        setPreferredSize(new Dimension(DIM, DIM));
        addMouseListener(this);
        this.posX = posX;
        this.posY = posY;
        this.client = client;
    }

    public void decouvreCase(int inv) { // Fonction pour le style case découverte
        colorR = 255;
        colorG = 255;
        colorB = 255;
        if (inv == 9) {
            s = "X";
        } else {
            s += inv;
        }
        repaint();
    }

    public void flagCase() { // Fonction pour le style case flagged
        if (flagged) {
            flagged = false;
            colorR = 128;
            colorG = 128;
            colorB = 128;
        } else {
            flagged = true;
            colorR = 255;
            colorG = 0;
            colorB = 0;

        }
        repaint();
    }

    public void sendLeftClick() { // Fonction envoyant au serveur la position et le type de clic
        try{
            client.out.writeUTF("L"+String.valueOf(posX).length() +String.valueOf(posY).length() +posX+posY);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendRightClick() { // Fonction envoyant au serveur la position et le type de clic
        try{
            client.out.writeUTF("R"+String.valueOf(posX).length() +String.valueOf(posY).length() +posX+posY);
        }catch (IOException e) {
            e.printStackTrace();
        }
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
        // Selon clic gauche ou clic droit -> différente fonction
        if (e.getModifiers() == MouseEvent.BUTTON3_MASK && e.getClickCount() == 1) {
            sendRightClick();
        } else {
            sendLeftClick();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
