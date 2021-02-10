package exception;

//Exception thrown when transaction not successful
public class UnsuccessfulTransactionException extends Exception {
    private String msg;

    public UnsuccessfulTransactionException(String msg) {
        this.msg = "Unsuccessful Transaction. " + msg;
        System.out.println(this.msg);
    }

    //EFFECTS: String representation of the exception
    public String toString() {
        return msg;
    }
}
