import java.util.ArrayList;
public class Calcolatrice
{
    private final ArrayList<Float> numeri;
    private final ArrayList<String> operazione;
    private float risultato;
    public Calcolatrice(ArrayList<String> operazioneInput , ArrayList<Float> numeriInput)
    {
        this.operazione = operazioneInput;
        this.numeri = numeriInput;
        this.risultato = numeri.getFirst(); // Inizializza il risultato con il primo numero
    }
    public ArrayList<String> getOperazione()
    {
        return operazione;
    }
    public ArrayList<Float> getNumeri()
    {
        return numeri;
    }
    public void setRisultato(float risultato)
    {
        this.risultato = risultato;
    }
    public float getRisultato()
    {
        return risultato;
    }
    public void eseguiCalcolo() throws DivisionePerZeroException
    {
        int i = 1;
        // Inizia con il primo numero
        for (float ignored : numeri) {
            if(i >= numeri.size())
            {
                break;
            }
            String oper = getOperazione().get(i-1); // Operazione tra numeri[i-1] e numeri[i]
            float numero = getNumeri().get(i);
            switch (oper)
            {
                case "+" -> setRisultato(risultato += numero);
                case "-" -> setRisultato(risultato -= numero);
                case "x" -> setRisultato(risultato *= numero);
                case "/" ->
                {
                    if(numero == 0)
                        throw new DivisionePerZeroException("\nErrore: La divisione per 0 è indefinibile\n");
                }
                case "%" -> setRisultato(risultato%=numero);
                case "^" -> setRisultato((float) Math.pow(risultato,numero));
            }
            i++;
        }
    }
    public String printRisultato()
    {
        return(getRisultato()+"\n");
    }
}
