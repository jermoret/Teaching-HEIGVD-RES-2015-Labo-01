package ch.heigvd.res.lab01.impl;

import static java.lang.Integer.min;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    private static int excludeNegative(int nbr) {
        if (nbr == -1) {
            return nbr = Integer.MAX_VALUE;
        } else {
            return nbr;
        }
    }

    /**
     * This method looks for the next new line separators (\r, \n, \r\n) to
     * extract the next line in the string passed in arguments.
     *
     * @param lines a string that may contain 0, 1 or more lines
     * @return an array with 2 elements; the first element is the next line with
     * the line separator, the second element is the remaining text. If the
     * argument does not contain any line separator, then the first element is
     * an empty string.
     */
    public static String[] getNextLine(String lines) {
        int idxWin = excludeNegative(lines.indexOf("\r\n"));
        int idxMac = excludeNegative(lines.indexOf("\r"));
        int idxUnix = excludeNegative(lines.indexOf("\n"));

        int idx = min(idxWin, min(idxMac, idxUnix));
        
        int idxSize = 1;
        if(idx == idxWin)
            idxSize = 2;

        String[] tab = new String[2];

        if (idx == Integer.MAX_VALUE) {
            tab[0] = "";
            tab[1] = lines;
        } else {
            tab[0] = lines.substring(0, idx + idxSize);
            tab[1] = lines.substring(idx + idxSize);
        }

        /*for(int i = 0; i < lines.length(); ++i) {
         if(lines.charAt(i) == '\n' && !possiblRetMac) {
         tab[1] += lines.charAt(i);
         } else if(lines.charAt(i) == '\r') {
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
         }*/
        return tab;
    }

}
