package com.mindmap.manager;

import com.mindmap.models.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CLIManagerTest {

    private CLIManager cliManager;

    @BeforeEach
    void setUp() {
        // Crée une MindMap de test
        String initialInput = "TestMap\n"; // Simuler la saisie du nom de la carte mentale
        InputStream input = new ByteArrayInputStream(initialInput.getBytes());
        Scanner scanner = new Scanner(input);
        cliManager = new CLIManager(scanner);
    }

    @Test
    void testAddNode() {
        cliManager.addNode(new String[]{"Child1", "TestMap"});
        Node child = cliManager.getMindMap().findNode("Child1");
        assertNotNull(child);
        assertEquals("Child1", child.name());
    }

    @Test
    void testRenameNode() {
        cliManager.addNode(new String[]{"Child1", "TestMap"});
        cliManager.renameNode(new String[]{"Child1", "ChildRenamed"});
        Node renamed = cliManager.getMindMap().findNode("ChildRenamed");
        assertNotNull(renamed);
        assertEquals("ChildRenamed", renamed.name());
    }

    @Test
    void testDeleteNode() {
        cliManager.addNode(new String[]{"Child1", "TestMap"});
        cliManager.deleteNode(new String[]{"Child1"});
        Node deleted = cliManager.getMindMap().findNode("Child1");
        assertNull(deleted);
    }

    @Test
    void testFindNode() {
        cliManager.addNode(new String[]{"Child1", "TestMap"});
        cliManager.addNode(new String[]{"Child2", "Child1"});
        Node found = cliManager.getMindMap().findNode("Child2");
        assertNotNull(found);
        assertEquals("Child2", found.name());
    }

    @Test
    void testNewMindMap() {
        String input = "TestMap\noui\n";
        Scanner newScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        cliManager = new CLIManager(newScanner);

        cliManager.newMindMap(new String[]{"NouvelleCarte"});

        assertEquals("NouvelleCarte", cliManager.getMindMap().root().name());
    }

    @Test
    void testSaveAndLoadMap() {
        cliManager.addNode(new String[]{"Child1", "TestMap"});
        cliManager.saveMap(new String[]{"testMap.json"});
    
        String input = "TestMap\noui\n";
        Scanner newScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        CLIManager newCliManager = new CLIManager(newScanner);
    
        newCliManager.loadMap(new String[]{"testMap.json"});
        Node child = newCliManager.getMindMap().findNode("Child1");
        assertNotNull(child);
    }
    
    @Test
    void testConfirmOverwrite_yes() {
        String input = "TestMap\noui\n";
        Scanner newScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        CLIManager manager = new CLIManager(newScanner);
    
        assertTrue(manager.confirmOverwrite());
    }
    
    @Test
    void testConfirmOverwrite_no() {
        String input = "TestMap\nnon\n";
        Scanner newScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        CLIManager manager = new CLIManager(newScanner);
    
        assertFalse(manager.confirmOverwrite());
    }
}
