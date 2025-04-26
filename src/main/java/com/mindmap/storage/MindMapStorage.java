package com.mindmap.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindmap.models.MindMap;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utilitaire pour la sauvegarde et le chargement d'une carte mentale
 * au format JSON à l'aide de la bibliothèque Gson.
 */
public class MindMapStorage {

    // Objet Gson pour sérialiser/désérialiser les objets MindMap avec indentation
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Sauvegarde une carte mentale dans un fichier JSON.
     *
     * @param map      La carte mentale à sauvegarder.
     * @param filePath Le chemin du fichier de destination (ex : "data/map.json").
     */
    public static void save(MindMap map, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(map, writer);
            System.out.println("Carte sauvegardée dans : " + filePath);
        } catch (IOException e) {
            System.out.println("Erreur de sauvegarde : " + e.getMessage());
        }
    }

    /**
     * Charge une carte mentale à partir d’un fichier JSON.
     *
     * @param filePath Le chemin du fichier à charger.
     * @return La carte mentale chargée, ou null en cas d'erreur.
     */
    public static MindMap load(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, MindMap.class);
        } catch (IOException e) {
            System.out.println("Erreur de chargement : " + e.getMessage());
            return null;
        }
    }
}
