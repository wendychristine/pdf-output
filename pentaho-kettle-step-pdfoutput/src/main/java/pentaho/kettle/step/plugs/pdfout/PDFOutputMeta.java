/*******************************************************************************
 *
 * PDF Output Writer - Pentaho Kettle Step plugin
 *
 * Author: Rishu Shrivastava
 * 
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package pentaho.kettle.step.plugs.pdfout;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.w3c.dom.Node;



/**
 * This Meta class is a part of the PDFOutput Step.
 * @author Rishu Shrivastava
 *
 */

public class PDFOutputMeta extends BaseStepMeta implements StepMetaInterface {
	
	private static Class<?> PKG = PDFOutputMeta.class;
	
	private String OutputFileName;
	private String[] KeyField;
	private String[] ValueField;
	//private String[] DefaultField;
	private String[] Type;
	private String[] Format;
	//private String[] Length;
	//private String[] Precision;
	//private String[] Currency;
	//private String[] Decimal;
	//private String[] Group;
	
	
	public PDFOutputMeta(){
		super();
	}
	
	public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta,
			TransMeta transMeta, String name) {
		return new PDFOutputDialog(shell, meta, transMeta, name);
	}

	public StepInterface getStep(StepMeta stepMeta,
			StepDataInterface stepDataInterface, int cnr, TransMeta transMeta,
			Trans disp) {
		return new PDFOutputStep(stepMeta, stepDataInterface, cnr, transMeta, disp);
	}

	public StepDataInterface getStepData() {
		// TODO Auto-generated method stub
		return new PDFOutputData();
	}

	public void setDefault() {
		// default output field name
		OutputFileName = "file";
		
		allocate(0);
	}
	
	
	public void allocate(int nrkeys){
		
		KeyField= new String[nrkeys];
		ValueField= new String[nrkeys];
		//DefaultField= new String[nrkeys];
		Type= new String[nrkeys];
		Format= new String[nrkeys];
		//Length= new String[nrkeys];
		//Precision= new String[nrkeys];
		//Currency= new String[nrkeys];
		//Decimal= new String[nrkeys];
		//Group= new String[nrkeys];
		
	}


	@Override
	public Object clone() {
		
		PDFOutputMeta retval= (PDFOutputMeta) super.clone();
		
		int nrKeys   = KeyField.length;

		retval.allocate(nrKeys);
		
		for (int i=0;i<nrKeys;i++)
		{
			retval.KeyField[i] = KeyField[i];
			retval.ValueField[i] = ValueField[i];
			//retval.DefaultField[i] = DefaultField[i];
			retval.Type[i] = Type[i];
			retval.Format[i] = Format[i];
			//retval.Length[i] = Length[i];
			//retval.Precision[i] = Precision[i];
			//retval.Currency[i] = Currency[i];
			//retval.Decimal[i] = Decimal[i];
			//retval.Group[i] = Group[i];
		}

		return retval;
	}


	@Override
	public void getFields(RowMetaInterface r, String origin,
			RowMetaInterface[] info, StepMeta nextStep, VariableSpace space) {
		
		// nothing is added or modified to the row
		
	}
	
	
	
	
		
	@Override
	public String getXML() throws KettleValueException {

		// only one field to serialize
		StringBuilder xml=new StringBuilder();
		
		xml.append("      ").append(XMLHandler.addTagValue("OutputFileName", OutputFileName));
				
		return xml.toString();
	}

	@Override
	public void loadXML(Node stepnode, List<DatabaseMeta> databases,
			Map<String, Counter> counters) throws KettleXMLException {

		try {
			setOutputFileName(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "OutputFileName")));
			
		} catch (Exception e) {
			throw new KettleXMLException("Plugin unable to read step info from XML node", e);
		}

	}

	@Override
	public void saveRep(Repository rep, ObjectId id_transformation,
			ObjectId id_step) throws KettleException {
		try {
			rep.saveStepAttribute(id_transformation, id_step, "OutputFileName", OutputFileName); //$NON-NLS-1$			
			
		} catch (Exception e) {
			throw new KettleException("Unable to save step into repository: "+ id_step, e);
		}
	}

	@Override
	public void readRep(Repository rep, ObjectId id_step,
			List<DatabaseMeta> databases, Map<String, Counter> counters)
			throws KettleException {
		try {
			OutputFileName = rep.getStepAttributeString(id_step, "OutputFileName"); //$NON-NLS-1$
			
		} catch (Exception e) {
			throw new KettleException("Unable to load step from repository", e);
		}
	}

	
	
	

	@Override
	public void check(List<CheckResultInterface> remarks, TransMeta transmeta,
			StepMeta stepMeta, RowMetaInterface prev, String input[],
			String output[], RowMetaInterface info) {

		CheckResult cr;

		// See if there are input streams leading to this step!
		if (input.length > 0) {
			cr = new CheckResult(CheckResultInterface.TYPE_RESULT_OK,
					BaseMessages.getString(PKG,"PDFOutput.CheckResult.ReceivingRows.OK"), stepMeta);
			remarks.add(cr);
		} else {
			cr = new CheckResult(CheckResultInterface.TYPE_RESULT_ERROR,
					BaseMessages.getString(PKG,"PDFOutput.CheckResult.ReceivingRows.ERROR"), stepMeta);
			remarks.add(cr);
		}

	}
	
	
	
	/*
	 * Pile up getters and setters.
	 */
	
	public String getOutputFileName() {
		return OutputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		OutputFileName = outputFileName;
	}

	public String[] getKeyField() {
		return KeyField;
	}

	public void setKeyField(String[] keyField) {
		KeyField = keyField;
	}

	public String[] getValueField() {
		return ValueField;
	}

	public void setValueField(String[] valueField) {
		ValueField = valueField;
	}

	public String[] getType() {
		return Type;
	}

	public void setType(String[] type) {
		Type = type;
	}

	public String[] getFormat() {
		return Format;
	}

	public void setFormat(String[] format) {
		Format = format;
	}

	/*public String[] getLength() {
		return Length;
	}

	public void setLength(String[] length) {
		Length = length;
	}

	public String[] getPrecision() {
		return Precision;
	}

	public void setPrecision(String[] precision) {
		Precision = precision;
	}

	public String[] getCurrency() {
		return Currency;
	}

	public void setCurrency(String[] currency) {
		Currency = currency;
	}

	public String[] getDecimal() {
		return Decimal;
	}

	public void setDecimal(String[] decimal) {
		Decimal = decimal;
	}

	public String[] getGroup() {
		return Group;
	}

	public void setGroup(String[] group) {
		Group = group;
	}

	public String[] getDefaultField() {
		return DefaultField;
	}

	public void setDefaultField(String[] defaultField) {
		DefaultField = defaultField;
	}
*/
	
	

}
