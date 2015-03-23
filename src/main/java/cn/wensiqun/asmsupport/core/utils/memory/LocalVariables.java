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
package cn.wensiqun.asmsupport.core.utils.memory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class LocalVariables implements Printable, Cloneable{

    public static class LocalHistory implements Cloneable{
        private List<ScopeLogicVariable> variables;
        private int activeIndex;
        
        private LocalHistory() {
            super();
            this.variables = new ArrayList<ScopeLogicVariable>();
        }
        
        private ScopeLogicVariable getActiveVariable(){
            return variables.get(activeIndex);
        }
        
        private ScopeLogicVariable getLastVariable(){
            return variables.get(variables.size() - 1);
        }
        
        private void addVar(ScopeLogicVariable l){
            variables.add(l);
        }
        
    }
    
    private static final Log LOG = LogFactory.getLog(LocalVariables.class);

    private List<LocalHistory> histories;
    private PrintHelper printHelper;
    private int maxPrintSize;
    private Localable cursor;
    
	public LocalVariables() {
        super();
        histories = new ArrayList<LocalHistory>();
        printHelper = new PrintHelper();
        maxPrintSize = -1;
    }

    public int getSize() {
        return histories.size();
    }

    public Localable getLastVariable(int index) {
        return histories.get(index).getLastVariable();
    }
    
    public void store(int index, ScopeLogicVariable var) {
        histories.get(index).addVar(var);
    }

    public boolean store(ScopeLogicVariable var) {
        LocalHistory history = new LocalHistory();
        history.addVar(var);
        return histories.add(history);
    }

    @Override
    public void printState(){
        LOG.debug(printHelper.getGridString(generateGridArray(), true, "local variables states"));
    }

    public void setCursor(Localable cursor) {
        this.cursor = cursor;
        hierarchy(cursor);
    }

    private void hierarchy(Localable cursor){
        for(LocalHistory history : histories){
            for(int i=0, len = history.variables.size(); i<len; i++){
                if(history.variables.get(i).equals(cursor)){
                    history.activeIndex = i;
                }
            }
        }
    }
    
    public List<Localable> getFrameLocals(Scope scope) {
        List<Localable> locals = new ArrayList<Localable>();
        for(LocalHistory his : histories) {
            ScopeLogicVariable var = his.getActiveVariable();
            if(var.getParent().generation > scope.generation || 
               var.equals(cursor)) {
               break; 
            }
            locals.add(var);
        }
        return locals;
    }
    
    public String[][] generateGridArray() {
        int gridRowSize = getSize() + 1;
        if(gridRowSize < 6){
            gridRowSize = 6;
        }
        String[][] grid = new String[gridRowSize][4];
        grid[0][1] = "Type";
        grid[0][2] = "Name";
        grid[0][3] = "Fragment";

        int gridBodyRowSize = getSize();
        int i = 0;
        for(; i<gridBodyRowSize; i++){
            Localable loc = histories.get(i).getActiveVariable();
            grid[i + 1][0] = localGraph(i);
            grid[i + 1][1] = loc.getDeclareType().getDescriptor();
            grid[i + 1][2] = loc.getName();
            grid[i + 1][3] = loc.getDeclareType().getSize() == loc.getPositions().length ? "false" : "true";
            
            if(loc.equals(cursor)){
                if(maxPrintSize < i + 1){
                    maxPrintSize = i + 1;
                    break;
                }else{
                    gridBodyRowSize = maxPrintSize;
                }
            }
        }
        
        for(; i < 5;i++){
            grid[i + 1][0] = localGraph(i);
        }
        
        return grid;
        
    }
    
    private String localGraph(int i){
        switch(i){
            case 0: 
                return " _";
            case 1: 
                return "| |      ";
            case 2: 
                return "| |      ";
            case 3:
                return "| |___   ";
            case 4:
                return "|_____|  ";       
        }
        return null;
    }
    
    
}
