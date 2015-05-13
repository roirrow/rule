package com.ql.util.express.instruction.op;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.ql.util.express.ExpressUtil;
import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.OperateData;
import com.ql.util.express.instruction.OperateDataCacheManager;

/**
 * 用户自定义的函数操作
 * @author qhlhl2010@gmail.com
 *
 */
public class OperatorSelfDefineClassFunction extends OperatorBase implements CanClone{
  String functionName;
  String[] parameterTypes;
  Class<?>[] parameterClasses ;
  Class<?> operClass;
  Method method;
  boolean isReturnVoid;

  public OperatorSelfDefineClassFunction(String aOperName,String aClassName, String aFunctionName,
          Class<?>[] aParameterClassTypes,String[] aParameterDesc,String[] aParameterAnnotation,String aErrorInfo) throws Exception {
		if (errorInfo != null && errorInfo.trim().length() == 0) {
			errorInfo = null;
		} 
		this.name = aOperName;
	    this.errorInfo = aErrorInfo;
	    this.functionName = aFunctionName;
	    this.parameterClasses = aParameterClassTypes;
	    this.parameterTypes = new String[aParameterClassTypes.length];
	    this.operDataDesc = aParameterDesc;
	    this.operDataAnnotation = aParameterAnnotation;
	    for(int i=0;i<this.parameterClasses.length;i++){
	      this.parameterTypes[i] = this.parameterClasses[i].getName();
	    }
	    operClass = ExpressUtil.getJavaClass(aClassName);
	    method = operClass.getMethod(functionName,parameterClasses);
	    this.isReturnVoid = method.getReturnType().equals(void.class);
  }

  public OperatorSelfDefineClassFunction(String aOperName,String aClassName, String aFunctionName,
                         String[] aParameterTypes,String[] aParameterDesc,String[] aParameterAnnotation,String aErrorInfo) throws Exception {
	if (errorInfo != null && errorInfo.trim().length() == 0) {
			errorInfo = null;
	} 
	this.name = aOperName;
    this.errorInfo = aErrorInfo;
    this.functionName = aFunctionName;
    this.parameterTypes = aParameterTypes;
    this.operDataDesc = aParameterDesc;
    this.operDataAnnotation = aParameterAnnotation;
    this.parameterClasses = new Class[this.parameterTypes.length];
    for(int i=0;i<this.parameterClasses.length;i++){
      this.parameterClasses[i] = ExpressUtil.getJavaClass(this.parameterTypes[i]);
    }
    operClass = ExpressUtil.getJavaClass(aClassName);
    method = operClass.getMethod(functionName,parameterClasses);
  }

	public OperatorBase cloneMe(String opName, String errorInfo)
			throws Exception {
		OperatorBase result = new OperatorSelfDefineClassFunction(opName,
				this.operClass.getName(), this.functionName,
				this.parameterClasses, this.operDataDesc,
				this.operDataAnnotation, errorInfo);
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
      Object obj = null;
      if( Modifier.isStatic(this.method.getModifiers())){
         obj = this.method.invoke(null,ExpressUtil.transferArray(parameres,parameterClasses));
      }else{
    	 obj = this.method.invoke(this.operClass.newInstance(),ExpressUtil.transferArray(parameres,parameterClasses));
      }

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
