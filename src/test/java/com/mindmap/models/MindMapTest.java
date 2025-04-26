package com.mindmap.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class MindMapTest {

    @Test
    void testCreateAndFindNode() {
        MindMap map = new MindMap("Root");
        Node root = map.root();

        Node child = new Node("Child");
        root.addChild(child);

        assertEquals(child, map.findNode("Child"));
    }

    @Test
    void testFindNodeNotFound() {
        MindMap map = new MindMap("Root");

        assertNull(map.findNode("Inconnu"));
    }

    @Test
void testPrintTree() {
    MindMap map = new MindMap("Root");
    Node root = map.root();

    Node child1 = new Node("Child1");
    Node child2 = new Node("Child2");
    Node grandChild = new Node("GrandChild");

    root.addChild(child1);
    root.addChild(child2);
    child1.addChild(grandChild);

    // Capturer la sortie console
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));

    try {
        map.printTree(root, "");
    } finally {
        System.setOut(originalOut);
    }
    
    String expectedOutput =
            "- Root\n" +
            "  - Child1\n" +
            "    - GrandChild\n" +
            "  - Child2\n";

    assertEquals(expectedOutput, outContent.toString());
}
}
