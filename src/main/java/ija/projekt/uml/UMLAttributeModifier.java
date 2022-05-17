package ija.projekt.uml;

public enum UMLAttributeModifier {

        PUBLIC('+'),
        PRIVATE('-'),
        PROTECTED('#'),
        PACKAGE('~');
        private char symbol;

        private UMLAttributeModifier(char symbol) {
            this.symbol = symbol;
        }

        public char getSymbol() {
            return symbol;
        }
}
