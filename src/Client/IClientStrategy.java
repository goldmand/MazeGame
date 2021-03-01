package Client;


import java.io.InputStream;
import java.io.OutputStream;
/**
 * IClientStrategy  interface represents client request strategy.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since 2020-15-03
 */
public interface IClientStrategy {
    public void clientStrategy(InputStream inputStream, OutputStream outputStream);
}
