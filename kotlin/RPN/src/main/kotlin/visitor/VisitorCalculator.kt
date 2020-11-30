package visitor

import token.TokenBrace
import token.TokenNumber
import token.TokenOperation
import java.util.Stack

class VisitorCalculator : Visitor {
    private val stack = Stack<Int>()

    override fun acceptToken(token: TokenBrace) {
        throw IllegalStateException("Unexpected brace in calculation stack")
    }

    override fun acceptToken(token: TokenNumber) {
        stack.add(token.getValue())
    }

    override fun acceptToken(token: TokenOperation) {
        check(stack.size >= 2) {
            "Illegal state of calculation stack"
        }
        val second = stack.pop()
        val first = stack.pop()
        val result = token.getOperation().apply(first, second)
        stack.add(result)
    }

    fun getResult(): Int {
        check(stack.size == 1) {
            "Calculation not finished"
        }
        return stack.pop()
    }
}
