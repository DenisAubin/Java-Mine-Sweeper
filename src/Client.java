/**
 * @author Denis Aubin
 */

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Client  {
    Socket sock;
    DataOutputStream out;
    DataInputStream in;
    String name;
    JFrame clientFrame;
    ClientGUI cGUI;

    public Client(String name, String address) {
        try {

            // Client se connecte au serveur address::8000 avec comme nom name
            Socket sock = new Socket(address, 8000);
            this.sock = sock;
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            this.out = out;
            this.name = name;

            if (name.length() > 0)
                out.writeUTF(name);
            else {
                out.writeUTF("John Doe");
                this.name = "John Doe";
            }

            //Ecoute de la dimension du champ puis creation du gui correspondant
            DataInputStream in = new DataInputStream(sock.getInputStream());
            this.in = in;
            int dimension = in.readInt();
            ClientGUI cGUI = new ClientGUI(dimension, this);
            this.cGUI = cGUI;


            // Gestion de la Frame
            JFrame clientFrame = new JFrame();
            clientFrame.setContentPane(cGUI);
            clientFrame.setTitle("Client " + this.name);
            clientFrame.pack();
            clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            clientFrame.setVisible(true);
            this.clientFrame = clientFrame;

            //Creation du thread d'ecoute du serveur
            ClientThread ct = new ClientThread(this);
            ct.start();


        } catch (UnknownHostException e) {
            System.out.println("Hôte inconnue");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class ClientThread extends Thread { //Thread d'ecoute du serveur
    Client client;

    public ClientThread(Client client) {
        this.client = client;
    }

    public void run() {
        while (true) {
            try {

                String action = client.in.readUTF();
                if (action.substring(0, 1).equals("U")) { // Si on recoit une info de clic gauche
                    int lenX = Integer.parseInt(action.substring(1, 2));
                    int lenY = Integer.parseInt(action.substring(2, 3));
                    int posX = Integer.parseInt(action.substring(3, 3 + lenX));
                    int posY = Integer.parseInt(action.substring(3 + lenX, 3 + lenX + lenY));
                    int inv = Integer.parseInt(action.substring(3 + lenX + lenY));
                    client.cGUI.uncoverCase(posX, posY, inv);

                }
                if (action.substring(0, 1).equals("F")) { // Si on recoit une info de clic droit
                    int lenX = Integer.parseInt(action.substring(1, 2));
                    int lenY = Integer.parseInt(action.substring(2, 3));
                    int posX = Integer.parseInt(action.substring(3, 3 + lenX));
                    int posY = Integer.parseInt(action.substring(3 + lenX));
                    client.cGUI.flagCase(posX, posY);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}