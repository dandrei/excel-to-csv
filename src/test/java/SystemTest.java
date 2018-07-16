import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static util.Read.clean;
import static util.Read.text;

public class SystemTest {

    @Test
    public void testHelp() throws ParseException {
        String[] args = new String[]{};
        String path = "/text/testHelp.txt";
        assert clean(text(path)).equals(clean(new ExcelExport().parse(args)));
    }

    @Test
    public void testExport() throws ParseException {
        String[] args = new String[]{"-i", "src/test/resources/excel/", "-p", "123", "-p", "Password", "-x", "./src/test/resources/output/"};
        String path = "/text/testExport.txt";
        assert clean(text(path)).equals(clean(new ExcelExport().parse(args)));
    }
}