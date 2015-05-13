package com.ql.util.express.instruction;

import com.ql.util.express.CallResult;
import com.ql.util.express.ExpressLoader;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import com.ql.util.express.InstructionSet;
import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.OperateData;
import com.ql.util.express.RunEnvironment;
import com.ql.util.express.instruction.opdata.OperateDataArrayItem;
import com.ql.util.express.instruction.opdata.OperateDataAttr;
import com.ql.util.express.instruction.opdata.OperateDataField;
import com.ql.util.express.instruction.opdata.OperateDataKeyValue;
import com.ql.util.express.instruction.opdata.OperateDataLocalVar;


public class OperateDataCacheManager {
	
	private static ThreadLocal<IOperateDataCache> m_OperateDataObjectCache = new ThreadLocal<IOperateDataCache>(){
		protected IOperateDataCache initialValue() {
	        return new OperateDataCacheImpl(30);
			//return new OperateDataCacheImpl4Orig();
	    }
	};
	public static IOperateDataCache getOperateDataCache(){
		return m_OperateDataObjectCache.get();
	}
	public static OperateData fetchOperateData(Object obj, Class<?> aType) {
		return getOperateDataCache().fetchOperateData(obj, aType);
	}
	public static OperateDataAttr fetchOperateDataAttr(String name, Class<?> aType) {
		return getOperateDataCache().fetchOperateDataAttr(name, aType);
	}
	public static OperateDataLocalVar fetchOperateDataLocalVar(String name, Class<?> aType) {
		return getOperateDataCache().fetchOperateDataLocalVar(name, aType);
	}
	public static OperateDataField fetchOperateDataField(Object aFieldObject,String aFieldName){
		return getOperateDataCache().fetchOperateDataField(aFieldObject, aFieldName);
	}
	public static OperateDataArrayItem fetchOperateDataArrayItem(OperateData aArrayObject,int aIndex){
		return getOperateDataCache().fetchOperateDataArrayItem(aArrayObject, aIndex);
	}
	public static OperateDataKeyValue fetchOperateDataKeyValue(OperateData aKey, OperateData aValue){
		return getOperateDataCache().fetchOperateDataKeyValue(aKey, aValue);
	}
	
	public static RunEnvironment fetRunEnvironment(InstructionSet aInstructionSet,InstructionSetContext  aContext,boolean aIsTrace){
		return getOperateDataCache().fetRunEnvironment(aInstructionSet, aContext, aIsTrace);
	}
	public static CallResult fetchCallResult(Object aReturnValue,boolean aIsExit){
		return getOperateDataCache().fetchCallResult(aReturnValue, aIsExit);
	}
	public static InstructionSetContext fetchInstructionSetContext(boolean aIsExpandToParent,ExpressRunner aRunner,IExpressContext<String,Object> aParent,ExpressLoader aExpressLoader,boolean aIsSupportDynamicFieldName){
		return getOperateDataCache().fetchInstructionSetContext(aIsExpandToParent, aRunner, aParent, aExpressLoader, aIsSupportDynamicFieldName);
	}
	
	public static long getFetchCount(){
		return getOperateDataCache().getFetchCount();
	}

	public static void resetCache(){
		getOperateDataCache().resetCache();
	}
	

}
