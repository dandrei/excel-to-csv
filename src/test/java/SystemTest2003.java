import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static util.Read.clean;
import static util.Read.text;

public class SystemTest2003 {

    @Test
    public void test2003() throws ParseException {
        String[] args = new String[]{"-i", "src/test/resources/excel/Test2003.xls"};
        String path = "/text/test2003.txt";
        assert clean(text(path)).equals(clean(new ExcelExport().parse(args)));
    }

    @Test
    public void testPassword2003() throws ParseException {
        String[] args = new String[]{"-i", "src/test/resources/excel/Test-password-2003.xls", "-p", "123", "-p", "Password"};
        String path = "/text/testPassword2003.txt";
        assert clean(text(path)).equals(clean(new ExcelExport().parse(args)));
    }

    @Test
    public void testUnknownPassword2003() throws ParseException {
        String[] args = new String[]{"-i", "src/test/resources/excel/Test-password-2003.xls", "-p", "123", "-p", "BAD_PASSWORD"};
        String path = "/text/testUnknownPassword2003.txt";
        assert clean(text(path)).equals(clean(new ExcelExport().parse(args)));
    }
}