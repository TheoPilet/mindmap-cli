package com.mindmap.models;

import java.util.Stack;

/**
 * Représente une carte mentale sous forme d'arbre, avec un nœud racine,
 * des nœuds enfants et une recherche en profondeur (DFS).
 */
public class MindMap {
    
    private Node root;

    public MindMap() {}

    public MindMap(String name) {
        this.root = new Node(name);
    }

    public Node root() {
        return root;
    }

    /**
     * Utilise une approche itérative avec une pile pour effectuer une recherche en profondeur
     * (DFS) à travers tous les nœuds.
     * 
     * @param name Le titre du nœud à rechercher.
     * @return Le nœud correspondant au name donné, ou null si le nœud n'est pas trouvé.
     */
    public Node findNode(String name) {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (current.name().equalsIgnoreCase(name)) {
                return current;
            }
            // Empile les enfants du nœud courant pour explorer leur sous-arbre
            for (Node child : current.childrens()) {
                stack.push(child);
            }
        }
        return null;
    }

    /**
     * Affiche récursivement l'arbre des nœuds à partir d'un nœud donné.
     * Utilise l'indentation pour montrer la hiérarchie des nœuds.
     * 
     * @param node Le nœud à partir duquel l'arbre doit être affiché.
     * @param indent L'indentation utilisée pour afficher les sous-nœuds.
     */
    public void printTree(Node node, String indent) {
        System.out.println(indent + "- " + node.name());
        for (Node child : node.childrens()) {
            printTree(child, indent + "  ");
        }
    }
}