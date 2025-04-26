package com.mindmap.manager;

import com.mindmap.models.MindMap;
import com.mindmap.models.Node;
import com.mindmap.storage.MindMapStorage;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Classe qui gère l'interaction avec l'utilisateur via la ligne de commande
 * pour manipuler une carte mentale. La classe gère les commandes à l'aide 
 * d'une boucle principale qui attend les entrées de l'utilisateur,
 * interprète ces entrées, et exécute l'action correspondante.
 */
public class CLIManager {
    private static final String PATH = "data/";
    private MindMap mindMap;
    private Scanner scanner;
    private final Map<String, Consumer<String[]>> commands = new HashMap<>();
    private final Map<CommandKey, String> commandUsages = new HashMap<>();

    private enum CommandKey {
        AJOUTER, RENOMER, SUPPRIMER, TROUVER, CHEMAIN, AFFICHER, ENREGISTRER, CHARGER, NOUVELLE, QUITTER
    }

    public CLIManager() {
        this(new Scanner(System.in));
    }

    /**
     * getter utilisé dans CLIManagerTest
     */
    public MindMap getMindMap(){ 
        return mindMap;
    }

    /**
     * Constructeur principal. Initialise la carte mentale et les commandes.
     */
    public CLIManager(Scanner scanner) {
        this.scanner = scanner;
        System.out.println("Bienvenue dans MindMap CLI");
        System.out.print("Nom de la carte mentale : ");
        this.mindMap = new MindMap(scanner.nextLine());
        initCommands();
    }
    
    /**
     * Initialise la liste des commandes disponibles et leur usage.
     */
    private void initCommands() {
        commandUsages.put(CommandKey.AJOUTER, CommandKey.AJOUTER.name().toLowerCase() + " <nom>, <parent>");
        commands.put(CommandKey.AJOUTER.name().toLowerCase(), this::addNode);

        commandUsages.put(CommandKey.RENOMER, CommandKey.RENOMER.name().toLowerCase() + " <ancien>, <nouveau>");
        commands.put(CommandKey.RENOMER.name().toLowerCase(), this::renameNode);

        commandUsages.put(CommandKey.SUPPRIMER, CommandKey.SUPPRIMER.name().toLowerCase() + " <nom>");
        commands.put(CommandKey.SUPPRIMER.name().toLowerCase(), this::deleteNode);

        commandUsages.put(CommandKey.TROUVER, CommandKey.TROUVER.name().toLowerCase() + " <nom>");
        commands.put(CommandKey.TROUVER.name().toLowerCase(), this::findNode);

        commandUsages.put(CommandKey.AFFICHER, CommandKey.AFFICHER.name().toLowerCase());
        commands.put(CommandKey.AFFICHER.name().toLowerCase(), args -> mindMap.printTree(mindMap.root(), ""));

        commandUsages.put(CommandKey.ENREGISTRER, CommandKey.ENREGISTRER.name().toLowerCase() + " <fichier.json>");
        commands.put(CommandKey.ENREGISTRER.name().toLowerCase(), this::saveMap);

        commandUsages.put(CommandKey.CHARGER, CommandKey.CHARGER.name().toLowerCase() + " <fichier.json>");
        commands.put(CommandKey.CHARGER.name().toLowerCase(), this::loadMap);

        commandUsages.put(CommandKey.NOUVELLE, CommandKey.NOUVELLE.name().toLowerCase() + " <nom>");
        commands.put(CommandKey.NOUVELLE.name().toLowerCase(), this::newMindMap);

        commandUsages.put(CommandKey.QUITTER, CommandKey.QUITTER.name().toLowerCase());
        commands.put(CommandKey.QUITTER.name().toLowerCase(), args -> System.exit(0));
    }

    /**
     * Boucle principale qui affiche les commandes disponibles
     * et interprète les entrées utilisateur.
     */
    public void start() {
        while (true) {
            System.out.println("\nCommandes :");
            for (CommandKey key : CommandKey.values()) {
                System.out.println("  " + commandUsages.get(key));
            }
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+", 2);
            String cmd = parts[0];
            String[] args = parts.length > 1 ? parts[1].split(",\\s*") : new String[0];

            Consumer<String[]> action = commands.get(cmd);
            if (action != null) {
                action.accept(args);
            } else {
                System.out.println("Commande inconnue.");
            }
        }
    }

