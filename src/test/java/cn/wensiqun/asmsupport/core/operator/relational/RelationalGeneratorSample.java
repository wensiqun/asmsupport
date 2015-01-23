package cn.wensiqun.asmsupport.core.operator.relational;

import example.AbstractExample;

public class RelationalGeneratorSample extends AbstractExample 
{

    private void testChar(char c)
    {
        String str;
        
        boolean crst = c == 0;
                crst = c > 0;
                crst = c < 0;
                crst = c >= 0;
                crst = c <= 0;
                crst = c == 1;
                crst = c > 1;
                crst = c < 1;
                crst = c >= 1;
                crst = c <= 1;
        
        if(c==0)
            str = "c == 0";
        

        if(c > 0)
            str = "c > 0";
        

        if(c < 0)
            str = "c < 0";
        

        if(c >= 0)
            str = "c >= 0";
        

        if(c <= 0)
            str = "c <= 0";
        

        if(c == 1)
            str = "c > 1";
        

        if(c > 1)
            str = "c > 1";
        

        if(c < 1)
            str = "c < 1";
        

        if(c >= 1)
            str = "c >= 1";
        

        if(c <= 1)
            str = "c <= 1";
    }
    
    
    private void testInt(char i)
    {
        String str;
        
        boolean irst = i == 0;
                irst = i > 0;
                irst = i < 0;
                irst = i >= 0;
                irst = i <= 0;
                irst = i == 1;
                irst = i > 1;
                irst = i < 1;
                irst = i >= 1;
                irst = i <= 1;
        
        if(i==0)
            str = "i == 0";
        

        if(i > 0)
            str = "i > 0";
        

        if(i < 0)
            str = "i < 0";
        

        if(i >= 0)
            str = "i >= 0";
        

        if(i <= 0)
            str = "i <= 0";
        

        if(i == 1)
            str = "i > 1";
        

        if(i > 1)
            str = "i > 1";
        

        if(i < 1)
            str = "i < 1";
        

        if(i >= 1)
            str = "i >= 1";
        

        if(i <= 1)
            str = "i <= 1";
    }
    
    
    private void testInt(double d)
    {
        String str;
        
        boolean drst = d == 0;
                drst = d > 0;
                drst = d < 0;
                drst = d >= 0;
                drst = d <= 0;
                drst = d == 1;
                drst = d > 1;
                drst = d < 1;
                drst = d >= 1;
                drst = d <= 1;
        
        if(d==0)
            str = "i == 0";
        

        if(d > 0)
            str = "d > 0";
        

        if(d < 0)
            str = "d < 0";
        

        if(d >= 0)
            str = "d >= 0";
        

        if(d <= 0)
            str = "d <= 0";
        

        if(d == 1)
            str = "d > 1";
        

        if(d > 1)
            str = "d > 1";
        

        if(d < 1)
            str = "d < 1";
        

        if(d >= 1)
            str = "d >= 1";
        

        if(d <= 1)
            str = "d <= 1";
    }
    
    public static void main(String[] args)
    {}

}
