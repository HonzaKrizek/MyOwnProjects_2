import java.math.MathContext;

public class Matika {

    int n = 3;
    double[][] matice = new double[n][n];
    double[][] kvadratickaFormaMatice = new double[n-1][n-1];

    // určení determinantu maticepodle Laplaceova rozvoje
    public double determinant(double[][] matice){
        double det = 0;
        // Laplaceův rozvoj determinantu
        for (int i = 0; i < matice.length; i++){
            // znaménko k algebraickému doplňku (-1)^{i+1}
            int sign = (i)%2 == 0 ? 1: -1;
            // podmínka, je-li velikost matice 1x1, již se nevolá subdeterminant
            det += matice.length > 1 ? sign*matice[i][0]*determinant(subMatice(matice,i,0)) : matice[i][0];
        }
        return det;
    }

    // matice získaná vynecháním i-tého řádku (řádek) a j-tého sloupce (sloupec)
    public double[][] subMatice(double[][] matice, int radek, int sloupec) {
        double[][] novaMatice = matice;
        int velikostMatice = matice.length;
        if (velikostMatice > 1 || radek < 0 || radek > matice.length || sloupec < 0 || sloupec > matice.length){
            novaMatice = new double[matice.length-1][matice.length-1];
            // nad vynechaným řádkem
            for (int i = 0; i < radek; i++){
                // napravo od vynechaného sloupce
                for (int j = 0; j < sloupec; j++) {
                    novaMatice[i][j] = matice[i][j];
                }
                // nalevo od vynechaného sloupce
                for (int j = sloupec + 1; j < matice.length; j++) {
                    novaMatice[i][j-1] = matice[i][j];
                }
            }
            // pod vynechaným řádkem
            for (int i = radek + 1; i < matice.length; i++){
                // nalevo od vynechaného sloupce
                for (int j = sloupec + 1; j < matice.length; j++) {
                    novaMatice[i-1][j-1] = matice[i][j];
                }
                // napravo od vynechaného sloupce
                for (int j = 0; j < sloupec; j++) {
                    novaMatice[i-1][j] = matice[i][j];
                }
            }
        }
        return novaMatice;
    }

    // záměna dvou řádků v matici
    public double[][] zamenaRadku(double[][] matice, int radek1, int radek2) {
        if ((radek1 < matice.length && radek1 >= 0) && (radek2 < matice.length && radek2 >= 0)) {
            if (radek1 == radek2){
                return matice;
            }
            for (int i = 0; i < matice.length; i++) {
                double help = matice[radek1][i];
                matice[radek1][i] = matice[radek2][i];
                matice[radek2][i] = help;
            }
        }
        return matice;
    }

    // c násobek řádku
    public double[][] cNasobekRadku(double[][] matice, int radek, double cNasobek) {
        if (radek >= 0 && radek < matice.length && cNasobek != 0) {
            for (int i = 0; i < matice.length; i++) {
                matice[radek][i] *= cNasobek;
            }
        }
        return matice;
    }

    // přičtení c násobku jednoho řádku k druhému
    public double[][] soucetRadku(double[][] matice, int radek1, int radek2, double cNasobek) {
        if ((radek1 < matice.length && radek1 >= 0) && (radek2 < matice.length && radek2 >= 0) && cNasobek != 0) {
            for (int i = 0; i < matice.length; i++) {
                matice[radek2][i] += cNasobek * matice[radek1][i];
            }
        }
        return matice;
    }

    // maximum

    // jordanuv tvar matice

    // hodnost matice




    /*
    public static void main(String[] args) {
        Matika matika = new Matika();
        matika.matice = new double[][]{{-1, 2, 0}, {1, 2, 0}, {0, 0, 3}};

        double det = matika.determinant(matika.matice);
        System.out.println("Determinant matice je: " + det);
    }

     */

}

