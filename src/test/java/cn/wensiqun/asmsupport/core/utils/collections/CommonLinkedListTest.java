package cn.wensiqun.asmsupport.core.utils.collections;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.core.utils.collections.CommonLinkedList;
import cn.wensiqun.asmsupport.core.utils.collections.LinkedList;
import cn.wensiqun.asmsupport.core.utils.collections.LinkedListNode;

public class CommonLinkedListTest
{

    @Test
    public void test()
    {
        MyNode n0 = new MyNode("0");
        MyNode n1 = new MyNode("1");
        MyNode n2 = new MyNode("2");
        MyNode n3 = new MyNode("3");
        MyNode n4 = new MyNode("4");
        MyNode n5 = new MyNode("5");
        MyNode n6 = new MyNode("6");
        MyNode n7 = new MyNode("7");
        MyNode n8 = new MyNode("8");
        MyNode n9 = new MyNode("9");
        MyNode n10 = new MyNode("10");
        
        LinkedList<MyNode> list = new CommonLinkedList<MyNode>();
        list.add(n3);
        list.add(n7);
        
        StringBuilder sb = new StringBuilder();
        for(MyNode n : list)
        {
            sb.append(n.name);
        }
        Assert.assertEquals("37", sb.toString());
        
        n1.setNext(n2);
        list.setHead(n1);
        sb = new StringBuilder();
        for(MyNode n : list)
        {
            sb.append(n.name);
        }
        Assert.assertEquals("1237", sb.toString());
        
        n4.setNext(n5);
        list.addAfter(n3, n4);
        sb = new StringBuilder();
        for(MyNode n : list)
        {
            sb.append(n.name);
        }
        Assert.assertEquals("123457", sb.toString());
        
        n8.setNext(n9);
        list.setLast(n8);
        sb = new StringBuilder();
        for(MyNode n : list)
        {
            sb.append(n.name);
        }
        Assert.assertEquals("12345789", sb.toString());
        
        
        
        
        list.setHead(n0);
        sb = new StringBuilder();
        for(MyNode n : list)
        {
            sb.append(n.name);
        }
        Assert.assertEquals("012345789", sb.toString());
        
        list.addAfter(n5, n6);
        sb = new StringBuilder();
        for(MyNode n : list)
        {
            sb.append(n.name);
        }
        Assert.assertEquals("0123456789", sb.toString());
        
        list.setLast(n10);
        sb = new StringBuilder();
        for(MyNode n : list)
        {
            sb.append(n.name);
        }
        Assert.assertEquals("012345678910", sb.toString());
        
        list.move(n2, n8);
        sb = new StringBuilder();
        for(MyNode n : list)
        {
            sb.append(n.name);
        }
        Assert.assertEquals("013456782910", sb.toString());
        

        list.remove(n8);
        sb = new StringBuilder();
        for(MyNode n : list)
        {
            sb.append(n.name);
        }
        Assert.assertEquals("01345672910", sb.toString());
        

        MyNode n11 = new MyNode("_11");
        list.replace(n7, n11);
        sb = new StringBuilder();
        for(MyNode n : list)
        {
            sb.append(n.name);
        }
        Assert.assertEquals("013456_112910", sb.toString());
    }
    
    class MyNode extends LinkedListNode{
        
        private String name;

        public MyNode(String name)
        {
            this.name = name;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException
        {
            return super.clone();
        }

        @Override
        public String toString()
        {
            return name;
        }
        
    }

}
