import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import sae.Pays;
import sae.Voyage;

public class GUI {


    //Affichage graphique pour la creation de pays et voyages ou on peut add destination, ajouter pays, calculer trajet plus court, plus court chemin
    public static void main(String args[]) {
        Voyage v = new Voyage();
        JFrame frame = new JFrame("Voyage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(4, 1));

        JButton addPays = new JButton("Ajouter Pays");
        JButton addDestination = new JButton("Ajouter Destination");
        JButton calculerTrajetPlusCourt = new JButton("Calculer Trajet Plus Court");
        JButton Export_Voyage = new JButton("Export Voyage");

        frame.add(addPays);
        frame.add(addDestination);
        frame.add(calculerTrajetPlusCourt);
        frame.add(Export_Voyage);

        frame.setVisible(true);

        addPays.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pays = JOptionPane.showInputDialog("Nom du pays");
                Pays p = new Pays(pays);
                v.ajouterPays(p);
            }
        });

        addDestination.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Choix uniquement de pays existant dans voyages en utilisant getPays
                JComboBox<Pays> pays1 = new JComboBox<Pays>();
                for (Pays p : v.getPays()) {
                    pays1.addItem(p);
                }
                JComboBox<Pays> pays2 = new JComboBox<Pays>();
                for (Pays p : v.getPays()) {
                    pays2.addItem(p);
                }
                JTextField duree = new JTextField();
                Object[] message = {
                    "Pays départ:", pays1,
                    "Pays arrivée:", pays2,
                    "Duree:", duree
                };
                int option = JOptionPane.showConfirmDialog(null, message, "Ajouter Destination", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Pays p1 = (Pays) pays1.getSelectedItem();
                    Pays p2 = (Pays) pays2.getSelectedItem();
                    int d = Integer.parseInt(duree.getText());
                    p1.addDestination(p2, d);
                }
            }});
        calculerTrajetPlusCourt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox<Pays> pays1 = new JComboBox<Pays>();
                for (Pays p1 : v.getPays()) {
                    pays1.addItem(p1);
                }
                JComboBox<Pays> pays2 = new JComboBox<Pays>();
                for (Pays p2 : v.getPays()) {
                    pays2.addItem(p2);
                }
                Object[] message = {
                    "Pays départ:", pays1,
                    "Pays arrivée:", pays2
                };
                int option = JOptionPane.showConfirmDialog(null, message, "Calculer Trajet Plus Court", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Pays p1 = (Pays) pays1.getSelectedItem();
                    Pays p2 = (Pays) pays2.getSelectedItem();
                    Voyage.calculerTrajetPlusCourt(v, p1);
                    System.out.println(Pays.plusCourtChemin(p1, p2));
                    //frame that shows the shortest path
                    JFrame frame2 = new JFrame("Plus court chemin");
                    frame2.setSize(400, 400);
                    frame2.setLayout(new GridLayout(1, 1));
                    JLabel label = new JLabel(Pays.plusCourtChemin(p1, p2));
                    frame2.add(label);
                    frame2.setVisible(true);


                }
            }});
        Export_Voyage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                v.exportVoyage();
            }
        });
    }



    
}
