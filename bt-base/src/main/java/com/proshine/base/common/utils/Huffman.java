package com.proshine.base.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Huffman {
    public enum CharacterSet {
        AllNonControl,
        LetterPlusNumber,
        UpperLetterPlusNumber
    }

    private static char CHAR[] = null;
    private static final int FREQ[] = {
            780, 81, 929, 776, 487, 436, 447, 306, 509, 511, 818, 795, 644, 379, 812, 533, 351, 939, 876, 550, 622,
            587, 208, 301, 471, 230, 844, 195, 226, 171, 228, 436, 311, 923, 430, 185, 905, 980, 439, 111, 258, 409,
            595, 262, 603, 711, 222, 117, 297, 319, 424, 508, 86, 262, 801, 29, 929, 730, 489, 579, 237, 459, 963,
            547, 521, 232, 489, 624, 679, 396, 367, 988, 38, 885, 913, 796, 99, 262, 335, 680, 137, 721, 107, 654,
            494, 779, 715, 904, 891, 334, 699, 198, 31, 744
    };
    private static Node tree = null;
    private static Map<Character, String> decodeMap = null;
    private static CharacterSet charSet = null;
    private static CharacterSet oldCharSet = null;

    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return (left == null && right == null);
        }
        @Override
        public int compareTo(Node that) {
            if (this == that) {
                return 0;
            }
            int diff = this.freq - that.freq;
            return (diff >= 0) ? 1 : -1;
        }
    }

    public static void setCharacterSet(CharacterSet set) {
        charSet = set;
        switch (charSet) {
            case UpperLetterPlusNumber:
                CHAR = new char[]{
                        'V', 'L', 'X', 'Y', '5', '2', 'F', 'A', 'T', 'W', '6', 'R', 'G', 'D', '7', '4', 'S', 'K', 'O',
                        'U', 'Q', 'P', 'I', 'E', '0', 'Z', 'M', '1', '3', 'H', 'N', 'C', '8', 'J', '9', 'B'
                };
                break;
            case LetterPlusNumber:
                CHAR = new char[]{
                        '3', 't', 's', 'I', 'x', 'V', 'W', 'H', '8', 'e', 'N', 'b', 'f', 'Z', 'P', 'X', 'E', 'n', '1',
                        'L', 'd', 'i', 'q', '5', '4', 'A', 'z', 'h', '9', 'J', 'Q', 'u', 'U', 'l', 'r', 'y', 'k', 'M',
                        'g', 'B', 'G', 'F', '0', 'R', 'C', 'O', 'p', 'D', 'o', '6', 'v', '7', 'a', 'j', 'Y', 'S', 'm',
                        'c', 'w', '2', 'T', 'K',
                };
                break;
            case AllNonControl:
            default:
                CHAR = new char[94];
                for (int i = 0; i < 94; i++) {
                    CHAR[i] = (char) (33 + i);
                }
        }
    }

    public static Long decode(String input, int digits) {
        buildTrie();
        if (decodeMap == null) {
            return null;
        }
        long value = 0;
        int i = 0;
        int j = 0;
        while (i < digits && j < input.length()) {
            char c = input.charAt(j++);
            String code = decodeMap.get(c);
            if (code == null) {
                return null;
            }
            for (char d : code.toCharArray()) {
                value <<= 1;
                i++;
                if (d == '1') {
                    value += 1;
                }
                if (i >= digits) {
                    break;
                }
            }
        }
        return value;
    }

    private static void buildTrie() {
        if (charSet == null) {
            return;
        }
        if (oldCharSet == charSet && tree != null) {
            return;
        }

        // initialize priority queue with singleton trees
        TreeSet<Node> nodes = new TreeSet<Node>();
        for (int i = 0; i < CHAR.length; i++) {
            nodes.add(new Node(CHAR[i], FREQ[i], null, null));
        }

        while (nodes.size() > 1) {
            Node left = nodes.pollFirst();
            Node right = nodes.pollFirst();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            nodes.add(parent);
        }

        tree = nodes.pollFirst();
        buildCode();
        oldCharSet = charSet;
    }

    private static void buildCode() {
        decodeMap = new HashMap<Character, String>();
        buildCode(tree, "");
    }

    private static void buildCode(Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(x.left, s + '0');
            buildCode(x.right, s + '1');
        } else {
            decodeMap.put(new Character(x.ch), s);
        }
    }

    public static String encode(long value, int digits) {
        buildTrie();
        if (tree == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        value <<= (64 - digits);
        while (i < digits) {
            Node x = tree;
            while (!x.isLeaf()) {
                boolean bit = (value & 0x8000000000000000L) == 0;
                i++;
                if (!bit) {
                    x = x.right;
                }else {
                    x = x.left;
                }
                value <<= 1;
            }
            builder.append(x.ch);
        }
        return builder.toString();
    }
}