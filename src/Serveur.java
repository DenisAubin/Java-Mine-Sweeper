/**
 * @author Denis Aubin
 */

import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class Serveur implements Runnable {

    String diff;
    private Thread process;
    public Champ champ;
    ArrayList<DataOutputStream> sortieClients;

    Serveur(String diff) {
        this.diff = diff;
        process = new Thread(this);
        process.start();
    }

    public void run() {
        System.out.println("Démarrage du serveur.");

        //Creation du champ
        champ = new Champ(diff);
        champ.placeMines();
        champ.countMines();
        Level lev = Level.valueOf(diff);
        int dimension = (lev.ordinal() + 1) * 10;

        sortieClients= new ArrayList<>(); //Liste des sorties pour le broadcast des clics

        try {
            ServerSocket servSock = new ServerSocket(8000); //Ouverture du serveur
            while (true) {

                Socket socket = servSock.accept(); //Acceptation de client
                System.out.println("Nouvelle connection au serveur.");

                //Lancement du thread propre au client
                ServerThread st = new ServerThread(socket,dimension,this);
                st.start();
            }
            /**
             gestSock.close();
             **/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(String msg){ // Fonction qui broadcast le msg à tous les clients
        for (DataOutputStream s: sortieClients) {
            try {
                s.writeUTF(msg);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ServerThread extends Thread { //Thread Serveur propre a un client

    Socket socket = null;
    int dimension;
    Serveur server;


    public ServerThread(Socket s, int dimension, Serveur server) {
        this.socket = s;
        this.dimension=dimension;
        this.server=server;
    }

    public void run() {
        boolean connected = true;
        try {

            DataInputStream entree = new DataInputStream(socket.getInputStream());
            DataOutputStream sortie = new DataOutputStream(socket.getOutputStream());
            server.sortieClients.add(sortie);//Ajoute la sortie a la liste des sorties pour broadcast

            //Lecture du nom du client
            String nomJoueur = entree.readUTF();
            System.out.println(nomJoueur + " connecté");

            sortie.writeInt(dimension); //Envoi de la dimension au client pour son gui

            while (connected) {//Tant que le client est connecté

                //Lecture de l'entree
                String action= entree.readUTF();

                if (action.substring(0,1).equals("L")){ // Si clic gauche -> boroadcast de decouvrir la case
                    int lenX = Integer.parseInt(action.substring(1,2));
                    int lenY = Integer.parseInt(action.substring(2,3));
                    int posX = Integer.parseInt(action.substring(3,3+lenX));
                    int posY = Integer.parseInt(action.substring(3+lenX));
                    System.out.println(nomJoueur+ " a uncover ("+posX+","+posY+")");
                    server.sendToAll("U"+lenX+lenY+posX+posY+server.champ.tab[posX][posY]);
                }
                if (action.substring(0,1).equals("R")){ //Si clic droit -> broadcast de flag la case
                    int lenX = Integer.parseInt(action.substring(1,2));
                    int lenY = Integer.parseInt(action.substring(2,3));
                    int posX = Integer.parseInt(action.substring(3,3+lenX));
                    int posY = Integer.parseInt(action.substring(3+lenX));
                    System.out.println(nomJoueur+ " a flag ("+posX+","+posY+")");
                    server.sendToAll("F"+lenX+lenY+posX+posY);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}