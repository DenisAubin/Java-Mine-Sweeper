/**
 * @author Denis Aubin
 */

import javax.swing.*;
import java.awt.*;


public class ClientGUI extends JPanel   { //Gui du Client en multijoueurs
    CaseClient[][] grid;

    ClientGUI(int dimension, Client client) {

        //Creation du panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //Creation du terrain, la grid
        JPanel mineGrid = new JPanel();
        mineGrid.setLayout(new GridLayout(dimension, dimension));
        grid = new CaseClient[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                CaseClient cc = new CaseClient(i, j, client);
                mineGrid.add(cc);
                grid[i][j]=cc;
            }
        }
        mainPanel.add(mineGrid, BorderLayout.CENTER);

        add(mainPanel);
    }

    public void uncoverCase(int x, int y, int inv) {//Appel de la fonction decouvrant la case
        grid[x][y].decouvreCase(inv);
    }

    public void flagCase(int x, int y) {//Appel de la fonction qui flag la case
        grid[x][y].flagCase();
    }



}
