ASMSupport
===

[中文README](./README_CN.md)


A java class byte code operate framework
---

The asmsupport is a java class byte code operate library, it make easier to write or modify a class at runtime. This framework developed base on [asm](http://asm.ow2.org/) framework, but different from asm that avoid use original jvm instruction and avoid maintain stack and local variables.

## Folder introduction


    asmsupport
      |-asmsupport-third           : The asmsupport third-part source code
      |-asmsupport-standard        : The asmsupport standard api 
      |-asmsupport-core            : The asmsupport core implements
      |-asmsupport-client          ： The asmsupport client api, it's the core wrapper
      |-asmsupport-issues          ： Some fixed bug test code
      |-asmsupport-sample          : The asmsupport sample
      |-src/site                   : Standard maven site folder.
      |-LICENSE.txt                : LGPL license
      |-README.md                  : README english version
      |-README_CN.md               : README chinese version
      
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
						getType(System.class).field("out").call("println", val("Hello ASMSupport."))
						return_();
					}
           });
    Class<?> FirstCaseClass = dummy.build();
    Method mainMethod = FirstCaseClass.getMethod("main", String[].class);
    mainMethod.invoke(FirstCaseClass, mainMethod);
    
    
## Sample : JSON & Proxy 

The JSON sample under the package "cn.wensiqun.asmsupport.sample.client.json下", run the "cn.wensiqun.asmsupport.sample.client.json.demo.Runner" main method, and you can get the generated class in folder "target/asmsupport-test-generated".


The Proxy sample under the package "cn.wensiqun.asmsupport.sample.client.proxy", run the cn.wensiqun.asmsupport.sample.client.proxy.demo.Runner main method, and you can get the generated class in folder "target/asmsupport-test-generated".
