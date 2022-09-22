package com.example.rbtree;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RedBlackTree {

    public final String ANSI_RESET = "\u001B[0m";
    public final String ANSI_BLACK = "\u001B[30m";
    public final String ANSI_RED = "\u001B[31m";
    public final String ANSI_GREEN = "\u001B[32m";
    public final String ANSI_YELLOW = "\u001B[33m";
    public final String ANSI_BLUE = "\u001B[34m";
    public final String ANSI_PURPLE = "\u001B[35m";
    public final String ANSI_CYAN = "\u001B[36m";
    public final String ANSI_WHITE = "\u001B[37m";

    private Node root;
    int allCount = 0;
    boolean flag = true;

    public void insert(int key, String name) {
        Node newNode = new Node(key, name);

        if (root == null) {
            root = newNode;
            root.setRed(false);
            System.out.println("Root inserted");
        } else {

            Node current = root;
            Node parent = current;

            while (current != null) {
                parent = current;
                switchColors(current);
                rotate(current);
                if (key < current.getId()) {
                    current = current.getLeftChild();
                } else {
                    current = current.getRightChild();
                }
            }
            if (key < parent.getId())
                parent.setLeftChild(newNode);
            else
                parent.setRightChild(newNode);

            newNode.setParent(parent);
            rotate(newNode);
        }
    }

    private void switchColors(Node current) {
        if (current.getLeftChild() != null && current.getRightChild() != null) {
            if (!current.isRed() && current.getLeftChild().isRed() && current.getRightChild().isRed()) {
                flip(current);
                flip(current.getLeftChild());
                flip(current.getRightChild());
            }
            root.setRed(false);
        }
    }

    private void flip(Node current) {
        current.setRed(!current.isRed());
    }


    private void rotate(Node current) {
        Node parent = current.getParent();
        if (parent == null)
            return;
        Node grandparent = parent.getParent();
        if (grandparent != null) {
            if (current.isRed() && parent.isRed()) {

                if (parent == grandparent.getLeftChild() && current == parent.getLeftChild()) {
                    setChild(parent, grandparent);
                    if (parent.getRightChild() != null)
                        parent.getRightChild().setParent(grandparent);

                    grandparent.setLeftChild(parent.getRightChild());
                    parent.setRightChild(grandparent);
                    flip(parent);
                    flip(grandparent);
                } else if (parent == grandparent.getRightChild() && current == parent.getRightChild()) {
                    setChild(parent, grandparent);

                    if (parent.getLeftChild() != null)
                        parent.getLeftChild().setParent(grandparent);

                    grandparent.setRightChild(parent.getLeftChild());
                    parent.setLeftChild(grandparent);
                    flip(parent);
                    flip(grandparent);
                } else if (parent == grandparent.getLeftChild() && current == parent.getRightChild()) {
                    grandparent.setLeftChild(current);
                    current.setParent(grandparent);
                    parent.setRightChild(current.getLeftChild());
                    if (current.getLeftChild() != null)
                        current.getLeftChild().setParent(parent);

                    current.setLeftChild(parent);
                    parent.setParent(current);

                    rotate(parent);
                } else if (parent == grandparent.getRightChild() && current == parent.getLeftChild()) {
                    grandparent.setRightChild(current);
                    current.setParent(grandparent);
                    parent.setLeftChild(current.getRightChild());
                    if (current.getRightChild() != null)
                        current.getRightChild().setParent(parent);
                    current.setRightChild(parent);
                    parent.setParent(current);
                    rotate(parent);
                }
            }
        }
    }

    private void setChild(Node parent, Node grandparent) {
        if (grandparent == root) {
            root = parent;
        } else {
            if (grandparent.getId() < grandparent.getParent().getId()) {
                grandparent.getParent().setLeftChild(parent);
            } else {
                grandparent.getParent().setRightChild(parent);
            }
        }
        parent.setParent(grandparent.getParent());
        grandparent.setParent(parent);
    }

    public void displayTree() {
        if (root != null)
            inOrder(root);
        else
            System.out.println(ANSI_RED + "No Any Data" + ANSI_RESET);
    }

    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.getLeftChild());
            if (localRoot.isRed())
                System.out.println(ANSI_RED + "id = " + localRoot.getId() + ", name - " + localRoot.getName() + ANSI_RESET);
            else
                System.out.println("id = " + localRoot.getId() + ", name - " + localRoot.getName());
            inOrder(localRoot.getRightChild());
        }
    }

    private void rnd(int num) {
        int key;
        int n = 0;
        while (n < num) {
            key = (int) (Math.random() * 1000);
            insert(key, "name");
            System.out.println("key = " + key);
            n++;
        }
    }

    private void checkBH(Node current) {
        if (current != null) {
            checkBH(current.getLeftChild());
            if (current.getLeftChild() == null && current.getRightChild() == null || current.getLeftChild() != null && current.getRightChild() == null || current.getLeftChild() == null && current.getRightChild() != null) {
                int count = 0;
                Node blackCount = current;
                while (blackCount != null) {
                    if (!blackCount.isRed())
                        count++;
                    blackCount = blackCount.getParent();
                }
                if (allCount != 0 && allCount < count || allCount != 0 && allCount > count) {
                    System.out.println("AllCount = " + allCount + ", count = " + count);
                    flag = false;
                }
                allCount = count;
            }
            checkBH(current.getRightChild());
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int key;
        String name;
        while (true) {
            System.out.println("Input: s, i, clear, exit, info");
            try {
                String choice = scanner.next();

                if (scanner.hasNextLine()) {
                    switch (choice) {
                        case "s" -> {
                            displayTree();
                        }
                        case "i" -> {
                            System.out.println("input key");
                            key = scanner.nextInt();
                            System.out.println("input value");
                            name = scanner.next();
                            insert(key, name);
                        }
                        case "exit" -> {
                            scanner.close();
                            return;
                        }
                        case "clear" -> root = null;
                        case "ch" -> {
                            if (root == null)
                                System.out.println(ANSI_RED + "No Any Data" + ANSI_RESET);
                            else {
                                System.out.println("root.getLeftChild = " + root.getLeftChild().getId() + ", root = " + root.getId() + ", rootRightChild = " + root.getRightChild().getId());
                                allCount = 0;
                                checkBH(root);
                            }
                        }
                        case "rnd" -> {
                            System.out.println("Input inserts count");
                            key = scanner.nextInt();
                            rnd(key);
                        }
                        case "info" -> System.out.println("Commands desc:\ni - inserts Node with key and data (name) in rbTree\ns - display rbTree\nclear - clear rbTree\nrnd (beta) - inserts random values in tree\nch - display root and roots left/right child");
                    }
                }
            } catch (InputMismatchException ex) {
                System.out.println(ANSI_YELLOW + "Oooops...something went wrong" + ANSI_RESET);
                scanner.next();
            }
        }
    }

}
