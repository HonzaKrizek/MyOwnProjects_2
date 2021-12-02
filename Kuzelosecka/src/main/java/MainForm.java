import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame{
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;
    private JSpinner spinner4;
    private JSpinner spinner5;
    private JSpinner spinner6;
    private JButton analyzujButton;
    private JLabel obecnyTvarLabel;
    private JLabel velkyDetLabel;
    private JLabel malyDetLabel;
    private JLabel kuzeloseckaLabel;
    private JPanel mainPanel;
    private JLabel maticeLabel;

    private Matika matika;
    private Specifikace specifikace;

    public MainForm() {
        setContentPane(mainPanel);
        setSize(400,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        matika = new Matika();
        specifikace = new Specifikace();


        analyzujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // vypisuje obecnou rovnici do labelu
                obecnyTvarLabel.setText(obecnyTvar());

                // vypisuje matici do labelu
                maticeLabel.setText(specifikace.vypisMatice( velkaMatice() ));

                // vypisuje hodnotu velkého determinantu do labelu
                velkyDetLabel.setText(String.valueOf( matika.determinant(velkaMatice())) );

                // vypisuje hodnotu malého determinantu do labelu
                malyDetLabel.setText(String.valueOf(
                        matika.determinant(matika.subMatice(velkaMatice(),2,2)) ));

                // vypisuje typ kuželosečky
                kuzeloseckaLabel.setText("<html><body>Singulární kuželosečka<br> - rovnoběžky</body></html>");
                if (obecnyTvar().contains("x") || obecnyTvar().contains("y")) {
                    kuzeloseckaLabel.setText(specifikace.typKuzelosecky(
                            matika.determinant(velkaMatice()),
                            matika.determinant(matika.subMatice(velkaMatice(), 2, 2))));
                }
            }
        });
    }

    public static void main(String[] args) {
        MainForm form = new MainForm();
    }


    private String obecnyTvar() {
        String rovnice = "";
        rovnice += Integer.parseInt(spinner1.getValue().toString()) != 0 ? koeficient(spinner1) + "x^2 " : "";
        rovnice += Integer.parseInt(spinner2.getValue().toString()) != 0 ? koeficient(spinner2) + "xy " : "";
        rovnice += Integer.parseInt(spinner3.getValue().toString()) != 0 ? koeficient(spinner3) + "y^2 " : "";
        rovnice += Integer.parseInt(spinner4.getValue().toString()) != 0 ? koeficient(spinner4) + "x " : "";
        rovnice += Integer.parseInt(spinner5.getValue().toString()) != 0 ? koeficient(spinner5) + "y " : "";
        rovnice += absolutniClen(spinner6) + " ";

        rovnice += " = 0";
        if(rovnice.charAt(0) == '+') {
            rovnice = rovnice.substring(1);
        }
        return rovnice;
    }

    private String koeficient(JSpinner spinner) {
        int hodnota = Integer.parseInt(spinner.getValue().toString());

        String pomocnyText ="+";
        if (hodnota < 0) {
            pomocnyText = "";
        }
        pomocnyText += spinner.getValue().toString();

        if (hodnota == 1){
            pomocnyText = "+";
        }
        if (hodnota == -1) {
            pomocnyText = "-";
        }
        return pomocnyText;

    }

    private String absolutniClen(JSpinner spinner) {
        int hodnota = Integer.parseInt(spinner.getValue().toString());

        String pomocnyText ="+";
        if (hodnota < 0) {
            pomocnyText = "";
        }
        pomocnyText += spinner.getValue().toString();
        return pomocnyText;
    }

    private double[][] velkaMatice() {
        double[][] matice = new double[3][3];
        //if (matice.length == 3){
            matice[0][0] = Double.parseDouble(spinner1.getValue().toString());
            matice[0][1] = Double.parseDouble(spinner2.getValue().toString())/2;
            matice[0][2] = Double.parseDouble(spinner4.getValue().toString())/2;
            matice[1][0] = matice[0][1];
            matice[1][1] = Double.parseDouble(spinner3.getValue().toString());
            matice[1][2] = Double.parseDouble(spinner5.getValue().toString())/2;
            matice[2][0] = matice[0][2];
            matice[2][1] = matice[1][2];
            matice[2][2] = Double.parseDouble(spinner6.getValue().toString());
        //}
        return matice;
    }



}
