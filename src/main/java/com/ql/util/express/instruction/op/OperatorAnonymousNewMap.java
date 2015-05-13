package com.ql.util.express.instruction.op;

import java.util.HashMap;
import java.util.Map;

import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.OperateData;
import com.ql.util.express.instruction.OperateDataCacheManager;
import com.ql.util.express.instruction.opdata.OperateDataKeyValue;

public class OperatorAnonymousNewMap extends OperatorBase {
	public OperatorAnonymousNewMap(String aName) {
		this.name = aName;
	}
	public OperatorAnonymousNewMap(String aAliasName, String aName, String aErrorInfo) {
		this.name = aName;
		this.aliasName = aAliasName;
		this.errorInfo = aErrorInfo;
	}

	public OperateData executeInner(InstructionSetContext  context, OperateData[] list) throws Exception {
		Map<Object,Object> result = new HashMap<Object,Object>();
		for(int i=0;i<list.length;i++){
			result.put(((OperateDataKeyValue)list[i]).getKey().getObject(context), ((OperateDataKeyValue)list[i]).getValue().getObject(context));
		}
		return OperateDataCacheManager.fetchOperateData(result,HashMap.class);
	}
}
