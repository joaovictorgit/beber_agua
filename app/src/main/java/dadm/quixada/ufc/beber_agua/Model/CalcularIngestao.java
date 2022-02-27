package dadm.quixada.ufc.beber_agua.Model;

public class CalcularIngestao {
    private double ml_jovem;
    private double ml_adulto;
    private double ml_idoso;
    private double ml_mais_66_anos;

    private double resultado_ml;
    private double resultado_total_ml;

    public CalcularIngestao(){
        this.ml_jovem = 40.0;
        this.ml_adulto = 35.0;
        this.ml_idoso = 30.0;
        this.ml_mais_66_anos = 25.0;

        this.resultado_ml = this.resultado_total_ml = 0.0;
    }

    public void CalcularTotalML(double peso, int idade){
        if(idade <= 17){
            this.resultado_ml = peso * this.ml_jovem;
            this.resultado_total_ml = this.resultado_ml;
        } else if(idade <= 55){
            this.resultado_ml = peso * this.ml_adulto;
            this.resultado_total_ml = this.resultado_ml;
        } else if(idade <= 65){
            this.resultado_ml = peso * this.ml_idoso;
            this.resultado_total_ml = this.resultado_ml;
        } else {
            this.resultado_ml = peso * this.ml_mais_66_anos;
            this.resultado_total_ml = this.resultado_ml;
        }
    }

    public double ResultadoML(){
        return this.resultado_total_ml;
    }
}
