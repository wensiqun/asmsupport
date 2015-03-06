ASMSupport
===

A java class byte code operate framework
---

The asmsupport is a java class byte code operate library, it make easier to write or modify a class at runtime. This framework developed base on [asm](http://asm.ow2.org/) framework, but different from asm that avoid use original jvm instruction and avoid maintain stack and local variables.

## Folder introduction

    asmsupport
      |-src/main/java          
        |-java                     : Maven standard source
          |-.../asmsupport 
            |-standard             : The asmsupport api 
            |-client               : The dummy api
            |-core                 : The asmsupport core implements
        |-resource                 : Maven standard source resources  
      |-src/test
        |-java                     : Maven standard test
        |-resource                 : Maven standard test resources
      |-src/third/java             : The third-part source code(as source)
      |-src/issue/java             : The fixed bug test code(as test)
      |-src/sample/java            : The old api example code(as test)
      |-src/dummy/java             : The dummy api example code(as test)

Preceding is all of the sources folder structure and descriptions, if you want import the project to eclipse, you must be use ["src/third/java", "src/issue/java", "src/sample/java", "src/dummy/java "] as source folder. 

## Maven Dependency
    
    <dependency>
        <groupId>cn.wensiqun</groupId>
        <artifactId>asmsupport</artifactId>
        <version>x.x.x</version>
    </dependency>
    
