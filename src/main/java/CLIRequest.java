import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CLIRequest {

    private final Set<File> files;
    private final Set<String> passwords;
    private final boolean shouldExport;
    private final File output;
    private final String help;

    CLIRequest(String help) {
        files = null;
        passwords = null;
        shouldExport = false;
        output = null;
        this.help = help;
    }

    CLIRequest(String[] paths, String[] passwords, boolean shouldExport, String output) {

        this.files = Stream.of(paths)
                .flatMap(this::toFile)
                .collect(Collectors.toSet());

        this.passwords = passwords == null ? new HashSet<>() : new HashSet<>(Arrays.asList(passwords));
        this.shouldExport = shouldExport;
        this.output = output != null ? new File(output) : null;
        if (output != null && !this.output.isDirectory())
            throw new RuntimeException("Output should be a directory");

        help = null;
    }

    private Stream<File> toFile(String location) {
        File path = new File(location);

        if (path.isDirectory())
            return Stream
                    .of(Objects.requireNonNull(path.listFiles()))
                    .filter(this::isExcel);
        else if (isExcel(path))
            return Stream.of(path);
        else
            return Stream.empty();
    }

    private boolean isExcel(File f) {
        return f.isFile() && f.getName().toLowerCase().matches(".*\\.xls[x]*$");
    }

    Set<File> getFiles() {
        return files;
    }

    Set<String> getPasswords() {
        return passwords;
    }

    boolean shouldExport() {
        return shouldExport;
    }

    Optional<File> getOutput() {
        return Optional.ofNullable(output);
    }

    Optional<String> getHelp() {
        return Optional.ofNullable(help);
    }
}
