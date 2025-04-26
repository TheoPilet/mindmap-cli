package com.mindmap.storage;

import com.mindmap.models.MindMap;
import com.mindmap.models.Node;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class MindMapStorageTest {

    private static final String TEST_FILE = "test_map.json";

    @AfterEach
    void cleanUp() throws Exception {
        Files.deleteIfExists(new File(TEST_FILE).toPath());
    }

    @Test
    void testSaveAndLoadMindMap() {
        // Crée une MindMap simple
        MindMap map = new MindMap("Projet");
        Node root = map.root();
        root.addChild(new Node("Idées"));
        root.addChild(new Node("Plan"));
        root.childrens().get(0).addChild(new Node("Brainstorming"));
        root.childrens().get(1).addChild(new Node("Étapes"));

        // Sauvegarde la carte mentale
        MindMapStorage.save(map, TEST_FILE);
        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "Le fichier JSON devrait être créé.");

        // Recharge la carte mentale
        MindMap loadedMap = MindMapStorage.load(TEST_FILE);
        assertNotNull(loadedMap, "La carte mentale chargée ne doit pas être nulle.");
        assertEquals("Projet", loadedMap.root().name(), "Le nom du nœud racine doit être identique.");

        // Vérifie un nœud spécifique
        Node etapes = loadedMap.findNode("Étapes");
        assertNotNull(etapes, "Le nœud 'Étapes' doit être trouvé.");
    }
}