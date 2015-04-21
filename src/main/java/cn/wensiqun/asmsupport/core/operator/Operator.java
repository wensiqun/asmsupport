/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public enum Operator {

    COMMON("", 0),
    POS_DEC("--", 0),
    POS_INC("++", 0),
    GET(".", 0),

    PRE_DEC("--", 1),
    PRE_INC("++", 1),
    NEG("-", 1),
    REVERSE("~", 1),
    NOT("!", 1),
    
    MUL("*", 2),
    DIV("/", 2),
    MOD("%", 2),

    ADD("+", 3),
    SUB("-", 3),
    
    LEFT_SHIFT("<<", 4),
    RIGHT_SHIFT(">>", 4),
    UNSIGNED_RIGHT_SHIFT(">>>", 4),

    LESS_THAN("<", 5),
    GREATER_THAN(">", 5),
    LESS_THAN_OR_EQUAL_TO("<=", 5),
    GREATER_THAN_OR_EQUAL_TO(">=", 5),
    INSTANCE_OF("instanceof", 5),

    EQUAL_TO("==", 6),
    NOT_EQUAL_TO("!=", 6),

    BIT_AND("&", 7),
    
    XOR("^", 8),
    
    BIT_OR("|", 9),
    
    CONDITION_AND("&&", 10),
    
    CONDITION_OR("||", 11),
    
    TERNARY("? :", 12),
    
    ASSIGN("=", 13);
    
    private String symbol;
    
    private int priority;
    
    private Operator(String symbol, int priority) {
        this.symbol = symbol;
        this.priority = priority;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getPriority() {
        return priority;
    }
    
    /**
     * 
     * 
     * @param operator
     * @return
     */
    public int compare(Operator operator) {
    	return operator.getPriority() - getPriority();
    }
    
}
