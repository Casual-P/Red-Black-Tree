package com.example.rbtree;

public class Node {
    private int id;
    private String name;
    private Node leftChild;
    private Node rightChild;
    private boolean isRed = true;
    private Node parent;

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public void setRed(boolean red) {
        isRed = red;
    }

    public boolean isRed() {
        return isRed;
    }

    @Override
    public String toString() {
        return "id - " + this.id + ", name - " + this.name;
    }
}
