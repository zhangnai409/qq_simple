package com.zrd.utils;

import java.util.Scanner;

/**
 * �����������
 */
@SuppressWarnings("all")
public class Utility {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * ���ܣ���ȡ���������һ���˵�ѡ�ֵ �� 1 ~ 5 �ķ�Χ
     *
     * @return
     */
    public static char readMenuSelection() {
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false);
            c = str.charAt(0); // ���ַ���ת���ַ�char����
            if (c != '1' && c != '2' && c != '3' &&
                    c != '4' && c != '5') {
                System.out.println("ѡ���������������");
            } else break;
        }
        return c;
    }

    /**
     * ���ܣ���ȡ���������һ���ֶ�
     *
     * @return
     */
    public static char readChar() {
        String str = readKeyBoard(1, false); // ����һ���ַ�
        return str.charAt(0);
    }

    /**
     * ���ܣ���ȡ���������һ���ַ�����ֱ�ӻس����򷵻�ָ����Ĭ��ֵ
     *
     * @param defaultValue
     * @return
     */
    public static char readChar(char defaultValue) {
        String str = readKeyBoard(1, false);
        return (str.length() == 0) ? defaultValue : str.charAt(0);
    }

    /**
     * ���ܣ���ȡ������������ͣ�����С��2
     *
     * @return
     */
    public static int readInt() {
        int n;
        for (; ; ) {
            String str = readKeyBoard(2, false);
            try {
                n = Integer.parseInt(str); // ���ַ���ת������
                break;
            } catch (NumberFormatException e) {
                System.out.println("���������������������");
            }
        }
        return n;
    }

    /**
     * ���ܣ���ȡ������������ͻ�Ĭ��ֵ����ֱ�ӻس����򷵻�Ĭ��ֵ�����򷵻�����ֵ
     *
     * @param defaultValue
     * @return
     */
    public static int readInt(int defaultValue) {
        int n;
        for (; ; ) {
            String str = readKeyBoard(10, true);
            if (str.equals("")) {
                return defaultValue;
            }

            try {
                n = Integer.parseInt(str); // ���ַ���ת������
                break;
            } catch (NumberFormatException e) {
                System.out.println("���������������������");
            }
        }
        return n;
    }

    /**
     * ���ܣ���ȡ���������ָ�����ȵ��ַ���
     *
     * @param limit
     * @return
     */
    public static String readString(int limit) {
        return readKeyBoard(limit, false);
    }

    /**
     * ���ܣ���ȡ���������ָ�����ȵ��ַ�����Ĭ��ֵ����ֱ�ӻس�������Ĭ��ֵ�����򷵻�����ֵ
     *
     * @param limit
     * @param defaultValu
     * @return
     */
    public static String readString(int limit, String defaultValu) {
        String str = readKeyBoard(limit, true);
        return str.equals("") ? defaultValu : str;
    }

    /**
     * ���ܣ���ȡ���������ȷ��ѡ� Y �� N
     *
     * @return
     */
    public static char readConfirmSelection() {
        System.out.println("���������ѡ��(Y / N)");
        char c;
        for (; ; ) {
            // �����ܵ��ַ���ת�ɴ�д��ĸ
            // y -> Y  n -> N
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.println("ѡ�����������ѡ��");
            }
        }
        return c;
    }

    private static String readKeyBoard(int limit, boolean blankRetuen) {
        // �����ַ���
        String line = "";

        // scanner.hasNextLine() �ж���û����һ��
        while (scanner.hasNextLine()) {
            line = scanner.nextLine(); // ��ȡһ��

            // �� line.length() = 0,���û�ֱ�ӻس���û�������κ�����
            if (line.length() == 0) {
                if (blankRetuen) return line; // blankRetuen = true�����Է��ؿմ�
                else continue; // �� blankRetuen = false�������ܿմ���������������
            }

            // ���û���������ݴ��� limit������ʾ����д��
            // ���û���������� > 0 <= limit �ͽ���
            if (line.length() < 1 || line.length() > limit) {
                System.out.println("���볤��(���ܴ���" + limit + ")��������������");
                continue;
            }
            break;
        }
        return line;
    }
}
