public class Specifikace {

    public String typKuzelosecky(double velDet, double malDet) {
        String typ = "<html><body>Singulární kuželosečka<br> - různoběžky<br> - přímka<br> - bod</body></html>";
        if (velDet != 0) {
            typ = "<html><body>Regulární kuželosečka<br>";
            if (malDet < 0) {
                typ += " - Hyperbola</body></html>";
            } else if (malDet == 0) {
                typ += " - Parabola</body></html>";
            } else {
                typ += " - Elipsa<br> - Kružnice</body></html>";
            }
        }
        return typ;
    }

    public String vypisMatice(double[][] matice) {
        String vypis = "";
        if (matice.length > 1) {
            vypis = "<html><body>/ ";
            for (int i = 0; i < matice.length; i++) {
                for (int j = 0; j < matice.length; j++) {
                    // levá závorka matice
                    if (j == 0) {
                        if (i > 0 && i < (matice.length - 1)) {
                            vypis += "| ";
                        } else if (i == (matice.length - 1)) {
                            vypis += "\\ ";
                        }
                    }
                    // prvek matice
                    vypis += matice[i][j] + " ";

                    // pravá závorka matice
                    if (j == (matice.length - 1)) {
                        if (i == 0) {
                            vypis += "\\<br>";
                        } else if (i > 0 && i < (matice.length - 1)) {
                            vypis += "| <br>";
                        }
                    }
                }
            }
            vypis += "/<body><html>";
        } else {
            // v případě, že matice je izomorfní reálným číslům
            vypis += matice[0][0];
        }
        return vypis;
    }
}
