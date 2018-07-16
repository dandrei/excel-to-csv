import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

class Excel2007 {

    private final File file;
    private final String password;

    Excel2007(File file, String password) {
        this.file = file;
        this.password = password;
    }

    Optional<ExcelReport> buildReport() throws IOException {
        try {
            Workbook wb = new XSSFWorkbook(new FileInputStream(file));
            return Optional.of(new ExcelReport(file, wb));
        } catch (OLE2NotOfficeXmlFileException ignored) {
            // Has password
        }

        try {
            Workbook wb = withPassword(password);
            return Optional.of(new ExcelReport(file, wb, password));
        } catch (NullPointerException | GeneralSecurityException ignored) {
            // Bad password
        }

        return Optional.of(new ExcelReport(file));
    }

    private Workbook withPassword(String password) throws IOException, GeneralSecurityException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        Decryptor decryptor = Decryptor.getInstance(new EncryptionInfo(fs));
        decryptor.verifyPassword(password);
        return new XSSFWorkbook(decryptor.getDataStream(fs));
    }
}
