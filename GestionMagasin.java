//Partie 1 : Déclarations et Variables Globales
import java.util.Scanner;
import java.io.*;

public class GestionMagasin {
    static final int MAX_PRODUITS = 100;
    static String[] noms = new String[MAX_PRODUITS];
    static double[] prix = new double[MAX_PRODUITS];
    static int[] quantites = new int[MAX_PRODUITS];
    static int nbProduits = 0;
    
    static Scanner scanner = new Scanner(System.in);
    static final String NOM_FICHIER = "stock.txt";

//Partie 2 : Fonctions d'Affichage et Saisie
public static void afficherMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Ajouter un nouveau produit");
        System.out.println("2. Afficher l'inventaire complet");
        System.out.println("3. Rechercher un produit");
        System.out.println("4. Réaliser une vente");
        System.out.println("5. Afficher les alertes");
        System.out.println("6. Sauvegarder les données");
        System.out.println("7. Quitter");
        System.out.print("Choix : ");
    }

    public static int saisirEntier(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        int valeur = scanner.nextInt();
        scanner.nextLine();
        return valeur;
    }

//Partie 3 : Gestion de l'Inventaire
public static void ajouterProduit() {
        if (nbProduits < MAX_PRODUITS) {
            System.out.print("Nom : ");
            noms[nbProduits] = scanner.nextLine();
            System.out.print("Prix : ");
            prix[nbProduits] = scanner.nextDouble();
            System.out.print("Quantité : ");
            quantites[nbProduits] = scanner.nextInt();
            scanner.nextLine();
            nbProduits++;
        }
    }

    public static int rechercherProduit(String nom) {
        for (int i = 0; i < nbProduits; i++) {
            if (noms[i].equalsIgnoreCase(nom)) {
                return i;
            }
        }
        return -1;
    }

//Partie 4 : Gestion des Ventes
public static void effectuerVente() {
        System.out.print("Nom du produit : ");
        String nom = scanner.nextLine();
        int index = rechercherProduit(nom);

        if (index != -1) {
            System.out.print("Quantité : ");
            int qte = scanner.nextInt();
            scanner.nextLine();

            if (quantites[index] >= qte) {
                double total = prix[index] * qte;
                if (total > 1000) {
                    total *= 0.9;
                }
                quantites[index] -= qte;
                System.out.println("Ticket de caisse : " + total + " DH");
            }
        }
    }

//Partie 5 : Statistiques et Rapports

public static void afficherStock() {
        System.out.println("NOM | PRIX | QTE | VALEUR");
        for (int i = 0; i < nbProduits; i++) {
            System.out.println(noms[i] + " | " + prix[i] + " | " + quantites[i] + " | " + (prix[i] * quantites[i]));
        }
    }

    public static void etatAlerte() {
        for (int i = 0; i < nbProduits; i++) {
            if (quantites[i] < 5) {
                System.out.println("Alerte : " + noms[i] + " (Stock bas)");
            }
        }
    }

//Partie 6 : Gestion des Fichiers

public static void sauvegarderStock() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(NOM_FICHIER))) {
            for (int i = 0; i < nbProduits; i++) {
                pw.println(noms[i] + ";" + prix[i] + ";" + quantites[i]);
            }
        } catch (IOException e) { }
    }

    public static void chargerStock() {
        File f = new File(NOM_FICHIER);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] d = ligne.split(";");
                noms[nbProduits] = d[0];
                prix[nbProduits] = Double.parseDouble(d[1]);
                quantites[nbProduits] = Integer.parseInt(d[2]);
                nbProduits++;
            }
        } catch (Exception e) { }
    }

//Partie 7 : Menu Principal

public static void main(String[] args) {
        chargerStock();
        int choix;
        do {
            afficherMenu();
            choix = saisirEntier("");
            switch (choix) {
                case 1: ajouterProduit(); break;
                case 2: afficherStock(); break;
                case 3: 
                    System.out.print("Nom : ");
                    int res = rechercherProduit(scanner.nextLine());
                    System.out.println(res != -1 ? "Trouvé" : "Non trouvé");
                    break;
                case 4: effectuerVente(); break;
                case 5: etatAlerte(); break;
                case 6: sauvegarderStock(); break;
                case 7: sauvegarderStock(); break;
            }
        } while (choix != 7);
    }
}


