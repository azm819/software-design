package visitor

import token.TokenBrace
import token.TokenNumber
import token.TokenOperation

class VisitorPrinter: Visitor {
    override fun acceptToken(token: TokenBrace) {
        println(token.textRepresentation())
    }

    override fun acceptToken(token: TokenNumber) {
        println("Number: " + token.textRepresentation())
    }

    override fun acceptToken(token: TokenOperation) {
        println("Operation: " + token.textRepresentation())
    }
}
