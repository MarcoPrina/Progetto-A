package comunicazione;

import dominio.eccezioni.GiocatoreDisconnessoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.Observer;


public class Client extends Observable implements Observer {

    private String username = "giocatore non registrato";
    private final PrintWriter aGiocatore;
    private final BufferedReader daGiocatore;
    private final Socket socket;
    private Leggi leggi;

    public Client(Socket socket) throws IOException {
        this.daGiocatore = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.aGiocatore = new PrintWriter(socket.getOutputStream(), true);
        this.socket = socket;

    }

    public void scrivi(String msg) {
        aGiocatore.println(msg);
    }

    public void iniziaLetturaOggetto() {
        this.leggi=new Leggi(daGiocatore);
        leggi.addObserver(this);
        Thread t = new Thread(leggi);
        t.start();
    }

    public String leggi() throws IOException, GiocatoreDisconnessoException {
        String letto;
        try {
            letto = daGiocatore.readLine();
            if (letto == null) {
                throw new GiocatoreDisconnessoException();
            }
        } catch (SocketTimeoutException e) {
            return null;
        }
        return letto;
    }


    public void scriviOggetto(Object pacco) throws IOException {
        aGiocatore.println(pacco.toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        this.setChanged();
        this.notifyObservers(arg);
    }

}
