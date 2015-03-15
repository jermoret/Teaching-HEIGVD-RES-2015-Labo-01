package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private boolean newLine = true;
    private boolean possiblRetMac = false;
    private int lineNumber = 1;
    private final String retUnix = "\n";
    private final String retMac = "\r";
    private final String retWin = "\r\n";

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        String ret = new String();
        if (str.contains(retWin)) {
            ret = retWin;
        } else if (str.contains(retMac)) {
            ret = retMac;
        } else if (str.contains(retUnix)) {
            ret = retUnix;
        }

        if (newLine) {
            writeNumberOfLine();
            newLine = false;
        }
        if (!ret.isEmpty()) {
            //int idxDeb = 0;
            int idxFin;
            while ((idxFin = str.indexOf(ret)) != -1) {
                super.write(str.substring(0, idxFin) + ret, 0, str.substring(0, idxFin).length() + ret.length());
                writeNumberOfLine();
                str = str.substring(idxFin + ret.length());
            }
            if (!str.isEmpty()) {
                super.write(str, 0, str.length());
            }

            /*String[] lines = str.split(ret);
             for(String s : lines) {
             writeNumberOfLine(ret);
             super.write(s, 0, s.length());
             newLine = false;
             }*/
        } else {
            super.write(str, off, len);
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        write(new String(cbuf), off, len);
    }

    @Override
    public void write(int c) throws IOException {
        if (newLine) {
            writeNumberOfLine();
            newLine = false;
        }

        if(c == '\n' && !possiblRetMac) {
            super.write(c);
            writeNumberOfLine();
        } else if(c == '\r') {
            possiblRetMac = true;
            super.write(c);
        } else if(possiblRetMac) {
            if(c == '\n') {
                super.write(c);
                writeNumberOfLine();
            } else {
                writeNumberOfLine();
                super.write(c);
            }  
            possiblRetMac = false;
        } else {
            super.write(c);
        }
    }

    private void writeNumberOfLine() throws IOException {
        String lineNumber = String.valueOf(this.lineNumber++) + "\t";
        super.write(lineNumber, 0, lineNumber.length());
    }

}
