package com.ql.util.express.instruction.opdata;

import com.ql.util.express.ExpressUtil;
import com.ql.util.express.InstructionSetContext;

public class OperateDataField extends OperateDataAttr {
	Object fieldObject;
	String orgiFieldName;
	
	public OperateDataField(Object aFieldObject,String aFieldName) {
		super(null,null);
		if(aFieldObject == null){
		   this.name = "没有初始化的Field";	
		}else{
		   this.name = aFieldObject.getClass().getName() + "." + aFieldName;
		}
		this.fieldObject = aFieldObject;
		this.orgiFieldName =aFieldName;
	}
	
	public void initialDataField(Object aFieldObject,String aFieldName){
		super.initialDataAttr(null, null);
		this.name = aFieldObject.getClass().getName() + "." + aFieldName;
		this.fieldObject = aFieldObject;
		this.orgiFieldName = aFieldName;
	}
	public void clearDataField(){
		super.clearDataAttr();
		this.name = null;
		this.fieldObject = null;
		this.orgiFieldName = null;
	}
    public String getName(){
    	return name;
    }
	public String toString() {
		try {			
			return name;
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

    public Object transferFieldName(InstructionSetContext context,String oldName){
		if (context.isSupportDynamicFieldName() == false) {
			return oldName;
		} else {
			try {
				OperateDataAttr o = (OperateDataAttr) context
						.findAliasOrDefSymbol(oldName);
				if (o != null) {
					return o.getObject(context);
				} else {
					return oldName;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
    }
	public Object getObjectInner(InstructionSetContext context) throws Exception {
		//如果能找到aFieldName的定义,则再次运算
		if(this.fieldObject instanceof OperateDataVirClass){
			return ((OperateDataVirClass)this.fieldObject).getValue(transferFieldName(context,this.orgiFieldName));
		}else{
		  return ExpressUtil.getProperty(this.fieldObject,transferFieldName(context,this.orgiFieldName));
		}
	}
    
	public Class<?> getType(InstructionSetContext context) throws Exception {
		if(this.fieldObject instanceof OperateDataVirClass){
			return ((OperateDataVirClass)this.fieldObject).getValueType(transferFieldName(context,this.orgiFieldName));
		}else{
		    return ExpressUtil.getPropertyClass(this.fieldObject,transferFieldName(context,this.orgiFieldName));
		}
	}

	public void setObject(InstructionSetContext context, Object value) throws Exception{
		if(this.fieldObject instanceof OperateDataVirClass){
			((OperateDataVirClass)this.fieldObject).setValue(transferFieldName(context,this.orgiFieldName).toString(),value);
		}else{
			ExpressUtil.setProperty(fieldObject, transferFieldName(context,this.orgiFieldName), value);
		}
		
	}
}