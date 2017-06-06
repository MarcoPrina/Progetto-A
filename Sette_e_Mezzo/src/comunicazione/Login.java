
package comunicazione;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Login {
    
    int port;
    
    Socket socketClient;
    BufferedReader in;
    PrintWriter out;
    Scanner tastiera;
    
    
    public Login() {
        try {
           
            tastiera = new Scanner(System.in);
            
            System.out.println("[0] - provo a connettermi al server...");
            int port = 8080;
            socketClient = new Socket (InetAddress.getLocalHost(), 8080);
            System.out.println("[1] - connesso!");
            in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            out = new PrintWriter(socketClient.getOutputStream(), true);
        } catch (UnknownHostException ex) {
            System.err.println("Host sconosciuto");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.err.println("Impossibile connnettersi al server");
        }
    }
    /**
     * 
     */
    public void comunica() {
        System.out.println("[3] - cosa vuoi fare? \n\t registrazione \n\t login \n\t convalida \n\t recupero");
        String messaggio = tastiera.nextLine();
        
            if (messaggio.equals("registrazione")) {
                registrazione();
            }
           else if (messaggio.equals("login")) {
                login();
            }
           else if (messaggio.equals("convalida")) {
                convalida();
            }
           else if (messaggio.equals("recupero")) {
                recupero();
            } 
            else
        {
            System.err.println("operazione non valida");
        }
        
            comunica();    
        
    }

    private void registrazione() {
        System.out.println("[4] - inserire email, user e psw separate da uno spazio:");
        String messaggio = tastiera.nextLine();
        String[] stringa_per_verifica = new String[3];        
        try {
            
            //effettuo una verifica sul messaggio digitato: controllo che il messaggio sia di tre parole e che non sia vuoto
            stringa_per_verifica = messaggio.split(" ");
            for (String s : stringa_per_verifica) {
                if (s != null) {
                    continue;
                }else{
                    System.err.println("messaggio inserito non valido");
                }
            }
            
            String messaggio_da_inviare = "registrazione " +messaggio;
            out.println(messaggio_da_inviare);
            String risposta = in.readLine();
            System.out.println("[5] - " +risposta);
            
        } catch (ArrayIndexOutOfBoundsException ex) {
                System.err.println("messaggio inserito non valido");
        } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);        
        }
    }

    
    private void login() {
        
        System.out.println("[4] - inserire username (o email) e psw separate da uno spazio: ");
        String credenziali = tastiera.nextLine();
        String[] stringa_per_verifica = new String[2];
        
        try {
            
            //effettuo un controllo sul messaggio digitato
            stringa_per_verifica = credenziali.split(" ");
            for (String s : stringa_per_verifica) {
                if (s != null) {
                    //do nothing
                }else{
                    System.err.println("messaggio inserito non valido");
                }
            }
            
            String messaggio_da_inviare = "login " +credenziali;
            out.println(messaggio_da_inviare);
            String risposta = in.readLine();
            System.out.println("[5] - " +risposta);
            
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("messaggio inserito non valido");            
        }
        
    }

    private void convalida() {
        System.out.println("[4] - inserire il codice di convalida:");
        String codice = tastiera.nextLine();
        
        try {
            
            //verifica sul messaggio digitato
            if (codice.contains(" ")) {
                System.err.println("codice inserito non valido");
            }
             
            String messaggio_da_inviare = "convalida " +codice;
            out.println(messaggio_da_inviare);
            String risposta = in.readLine();
            System.out.println("[5] - " +risposta);
            
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void recupero() {
        System.out.println("[4] - inserire l'email per il recupero della psw:");
        String email = tastiera.nextLine();
        
        try {
            
            //controllo sul messaggio digitato
            if (email.contains("@")) {
                //do nothing
            } else {
                System.err.println("email inserita non valida");
            }
            if (email.contains(" ")) {
                System.err.println("email inserita non valida");
            }
            
            String messaggio_da_inviare = "recupero " +email;
            out.println(messaggio_da_inviare);
            String risposta = in.readLine();
            System.out.println("[5] - " +risposta);
            
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}