package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int n = args.length;
        String alg = "shift";
        String operation = "enc";
        String msg = "";
        String msgData = null;
        String msgIn = "";
        int key = 0;
        String out = null;

        boolean hasFileIn = true;

        //find the args[i].equals to "-alg", "-mode", "-key" and "-data",
        // assign the next args as the value to it
        for (int i = 0 ; i < n; i++){
            if (args[i].equals("-alg")){
                alg = args[i+1];
            }

            if (args[i].equals("-mode")){
                operation = args[i+1];
            }
            if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i+1]);
            }
            if (args[i].equals("-data")) {
                msgData = args[i+1];
                hasFileIn = false;
            }
            if (args[i].equals("-out")) {
                out = args[i+1];
            }
        }
        if(Objects.nonNull(msgData)){
            msg = msgData;
        }
        //variables to store file paths
        String in = "";
        if (hasFileIn) {
            for (int i = 0; i < n; i++) {
                if (args[i].equals("-in")) {
                    in = args[i + 1];

                }

            }

            //get msg from file
            File fileIn = new File(in);

            try (Scanner scanner = new Scanner(fileIn)) {
                while (scanner.hasNext()) {
                    msgIn = scanner.nextLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println("No file found: " + "Error");
            }

            //prefer -data over -in
            if (Objects.isNull(msgData)){
                msg = msgIn;
            }

        }

        //MAIN PROG
        String output;
        //set algorithm
        if (alg.equals("shift")) {
            if (operation.equals("dec")){
                output = shift(msg, 26-key);
            } else{
                output = shift(msg, key);
            }
        } else {
            if (operation.equals("dec")){
                output = encrypt(msg, -1*key);
            } else{
                output = encrypt(msg, key);
            }
        }

        if (Objects.nonNull(out)) {

            //write encrypted msg to file
            File fileOut = new File(out);

            try (FileWriter writer = new FileWriter(fileOut)) {
                writer.write(output);
            } catch (IOException e) {
                System.out.print("Error you moron");
            }

        } else {
            System.out.println(output);
        }


    }

    static String encrypt(String msg, int key){

        String order = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        String msg_shifted ="";

        for (int i = 0; i < msg.length(); i++){
            if((order.indexOf(msg.charAt(i)) + key) < order.length()){
                msg_shifted += order.charAt(order.indexOf(msg.charAt(i)) + key);
            }else if((order.indexOf(msg.charAt(i)) + key) >= order.length()) {
                msg_shifted += order.charAt((order.indexOf(msg.charAt(i)) + key) % order.length());
            }
        }
        return msg_shifted;
    }

    static String shift(String msg, int key){
        String order = "abcdefghijklmnopqrstuvwxyz";
        String msg_shifted ="";
//        if (key < 0) {
//            key = 26 - key;
//        }

        for (int i = 0; i < msg.length(); i++){
            if(msg.charAt(i) == ' '){
                msg_shifted += " ";
            }else if (msg.charAt(i) == '?' | msg.charAt(i) == '!' | msg.charAt(i) == '&'){
                break;
            }else if((order.indexOf(msg.charAt(i)) + key) < order.length()){
                msg_shifted += order.charAt(order.indexOf(msg.charAt(i)) + key);
            }else if((order.indexOf(msg.charAt(i)) + key) >= order.length()) {
                msg_shifted += order.charAt((order.indexOf(msg.charAt(i)) + key) % order.length());
            }
        }
        return msg_shifted;
    }



    }

