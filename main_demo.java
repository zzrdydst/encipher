import java.awt.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public final class main_demo {
    public static void main(String[] args) {

        JFrame main_frame = new JFrame("文件加密解密系统");
        main_frame.setSize(400, 300);
        JTextField key_field = new JTextField("");
        key_field.setBounds(100, 100, 200, 50);
        Container key_container = main_frame.getContentPane();
        JPanel key_panel = new JPanel();
        key_panel.setBounds(200, 50, 200, 50);
        key_panel.add(key_field);
        key_container.add(key_panel);
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.setVisible(true);


        Scanner sc = new Scanner(System.in);
        System.out.println("欢迎使用文件加密解密系统！");
        System.out.println("注意：请保证input文件夹内是你想要加密/解密的文件");
        System.out.println("======================================");
        do{
            System.out.print("请输入密钥（格式：12.34.56.78）：");
        }while (!sc.hasNext());
        String str = sc.next();
        String[] temp = str.split( "\\.");
        byte[] key = new byte[4];
        for (int i = 0; i < 4; i++) {
            key[i] = (byte) Integer.parseInt(temp[i]);
        }

        try {
            File input_dir = new File("input\\");
            File output_dir = new File("output\\");
            int num = input_dir.listFiles().length;
            File[] input_files = input_dir.listFiles();
            File[] output_files = new File[num];

            for (int i = 0; i < output_files.length; i++) {
                output_files[i] = new File(output_dir, input_files[i].getName());
            }

            for (int i = 0; i < num; i++) {
                System.out.println("正在处理文件：" + input_files[i].getName());
                if (input_files[i].isFile()) {
                    FileInputStream reader = new FileInputStream(input_files[i]);
                    FileOutputStream writer = new FileOutputStream(output_files[i]);
                    byte[] temple = reader.readAllBytes();

                    for (int j = 0; j < temple.length; j++) {
                        temple[j] ^= key[j % 4];
                    }
                    writer.write(temple);
                    reader.close();
                    writer.close();
                } else{
                    System.out.println(input_files[i].getName() + "文件无法找到");
                }
                System.out.println("文件：" + input_files[i].getName() + "处理完成");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private main_demo(){}
}

// author: zzrdydst