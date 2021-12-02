import javax.swing.*;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SpravceDat {

    public void uloz(File vystupniSoubor, String[] XMLs, List<DefaultListModel> listyDat) throws IOException, XMLStreamException {
        if (vystupniSoubor.exists()) {
            if (!vystupniSoubor.createNewFile()) {
                System.out.println("Nepodařilo se vytvořit nový soubor! Zkontrolujte oprávnění.");
            }

            final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(vystupniSoubor));

            for(int i = 0; i < XMLs.length; i++) {
                final ZipEntry zip = new ZipEntry(XMLs[i]);
                zos.putNextEntry(zip);
                // pomocný output stream
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // náš xml encoder
                XMLEncoderData encoder = new XMLEncoderData(baos);
                // tímto zapíšeme data do pomocného streamu
                encoder.zapisObjektu(Collections.list(listyDat.get(i).elements()));
                // nakonec obsah pomocného streamu zapíšeme do zipu
                zos.write(baos.toByteArray());
            }
            zos.flush();
            zos.close();

        }

    }

}
