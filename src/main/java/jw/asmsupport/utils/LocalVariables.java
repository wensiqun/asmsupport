package jw.asmsupport.utils;

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

    private static class HLocalVariables implements Cloneable{
        private List<Localable> hlocals;
        private int maxVarIndex;

		@SuppressWarnings("unchecked")
		@Override
		protected Object clone() {
			try {
				HLocalVariables hlv = (HLocalVariables) super.clone();
                hlv.hlocals = (List<Localable>) ((ArrayList<Localable>) hlocals).clone();
                return hlv;
			} catch (CloneNotSupportedException e) {
	            throw new InternalError();
			}
		}
        
        private HLocalVariables() {
            super();
            this.hlocals = new ArrayList<Localable>();
        }
        
        private Localable getLocalVar(){
            Localable l = hlocals.get(maxVarIndex);
            return l;
        }
        
        private Localable getLastLocalVar(){
            return hlocals.get(hlocals.size() - 1);
        }
        
        private void addVar(Localable l){
            hlocals.add(l);
        }
        
        
    }
    
    private static Log log = LogFactory.getLog(Stack.class);

    private List<HLocalVariables> locals;
    private PrintHelper ph;
    private int maxPrintSize;
    private Localable cursor;
    
    @SuppressWarnings("unchecked")
	@Override
	public Object clone(){
		try {
			LocalVariables clo = (LocalVariables) super.clone();
			clo.locals = (List<HLocalVariables>) ((ArrayList<HLocalVariables>) this.locals).clone();
			return clo;
		} catch (CloneNotSupportedException e) {
            throw new InternalError();
		}
	}

	public LocalVariables() {
        super();
        locals = new ArrayList<HLocalVariables>();
        ph = new PrintHelper();
        maxPrintSize = -1;
    }

    public int getSize() {
        return locals.size();
    }

    public Localable getLast(int index) {
        return locals.get(index).getLastLocalVar();
    }
    
    public void store(int index, Localable var) {
        locals.get(index).addVar(var);
    }

    public boolean store(Localable var) {
        HLocalVariables hlv = new HLocalVariables();
        hlv.addVar(var);
        return locals.add(hlv);
    }

    @Override
    public void printState(){
        log.debug(ph.getGridString(generateGridArray(), true, "local variables states"));
    }

    public void setCursor(Localable cursor) {
        this.cursor = cursor;
        hierarchy(cursor);
    }

    private void hierarchy(Localable cursor){
        for(HLocalVariables hlv : locals){
            for(int i=0, len = hlv.hlocals.size(); i<len; i++){
                if(hlv.hlocals.get(i).equals(cursor)){
                    hlv.maxVarIndex = i;
                }
            }
        }
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
            Localable loc = locals.get(i).getLocalVar();
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
