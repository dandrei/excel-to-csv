import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

class Excel2003 {
    private final File file;
    private final String password;

    Excel2003(File file, String password) {
        this.file = file;
        this.password = password;
    }

    Optional<ExcelReport> buildReport() throws IOException {
        try {
            return Optional.of(new ExcelReport(
                    file,
                    new HSSFWorkbook(new FileInputStream(file))
            ));
        } catch (EncryptedDocumentException ignored) {
        }

        try {
            return Optional.of(new ExcelReport(
                    file,
                    getWorkbook(password),
                    password
            ));
        } catch (EncryptedDocumentException ignored) {
        }

        return Optional.of(new ExcelReport(file));
    }

    private Workbook getWorkbook(String password) throws IOException {
        Biff8EncryptionKey.setCurrentUserPassword(password);
        NPOIFSFileSystem fs = new NPOIFSFileSystem(file, true);
        HSSFWorkbook hwb = new HSSFWorkbook(fs.getRoot(), true);
        Biff8EncryptionKey.setCurrentUserPassword(null);
        return hwb;
    }
}
