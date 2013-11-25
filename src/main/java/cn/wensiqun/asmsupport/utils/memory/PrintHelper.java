/**
 * 
 */
package cn.wensiqun.asmsupport.utils.memory;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class PrintHelper {
    
    public String getGridString(String[][] grid, boolean header, String info){
        String lineSeq = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder(info);
        sb.append(lineSeq);
        
        if(grid.length == 0 || grid[0].length == 0){
            return sb.append("*").append(lineSeq).append("*").toString();
        }
        
        int[] maxLen = new int[grid[0].length];
        //get each column max length;
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[i].length; j++){
                maxLen[j] = getMax(maxLen[j], grid[i][j] == null ? 0 : grid[i][j].length());
            }
        }
        
        //length of grid top line;
        int gridLength = maxLen.length + 1;
        for(int i=0; i<maxLen.length; i++){
            gridLength += maxLen[i];
        }
        //input of grid top line;
        repateAppend(sb, "*", gridLength).append(lineSeq);
        
        //input hearder
        
        //input each column
        for(int rowIndex = 0; rowIndex<grid.length; rowIndex++){
            for(int j=0; j<grid[rowIndex].length; j++){
                sb.append("|");
                fillAppend(sb, grid[rowIndex][j], ' ', maxLen[j]);
            }
            sb.append("|").append(lineSeq);
            if(rowIndex == 0 && header){
                repateAppend(sb, "*", gridLength).append(lineSeq);
            }
        }

        //input of grid bottom line;
        repateAppend(sb, "*", gridLength).append(lineSeq);
        
        return sb.toString();
    }
    
    private StringBuilder fillAppend(StringBuilder s, Object o, char filler, int maxlength){
        String oStr = o == null ? "" : o.toString();
        s.append(oStr);
        int poor = maxlength - oStr.length();
        return repateAppend(s, filler, poor);
    }
    
    private StringBuilder repateAppend(StringBuilder s, Object o, int times){
        while(times>0){
            s.append(o);
            times--;
        }
        return s;
    }
    
    private int getMax(int orgL, int newL){
        if(orgL < newL){
            return newL;
        }
        return orgL;
    }
}
