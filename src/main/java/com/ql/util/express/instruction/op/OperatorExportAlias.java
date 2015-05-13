package com.ql.util.express.instruction.op;

import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.OperateData;
import com.ql.util.express.instruction.opdata.OperateDataAlias;
import com.ql.util.express.instruction.opdata.OperateDataAttr;

public class OperatorExportAlias extends OperatorBase {
	public OperatorExportAlias(String aName) {
		this.name = aName;
	}
	public OperatorExportAlias(String aAliasName, String aName, String aErrorInfo) {
		this.name = aName;
		this.aliasName = aAliasName;
		this.errorInfo = aErrorInfo;
	}

	public OperateData executeInner(InstructionSetContext context, OperateData[] list) throws Exception {
		String varName = (String)list[0].getObjectInner(context);	
		OperateDataAttr realAttr = (OperateDataAttr)list[1];
		OperateDataAttr result = new OperateDataAlias(varName,realAttr);
		context.exportSymbol(varName, result);
		return result;
	}
}