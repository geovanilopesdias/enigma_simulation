package enigma_simulator_betha;

import static java.lang.Math.random;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Geovani Lopes Dias
 */

public class Enigma_simulator_betha {
    
    public static int cl = 40;
    public static String lineP = new String(new char[cl]).replace("\0", "=");
    public static String lineS = new String(new char[cl]).replace("\0", "-");
        
    public static char chars[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
        'x', 'y', 'z', ' ', '-', ',', '.', ';', ':', '?', '!', '$', '°', 'º',
        'ª', '/', '@', '_', '&', '%', '(', ')', 'á', 'à', 'â', 'ã', 'é', 'ê',
        'í', 'ó', 'ô', 'õ', 'ú', 'ü', 'ñ', '0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9'};
    
    static int[] indexDrawer() {
        int keyboardLen = chars.length;
        double firstIndex = 1 + random()*(keyboardLen-1);
        int fi = (int) firstIndex;
        int indexesDrawn[] = new int[keyboardLen];
        /* Due to Java standard array limitations, the last but one element will
        always be zero.
        */
        
        indexesDrawn[keyboardLen-1] = fi;
        int counter = 0;
        
        do {
            boolean contains = false;
            double nextIndex = 1 + random()*(keyboardLen-1);
            int ni = (int) nextIndex;
                                                
            for (int x : indexesDrawn){
                if (x == ni){
                    contains = true;
                    break;
                }
            }
            
            if (contains == false){
                indexesDrawn[counter] = ni;
                counter++;
            }
        } while (counter < (keyboardLen-2)); // Já inicia o loop com um elemento
        return indexesDrawn;
    }
            
    
    static Object[] cryptor(String message){
        String msg = message.toLowerCase();
        int ckey[] = indexDrawer(); // Chave criptográfica
        int indexMessage[] = new int[msg.length()]; // Índices dos caracteres da mensagem
        int cryptedIndexes[] = new int[msg.length()]; // Criptação dos índices
        String cryptedMessage = ""; // Mensagem criptografada
        
        int counter = 0;
        for (char letter: msg.toCharArray()){
            for (int i = 0; i < chars.length; i++){
                if (chars[i] == letter){
                    indexMessage[counter] = i;
                    counter++;
                }
            }
        }
        
        counter = 0;
        for (int index: indexMessage){
            cryptedIndexes[counter] = ckey[index];
            counter++;
        }
        
        for (int cIndex: cryptedIndexes){
            cryptedMessage += chars[cIndex];
        }
        
        Object[] cryptogData = {ckey, cryptedMessage};
        return cryptogData;
    }
    
    
    static String decryptor(Object[] data){
        int counter;
        int[] ckey = (int[])data[0];
        String cmsg = (String)data[1];
        String decryptedMessage = "";
        int indexOnCrypt[] = new int[cmsg.length()];
        int indexDecrypt[] = new int[cmsg.length()];
        
        counter = 0;
        for (char letter: cmsg.toCharArray()){
            for (int i = 0; i < chars.length; i++){
                if (chars[i] == letter){
                    indexOnCrypt[counter] = i;
                    counter++;
                    break;
                }
            }
        }
                      
        counter = 0;
        for (int index: indexOnCrypt){
            for (int i = 0; i < ckey.length; i++){
                if (ckey[i] == index) {
                    indexDecrypt[counter] = i;
                    counter++;
                    break;
                }
            }
        }
        
        for (int index: indexDecrypt){
            decryptedMessage += chars[index];
        }
        return decryptedMessage;
    }
    
    
    static void head(){
        System.out.println(lineP);
        System.out.println("Cryptor Gheowin - Betha Version");
        System.out.println("Writen by Geovani L. Dias");
        System.out.println(lineS);
        System.out.println("Options:");
        System.out.println("|1: Encrypt a message");
        System.out.println("|2: Decrypt a message");
        System.out.println("|3: See the characters table");
        System.out.println(lineS);
    }

    
    public static void main(String[] args) {
        head();
        
        // Input management
        Scanner userOption = new Scanner(System.in);
        int q1 = 0;
        boolean userCondition = false;
        while (userCondition == false){
            System.out.print("Sua opção: ");
            q1 = userOption.nextInt();
            userCondition = (q1 == 1||q1 == 2||q1 == 3);
        }
        
        // Encrypting
        switch (q1) {
            case 1:
                System.out.println(lineS);
                System.out.print("Insert the message to encrypt: ");
                Scanner userMessageEnc = new Scanner(System.in);
                String msgToEncrypt = userMessageEnc.nextLine();

                // Encrypting process
                Object[] enigma = cryptor(msgToEncrypt);
                int[] crypKey = (int[]) enigma[0];
                String msgEncrypted = (String) enigma[1];

                // Outputting encrypted message and cryptographic key
                System.out.println(lineS);
                System.out.println("Your message: "+msgToEncrypt);
                System.out.println("Your message encrypted: "+msgEncrypted);
                System.out.println("Your key (copy/paste it carefully for future decryption):");
                int counter = 0;
                for (int letterIndex: crypKey){
                    String endLine = ",";
                    if (counter == chars.length - 1){
                        endLine = "";
                    }
                    System.out.print(letterIndex + endLine);
                    counter++;
                }
                System.out.println("");
                System.out.println(lineS);
                break;
                
            case 2:
                System.out.println(lineS);
                System.out.println("Warning: the current system support only "
                        + "simple characters (a, b, c; 1, 2, 3)!");
                System.out.print("Insert the message to decrypt: ");
                Scanner userMessageDec = new Scanner(System.in);
                String msgToDecrypt = userMessageDec.nextLine();
                System.out.print("Insert the cryptographic key: ");
                Scanner userCrypKey = new Scanner(System.in);
                String cripKey = userCrypKey.nextLine();
                
                // Transforming the inputted key to int array:
                String[] cripKeyArray = cripKey.split(","); 
                int[] cripKeyIntArray = new int[cripKeyArray.length];
                for (int i = 0; i < cripKeyArray.length; i++){
                    cripKeyIntArray[i] = Integer.parseInt(cripKeyArray[i]);
                }
                
                // Passing the data to decrypt() and outputting it:
                Object[] decryptionData = {cripKeyIntArray, msgToDecrypt};
                String decrypMessage = decryptor(decryptionData);
                
                System.out.println(lineS);
                System.out.println("Decrypting your message:");
                System.out.println(decrypMessage);
                break;
                
            case 3:
                System.out.println(lineS);
                System.out.println("Showing the characters table");
                int index = 0;
                for (char letter: chars){
                    System.out.print(index+": "+letter);
                    if (index == chars.length - 1){
                        System.out.println(".");
                    }
                    else{
                        if (index > 0 && index%6 == 0){
                            System.out.println("| "+"\n");
                        }
                        else {
                            System.out.print("| ");
                        }
                        index++;
                    }
                }
                break;
        }
    }
}
