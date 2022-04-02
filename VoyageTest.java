// Fichier  : VoyageTest.java
// Auteur   : FRIOUICHEN Mohammed
// BUT INFO 2021/2022

//SAE S2.02 exploitation algorithmique d'un problème

/** package & import */

import sae.Pays;
import sae.Voyage;

/** Classe VoyageTest du package sae de la SAE S2.02 exploitation algorithmique d'un problème */
public class VoyageTest{


    public static void main(String args[]) {
        
        //Creation des pays

        Pays paysA = new Pays("FRANCE");
        Pays paysB = new Pays("ESPAGNE");
        Pays paysC = new Pays("BELGIQUE");
        Pays paysD = new Pays("ALLEMAGNE");
        Pays paysE = new Pays("SUISSE");
        Pays paysF = new Pays("PORTUGAL");
        
        //Creation des arretes entre les pays en precisant la duree de quarantaine

        paysA.addDestination(paysB, 10);
        paysA.addDestination(paysC, 15);

        paysB.addDestination(paysD, 12);
        paysB.addDestination(paysF, 15);

        paysC.addDestination(paysE, 10);

        paysD.addDestination(paysE, 2);
        paysD.addDestination(paysF, 1);

        paysF.addDestination(paysE, 5);

        //Creation du graphe (voyage)

        Voyage v = new Voyage();
        
        //Ajout des pays dans notre graphe (voyage)

        v.ajouterPays(paysA);
        v.ajouterPays(paysB);
        v.ajouterPays(paysC);
        v.ajouterPays(paysD);
        v.ajouterPays(paysE);
        v.ajouterPays(paysF);

        //Pays de départ
        Pays pays_dep=paysA;

        //Pays d'arrivée
        Pays pays_arr=paysE;

        //Appliquation de l'algorithme de Dijstra
        v = Voyage.calculerTrajetPlusCourt(v, pays_dep);

        //Affichage du plus court chemin entre le pays de départ et le pays d'arrivée
        System.out.println(Pays.plusCourtChemin(pays_dep,pays_arr));
    }

}
