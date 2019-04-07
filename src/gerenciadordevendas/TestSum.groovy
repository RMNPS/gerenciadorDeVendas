/*
 * GerenciadorDeVendas: TestSum.java
 * Enconding: UTF-8
 * Data de criação: 01/03/2018 15:10:54
 */
package gerenciadordevendas;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 *
 * @author Ramon Porto
 */
public class TestSum {

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat();
//        int a[] = new int[]{3, 2, 4};
//
//        System.out.println(Arrays.toString(twoSum(a, 6)));

//        ListNode l1 = new ListNode(1);
////        l1.next = new ListNode(8);
////        l1.next.next = new ListNode(3);
//
//        ListNode l2 = new ListNode(9);
//        l2.next = new ListNode(9);
//        l2.next.next = new ListNode(4);
//        long antes = System.nanoTime();
//        System.out.println(addTwoNumbers(l1, l2));
//        System.out.println("Tempo: " + df.format(System.nanoTime() - antes));
        long antes = System.nanoTime();
        System.out.println(reverse(1534236469));
        for (int i = 0; i < 1; i++) {
            reverse(1534236469);
        }
        System.out.println("Tempo: " + df.format(System.nanoTime() - antes));
        int i = 0;
        System.out.println(i++);
        System.out.println(i);
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i != j && nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    public static class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            ListNode aux = this;
            String val = "";
            while (aux != null) {
                val += aux.val + ", ";
                aux = aux.next;
            }
            return val;
        }

    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p = l1, q = l2, result = new ListNode(0), next3 = result;
        int carry = 0;
        int num = 0;
        while (p != null || q != null) {
            int val1 = 0;
            int val2 = 0;
            if (p != null) {
                val1 = p.val;
                p = p.next;
            }
            if (q != null) {
                val2 = q.val;
                q = q.next;
            }
            int valor = carry + val1 + val2;
            carry = 0;
            if (valor > 9) {
                carry = 1;
                valor = valor - 10;
            }

            next3.next = new ListNode(valor);
            next3 = next3.next;

        }

        if (carry > 0) {
            next3.next = new ListNode(carry);
        }
        return result.next;
    }

    public static int lengthOfLongestSubstring(String s) {
        char[] c = s.toCharArray();

        String sResult = "";
        int maior = 0;

        for (char x : c) {
            int indexOf = sResult.indexOf(x);
            if (indexOf > -1) {
                sResult = sResult.substring(indexOf + 1);
            }
            sResult = sResult + x;
            maior = Math.max(maior, sResult.length());
        }
        return maior;
    }

    public static int reverse(int x) {
        int reversedNumber = 0;

        while (x != 0) {
            reversedNumber = reversedNumber * 10 + x % 10;
            x /= 10;
            if (reversedNumber > 214748364 || reversedNumber < -21474836) {
                return 0;
            }
        }

        return reversedNumber;
    }

    public static int numJewelsInStones(String J, String S) {
        int count = 0;
        char[] s = S.toCharArray();
        for (char c : J.toCharArray()) {
            for (char d : s) {
                if (c == d) {
                    count++;
                }
            }
        }
        return count;

    }
}
