package Exception;

import exception.UnsuccessfulTransactionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnsuccessfulTransactionExceptionTest {

    @Test
    void testToString() {
        UnsuccessfulTransactionException ute = new UnsuccessfulTransactionException("testing");
        assertEquals("Unsuccessful Transaction. testing", ute.toString());

    }
}
