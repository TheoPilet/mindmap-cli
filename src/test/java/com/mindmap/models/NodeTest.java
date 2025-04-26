package com.mindmap.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void testAddChildWithinDepthLimit() {
        Node root = new Node("Root");
        Node child = new Node("Child");

        root.addChild(child);

        assertEquals(1, root.childrens().size());
        assertEquals(root, child.parent());
    }

    @Test
    void testAddChildExceedingDepthLimit() {
        Node root = new Node("Root");
        Node level1 = new Node("Level1");
        Node level2 = new Node("Level2");
        Node level3 = new Node("TooDeep");

        root.addChild(level1);
        level1.addChild(level2);
        level2.addChild(level3); // ne devrait pas s'ajouter

        assertNull(level3.parent());
        assertTrue(level2.childrens().isEmpty());
    }

    @Test
    void testRemoveChild() {
        Node parent = new Node("Parent");
        Node child = new Node("Child");

        parent.addChild(child);
        parent.removeChild(child);

        assertTrue(parent.childrens().isEmpty());
        assertNull(child.parent());
    }
}
