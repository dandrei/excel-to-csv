import com.google.common.base.Charsets;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;

class CSVExport {

    private Writer getBufferedWriter(File file) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), Charsets.UTF_8));
    }

    private ICSVWriter getWriter(File file) throws FileNotFoundException {
        return new CSVWriterBuilder(getBufferedWriter(file))
                .withSeparator(',')
                .withQuoteChar('"')
                .withEscapeChar('"')
                .build();
    }

    CSVExport(Workbook workbook, File input, File destination) {

        try {
            if (workbook.getNumberOfSheets() == 1)
                writeSheet(workbook.getSheetAt(0), csvFile(input, destination));
            else
                for (int i = 0; i < workbook.getNumberOfSheets(); i++)
                    writeSheet(workbook.getSheetAt(i), csvFile(input, i, destination));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File csvFile(File file, int sheet, File destination) throws IOException {
        return buildPath(file, destination, "." + sheet + ".csv");
    }

    private File csvFile(File file, File out) throws IOException {
        return buildPath(file, out, ".csv");
    }

    private File buildPath(File file, File destination, String suffix) throws IOException {
        return new File(destination.getCanonicalPath() + File.separator + baseName(file) + suffix);
    }

    private String baseName(File file) {
        String fileName = file.getName();

        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(0, fileName.lastIndexOf("."));
        else
            return "";
    }

    private void writeSheet(Sheet sheet, File file) throws IOException {
        if (file.exists() && !file.delete())
            throw new RuntimeException("Couldn't delete " + file.getCanonicalPath());

        try (ICSVWriter csvWriter = getWriter(file)) {
            for (Row row : sheet) {
                ArrayList<String> data = new ArrayList<>();

                for (Cell cell : row) {
                    cell.setCellType(CellType.STRING);
                    switch (cell.getCellTypeEnum()) {
                        case BOOLEAN:
                            data.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case NUMERIC:
                            data.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            data.add(String.valueOf(cell.getStringCellValue()));
                            break;
                        case BLANK:
                            data.add("");
                            break;
                        default:
                            data.add(cell.toString());
                    }
                }
                csvWriter.writeNext(data.toArray(new String[]{}));
                csvWriter.flush();
            }
        }
    }
}
