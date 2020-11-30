import token.Token
import tokenizer.Tokenizer
import visitor.VisitorCalculator
import visitor.VisitorParser
import visitor.VisitorPrinter

fun parsedTokens(tokens: List<Token>): List<Token> {
    val parser = VisitorParser()
    parser.iterateOverTokens(tokens)
    return parser.getParsedTokens()
}

fun printTokens(tokens: List<Token>) {
    val printer = VisitorPrinter()
    printer.iterateOverTokens(tokens)
    println()
}

fun calculate(tokens: List<Token>): Int {
    val calculator = VisitorCalculator()
    calculator.iterateOverTokens(tokens)
    return calculator.getResult()
}

fun main() {
    val expression = "1 + 2 + 3 * 4 * (19 - 7) / 2"
    println(expression)
    val tokenizer = Tokenizer()
    tokenizer.processInput(expression)

    val polishTokens = parsedTokens(tokenizer.getTokens())

    printTokens(polishTokens)

    println(calculate(polishTokens))
}
