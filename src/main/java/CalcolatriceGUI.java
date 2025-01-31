import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class CalcolatriceGUI extends JFrame {
    private JButton Button1;
    private JButton Button3;
    private JButton Button2;
    private JButton bottoneUguale;
    private JButton Button4;
    private JButton Button5;
    private JButton Button6;
    private JButton bottoneMeno;
    private JButton Button7;
    private JButton Button8;
    private JButton Button9;
    private JButton bottonePiu;
    private JButton Button0;
    private JTextArea textArea1;
    private JPanel panel;
    private JButton CEButton;
    private JButton xButton;
    private JButton bottoneDiviso;
    private JButton bottoneVirgola;
    private JButton bottoneResto;
    private JButton bottoneRadice;
    private JButton xXButton;
    private String numeroCorrente = "";
    ArrayList<Float> numeri = new ArrayList<>();
    ArrayList<String> operazioni = new ArrayList<>();
    public CalcolatriceGUI()
    {
        setContentPane(panel);
        setTitle("Calcolatrice Retro");
        setSize(600, 300);  // Altezza aumentata per il miglior layout
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        // Impostazione grafica retro (legno)
        panel.setBackground(new Color(204, 153, 102)); // Colore legno chiaro per il background
        panel.setLayout(new BorderLayout());
        // Impostazione del font e dimensioni per il JTextArea
        textArea1.setFont(new Font("Courier New", Font.BOLD, 24)); // Font tipo macchina da scrivere
        textArea1.setEditable(false); // Rende l'area di testo non editabile
        textArea1.setBackground(new Color(255, 239, 186)); // Colore chiaro per lo stile legno
        textArea1.setForeground(Color.BLACK); // Testo nero
        textArea1.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2)); // Bordo marrone
        textArea1.setPreferredSize(new Dimension(600, 30));  // Imposta una dimensione fissa per l'area di testo più piccola all'inizio
        textArea1.setLineWrap(true);  // Permette di andare a capo nel caso di stringhe lunghe
        textArea1.setWrapStyleWord(true);  // Word wrap per migliorare la leggibilità
        // Aggiungi i listener per i numeri
        Button0.addActionListener(e -> aggiungiCifra("0"));
        Button1.addActionListener(e -> aggiungiCifra("1"));
        Button2.addActionListener(e -> aggiungiCifra("2"));
        Button3.addActionListener(e -> aggiungiCifra("3"));
        Button4.addActionListener(e -> aggiungiCifra("4"));
        Button5.addActionListener(e -> aggiungiCifra("5"));
        Button6.addActionListener(e -> aggiungiCifra("6"));
        Button7.addActionListener(e -> aggiungiCifra("7"));
        Button8.addActionListener(e -> aggiungiCifra("8"));
        Button9.addActionListener(e -> aggiungiCifra("9"));
        // Aggiungi i listener per le operazioni
        bottonePiu.addActionListener(e -> aggiungiOperazione("+"));
        bottoneMeno.addActionListener(e -> aggiungiOperazione("-"));
        xButton.addActionListener(e -> aggiungiOperazione("x"));
        bottoneDiviso.addActionListener(e -> aggiungiOperazione("/"));
        bottoneResto.addActionListener(e -> aggiungiOperazione("%"));
        bottoneRadice.addActionListener(e -> aggiungiOperazione("sqrt"));
        xXButton.addActionListener(e -> aggiungiOperazione("^"));
        bottoneUguale.addActionListener(e ->
        {
            try
            {
                calcolaRisultato();
            }
            catch (DivisionePerZeroException ex)
            {
                throw new RuntimeException(ex);
            }
        });
        CEButton.addActionListener(e -> clearAll());
        bottoneVirgola.addActionListener(e -> aggiungiCifra("."));
        // Impostazione del layout retro per i bottoni
        setButtonStyle(bottonePiu);
        setButtonStyle(bottoneMeno);
        setButtonStyle(xButton);
        setButtonStyle(bottoneDiviso);
        setButtonStyle(bottoneResto);
        setButtonStyle(bottoneRadice);
        setButtonStyle(xXButton);
        setButtonStyle(bottoneUguale);
        setButtonStyle(CEButton);
        setButtonStyle(bottoneVirgola);
    }
    // Imposta lo stile retro per ogni bottone
    private void setButtonStyle(JButton button)
    {
        button.setFont(new Font("Courier New", Font.BOLD, 18));  // Font tipo macchina da scrivere
        button.setBackground(new Color(205, 133, 63));  // Colore legno per i bottoni
        button.setForeground(Color.WHITE);  // Testo bianco
        button.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19)));  // Bordo marrone scuro
    }
    // Aggiungi una cifra al numero corrente
    public void aggiungiCifra(String cifra)
    {
        if (cifra.equals(".") && !numeroCorrente.contains("."))
        {
            numeroCorrente += cifra;
            textArea1.append(cifra);
        } else if (!cifra.contains("."))
        {
            numeroCorrente += cifra;
            textArea1.append(cifra);
        }
    }
    // Aggiungi l'operazione e il numero corrente alla lista
    public void aggiungiOperazione(String op)
    {
        if (!numeroCorrente.isEmpty())
        {
            numeri.add(Float.parseFloat(numeroCorrente)); // Aggiungi il numero corrente
            numeroCorrente = ""; // Azzera il numero corrente
        }
        if (op.equals("sqrt"))
        {
            // Gestione separata per la radice quadrata
            if (!numeri.isEmpty())
            {
                float numero = numeri.get(numeri.size() - 1); // Prendi l'ultimo numero inserito
                float risultato = (float) Math.sqrt(numero);  // Calcola la radice quadrata
                numeri.set(numeri.size() - 1, risultato); // Sostituisci il numero con il risultato
                textArea1.append(" sqrt"); // Aggiungi l'operazione alla vista
            }
        } else
        {
            if (!numeri.isEmpty())
            { // Aggiungi l'operazione solo se non è già presente
                operazioni.add(op);
                textArea1.append(" " + op + " ");
            }
        }
    }
    // Calcola il risultato dell'operazione
    public void calcolaRisultato() throws DivisionePerZeroException
    {
        // Se c'è un numero da aggiungere, lo aggiungiamo
        if (!numeroCorrente.isEmpty())
        {
            numeri.add(Float.parseFloat(numeroCorrente));
            numeroCorrente = ""; // Azzera il numero corrente
        }

        if (numeri.size() != operazioni.size() + 1)
        {
            textArea1.append("\nErrore: Operazione incompleta\n");
            return;
        }

        // Esegui il calcolo con la classe Calcolatrice
        Calcolatrice calcolatrice = new Calcolatrice(operazioni, numeri);
        try
        {
            calcolatrice.eseguiCalcolo();
            textArea1.append(" = " + calcolatrice.printRisultato() + "\n");
        }
        catch (DivisionePerZeroException e)
        {
            textArea1.append("\nErrore: La divisione per 0 è indefinibile\n");
        }
    }
    // Pulisce tutto
    public void clearAll() {
        textArea1.setText("");
        numeri.clear();
        operazioni.clear();
    }
}
