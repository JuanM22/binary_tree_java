/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juan.tree;

/**
 *
 * @author DELL
 */
public class Tree {

    private NodeT root;

    public Tree() {
    }

    public Tree(NodeT root) {
        this.root = root;
    }

    public NodeT getRoot() {
        return root;
    }

    public void setRoot(NodeT root) {
        this.root = root;
    }

    public String preOrder(NodeT root, String traversal) {
        if (root != null) {
            traversal += root.getValue() + " ";
            traversal = preOrder(root.getLeft(), traversal);
            traversal = preOrder(root.getRight(), traversal);
        }
        return traversal;
    }

    public String inOrder(NodeT root, String traversal) {
        if (root != null) {
            traversal = inOrder(root.getLeft(), traversal);
            traversal += root.getValue() + " ";
            traversal = inOrder(root.getRight(), traversal);
        }
        return traversal;
    }

    public String postOrder(NodeT root, String traversal) {
        if (root != null) {
            traversal = postOrder(root.getLeft(), traversal);
            traversal = postOrder(root.getRight(), traversal);
            traversal += root.getValue() + " ";
        }
        return traversal;
    }
    
}
