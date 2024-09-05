package backend.academy.Exceptions;

public class StorageNotInitialized extends Exception{
    public StorageNotInitialized(String message) {
        super("Cannot initialize words storage" + message);
    }
}
