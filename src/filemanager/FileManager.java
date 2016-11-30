package filemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Aldo Escobar
 */
public class FileManager {
    //public static String expensivePath = "\\\\PC3-PC\\Users\\Public\\Files\\Expensive";
    public static String expensivePath = "\\\\LSO-25-PC\\Users\\Public\\Files\\Expensive";
    //public static String expensivePath = "Expensive";
   
    private static void addClient(String id, String clientName) throws IOException{
        BufferedWriter clientWriter = new BufferedWriter(new FileWriter("Clients", true));
        clientWriter.write(id + " " + clientName); clientWriter.newLine();
        clientWriter.close();
    }
    
    private static void sell(String clientId, String productId, float chantity) throws IOException{
        System.out.println("Debug: Empezando Venta");
        boolean clientFlag = searchClientById(clientId);
        boolean productFlag = searchProductById(productId);
        if(clientFlag && productFlag){            
            BufferedWriter sellWriter = new BufferedWriter(new FileWriter("Sells", true));
            sellWriter.write(clientId + " " + productId + " " + chantity); sellWriter.newLine();
            sellWriter.close();
            System.out.println("Succes sale");
        }        
    }   
    
    private static String search(String product) throws IOException{                   
       
        // CHEAP FILE
        BufferedReader readCheap = new BufferedReader(new FileReader("Cheap"));
        String line = readCheap.readLine();
        String[] lineFragments;
        while(line != null){
            lineFragments = line.split(" ");
            if (lineFragments[1].equalsIgnoreCase(product)){
                readCheap.close();
                System.out.println("Found it in cheap");
                return "Cheap";            
            } 
            line = readCheap.readLine();
        }
        readCheap.close();
        
        // EXPENSIVE FILE
        BufferedReader readExpensive = new BufferedReader(new FileReader(expensivePath));        
        line = readExpensive.readLine();
        while(line != null){
            lineFragments = line.split(" ");            
            if (lineFragments[1].equalsIgnoreCase(product)){
                readExpensive.close();
                System.out.println("Found it in expensive");
                return expensivePath;
            } 
            line = readExpensive.readLine();
        }
        readExpensive.close();
                
        System.err.println("Product not found"); return null;
        
    } // End Search
    
    private static boolean searchProductById(String productId) throws IOException{
        // CHEAP FILE
        BufferedReader readCheap = new BufferedReader(new FileReader("Cheap"));
        String line = readCheap.readLine();
        String[] lineFragments;
        while(line != null){
            lineFragments = line.split(" ");
            if (lineFragments[0].equalsIgnoreCase(productId)){
                readCheap.close();
                System.out.println("Found product" + productId +  "in cheap");
                return true;            
            } 
            line = readCheap.readLine();
        }
        readCheap.close();
        
        // EXPENSIVE FILE
        BufferedReader readExpensive = new BufferedReader(new FileReader(expensivePath));        
        line = readExpensive.readLine();
        while(line != null){
            lineFragments = line.split(" ");            
            if (lineFragments[0].equalsIgnoreCase(productId)){
                readExpensive.close();
                System.out.println("Found product" + productId +  "in Expensive");
                return true;
            } 
            line = readExpensive.readLine();
        }
        readExpensive.close();
                
        System.err.println("Product not found"); return false;
        
    } // END searchById
    
    private static boolean searchClientById(String clientId) throws IOException{
        BufferedReader readClients = new BufferedReader(new FileReader("Clients"));
        String line = readClients.readLine();
        String[] lineFragments;
        while(line != null){
            lineFragments = line.split(" ");
            if (lineFragments[0].equalsIgnoreCase(clientId)){
                readClients.close();
                System.out.println("Found client " + clientId + " " +  lineFragments[1]);
                return true;            
            } 
            line = readClients.readLine();
        }
        readClients.close();
        System.err.println("Client not found"); return false;
    } // END searchClientById
    
    private static void modify(String product, float newPrice) throws IOException{
        String whereIs = search(product);
        BufferedWriter tmpWriter = new BufferedWriter(new FileWriter("TMP"));
        
        if (whereIs != null){
            BufferedReader fileName = new BufferedReader(new FileReader(whereIs));
            String line = fileName.readLine();
            String[] lineFragments;
            while(line != null){
                lineFragments = line.split(" ");
                if (lineFragments[0].equalsIgnoreCase(product)){
                    tmpWriter.write(lineFragments[0] + " " + newPrice); tmpWriter.newLine();
                }else{
                    tmpWriter.write(line); tmpWriter.newLine();
                } // end if      
                line = fileName.readLine();
            } // end while
            fileName.close();
            tmpWriter.close();            
            tmpExchange(whereIs, newPrice);
            System.out.println("I modified something the " + product + "in file: " + whereIs + " with price " + newPrice);
        }else{
            System.err.println("Product not found");
        } // end if
    } // END Modify
    
