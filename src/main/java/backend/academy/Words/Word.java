package backend.academy.Words;

public record Word(String name, String hint) {

    public void displayWord() {
        System.out.println(name);
    }

    public void displayHint() {
        System.out.println(hint);
    }

}
