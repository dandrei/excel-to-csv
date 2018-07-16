import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;

class ExcelReport {

    private static String Y = "yes";
    private static String N = "no";
    private static String N_A = "";

    // input
    private final File file;
    private final Workbook workbook;
    private final String password;

    // derived
    private final String format;
    private final boolean isPasswordProtected;
    private final boolean isPasswordKnown;
    private final int sheets;

    /**
     * File has an unknown password
     */
    ExcelReport(File file) {
        this(file, null, null, true);
    }

    /**
     * File doesn't have a password
     */
    ExcelReport(File file, Workbook workbook) {
        this(file, workbook, null, false);
    }

    /**
     * File has a password a known password
     */
    ExcelReport(File file, Workbook workbook, String password) {
        this(file, workbook, password, true);
    }

    /**
     * @param file                the Excel file
     * @param workbook            the Workbook representation
     * @param password            the password for the file
     * @param isPasswordProtected whether the file has a password
     */
    private ExcelReport(File file, Workbook workbook, String password, boolean isPasswordProtected) {
        this.file = file;
        this.workbook = workbook;
        this.password = password;

        this.format = workbook == null ? null :
                workbook.getClass().equals(HSSFWorkbook.class) ? "2003" :
                        workbook.getClass().equals(XSSFWorkbook.class) ? "2007" :
                                null;

        this.isPasswordProtected = isPasswordProtected;
        this.isPasswordKnown = isPasswordProtected && password != null;
        this.sheets = workbook != null ? workbook.getNumberOfSheets() : 0;
    }

    File getFile() {
        return file;
    }

    Workbook getWorkbook() {
        return workbook;
    }

    boolean isPasswordKnown() {
        return isPasswordKnown;
    }

    boolean noPassword() {
        return !isPasswordProtected;
    }

    boolean canExport() {
        return noPassword() || isPasswordKnown;
    }

    String format() {
        return format != null ? format : N_A;
    }

    String passwordProtected() {
        return isPasswordProtected ? Y : N;
    }

    String password() {
        return isPasswordProtected && isPasswordKnown ? password : N_A;
    }

    String passwordKnown() {
        if (isPasswordProtected)
            if (isPasswordKnown)
                return Y;
            else
                return N;
        else
            return N_A;
    }

    String extensionMatches() {
        if (format == null)
            return N_A;

        String name = file.getName().toLowerCase();
        return (name.endsWith("xls") && "2003".equals(format)) || (name.endsWith("xlsx") && "2007".equals(format)) ? Y : N;
    }

    String sheets() {
        return sheets > 0 ? String.valueOf(sheets) : N_A;
    }
}
