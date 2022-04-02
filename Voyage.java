// Fichier  : Voyage.java
// Auteur   : FRIOUICHEN Mohammed
// BUT INFO 2021/2022

//SAE S2.02 exploitation algorithmique d'un probleme

/** package & import */

package sae;
import java.util.*;
import java.util.Map.Entry;

/** Classe Voyage (Graphe) du package sae de la SAE S2.02 exploitation algorithmique d'un probleme  */
public class Voyage {


    /** HashSet est une liste de pays */
    private Set<Pays> pays = new HashSet<>();
    
    /** Getter permettant de récupérer les pays  */
    public Set<Pays> getPays() {
        return pays;
    }
    
    /** Methode permetant d'ajouter un pays prenant un pays en argument */
    public void ajouterPays(Pays p) {
        pays.add(p);
    }


    /** Methode static prenant un hashset de pays en argument, représentant les pays non traites de l'algorithme et retourne un int de duree la plus courte */
    private static Pays trouverDureePlusCourte(Set < Pays > paysNonTraites) {
        Pays quarantainePlusCourte_Pays = null;
        int quarantainePlusCourte = Integer.MAX_VALUE;
        for (Pays pays: paysNonTraites) {
            int quarantainePays = pays.getQuarantaine();
            if (quarantainePays < quarantainePlusCourte) {
                quarantainePlusCourte = quarantainePays;
                quarantainePlusCourte_Pays = pays;
            }
        }
        return quarantainePlusCourte_Pays;
    }

    /** Methode static prenant un pays, un int et un autre pays en argument, permetant de calculer la duree minimal entre chacun d'eux (necessaires pour l'algorithme)  */
    private static void calculerDureeMinimale(Pays paysEtudie,Integer coutArretesQuarantaines, Pays paysDep) {
        Integer sourceQuarantaine = paysDep.getQuarantaine();
        if (sourceQuarantaine + coutArretesQuarantaines < paysEtudie.getQuarantaine()) {
            paysEtudie.setQuarantaine(sourceQuarantaine + coutArretesQuarantaines);
            LinkedList<Pays> courtTrajet = new LinkedList<>(paysDep.getCourtTrajet());
            courtTrajet.add(paysDep);
            paysEtudie.setCourtTrajet(courtTrajet);
        }

    }
    
    /** Methode static prenant un voyage et un pays en argument, grace à l'utilisation de deux HashSet nous pourrons produire des valeurs tampons des pays traites et non traites
     * Cette méthode représente l'algorithme de Moore Dijkstra et renvois un voyage (Graphe)
     */
    public static Voyage calculerTrajetPlusCourt(Voyage voyage, Pays p) {
        //Non application de la duree de quarantaine pour le pays de depart
        p.setQuarantaine(0);
    
        Set<Pays> paysTraites = new HashSet<>();
        Set<Pays> paysNonTraites = new HashSet<>();
    
        paysNonTraites.add(p);
    
        while (paysNonTraites.size() != 0) {
            Pays paysActuelle = trouverDureePlusCourte(paysNonTraites);
            paysNonTraites.remove(paysActuelle);
            for (Entry < Pays, Integer> adjacencyPair: 
                paysActuelle.getPaysVoisins().entrySet()) {
                Pays paysVoisins = adjacencyPair.getKey();
                Integer coutArretesQuarantaines = adjacencyPair.getValue();
                if (!paysTraites.contains(paysVoisins)) {
                    calculerDureeMinimale(paysVoisins, coutArretesQuarantaines, paysActuelle);
                    paysNonTraites.add(paysVoisins);
                }
            }
            paysTraites.add(paysActuelle);
        }
        
        return voyage;
    }



}
