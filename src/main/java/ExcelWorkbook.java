import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

class ExcelWorkbook {

    private final File file;
    private final String password;

    ExcelWorkbook(File file) {
        this(file, null);
    }

    ExcelWorkbook(File file, String password) {
        this.file = file;
        this.password = password;
    }

    Optional<ExcelReport> buildReport() {
        String name = file.getName().toLowerCase();

        try {
            if (name.endsWith(".xls"))
                try {
                    return new Excel2003(file, password).buildReport();
                } catch (IOException | OfficeXmlFileException e) {
                    return new Excel2007(file, password).buildReport();
                }

            else if (name.endsWith(".xlsx"))
                try {
                    return new Excel2007(file, password).buildReport();
                } catch (IOException e) {
                    return new Excel2003(file, password).buildReport();
                }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();

    }
}
