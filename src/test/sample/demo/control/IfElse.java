package demo.control;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.control.Else;
import cn.wensiqun.asmsupport.block.control.ElseIF;
import cn.wensiqun.asmsupport.block.control.IF;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import demo.CreateMethod;

public class IfElse extends CreateMethod  {

	public IfElse(ClassCreator creator) {
		super(creator);
	}

	@Override
	public void createMethod() {

		creator.createMethod("ifelse", new AClass[]{AClass.STRING_ACLASS, AClass.INT_ACLASS}, new String[]{"str", "i"}, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						final LocalVariable str = argus[0];
						final LocalVariable i = argus[1];
						
						ifthan(new IF(invoke(str, "equals", Value.value("A"))){
							@Override
							public void body() {
								ifthan(new IF(equal(i, Value.value(0))){

									@Override
									public void body() {
									    invoke(out, "println", Value.value("str is 'A', i is 0"));
									}
									
								}).elsethan(new Else(){

									@Override
									public void body() {
									    invoke(out, "println", Value.value("str is 'A', i is not 0"));
									}
									
								});
							}
						}).elseif(new ElseIF(invoke(str, "equals", Value.value("B"))){

							@Override
							public void body() {
								ifthan(new IF(equal(i, Value.value(0))){

									@Override
									public void body() {
									    invoke(out, "println", Value.value("str is 'B', i is 0"));
									}
									
								}).elsethan(new Else(){

									@Override
									public void body() {
									    invoke(out, "println", Value.value("str is 'B', i is not 0"));
									}
									
								});
							}
							
						}).elsethan(new Else(){

							@Override
							public void body() {
								ifthan(new IF(equal(i, Value.value(0))){

									@Override
									public void body() {
									    invoke(out, "println", Value.value("str is unknow, i is 0"));
									}
									
								}).elsethan(new Else(){

									@Override
									public void body() {
									    invoke(out, "println", Value.value("str is unknow, i is not 0"));
									}
									
								});
							}
							
						});
						
					    runReturn();
					}
		        }
		);
	}

}
