package com.juan.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author DELL
 */
public class Main {

    public void run() {
        String listRepresentation = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ///////////////////////////////////////////
        Map<Integer, String> trees = new HashMap<>();
        trees.put(1, "1(2(4(1,6),5(3,4)),3(9,7))");
        trees.put(2, "25(15(10(4,12),22(18,24)),50(35(31,44),70(66,90)))");
        trees.put(3, "1(2(4(8(_,13),_),5(_,9(14,_))),3(6(10(_,15),_),7(11,12)))");
        trees.put(4, "43(15(8,30(20,35)),60(50,82(70,_)))");

        int option = 5;

        System.out.println("******** MENÚ ********");
        System.out.println("[1] Primer ejemplo");
        System.out.println("[2] Segundo ejemplo");
        System.out.println("[3] Tercer ejemplo");
        System.out.println("[4] Cuarto ejemplo");
        System.out.println("[5] Salir");

        try {
            System.out.println("Seleccione una opción: \b");
            option = Integer.parseInt(br.readLine());
        } catch (NumberFormatException | IOException e) {
            System.out.println("La opción debe ser un valor numérico");
        }

        if (option != 5) {

            listRepresentation = trees.get(option);
            System.out.println("\nList representation -> " + listRepresentation);
            if (!validateStringList(listRepresentation)) {
                System.out.println("Lista no valida...");
            } else {
                Tree tree = createTreeFromList(listRepresentation);
                System.out.println("\n************* Tree Traversals *************\n");
                System.out.println("InOrder: " + tree.inOrder(tree.getRoot(), ""));
                System.out.println("PreOrder: " + tree.preOrder(tree.getRoot(), ""));
                System.out.println("PostOrder: " + tree.postOrder(tree.getRoot(), ""));
                System.out.println("\n**************************************************\n");
            }
        }

    }

    public List<String> getSubLevels(int times, String listRepresentation) {
        List<String> values = new ArrayList<>();
        String pattern = "\\((.*?)\\){" + times + ",}"; // First
        Matcher matcher = Pattern.compile(pattern).matcher(listRepresentation);
        int rightNodeStartPos = listRepresentation.indexOf(",");
        if (matcher.find() && matcher.start() < rightNodeStartPos) {
            String value = matcher.group();
            rightNodeStartPos = matcher.end();
            if (!validateStringList(value)) {
                rightNodeStartPos = listRepresentation.indexOf(")", rightNodeStartPos + 1) + 1;
                values.add(listRepresentation.substring(matcher.start() + 1, rightNodeStartPos));
            } else {
                values.add(value.substring(1, value.length() - 1));
            }
        } else {
            values.add(null);
        }
        if (rightNodeStartPos < listRepresentation.length()) {
            values.add(listRepresentation.substring(rightNodeStartPos + 1, listRepresentation.length()));
        }
        return values;
    }

    public int getTimes(String text) {
        Matcher m1 = Pattern.compile("\\)*\\,").matcher(text);
        int times = 0;
        while (m1.find()) {
            String value = m1.group();
            value = value.replace(",", "");
            if (times < value.length()) {
                times = value.length();
            }
        }
        if (times == 0) {
            Matcher m2 = Pattern.compile("\\)*").matcher(text);
            while (m2.find()) {
                String value = m2.group();
                value = value.replace(",", "");
                if (times < value.length()) {
                    times = value.length();
                }
            }
        }
        return times;
    }

    public boolean validateStringList(String listRepresentation) {
        listRepresentation = listRepresentation.replaceAll("[\\,_[0-9]\\s]", "");
        List<String> parenthesis = new ArrayList<>(Arrays.asList(listRepresentation.split("")));
        Predicate<String> predicate = (value) -> value.equals("(");
        int openerParentheses = parenthesis.size();
        parenthesis.removeIf(predicate);
        int closererParentheses = parenthesis.size();
        openerParentheses -= closererParentheses;
        return openerParentheses == closererParentheses;
    }

    public Tree createTreeFromList(String listRepresentation) {
        //////////////////// RAÍZ /////////////////////////
        NodeT root = new NodeT(getValue(listRepresentation, 0), null, null); // Nodo raíz //
        Tree tree = new Tree(root);
        listRepresentation = listRepresentation.substring(listRepresentation.indexOf("(") + 1, listRepresentation.length() - 1);
        ///////////////////// PRIMER NIVEL ////////////////////////
        int times = getTimes(listRepresentation);
        List<String> values = getSubLevels(times, listRepresentation);
        //// ******************************************** ////////////////
        NodeT left = new NodeT(getValue(listRepresentation, 0), null, null);
        tree.getRoot().setLeft(left);
        tree = createChildren(values.get(0), left, tree);
        /////////////////////
        NodeT right = new NodeT(getValue(values.get(1), 0), null, null);
        tree.getRoot().setRight(right);
        listRepresentation = values.get(1).substring(values.get(1).indexOf("(") + 1, values.get(1).length() - 1);
        tree = createChildren(listRepresentation, right, tree);
        return tree;
    }

    public Tree createChildren(String subList, NodeT father, Tree tree) {
        if (subList != null && father != null) {
            if (subList.charAt(subList.length() - 1) == ' ') {
                subList = subList.substring(0, subList.length() - 2);
            }
            NodeT left = (subList.charAt(0) != '_') ? new NodeT(getValue(subList, 0), null, null) : null;
            father.setLeft(left);
            //////////////////////
            int times = getTimes(subList);
            List<String> values = getSubLevels(times, subList);
            //////////////////////
            NodeT right = null;
            if (values.size() > 1) {
                int rightNodeValue = getValue(values.get(1), 0);
                right = (rightNodeValue != -1) ? new NodeT(rightNodeValue, null, null) : null;
                father.setRight(right);
            }
            if (subList.contains("(")) {
                if (subList.indexOf(",") > subList.indexOf("(")) {
                    tree = createChildren(values.get(0), left, tree);
                }
                if (values.size() > 1) {
                    String data = values.get(1);
                    if (data.contains("(")) {
                        data = data.substring(data.indexOf("(") + 1, data.length() - 1);
                    }
                    tree = createChildren(data, right, tree);
                }
            }
        }
        return tree;
    }

    public static int getValue(String values, int start) {
        int openParen = values.indexOf("(", start);
        int closeParen = values.indexOf(")", start);
        int space = values.indexOf(",", start);
        List<Integer> characters = new ArrayList<>(Arrays.asList(openParen, closeParen, space));
        Predicate<Integer> removeIfDownZero = (value) -> value == -1;
        characters.removeIf(removeIfDownZero);
        int length = values.length();
        if (!characters.isEmpty()) {
            Collections.sort(characters);
            length = characters.get(0);
        }
        String number = values.substring(start, length);
        return !number.equals("_") ? Integer.parseInt(number) : -1;
    }

}
