/**
 * @author Denis Aubin
 */


import java.util.*;

import static java.lang.Math.*;

public class Champ {
    final static int DIM = 5;
    static int dimension;
    static int nbMines = 3;
    final static int MINE = 9;
    Random alea = new Random();
    int[][] tab;
    String chosenLevel ;


    public Champ(String level) { //Constructeur du champ -> crée le tableau et prend difficulté
        Level lev = Level.valueOf(level);
        chosenLevel = level;
        dimension = (lev.ordinal() + 1) * 10;
        nbMines = dimension * dimension / 10;
        tab = new int[dimension][dimension];
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                tab[i][j] = 0;
            }
        }
    }


    public void placeMines() { //Place les mines au hasard dans le champ
        for (int i = 0; i < nbMines; i++) {
            int x = alea.nextInt(tab.length); // tirage au sort nb ∈ [0,DIM-1]
            int y = alea.nextInt(tab[0].length); // tirage au sort nb ∈ [0,DIM-1]
            while (tab[x][y] != 0) {
                x = alea.nextInt(tab.length);
                y = alea.nextInt(tab[0].length);
            }
            tab[x][y] = MINE;
        }
    }

    public void countMines() { // Compte le nombre de mines voisines pour affichage
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tab[i][j] == MINE) {
                    int rStart = max(i - 1, 0);
                    int rFinish = min(i + 1, dimension - 1);
                    int cStart = max(j - 1, 0);
                    int cFinish = min(j + 1, dimension - 1);
                    for (int currentRow = rStart; currentRow <= rFinish; currentRow++) {
                        for (int currentCol = cStart; currentCol <= cFinish; currentCol++) {
                            if (tab[currentRow][currentCol] != MINE) {
                                tab[currentRow][currentCol]++;
                            }
                        }
                    }
                }
            }
        }
    }

    public void affText() { // Fonction d'affichage dans la console pour debug
        System.out.print("\n");
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                if (tab[i][j] == MINE) {
                    System.out.print(" X ");
                } else {
                    System.out.print(" ");
                    System.out.print(tab[i][j]);
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    public int getNbMinesRestantes() { // Renvoie le nb de mines du champ
        int nbm = 0;
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                if (tab[i][j] == MINE) {
                    nbm++;
                }
            }
        }
        return nbm;
    }

    public void resetChamp() { //Reset le champ (non utilisé car on dispose le demineur à la sortie)
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                tab[i][j] = 0;
            }
        }
        placeMines();
    }

    public String toString() {
        String output;
        output = "Dimension: " + dimension + "\n" + "Nombre de mines :" + nbMines + "\n";
        affText();
        return output;
    }
}
