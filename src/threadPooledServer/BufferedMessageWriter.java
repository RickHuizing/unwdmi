package threadPooledServer;

import java.io.BufferedWriter;
import java.io.Writer;

/**
 * Created by Lenovo T420 on 21-1-2018.
 */
public class BufferedMessageWriter extends BufferedWriter {
    private int station;

    public BufferedMessageWriter(Writer out, int station) {
        super(out);
        this.station = station;
    }
    public int getStation(){
        return this.station;
    }


}