    private static void tmpExchange(String whereIs, float newPrice) throws IOException{
        //String expensivePath = "\\\\PC3-PC\\Users\\Public\\Files\\Expensive";
        BufferedWriter fileWriter;
        BufferedWriter fileAux;
        BufferedReader tmpReader = new BufferedReader(new FileReader("TMP"));
        String whereGo = newPrice <= 100 ? "Cheap" : expensivePath;
        String line = tmpReader.readLine();
        String[] lineFragments;
        float howMuch = 100;
        
        if(whereIs.equals(whereGo)){
            fileWriter = new BufferedWriter(new FileWriter(whereIs));                
            while(line != null){            
                fileWriter.write(line); fileWriter.newLine();
                line = tmpReader.readLine();                
            } // end while
            fileWriter.close();
            tmpReader.close();
            return;
        } // end if
                
        if(whereGo.equals(expensivePath)){
            fileWriter = new BufferedWriter(new FileWriter("Cheap"));
            //fileAux = new BufferedWriter(new FileWriter("Expensive", true));
            fileAux = new BufferedWriter(new FileWriter(expensivePath, true));
            while(line != null){
                lineFragments = line.split(" ");
                if(Float.parseFloat(lineFragments[2]) > 100){
                    fileAux.write(line); fileAux.newLine();
                }else{
                    fileWriter.write(line); fileWriter.newLine();
                } // end if
                line = tmpReader.readLine();
            } // end while
        }else{
            //fileWriter = new BufferedWriter(new FileWriter("Expensive"));
            fileWriter = new BufferedWriter(new FileWriter(expensivePath));
            fileAux = new BufferedWriter(new FileWriter("Cheap", true));
            while(line != null){
                lineFragments = line.split(" ");
                if(Float.parseFloat(lineFragments[2]) <= 100){
                    fileAux.write(line); fileAux.newLine();
                }else{
                    fileWriter.write(line); fileWriter.newLine();
                } // end if
                line = tmpReader.readLine();
            } // end while
        //} // end if
        } // end if
        
        fileAux.close();
        fileWriter.close();
        tmpReader.close();
        
        // BufferedReader tmp = 
    } // End tmpManager
    
    private static boolean WriteValid(String input){
        try {
            String[] inputFragments = input.split(" ");                        
            if (inputFragments.length != 3){
                System.err.println("Input invalid"); return false;                        
            }             
            Float.parseFloat(inputFragments[2]);
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }        
    } // END StandarInput
    
    private static boolean ComandInput(String comand){
        try {
            String[] inputFragments = comand.split(" ");
            
            boolean len = (inputFragments.length >= 2 && inputFragments.length <=4 ) ? true : false;
            boolean command = ( inputFragments[0].equalsIgnoreCase("search") || 
                                inputFragments[0].equalsIgnoreCase("modify") ||
                                inputFragments[0].equalsIgnoreCase("sell") ||
                                inputFragments[0].equalsIgnoreCase("client") ) ? true:false;
            System.out.println("LenF: " + len + " commandF: " + command);
            if(len && command) return true;
            return false;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
    private static BufferedWriter binding(BufferedWriter writer, String path) throws IOException{        
        writer.close();        
        writer = new BufferedWriter(new FileWriter(path, true));        
        return writer;
    }
    
    public static void main(String[] args) throws IOException{        
        BufferedWriter stockWriter = new BufferedWriter(new FileWriter("Stock", true));
        BufferedWriter cheapWriter = new BufferedWriter(new FileWriter("Cheap", true));        
        BufferedWriter expensiveWriter = new BufferedWriter(new FileWriter(expensivePath, true));
        //BufferedWriter expensiveWriter = new BufferedWriter(new FileWriter("Expensive", true));
        
        Scanner scn = new Scanner(System.in);     
        String input = scn.nextLine();
        String aux = "";
        String comand;
        String article;
        String clientName;
        String[] output;
        float howMuch = 100;
        float chantity;
        float price;
        
        
        while(!input.equalsIgnoreCase("Exit")){
            
            if(ComandInput(input)){
                output = input.split(" ");
                comand = output[0];
                article = output[1];
                price = Float.parseFloat(((output.length == 3 && !comand.equalsIgnoreCase("Client"))  ? output[2]:"0")); 
                clientName = comand.equalsIgnoreCase("Client") || comand.equalsIgnoreCase("sell") ? output[2]:null;
                chantity = Float.parseFloat( comand.equalsIgnoreCase("sell") ? output[3]:"0");
                
                if(comand.equalsIgnoreCase("search")){
                    search(article);
                }
                if(comand.equalsIgnoreCase("modify")){
                    modify(article, price);
                }
                if(comand.equalsIgnoreCase("client")){
                    addClient(article, clientName); // (String Clave, String NombreCliente)
                }
                if(comand.equalsIgnoreCase("sell")){
                    sell(article, clientName, chantity ); // (String Clave, String NombreCliente, Float CantidadVendida)
                }
            }else
            if(WriteValid(input) && !input.equals(aux)){                
                    stockWriter.write(input); stockWriter.newLine();
                    stockWriter = binding(stockWriter, "Stock");
                    System.out.println("Write in stock: " + input);
                    
                    output = input.split(" ");
                    //article = output[0];
                    price = Float.parseFloat(output[2]);
                    
                    if(price <= howMuch){
                        cheapWriter.write(input); cheapWriter.newLine();
                        cheapWriter = binding(cheapWriter, "Cheap");
                        System.out.println("Write in Cheap: " + input);
                    }else{                        
                        expensiveWriter.write(input); expensiveWriter.newLine();                        
                        expensiveWriter.close();
                        expensiveWriter = new BufferedWriter(new FileWriter(expensivePath, true));
                        //expensiveWriter = binding(expensiveWriter, expensivePath);
                        System.out.println("Write in expensive: " + input);
                    }                
            } // end if
            aux = input;
            input = scn.nextLine();
        }  // end while
        stockWriter.close();
        cheapWriter.close();
        expensiveWriter.close();
    } // END main
} // END Archivos
