package com.mindmap.models;

import java.util.ArrayList;

/**
 * Représente un nœud dans une carte mentale.
 * Chaque nœud peut avoir un nom, un parent, et plusieurs enfants.
 * L’arborescence est limitée à une profondeur maximale définie.
 */
public class Node {
    
    // Profondeur maximale autorisée pour les nœuds enfants (racine = niveau 0)
    private static final int DEPTH = 2;

    private String name;

    private transient Node parent;

    private ArrayList<Node> childrens = new ArrayList<>();

    public Node(){}

    public Node(String name){
        this.name = name;
    }

    public ArrayList<Node> childrens() {
        return childrens;
    }

    public String name() {
        return name;
    }

    public Node parent() {
        return parent;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }

    /**
     * Calcule la profondeur du nœud dans l’arbre, en comptant le nombre
     * d’ancêtres jusqu’à la racine.
     * 
     * @return La profondeur (niveau) du nœud.
     */
    public int getDepth() {
        int depth = 0;
        Node current = this;
        while (current.parent != null) {
            depth++;
            current = current.parent;
        }
        return depth;
    }

    /**
     * Ajoute un enfant à ce nœud, si la profondeur maximale n’est pas dépassée.
     * Met également à jour le parent de l’enfant.
     * 
     * @param child Le nœud enfant à ajouter.
     */
    public void addChild(Node child) {
        if (getDepth() >= DEPTH) {
            System.out.println("Niveau maximum atteint (" + (DEPTH + 1) + " niveaux autorisés)");
            return;
        }
        childrens.add(child);
        child.parent = this;
    }

    /**
     * Supprime un enfant de ce nœud.
     * Détruit également la relation parentale côté enfant pour éviter les références pendantes.
     * 
     * @param child Le nœud enfant à supprimer.
     */
    public void removeChild(Node child) {
        childrens.remove(child);
        child.setParent(null);
    }

}
