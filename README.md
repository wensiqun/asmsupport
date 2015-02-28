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
            
        |-resource                 : Maven standard source resources  
      |-src/test
        |-java                     : Maven standard test
        |-resource                 : Maven standard test resources
      |-src/third/java             : The third-part source code(as source)
      |-src/issue/java             : The fixed bug test code(as test)
      |-src/sample/java            : The old api example code(as test)
      |-src/dummy/java             : The dummy api example code(as test)

Preceding is the  