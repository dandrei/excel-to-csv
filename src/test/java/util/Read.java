package util;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.stream.Collectors;

public class Read {
    public static String clean(String text) {
        return text.replaceAll("[\\r\\n]", "").trim();
    }

    public static String text(String path) {
        return Joiner.on(System.lineSeparator()).join(lines(path)).trim();
    }

    private static Collection<String> lines(String path) {
        return reader(path).lines().collect(Collectors.toList());
    }

    private static BufferedReader reader(String path) {
        try {
            return new BufferedReader(
                    new InputStreamReader(
                            Read.class.getResource(path).openStream(),
                            Charset.forName(Charsets.UTF_8.name()).newDecoder()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
