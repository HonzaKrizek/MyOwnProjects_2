import javax.swing.*;
import javax.xml.stream.XMLStreamException;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class DataForm extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox vlastntiCombo;
    private JComboBox prirazeniCombo;
    private JButton pridatButton;
    private JComboBox comboBox1;
    private JButton doXMLButton;
    private JList list1;
    private JTextField textField5;
    private JPanel mainPanel;
    private JLabel popisLabel;
    private JButton doZIPButton;
    private JLabel vlastostLabel;
    private JLabel prirazeniLabel;
    private JLabel prikladLabel;

    // listy dat
    private DefaultListModel<RasaData> rasy = new DefaultListModel<>();
    private DefaultListModel<PovolaniData> povolanis = new DefaultListModel<>();
    private DefaultListModel<DovednostData> dovednosti = new DefaultListModel<>();
    private DefaultListModel<ZvlastniSchopnostData> schopnosti = new DefaultListModel<>();

    private String soubor ="";


    public DataForm(){
        setContentPane(mainPanel);
        setSize(600,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // nastavení vlastností do comboBoxu
        vlastntiCombo.setModel(new DefaultComboBoxModel(new String[]{"Tělo", "Vliv", "Duše"}));


        // tlačítko přidat
        pridatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // pomocné stringy
                String vlastnost = vlastntiCombo.getItemAt(vlastntiCombo.getSelectedIndex()).toString();
                String prirazeni = "";
                if (!(prirazeniCombo.getSelectedIndex() == -1)){
                    prirazeni = prirazeniCombo.getItemAt(prirazeniCombo.getSelectedIndex()).toString();
                }

                // přidání položek do Listu
                switch (comboBox1.getSelectedIndex()) {
                    case 1:
                        if(!textField1.getText().equals("") &&
                                !textField2.getText().equals("")) {
                            list1.setModel(rasy);
                            RasaData rasa = new RasaData();
                            rasa.setIndex(rasy.getSize()+1);
                            rasa.setNazevRasy(textField1.getText());
                            rasa.setPopisRasy(textField2.getText());
                            rasy.addElement(rasa);
                        }
                        break;
                    case 2:
                        if(!textField1.getText().equals("") &&
                                !textField2.getText().equals("")) {
                            list1.setModel(povolanis);
                            PovolaniData povolani = new PovolaniData();
                            povolani.setIndex(povolanis.getSize()+1);
                            povolani.setNazevPovolani(textField1.getText());
                            povolani.setPopisPovolani(textField2.getText());
                            povolanis.addElement(povolani);
                        }
                        break;
                    case 3:
                        if(!textField1.getText().equals("") &&
                                !textField2.getText().equals("") &&
                                !(prirazeniCombo.getSelectedIndex() == -1)) {
                            list1.setModel(dovednosti);
                            DovednostData dovednost = new DovednostData();
                            dovednost.setIndex(dovednosti.getSize()+1);
                            dovednost.setNazevDovednosti(textField1.getText());
                            dovednost.setPopisDovednosti(textField2.getText());
                            dovednost.setVlastnostDovednosti(vlastnost);
                            dovednost.setPovolani(prirazeni);
                            dovednosti.addElement(dovednost);
                        }
                        break;
                    case 4:
                        if(!textField1.getText().equals("") &&
                                !textField2.getText().equals("") &&
                                !(prirazeniCombo.getSelectedIndex() == -1) &&
                                !textField5.getText().equals("")) {
                            list1.setModel(schopnosti);
                            ZvlastniSchopnostData schopnost = new ZvlastniSchopnostData();
                            schopnost.setIndex(schopnosti.getSize()+1);
                            schopnost.setNazevZvlastniSchopnosti(textField1.getText());
                            schopnost.setPopisZvlastniSchopnosti(textField2.getText());
                            schopnost.setVlastnostZvlastniSchopnosti(vlastnost);
                            schopnost.setPrirazeni(prirazeni);
                            schopnost.setPrikladZvlastniSchopnosti(textField5.getText());
                            schopnosti.addElement(schopnost);
                        }
                        break;
                    case 0:
                        clear();
                        break;
                }
                clear();
            }
        });

        // výběrové pole
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                popisLabel.setText(comboBox1.getItemAt(comboBox1.getSelectedIndex()).toString() + " ");

                clear();
                switch (comboBox1.getSelectedIndex()) {
                    case 0:
                        visible(true,true,true);
                        break;
                    case 1:
                        list1.setModel(rasy);
                        visible(false,false,false);
                        break;
                    case 2:
                        list1.setModel(povolanis);
                        visible(false,false,false);
                        break;
                    case 3:
                        list1.setModel(dovednosti);
                        prirazeniCombo.setModel(prirazeni());
                        visible(true,true,false);
                        break;
                    case 4:
                        list1.setModel(schopnosti);
                        prirazeniCombo.setModel(prirazeni());
                        visible(true,true,true);
                        break;
                }
            }
        });

        // zápis do XML podle zvoleného Listu
        doXMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (comboBox1.getSelectedIndex()) {
                    case 0:
                        break;
                    case 1:
                        serializuj("rasy.xml", rasy);
                        break;
                    case 2:
                        serializuj("povolani.xml", povolanis);
                        break;
                    case 3:
                        serializuj("dovednosti.xml", dovednosti);
                        break;
                    case 4:
                        serializuj("schopnosti.xml", schopnosti);
                        break;
                }

            }
        });

        // zápis do ZIPu
        doZIPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final File data = new File("drd.zip");

                String[] xmlData = {"rasy.xml", "povolani.xml", "dovednosti.xml", "schopnosti.xml"};
                ArrayList<DefaultListModel> listyData = new ArrayList<>();
                listyData.add(rasy);
                listyData.add(povolanis);
                listyData.add(dovednosti);
                listyData.add(schopnosti);

                try {
                    data.createNewFile();
                    SpravceDat spravce = new SpravceDat();
                    spravce.uloz(data, xmlData, listyData);
                } catch (IOException|XMLStreamException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    // hlavní construktor
    public static void main(String[] args) {
        DataForm dataForm = new DataForm();
    }

    // vyčištění textových polí
    private void clear(){
        textField1.setText("");
        textField2.setText("");
        textField5.setText("");
    }

    // zneviditelnění položek
    private void visible(boolean tf3, boolean tf4, boolean tf5){
        vlastntiCombo.setVisible(tf3);
        vlastostLabel.setVisible(tf3);
        prirazeniCombo.setVisible(tf4);
        prirazeniLabel.setVisible(tf4);
        textField5.setVisible(tf5);
        prikladLabel.setVisible(tf5);
    }

    // přiřazení názvu povolání a ras do výběru comboListu
    private DefaultComboBoxModel<String> prirazeni(){
        DefaultComboBoxModel<String> prirazeni = new DefaultComboBoxModel<>();
        for (int i = 0; i < povolanis.getSize(); i++){
            prirazeni.addElement(povolanis.elementAt(i).getNazevPovolani());
        }
        if (comboBox1.getSelectedIndex() == 4){
            for (int i = 0; i < rasy.getSize(); i++){
                prirazeni.addElement(rasy.elementAt(i).getNazevRasy());
            }
        }
        return prirazeni;
    }

    // zápis do XML
    private void serializuj(String soubor, DefaultListModel listModel){
        try {
            XMLEncoderData xed = new XMLEncoderData(new FileWriter(soubor));
            xed.zapisObjektu(Collections.list(listModel.elements()));
        } catch (IOException | XMLStreamException ex) {
            ex.printStackTrace();
        }
    }
}
