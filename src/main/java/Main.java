import cubes.*;
import graphicalMenu.GraphMenu;

public class Main {

    public static void main(String[] args) {
        System.out.println(Algorithm.optimizeAlg("UDRR"));
    }
}

//            else if (cur.charAt(0) != nex.charAt(0)){
//                    int size = movesList.size();
//final int sum = cur.charAt(0) + nex.charAt(0);
//        if (sum ==158) {
//        if (cur.equals("R") && nex.equals("L") || cur.equals("L") && nex.equals("R")) {
//        movesList.set(j, "x'");
//        movesList.set(j + 1, "R2");
//        }
//        else if (cur.equals("R") && nex.equals("L'") || cur.equals("L'") && nex.equals("R")) {
//        movesList.set(j, "x");
//        movesList.remove(j + 1);
//        }
//        else if (cur.equals("R") && nex.equals("L2") || cur.equals("L2") && nex.equals("R")) {
//        movesList.set(j, "x");
//        movesList.set(j + 1, "L'");
//        }
//        else if (cur.equals("R'") && nex.equals("L") || cur.equals("L") && nex.equals("R'")) {
//        movesList.set(j, "x'");
//        movesList.remove(j + 1);
//        }
//        else if (cur.equals("R'") && nex.equals("L'") || cur.equals("L'") && nex.equals("R'")) {
//        movesList.set(j, "x");
//        movesList.set(j + 1, "R2");
//        }
//        else if (cur.equals("R'") && nex.equals("L2") || cur.equals("L2") && nex.equals("R'")) {
//        movesList.set(j, "x'");
//        movesList.set(j + 1, "L");
//        }
//        else if (cur.equals("R2") && nex.equals("L") || cur.equals("L") && nex.equals("R2")) {
//        movesList.set(j, "x'");
//        movesList.set(j + 1, "R'");
//        }
//        else if (cur.equals("R2") && nex.equals("L'") || cur.equals("L'") && nex.equals("R2")) {
//        movesList.set(j, "x");
//        movesList.set(j + 1, "R");
//        }
//        else if (cur.equals("R2") && nex.equals("L2") || cur.equals("L2") && nex.equals("R2")) {
//        movesList.set(j, "x2");
//        movesList.remove(j + 1);
//        }
//        if (j > 0 && size > movesList.size()) {
//        j--;
//        }
//        }
//        else if (sum ==153) {
//        if (cur.equals("U") && nex.equals("D") || cur.equals("D") && nex.equals("U")) {
//        movesList.set(j, "y'");
//        movesList.set(j + 1, "U2");
//        }
//        else if (cur.equals("U") && nex.equals("D'") || cur.equals("D'") && nex.equals("U")) {
//        movesList.set(j, "y");
//
//        movesList.remove(j + 1);
//        }
//        else if (cur.equals("U") && nex.equals("D2") || cur.equals("D2") && nex.equals("U")) {
//        movesList.set(j, "y");
//        movesList.set(j + 1, "D'");
//        }
//        else if (cur.equals("U'") && nex.equals("D") || cur.equals("D") && nex.equals("U'")) {
//        movesList.set(j, "y'");
//        movesList.remove(j + 1);
//        }
//        else if (cur.equals("U'") && nex.equals("D'") || cur.equals("D'") && nex.equals("U'")) {
//        movesList.set(j, "y");
//        movesList.set(j + 1, "U2");
//        }
//        else if (cur.equals("U'") && nex.equals("D2") || cur.equals("D2") && nex.equals("U'")) {
//        movesList.set(j, "y'");
//        movesList.set(j + 1, "L");
//        }
//        else if (cur.equals("U2") && nex.equals("D") || cur.equals("D") && nex.equals("U2")) {
//        movesList.set(j, "y'");
//        movesList.set(j + 1, "U'");
//        }
//        else if (cur.equals("U2") && nex.equals("D'") || cur.equals("D'") && nex.equals("U2")) {
//        movesList.set(j, "y");
//        movesList.set(j + 1, "U");
//        }
//        else if (cur.equals("U2") && nex.equals("D2") || cur.equals("D2") && nex.equals("U2")) {
//        movesList.set(j, "y2");
//        movesList.remove(j + 1);
//        }
//
//        if (j > 0 && size > movesList.size()) {
//        j--;
//        }
//        }
//        else if (sum==136) {
//        if (cur.equals("F") && nex.equals("B") || cur.equals("B") && nex.equals("F")) {
//        movesList.set(j, "z'");
//        movesList.set(j + 1, "F2");
//        }
//        else if (cur.equals("F") && nex.equals("B'") || cur.equals("B'") && nex.equals("F")) {
//        movesList.set(j, "z");
//        movesList.remove(j + 1);
//        }
//        else if (cur.equals("F") && nex.equals("B2") || cur.equals("B2") && nex.equals("F")) {
//        movesList.set(j, "z");
//        movesList.set(j + 1, "B'");
//        }
//        else if (cur.equals("F'") && nex.equals("B") || cur.equals("B") && nex.equals("F'")) {
//        movesList.set(j, "z'");
//        movesList.remove(j + 1);
//        }
//        else if (cur.equals("F'") && nex.equals("B'") || cur.equals("B'") && nex.equals("F'")) {
//        movesList.set(j, "z");
//        movesList.set(j + 1, "F2");
//        }
//        else if (cur.equals("F'") && nex.equals("B2") || cur.equals("B2") && nex.equals("F'")) {
//        movesList.set(j, "z'");
//        movesList.set(j + 1, "B");
//        }
//        else if (cur.equals("F2") && nex.equals("B") || cur.equals("B") && nex.equals("F2")) {
//        movesList.set(j, "z'");
//        movesList.set(j + 1, "F'");
//        }
//        else if (cur.equals("F2") && nex.equals("B'") || cur.equals("B'") && nex.equals("F2")) {
//        movesList.set(j, "z");
//        movesList.set(j + 1, "F");
//        }
//        else if (cur.equals("F2") && nex.equals("B2") || cur.equals("B2") && nex.equals("F2")) {
//        movesList.set(j, "z2");
//        movesList.remove(j + 1);
//        }
//        if (j > 0 && size > movesList.size()) {
//        j--;
//        }
//        }
//        else j++;
//        }