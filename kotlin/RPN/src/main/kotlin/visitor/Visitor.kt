package visitor

import token.Token
import token.TokenBrace
import token.TokenNumber
import token.TokenOperation

interface Visitor {
    fun acceptToken(token: TokenBrace)

    fun acceptToken(token: TokenNumber)

    fun acceptToken(token: TokenOperation)

    fun iterateOverTokens(tokens: List<Token>) {
        for (token in tokens) {
            when (token) {
                is TokenBrace -> acceptToken(token)
                is TokenNumber -> acceptToken(token)
                is TokenOperation -> acceptToken(token)
                else -> throw IllegalStateException("Unexpected token: " + token.textRepresentation())
            }
        }
    }
}
