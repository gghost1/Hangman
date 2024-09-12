package backend.academy.Words;

public record Word(String name, String hint) {

    public void displayWord() {
        System.out.println(name);
        /*
        @todo: replace by front method
         */
    }

    public void displayHint() {
        System.out.println(hint);
        /*
        @todo: replace by front method
         */
    }

}
