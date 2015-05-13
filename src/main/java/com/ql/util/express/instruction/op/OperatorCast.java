package com.ql.util.express.instruction.op;

import com.ql.util.express.ExpressUtil;
import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.OperateData;
import com.ql.util.express.instruction.OperateDataCacheManager;

public class OperatorCast extends OperatorBase {
	public OperatorCast(String aName) {
		this.name = aName;
	}

	public OperateData executeInner(InstructionSetContext parent, OperateData[] list) throws Exception {
		Class<?> tmpClass = (Class<?>) list[0].getObject(parent);
		Object castObj = ExpressUtil.castObject(list[1].getObject(parent), tmpClass,true);
		OperateData result = OperateDataCacheManager.fetchOperateData(castObj,tmpClass);
		return result;
	}
}