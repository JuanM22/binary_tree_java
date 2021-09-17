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
public class NodeT {

    private int value;
    private NodeT left;
    private NodeT right;

    public NodeT() {
    }

    public NodeT(int value, NodeT left, NodeT right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public NodeT getLeft() {
        return left;
    }

    public void setLeft(NodeT left) {
        this.left = left;
    }

    public NodeT getRight() {
        return right;
    }

    public void setRight(NodeT right) {
        this.right = right;
    }
    
}