    /**
     * Ajoute un nouveau nœud à un parent existant.
     * @param args [nom du nœud, nom du parent]
     */
    public void addNode(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage : " + commandUsages.get(CommandKey.AJOUTER));
            return;
        }

        String name = args[0], parentName = args[1];
        if (mindMap.findNode(name) != null) {
            System.out.println("Un nœud avec ce nom existe déjà.");
            return;
        }

        Node parent = mindMap.findNode(parentName);
        if (parent == null) {
            System.out.println("Parent introuvable.");
            return;
        }

        parent.addChild(new Node(name));
        System.out.println("Nœud ajouté.");
    }

    /**
     * Renomme un nœud existant.
     * @param args [ancien nom, nouveau nom]
     */
    public void renameNode(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage : " + commandUsages.get(CommandKey.RENOMER));
            return;
        }

        Node node = mindMap.findNode(args[0]);
        if (node == null) {
            System.out.println("Nœud introuvable.");
            return;
        }

        if (mindMap.findNode(args[1]) != null) {
            System.out.println("Ce nom est déjà utilisé par un autre nœud.");
            return;
        }

        node.setName(args[1]);
        System.out.println("Nœud renommé.");
    }

    /**
     * Supprime un nœud existant (hors racine).
     * @param args [nom du nœud à supprimer]
     */
    public void deleteNode(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage : " + commandUsages.get(CommandKey.SUPPRIMER));
            return;
        }

        Node node = mindMap.findNode(args[0]);
        if (node == null || node.parent() == null) {
            System.out.println("Nœud introuvable ou suppression interdite.");
            return;
        }

        node.parent().removeChild(node);
        System.out.println("Nœud supprimé.");
    }

    /**
     * Trouve et affiche le chemin vers un nœud donné.
     * @param args [nom du nœud]
     */
    public void findNode(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage : " + commandUsages.get(CommandKey.TROUVER));
            return;
        }

        Node node = mindMap.findNode(args[0]);
        if (node == null) {
            System.out.println("Nœud non trouvé.");
        } else {
            System.out.print("Chemin : ");
            printPath(node);
            System.out.println();
        }
    }

    /**
     * Affiche récursivement le chemin d’un nœud à partir de la racine.
     * @param node Le nœud cible
     */
    private void printPath(Node node) {
        if (node.parent() != null) {
            printPath(node.parent());
        }
        System.out.print("/" + node.name());
    }

    /**
     * Sauvegarde la carte mentale dans un fichier JSON.
     * @param args [nom du fichier]
     */
    public void saveMap(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage : " + commandUsages.get(CommandKey.ENREGISTRER));
            return;
        }

        MindMapStorage.save(mindMap, PATH + args[0]);
        System.out.println("Carte enregistrée.");
    }

    /**
     * Charge une carte mentale depuis un fichier JSON, après confirmation.
     * @param args [nom du fichier]
     */
    public void loadMap(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage : " + commandUsages.get(CommandKey.CHARGER));
            return;
        }

        if (!confirmOverwrite()) return;

        MindMap loaded = MindMapStorage.load(PATH + args[0]);
        if (loaded != null) {
            this.mindMap = loaded;
            System.out.println("Carte chargée avec succès !");
        } else {
            System.out.println("Échec du chargement.");
        }
    }

    /**
     * Crée une nouvelle carte mentale vide avec un nom donné, après confirmation.
     * @param args [nom de la nouvelle carte]
     */
    public void newMindMap(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage : " + commandUsages.get(CommandKey.NOUVELLE));
            return;
        }

        if (!confirmOverwrite()) return;

        this.mindMap = new MindMap(args[0]);
        System.out.println("Nouvelle carte créée !");
    }

    /**
     * Demande à l'utilisateur une confirmation avant d'écraser la carte mentale actuelle.
     * @return true si l'utilisateur confirme, false sinon.
     */
    public boolean confirmOverwrite() {
        System.out.println("Cela va remplacer la carte mentale actuelle et les modifications non enregistrées seront perdues.");
        System.out.print("Voulez-vous continuer ? (oui/non) : ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("oui")) {
            System.out.println("Annulé.");
            return false;
        }
        return true;
    }
}

