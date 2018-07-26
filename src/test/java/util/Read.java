package util;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class Read {
    private static String clean(String text) {
        return text
                .replaceAll("[\\r]+", "\n")
                .replaceAll("[\\n]+", "\n")
                .trim();
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

    public static boolean sameLines(String s1, String s2) {
        String[] lines1 = clean(s1).split("\n");
        Arrays.sort(lines1);
        String[] lines2 = clean(s2).split("\n");
        Arrays.sort(lines2);
        return Arrays.equals(lines1, lines2);
    }
}
