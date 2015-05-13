package com.ql.util.express.instruction.op;

import java.lang.reflect.Method;

import com.ql.util.express.ExpressUtil;
import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.OperateData;
import com.ql.util.express.instruction.OperateDataCacheManager;

/**
 * 用户自定义的服务函数操作
 * @author qhlhl2010@gmail.com
 *
 */
public class OperatorSelfDefineServiceFunction extends OperatorBase implements CanClone{
  Object serviceObject;
  String functionName;
  String[] parameterTypes;
  Class<?>[] parameterClasses ;
  Method method;
  boolean isReturnVoid;
  
	public OperatorSelfDefineServiceFunction(String aOperName,
			Object aServiceObject, String aFunctionName,
			Class<?>[] aParameterClassTypes,String[] aParameterDesc,String[] aParameterAnnotation, String aErrorInfo) throws Exception {
		if (errorInfo != null && errorInfo.trim().length() == 0) {
			errorInfo = null;
		} 
		this.name = aOperName;
		this.errorInfo = aErrorInfo;
		this.serviceObject = aServiceObject;
		this.functionName = aFunctionName;
		this.parameterClasses = aParameterClassTypes;
	    this.operDataDesc = aParameterDesc;
	    this.operDataAnnotation = aParameterAnnotation;
		this.parameterTypes = new String[this.parameterClasses.length];
		for (int i = 0; i < this.parameterClasses.length; i++) {
			this.parameterTypes[i] = this.parameterClasses[i].getName();
		}
		Class<?> operClass = serviceObject.getClass();
		method = operClass.getMethod(functionName, parameterClasses);
		this.isReturnVoid = method.getReturnType().equals(void.class);
	}
  
  public OperatorSelfDefineServiceFunction(String aOperName,Object aServiceObject, String aFunctionName,
                         String[] aParameterTypes,String[] aParameterDesc,String[] aParameterAnnotation,String aErrorInfo) throws Exception {
    
	if (errorInfo != null && errorInfo.trim().length() == 0) {
			errorInfo = null;
	}  
	this.name = aOperName;
    this.errorInfo = aErrorInfo;
    this.serviceObject = aServiceObject;
    this.functionName = aFunctionName;
    this.parameterTypes = aParameterTypes;
    this.operDataDesc = aParameterDesc;
    this.operDataAnnotation = aParameterAnnotation;
    this.parameterClasses = new Class[this.parameterTypes.length];
    for(int i=0;i<this.parameterClasses.length;i++){
      this.parameterClasses[i] =ExpressUtil.getJavaClass(this.parameterTypes[i]);
    }
    Class<?> operClass = serviceObject.getClass();
    method = operClass.getMethod(functionName,parameterClasses);
   
  }

	public OperatorBase cloneMe(String opName, String errorInfo)
			throws Exception {
		OperatorBase result = new OperatorSelfDefineServiceFunction(opName,
				this.serviceObject, this.functionName, this.parameterClasses,
				this.operDataDesc, this.operDataAnnotation, errorInfo);
		return result;
	}
  public OperateData executeInner(InstructionSetContext context, OperateData[] list) throws
      Exception {
      if(this.parameterClasses.length != list.length){
        throw new Exception("定义的参数长度与运行期传入的参数长度不一致");
      }
      Object[] parameres = new Object[list.length];
      for(int i=0;i<list.length;i++){
        parameres[i] = list[i].getObject(context);
      }

      Object obj = this.method.invoke(this.serviceObject,ExpressUtil.transferArray(parameres,parameterClasses));
      if(obj != null){
         return OperateDataCacheManager.fetchOperateData(obj,obj.getClass());
      }
      if(this.isReturnVoid == true){
    	  return OperateDataCacheManager.fetchOperateDataAttr("null", void.class);
      }else{
    	  return OperateDataCacheManager.fetchOperateDataAttr("null", null);  
      }
  }
}
