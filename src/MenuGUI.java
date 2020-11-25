/**
 * @author Denis Aubin
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGUI extends JFrame implements ActionListener {

    //Variables pour les listeners
    private JButton butSolo;
    private JButton butMulti;

    MenuGUI() { //Constructeur du menu principal

        // Panel Principal en border layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Implementation de center panel -> Image bannière

        JPanel banner = new JPanel();
        JLabel image = new JLabel(new ImageIcon(getClass().getResource("img/Demineur.png")));
        banner.add(image);
        mainPanel.add(banner, BorderLayout.CENTER);

        //Implementation du bottom panel -> boutons de selection du mode de jeu
        JPanel botPanel = new JPanel();
        botPanel.setLayout(new FlowLayout());

        // Mode de jeu solo = mode de jeu normal
        JButton butSolo = new JButton("Solo");
        this.butSolo = butSolo;
        butSolo.addActionListener(this);
        botPanel.add(butSolo);

        // Mode de jeu multi -> On peut lancer un serveur et/ou en rejoindre un
        JButton butMulti = new JButton("Multi");
        this.butMulti = butMulti;
        butMulti.addActionListener(this);
        botPanel.add(butMulti);


        mainPanel.add(botPanel, BorderLayout.SOUTH);

        // Gestion de la Frame
        setContentPane(mainPanel);
        setTitle("Menu");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == butSolo) { // Le mode solo a son main dans la classe Demineur.
            new Demineur();
        }
        if (e.getSource() == butMulti) {

            // Boite de dialogue proposant d'heberger ou de rejoindre une partie
            Object options[] = {"Heberger", "Rejoindre"};
            String msg = "Voulez vous heberger ou rejoindre ?";
            String ttl = "Options MultiJoueur";
            int retour = JOptionPane.showOptionDialog(null, msg, ttl, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (retour == JOptionPane.YES_OPTION) { // Choisi Heberger -> Lancement de serveur

                //Boite de dialogues pour choisir la difficulté de la partie
                Object diffOptions[] = {"EASY", "MEDIUM", "HARD"};
                JComboBox diff = new JComboBox(diffOptions);
                Object[] servParameters = {
                        "Difficulté:", diff
                };
                int servDiff = JOptionPane.showConfirmDialog(null, servParameters, "Menu de difficulté", JOptionPane.OK_OPTION);

                if (servDiff == JOptionPane.OK_OPTION) { // Difficultée choisie -> lancement du serveur
                    System.out.println("Difficulté choisie : " + diffOptions[diff.getSelectedIndex()]);
                    new Serveur(diffOptions[diff.getSelectedIndex()].toString());
                }

            }
            if (retour == JOptionPane.NO_OPTION) { // Choisi Rejoindre -> Lancement du Client

                // Boite de dialogue pour definir le client et le serveur a rejoindre
                String nameClient = "";
                String servAddress = "";
                JTextField name = new JTextField("007");
                JTextField serv = new JTextField("localhost");
                Object[] parameters = {
                        "Votre nom:", name,
                        "Adresse du serveur:", serv
                };

                int connect = JOptionPane.showConfirmDialog(null, parameters, "Connect Menu", JOptionPane.OK_OPTION);

                if (connect == JOptionPane.OK_OPTION) { //Paramètres rentrés -> lancement du client
                    nameClient = name.getText();
                    servAddress = serv.getText();
                    Client iencli = new Client(nameClient, servAddress);
                }
            }


        }
    }

}
