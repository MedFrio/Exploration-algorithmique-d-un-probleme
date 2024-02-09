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
        frame.setLayout(new GridLayout(2, 1));

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
                String pays=null;
                Pays p = null;
                while (pays == null || pays.equals("")) {
                    // if the user cancel or close the dialog, close
                    if (JOptionPane.showConfirmDialog(null, "Ajouter un pays?", "Ajouter Pays", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                        return;
                    }
                    pays = JOptionPane.showInputDialog("Nom du pays");
                    System.out.println(pays);
                    p = new Pays(pays);
                }
                if (p != null){
                v.ajouterPays(p);
            }
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
                    "Durée (0 MIN - 1000 MAX):", duree
                };
            try {
                int option = JOptionPane.showConfirmDialog(null, message, "Ajouter Destination", JOptionPane.OK_CANCEL_OPTION);
                //verify d is a number
                if (option == JOptionPane.OK_OPTION && !duree.getText().equals("") && !duree.getText().equals(null) && !pays1.getSelectedItem().equals(pays2.getSelectedItem()) && !pays1.getSelectedItem().equals(null) && !pays2.getSelectedItem().equals(null) && Integer.parseInt(duree.getText())>=0 && Integer.parseInt(duree.getText())<=1000 && !pays1.getSelectedItem().equals("") && !pays2.getSelectedItem().equals("")) {
                    Pays p1 = (Pays) pays1.getSelectedItem();
                    Pays p2 = (Pays) pays2.getSelectedItem();
                    int d = Integer.parseInt(duree.getText());
                    p1.addDestination(p2, d);

                    

                }
                else if (JOptionPane.CANCEL_OPTION == option || JOptionPane.CLOSED_OPTION == option) {
                    System.out.println("Cancelled");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Erreur dans les données entrées. Veuillez réessayer.");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Erreur dans les données entrées. Veuillez réessayer.");
            }
            catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null, "Erreur dans les données entrées. Veuillez réessayer.");
            }}});
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
                if (option == JOptionPane.OK_OPTION && pays1.getSelectedItem()!=pays2.getSelectedItem() && !pays1.getSelectedItem().equals(null) && !pays2.getSelectedItem().equals(null)){
                    Pays p1 = (Pays) pays1.getSelectedItem();
                    Pays p2 = (Pays) pays2.getSelectedItem();
                    if (!p1.getPaysVoisins().isEmpty() || !p2.getPaysVoisins().isEmpty()){
                        System.out.println(p1);
                        System.out.println(p2);
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
                    else {
                        JOptionPane.showMessageDialog(null, "Calcul impossible, veuillez ajouter au moins une destinations aux pays sélectionnés.");
                    }

                }
                else if (JOptionPane.CANCEL_OPTION == option || JOptionPane.CLOSED_OPTION == option) {
                    System.out.println("Cancelled");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Erreur dans les données entrées. Veuillez réessayer.");
                }
            }});
        Export_Voyage.addActionListener(new ActionListener() {
            boolean at_least_one = false;
            public void actionPerformed(ActionEvent e) {

                if (v.getPays().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Voyage non exportable, veuillez ajouter des pays.");
                }
                v.getPays().forEach(p -> {
                    if (!p.getPaysVoisins().isEmpty()) {
                        at_least_one = true;
                    }
                
            

                });
                if (at_least_one != true) {
                    JOptionPane.showMessageDialog(null, "Voyage non exportable, veuillez ajouter des destinations à au moins un pays.");
                }
                else if (at_least_one == true) {
                    v.exportVoyage();
                    JOptionPane.showMessageDialog(null, "Voyage exporté avec succès.\n Attention : Ce fichier contient uniquement les pays et leur duree de quarantaine et non les destinations");
                } 
                else {
                    JOptionPane.showMessageDialog(null, "Erreur inconnue.");
                }


            }
        });
    }



    
}
