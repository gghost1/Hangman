package backend.academy.exceptions;

public class StorageNotInitializedException extends Exception {
    public StorageNotInitializedException(String message) {
        super("Cannot initialize words storage" + message);
    }
}
