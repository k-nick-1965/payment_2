package exchange;

import java.io.IOException;
import java.util.Optional;

public interface ExchWithClient {

    <T extends Pack> Optional<T> giveFromTheClient(Class<T> valueType) throws PackException, ClassNotFoundException;

    void sendToClient(Pack cont) throws IOException;

}
