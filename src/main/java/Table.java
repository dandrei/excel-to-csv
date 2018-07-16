import com.bethecoder.ascii_table.ASCIITable;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private transient final String[] head;
    private transient final List<String[]> body = new ArrayList<>();

    Table(final String... head) {
        this.head = head;
    }

    void row(final Object... strings) {
        String[] list = new String[strings.length];

        for (int i = 0; i < strings.length; i++)
            if (strings[i] == null)
                list[i] = "NULL";
            else
                list[i] = strings[i].toString();

        body.add(list);
    }

    /**
     * Returns the table
     */
    public String toString() {
        return body.isEmpty()
                ? "N/A"
                : ASCIITable
                .getInstance()
                .getTable(head, body.toArray(new String[][]{}));
    }
}
