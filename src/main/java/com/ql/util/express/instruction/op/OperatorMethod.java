package com.ql.util.express.instruction.op;

import java.lang.reflect.Method;

import com.ql.util.express.ExpressUtil;
import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.OperateData;
import com.ql.util.express.instruction.OperateDataCacheManager;
import com.ql.util.express.instruction.opdata.OperateClass;
import com.ql.util.express.instruction.opdata.OperateDataVirClass;

public class OperatorMethod extends OperatorBase {
	public OperatorMethod() {
		this.name ="MethodCall";
	}

	static Class<?> ArrayClass = (new Object[]{}).getClass();
	
	public OperateData executeInner(InstructionSetContext parent, OperateData[] list) throws Exception {
		
		Object obj = list[0].getObject(parent);
		if(obj instanceof OperateDataVirClass ){
			OperateDataVirClass vClass = (OperateDataVirClass)obj;
			String methodName = list[1].getObject(parent).toString();
			OperateData[] parameters = new OperateData[list.length - 2];
			for(int i=0;i< list.length -2;i++){
				parameters[i] =list[i+2];
			}
			return vClass.callSelfFunction(methodName, parameters);
		}
		
		String methodName = list[1].getObject(parent).toString();
		if (obj == null) {
			// 对象为空，不能执行方法
			String msg = "对象为空，不能执行方法:";
			throw new Exception(msg + methodName);
		} else {
			Class<?>[] types = new Class[list.length - 2];
			Class<?>[] orgiTypes = new Class[list.length - 2];
			
			Object[] objs = new Object[list.length - 2];
			Object tmpObj;
			for (int i = 0; i < types.length; i++) {
				tmpObj = list[i + 2].getObject(parent);
				types[i] = list[i + 2].getType(parent);
				orgiTypes[i] = list[i + 2].getType(parent);
				objs[i] = tmpObj;
			}
			Method m = null;
			if (list[0] instanceof OperateClass) {// 调用静态方法
				m = ExpressUtil.findMethodWithCache((Class<?>) obj, methodName,
						types, true, true);
			} else {
				m = ExpressUtil.findMethodWithCache(obj.getClass(), methodName,
						types, true, false);
			}
			if(m == null){
				types = new Class[]{ArrayClass};
				if (list[0] instanceof OperateClass) {// 调用静态方法
					m = ExpressUtil.findMethodWithCache((Class<?>) obj, methodName,
							types, true, true);
				} else {
					m = ExpressUtil.findMethodWithCache(obj.getClass(), methodName,
							types, true, false);
				}
				objs = new Object[]{objs};				
			}
			if (m == null) {
				StringBuilder  s = new StringBuilder();
				s.append("没有找到" + obj.getClass().getName() + "的方法："
						+ methodName + "(");
				for (int i = 0; i < orgiTypes.length; i++) {
					if (i > 0)
						s.append(",");
					if(orgiTypes[i] == null){
						s.append("null");
					}else{
					    s.append(orgiTypes[i].getName());
					}
				}
				s.append(")");
				throw new Exception(s.toString());
			}
			
			if (list[0] instanceof OperateClass) {// 调用静态方法
				boolean oldA = m.isAccessible();
				m.setAccessible(true);
				tmpObj = m.invoke(null,ExpressUtil.transferArray(objs,m.getParameterTypes()));
				m.setAccessible(oldA);
			} else {
				boolean oldA = m.isAccessible();
				m.setAccessible(true);
				tmpObj = m.invoke(obj, ExpressUtil.transferArray(objs,m.getParameterTypes()));
				m.setAccessible(oldA);
			}
			return OperateDataCacheManager.fetchOperateData(tmpObj, m.getReturnType());
		}
	}
    public String toString(){
    	return this.name;
    }
}
