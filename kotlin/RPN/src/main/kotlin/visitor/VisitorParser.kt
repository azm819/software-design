package visitor

import token.*
import java.util.ArrayList

import java.util.Stack

class VisitorParser : Visitor {
    private val operations: Stack<Token> = Stack<Token>()
    private val numbers: MutableList<Token> = ArrayList<Token>()

    override fun acceptToken(token: TokenBrace) {
        when (token) {
            is TokenBraceLeft -> operations.add(token)
            is TokenBraceRight -> while (!operations.isEmpty()) {
                val lastToken: Token = operations.peek()
                if (lastToken is TokenBrace && lastToken is TokenBraceLeft) {
                    operations.pop()
                    break
                }
                if (lastToken is TokenOperation) {
                    numbers.add(lastToken)
                    operations.pop()
                    continue
                }
                throw IllegalStateException("Unexpected token: " + token.textRepresentation())
            }
        }
    }

    override fun acceptToken(token: TokenNumber) {
        numbers.add(token)
    }

    override fun acceptToken(token: TokenOperation) {
        while (!operations.isEmpty()) {
            val lastToken: Token = operations.peek()
            if (lastToken is TokenOperation && token.getPriority() <= lastToken.getPriority()) {
                numbers.add(lastToken)
                operations.pop()
            } else {
                break
            }
        }
        operations.add(token)
    }

    fun getParsedTokens(): List<Token> {
        while (!operations.isEmpty()) {
            val lastToken: Token = operations.peek()
            if (lastToken is TokenOperation) {
                numbers.add(lastToken)
                operations.pop()
            } else {
                throw IllegalStateException("No matching closing bracket")
            }
        }
        return numbers
    }
}
