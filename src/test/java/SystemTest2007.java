import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static util.Read.sameLines;
import static util.Read.text;

public class SystemTest2007 {

    @Test
    public void test2007() throws ParseException {
        String[] args = new String[]{"-i", "src/test/resources/excel/Test2007.xlsx"};
        String path = "/text/test2007.txt";

        assert sameLines(
                text(path),
                new ExcelExport().parse(args)
        );
    }

    @Test
    public void testPassword2007() throws ParseException {
        String[] args = new String[]{"-i", "src/test/resources/excel/Test-password-2007.xlsx", "-p", "Password"};
        String path = "/text/testPassword2007.txt";

        assert sameLines(
                text(path),
                new ExcelExport().parse(args)
        );
    }

    @Test
    public void testUnknownPassword2007() throws ParseException {
        String[] args = new String[]{"-i", "src/test/resources/excel/Test-password-2007.xlsx", "-p", "123", "-p", "BAD_PASSWORD"};
        String path = "/text/testUnknownPassword2007.txt";

        assert sameLines(
                text(path),
                new ExcelExport().parse(args)
        );
    }
}