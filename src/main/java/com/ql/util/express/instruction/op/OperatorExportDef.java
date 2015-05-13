package com.ql.util.express.instruction.op;

import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.OperateData;
import com.ql.util.express.instruction.opdata.OperateDataAttr;

public class OperatorExportDef extends OperatorBase {
	public OperatorExportDef(String aName) {
		this.name = aName;
	}
	public OperatorExportDef(String aAliasName, String aName, String aErrorInfo) {
		this.name = aName;
		this.aliasName = aAliasName;
		this.errorInfo = aErrorInfo;
	}

	public OperateData executeInner(InstructionSetContext context, OperateData[] list) throws Exception {
		Class<?> tmpClass = (Class<?>) list[0].getObject(context);
		String varName = (String)list[1].getObject(context);		
		//OperateDataLocalVar result = new OperateDataLocalVar(varName,tmpClass);
		//context.exportSymbol(varName, result);
		OperateDataAttr result = (OperateDataAttr)context.getSymbol(varName);
		result.setDefineType(tmpClass);
		return result;
	}
}