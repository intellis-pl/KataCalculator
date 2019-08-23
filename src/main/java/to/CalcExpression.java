package to;

public class CalcExpression {

    private String delimiters;
    private String numbers;

    public CalcExpression(String delimiters, String numbers) {
        this.delimiters = delimiters;
        this.numbers = numbers;
    }

    public String getDelimiters() {
        return delimiters;
    }

    public String getNumbers() {
        return numbers;
    }
}
