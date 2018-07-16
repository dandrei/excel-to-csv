import org.apache.commons.cli.ParseException;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelExport {

    public static void main(String[] argv) {
        try {
            Logger.log(new ExcelExport().parse(argv));
        } catch (Throwable e) {
            Logger.log(e.getMessage());
            e.printStackTrace();
        }
    }

    String parse(String[] argv) throws ParseException {
        Optional<String> result = new CLIOptions().parse(argv).map(cliRequest -> {

            if (cliRequest.getHelp().isPresent())
                return cliRequest.getHelp().get();

            List<ExcelReport> reports = cliRequest
                    .getFiles()
                    .stream()
                    .map(f -> buildReport(f, cliRequest.getPasswords()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            if (cliRequest.shouldExport())
                reports.stream()
                        .filter(ExcelReport::canExport)
                        .forEach(r -> new CSVExport(
                                r.getWorkbook(),
                                r.getFile(),
                                cliRequest.getOutput().orElse(r.getFile().getParentFile())
                        ));

            Table table = new Table(
                    "File",
                    "Format",
                    "Extension matches?",
                    "Password protected?",
                    "Password known?",
                    "Password",
                    "Sheets"
            );

            reports.stream()
                    .filter(Objects::nonNull)
                    .forEach(r -> table.row(
                            r.getFile().getName(),
                            r.format(),
                            r.extensionMatches(),
                            r.passwordProtected(),
                            r.passwordKnown(),
                            r.password(),
                            r.sheets()
                    ));

            return "report:\n" + table;
        });

        return result.orElse(null);
    }

    private static Optional<ExcelReport> buildReport(File file, Set<String> passwords) {
        Optional<ExcelReport> report = new ExcelWorkbook(file).buildReport();

        if (report.filter(ExcelReport::noPassword).isPresent())
            return report;

        Set<ExcelReport> candidates = passwords
                .stream()
                .map(p -> new ExcelWorkbook(file, p).buildReport())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        Optional<ExcelReport> withPassword = candidates.stream()
                .filter(ExcelReport::isPasswordKnown)
                .findFirst();

        return withPassword.isPresent() ? withPassword : candidates.stream().findFirst();

    }
}