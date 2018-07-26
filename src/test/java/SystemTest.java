import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static util.Read.sameLines;
import static util.Read.text;

public class SystemTest {

    @Test
    public void testHelp() throws ParseException {
        String[] args = new String[]{};
        String path = "/text/testHelp.txt";

        assert sameLines(
                text(path),
                new ExcelExport().parse(args)
        );
    }

    @Test
    public void testExport() throws ParseException {
        String[] args = new String[]{"-i", "src/test/resources/excel/", "-p", "123", "-p", "Password", "-x", "./src/test/resources/output/"};
        String path = "/text/testExport.txt";

        assert sameLines(
                text(path),
                new ExcelExport().parse(args)
        );
    }
}