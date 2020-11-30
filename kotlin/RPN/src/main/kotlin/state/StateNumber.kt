package state

import token.TokenNumber
import tokenizer.Tokenizer

class StateNumber: State {
    private var currentNumber = 0

    override fun start(tokenizer: Tokenizer, c: Char) {
        if (Character.isDigit(c)) {
            currentNumber *= 10
            currentNumber += c - '0'
        } else {
            tokenizer.getTokens().add(TokenNumber(currentNumber))
            tokenizer.setState(StateSymbol())
            tokenizer.processCharacter(c)
        }
    }

    override fun finish(tokenizer: Tokenizer) {
        tokenizer.getTokens().add(TokenNumber(currentNumber))
    }
}
