import org.apache.commons.cli.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class CLIOptions {

    private final Options options = new Options();

    CLIOptions() {
        options.addOption(option("i", "input", "File(s) or folder(s) to process", "path", false));
        options.addOption(option("x", "export", "Export files to path. If no path is given, each file will be exported in place", "path", true));
        options.addOption(option("p", "password", "Password for file", "password", true));
    }

    private Option option(String shortName, String longName, String description, String argument, boolean isOptional) {
        Option.Builder builder = Option
                .builder(shortName)
                .longOpt(longName);

        if (argument != null)
            builder = builder
                    .hasArgs()
                    .argName(argument)
                    .optionalArg(isOptional);

        builder.desc(description);
        return builder.build();
    }

    Optional<CLIRequest> parse(String[] argv) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, argv);

        if (commandLine.hasOption("i"))
            return Optional.of(new CLIRequest(
                    commandLine.getOptionValues("i"),
                    commandLine.getOptionValues("p"),
                    commandLine.hasOption("x"),
                    commandLine.getOptionValue("x")
            ));
        else {
            return Optional.of(new CLIRequest(getHelp()));
        }
    }

    private String getHelp() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        HelpFormatter hf = new HelpFormatter();
        hf.printHelp(
                pw,
                200,
                "./excel-to-csv.sh",
                "options:",
                options,
                hf.getLeftPadding(),
                hf.getDescPadding(),
                "",
                true
        );
        return sw.toString().trim();
    }

    private void _printParameters(CommandLine line) {
        Table table = new Table("input option", "argument name", "argument values");
        Stream.of(line.getOptions()).forEach(o -> table.row(o.getOpt(), o.getArgName(), o.getValuesList()));

        Logger.log("command line options: " + line.getOptions().length);
        Logger.log(table);
    }
}
