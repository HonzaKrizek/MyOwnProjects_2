import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.List;

public class XMLEncoderData {

    private final OutputStream outputStream;
    private final FileWriter filewriter;

    public XMLEncoderData(OutputStream outputStream) {
        this.outputStream = outputStream;
        filewriter = null;
    }


    public XMLEncoderData(FileWriter fileWriter) {
        this.filewriter = fileWriter;
        outputStream = null;
    }

    public void zapisObjektu(List<Object> objects) throws XMLStreamException {

        // pomocný string
        String skupina = "";
        if (!objects.isEmpty()) {
            if (objects.get(0) instanceof RasaData){
                skupina = Konstanty.RASY;
            } else if (objects.get(0) instanceof PovolaniData) {
                skupina = Konstanty.POVOLANII;
            } else if (objects.get(0) instanceof DovednostData) {
                skupina = Konstanty.DOVEDNOSTI;
            } else if (objects.get(0) instanceof ZvlastniSchopnostData) {
                skupina = Konstanty.SCHOPNOSTI;
            }

            XMLOutputFactory xof = XMLOutputFactory.newFactory();
            XMLStreamWriter xsw = null;

            try {
                // Roztřídění podle výstupu buď ZIP nebo XML
                if (filewriter == null && !(outputStream == null)){
                    xsw = xof.createXMLStreamWriter(outputStream);
                } else if (outputStream == null && !(filewriter == null)){
                    xsw = xof.createXMLStreamWriter(filewriter);
                } else{
                    return;
                }

                xsw.writeStartDocument();
                xsw.writeStartElement(skupina);

                // roztřídění podle tříd
                if (objects.get(0) instanceof RasaData){
                    for (Object object : objects) {
                        zapisRasu(xsw, (RasaData) object);
                    }
                } else if (objects.get(0) instanceof PovolaniData) {
                    for (Object object : objects) {
                        zapisPovolani(xsw, (PovolaniData) object);
                    }
                } else if (objects.get(0) instanceof DovednostData) {
                    for (Object object : objects) {
                        zapisDovednost(xsw, (DovednostData) object);
                    }
                } else if (objects.get(0) instanceof ZvlastniSchopnostData) {
                    for (Object object : objects) {
                        zapisSchopnost(xsw, (ZvlastniSchopnostData) object);
                    }
                }

                xsw.writeEndElement();
                xsw.writeEndDocument();
                xsw.flush();
            } finally {
                if (xsw != null) {
                    try {
                        xsw.close();
                    } catch (XMLStreamException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private void zapisElement(XMLStreamWriter xsw, String nazevElementu, String hodnota) throws XMLStreamException{
        xsw.writeStartElement(nazevElementu);
        xsw.writeCharacters(hodnota);
        xsw.writeEndElement();
    }

    private void zapisRasu(XMLStreamWriter xsw, RasaData rasa) throws XMLStreamException {
        xsw.writeStartElement(Konstanty.RASA);
        xsw.writeAttribute(Konstanty.INDEX, Integer.toString(rasa.getIndex()));
        zapisElement(xsw, Konstanty.NAZEV, rasa.getNazevRasy());
        zapisElement(xsw, Konstanty.POPIS, rasa.getPopisRasy());
        xsw.writeEndElement();
    }

    private void zapisPovolani(XMLStreamWriter xsw, PovolaniData povolani) throws XMLStreamException {
        xsw.writeStartElement(Konstanty.POVOLANI);
        xsw.writeAttribute(Konstanty.INDEX, Integer.toString(povolani.getIndex()));
        zapisElement(xsw, Konstanty.NAZEV, povolani.getNazevPovolani());
        zapisElement(xsw, Konstanty.POPIS, povolani.getPopisPovolani());
        xsw.writeEndElement();
    }

    private void zapisDovednost(XMLStreamWriter xsw, DovednostData dovednost) throws XMLStreamException {
        xsw.writeStartElement(Konstanty.DOVEDNOST);
        xsw.writeAttribute(Konstanty.INDEX, Integer.toString(dovednost.getIndex()));
        zapisElement(xsw, Konstanty.NAZEV, dovednost.getNazevDovednosti());
        zapisElement(xsw, Konstanty.POPIS, dovednost.getPopisDovednosti());
        zapisElement(xsw, Konstanty.VLASTNOST, dovednost.getVlastnostDovednosti());
        zapisElement(xsw, Konstanty.PRIRAZENI, dovednost.getPovolani());
        xsw.writeEndElement();
    }

    private void zapisSchopnost(XMLStreamWriter xsw, ZvlastniSchopnostData schopnost) throws XMLStreamException {
        xsw.writeStartElement(Konstanty.SCHOPNOST);
        xsw.writeAttribute(Konstanty.INDEX, Integer.toString(schopnost.getIndex()));
        zapisElement(xsw, Konstanty.NAZEV, schopnost.getNazevZvlastniSchopnosti());
        zapisElement(xsw, Konstanty.POPIS, schopnost.getPopisZvlastniSchopnosti());
        zapisElement(xsw, Konstanty.VLASTNOST, schopnost.getVlastnostZvlastniSchopnosti());
        zapisElement(xsw, Konstanty.PRIRAZENI, schopnost.getPrirazeni());
        zapisElement(xsw, Konstanty.PRIKLAD, schopnost.getPrikladZvlastniSchopnosti());
        xsw.writeEndElement();
    }




}
