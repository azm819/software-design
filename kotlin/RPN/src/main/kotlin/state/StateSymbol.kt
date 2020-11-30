package state

import token.TokenBraceLeft
import token.TokenBraceRight
import token.TokenOperation
import tokenizer.Tokenizer

class StateSymbol: State {
    override fun start(tokenizer: Tokenizer, c: Char) {
        when (c) {
            '(' -> {
                tokenizer.getTokens().add(TokenBraceLeft())
                return
            }
            ')' -> {
                tokenizer.getTokens().add(TokenBraceRight())
                return
            }
            '+' -> {
                tokenizer.getTokens().add(TokenOperation.plus())
                return
            }
            '-' -> {
                tokenizer.getTokens().add(TokenOperation.minus())
                return
            }
            '/' -> {
                tokenizer.getTokens().add(TokenOperation.div())
                return
            }
            '*' -> {
                tokenizer.getTokens().add(TokenOperation.mul())
                return
            }
        }
        if (Character.isWhitespace(c)) {
            return
        }
        if (Character.isDigit(c)) {
            tokenizer.setState(StateNumber())
            tokenizer.processCharacter(c)
            return
        }
        throw IllegalStateException("Unrecognized character: $c")
    }
}