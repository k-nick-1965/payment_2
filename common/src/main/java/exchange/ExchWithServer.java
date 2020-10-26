package exchange;

import java.io.IOException;

public interface ExchWithServer {

    <T extends Pack> T giveFromTheServer(Pack cont, Class<T> valueType ) throws IOException, WaitAnswerException, ClassNotFoundException;

    void sendToServer(Pack cont) throws IOException;

}
