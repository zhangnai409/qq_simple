package com.zrd.utils;

import java.util.Scanner;

/**
 * 键盘输入管理
 */
@SuppressWarnings("all")
public class Utility {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * 功能：读取键盘输入的一个菜单选项，值 ： 1 ~ 5 的范围
     *
     * @return
     */
    public static char readMenuSelection() {
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false);
            c = str.charAt(0); // 将字符串转成字符char类型
            if (c != '1' && c != '2' && c != '3' &&
                    c != '4' && c != '5') {
                System.out.println("选择错误，请重新输入");
            } else break;
        }
        return c;
    }

    /**
     * 功能：读取键盘输入的一个字段
     *
     * @return
     */
    public static char readChar() {
        String str = readKeyBoard(1, false); // 就是一个字符
        return str.charAt(0);
    }

    /**
     * 功能：读取键盘输入的一个字符，若直接回车，则返回指定的默认值
     *
     * @param defaultValue
     * @return
     */
    public static char readChar(char defaultValue) {
        String str = readKeyBoard(1, false);
        return (str.length() == 0) ? defaultValue : str.charAt(0);
    }

    /**
     * 功能：读取键盘输入的整型，长度小于2
     *
     * @return
     */
    public static int readInt() {
        int n;
        for (; ; ) {
            String str = readKeyBoard(2, false);
            try {
                n = Integer.parseInt(str); // 将字符串转成整型
                break;
            } catch (NumberFormatException e) {
                System.out.println("数字输入错误，请重新输入");
            }
        }
        return n;
    }

    /**
     * 功能：读取键盘输入的整型或默认值，若直接回车，则返回默认值；否则返回输入值
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
                n = Integer.parseInt(str); // 将字符串转成整型
                break;
            } catch (NumberFormatException e) {
                System.out.println("数字输入错误，请重新输入");
            }
        }
        return n;
    }

    /**
     * 功能：读取键盘输入的指定长度的字符串
     *
     * @param limit
     * @return
     */
    public static String readString(int limit) {
        return readKeyBoard(limit, false);
    }

    /**
     * 功能：读取键盘输入的指定长度的字符串或默认值，若直接回车，返回默认值，否则返回输入值
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
     * 功能：读取键盘输入的确认选项， Y 或 N
     *
     * @return
     */
    public static char readConfirmSelection() {
        System.out.println("请输入你的选择(Y / N)");
        char c;
        for (; ; ) {
            // 将接受的字符，转成大写字母
            // y -> Y  n -> N
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.println("选择错误，请重新选择：");
            }
        }
        return c;
    }

    private static String readKeyBoard(int limit, boolean blankRetuen) {
        // 定义字符串
        String line = "";

        // scanner.hasNextLine() 判断有没有下一行
        while (scanner.hasNextLine()) {
            line = scanner.nextLine(); // 读取一行

            // 若 line.length() = 0,即用户直接回车，没有输入任何内容
            if (line.length() == 0) {
                if (blankRetuen) return line; // blankRetuen = true，可以返回空串
                else continue; // 若 blankRetuen = false，不接受空串，必须输入内容
            }

            // 若用户输入的内容大于 limit，就提示重新写入
            // 若用户输入的内容 > 0 <= limit 就接受
            if (line.length() < 1 || line.length() > limit) {
                System.out.println("输入长度(不能大于" + limit + ")错误，请重新输入");
                continue;
            }
            break;
        }
        return line;
    }
}
