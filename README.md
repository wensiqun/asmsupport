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
      |-src/third/java             : The third-part source code(as source folder)
      |-src/issue/java             : The fixed bug test code(as test folder)
      |-src/sample/java            : The some simple exampe.(as test folder)
          |-oldApi                 : The old api example.
          |-dummy                  : The dummy api example
          |-json                   : The simple json serialization tool use asmsupport
          |-proxy                  : The simple dynamic proxy framework use asmsupport
      |-src/site                   : The project document site folder.

Preceding is all of the sources folder structure and descriptions, if you want import the project to eclipse, you must be manual use ["src/third/java", "src/issue/java", "src/sample/java"] as source folder. 

## Maven Dependency
    
    <dependency>
        <groupId>cn.wensiqun</groupId>
        <artifactId>asmsupport</artifactId>
        <version>x.x.x</version>
    </dependency>
    
The last stable version is 0.4
    
## License

Asmsupport is licensed under the GNU Lesser General Public License (LGPL)

## First Case

The following code will generate.


    public class FirstCase {
        
        public static void main(String[] args) {
            System.out.println("Hello ASMSupport.");
        }
        
    }

The following is code to generate preceding case.

    DummyClass dummy = new DummyClass("FirstCase").public_()
           .newMethod("main").public_().static_().argTypes(String[].class)
           .body(new MethodBody(){
					public void body(LocalVariable... args) {
						call(defType(System.class).field("out"), "println", val("Hello ASMSupport."))
						return_();
					}
           });
    Class<?> FirstCaseClass = dummy.build();
    //The following code will use reflection to call main method.
    Method mainMethod = FirstCaseClass.getMethod("main", String[].class);
    mainMethod.invoke(FirstCaseClass, mainMethod);
    
## Sample : JSON & Proxy 

The JSON sample under the folder "src/sample/java/json", run the json.demo.Runner main method, and you can get the generated class in folder "target/sample/json".


The Proxy sample under the folder "src/sample/java/proxy", run the json.demo.Runner main method, and you can get the generated class in folder "target/sample/proxy".
